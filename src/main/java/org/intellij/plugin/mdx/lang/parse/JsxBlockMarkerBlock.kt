package org.intellij.plugin.mdx.lang.parse

import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.parser.LookaheadText
import org.intellij.markdown.parser.ProductionHolder
import org.intellij.markdown.parser.constraints.MarkdownConstraints
import org.intellij.markdown.parser.markerblocks.MarkdownParserUtil
import org.intellij.markdown.parser.markerblocks.MarkerBlock
import org.intellij.markdown.parser.markerblocks.MarkerBlockImpl
import org.intellij.markdown.parser.sequentialparsers.SequentialParser
import kotlin.text.Regex

class JsxBlockMarkerBlock(myConstraints: MarkdownConstraints,
                           private val productionHolder: ProductionHolder,
                           private val endCheckingRegex: Regex?,
                           startPosition: LookaheadText.Position,
                           private val isExportImportMultiline: Boolean)
    : MarkerBlockImpl(myConstraints, productionHolder.mark()) {
    init {
        if (!isExportImportMultiline) {
            productionHolder.addProduction(listOf(SequentialParser.Node(
                    startPosition.offset..startPosition.nextLineOrEofOffset, MdxTokenTypes.JSX_BLOCK_CONTENT)))
        }
    }

    private var realInterestingOffset = -1

    override fun allowsSubBlocks(): Boolean = false

    override fun isInterestingOffset(pos: LookaheadText.Position): Boolean = true

    override fun getDefaultAction(): MarkerBlock.ClosingAction {
        return MarkerBlock.ClosingAction.DONE
    }

    override fun doProcessToken(pos: LookaheadText.Position, currentConstraints: MarkdownConstraints): MarkerBlock.ProcessingResult {
        if (!isExportImportMultiline) {
            if (pos.offsetInCurrentLine != -1) {
                return MarkerBlock.ProcessingResult.CANCEL
            }

            val prevLine = pos.prevLine ?: return MarkerBlock.ProcessingResult.DEFAULT
            if (!MarkdownConstraints.fillFromPrevious(pos, constraints).extendsPrev(constraints)) {
                return MarkerBlock.ProcessingResult.DEFAULT
            }

            if (endCheckingRegex == null && MarkdownParserUtil.calcNumberOfConsequentEols(pos, constraints) >= 2) {
                return MarkerBlock.ProcessingResult.DEFAULT
            } else if (endCheckingRegex != null && endCheckingRegex.find(prevLine) != null) {
                return MarkerBlock.ProcessingResult.DEFAULT
            }

            if (pos.currentLine.isNotEmpty()) {
                productionHolder.addProduction(listOf(SequentialParser.Node(
                        pos.offset + 1 + constraints.getCharsEaten(pos.currentLine)..pos.nextLineOrEofOffset,
                        MdxTokenTypes.JSX_BLOCK_CONTENT)))
            }

        } else {
            if (pos.offset < realInterestingOffset) {
                return MarkerBlock.ProcessingResult.CANCEL
            }
            // Eat everything if we're on code line
            if (pos.char != '\n') {
                return MarkerBlock.ProcessingResult.CANCEL
            }

            assert(pos.char == '\n')

            val nextLineConstraints = MarkdownConstraints.fromBase(pos, constraints)
            if (!nextLineConstraints.extendsPrev(constraints)) {
                return MarkerBlock.ProcessingResult.DEFAULT
            }

            val nextLineOffset = pos.nextLineOrEofOffset
            realInterestingOffset = nextLineOffset

            val currentLine = pos.currentLine.subSequence(nextLineConstraints.getIndent(), pos.currentLine.length)
            if (endsThisBlock(currentLine)) {
                productionHolder.addProduction(listOf(SequentialParser.Node(pos.offset + 1..pos.nextLineOrEofOffset,
                        MdxTokenTypes.JSX_BLOCK_CONTENT)))
                scheduleProcessingResult(nextLineOffset, MarkerBlock.ProcessingResult.DEFAULT)
            } else {
                val contentRange = Math.min(pos.offset + 1 + constraints.getIndent(), nextLineOffset)..nextLineOffset
                if (contentRange.start < contentRange.endInclusive) {
                    productionHolder.addProduction(listOf(SequentialParser.Node(
                            contentRange, MdxTokenTypes.JSX_BLOCK_CONTENT)))
                }
            }
        }
        return MarkerBlock.ProcessingResult.CANCEL
    }

    private fun endsThisBlock(line: CharSequence): Boolean {
        return endCheckingRegex!!.matches(line)
    }

    override fun calcNextInterestingOffset(pos: LookaheadText.Position): Int {
        return pos.offset + 1
    }

    override fun getDefaultNodeType(): IElementType {
        return MdxElementTypes.JSX_BLOCK
    }
}