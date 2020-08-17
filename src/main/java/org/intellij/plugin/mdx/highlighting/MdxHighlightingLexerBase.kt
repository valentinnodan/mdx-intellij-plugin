package org.intellij.plugin.mdx.highlighting

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.accept
import org.intellij.markdown.ast.visitors.RecursiveVisitor
import org.intellij.plugin.mdx.lang.parse.MdxFlavourDescriptor
import org.intellij.plugin.mdx.lang.parse.MdxTokenTypes
import org.intellij.plugins.markdown.lang.MarkdownElementType
import org.intellij.plugins.markdown.lang.MarkdownElementTypes
import org.intellij.plugins.markdown.lang.parser.MarkdownParserAdapter
import org.intellij.plugins.markdown.lang.parser.MarkdownParserManager
import java.util.*

class MdxHighlightingLexerBase() : LexerBase() {
    private var myBuffer: CharSequence? = null
    private var myBufferStart = 0
    private var myBufferEnd = 0
    private var myLexemes: MutableList<IElementType>? = null
    private var myStartOffsets: MutableList<Int>? = null
    private var myEndOffsets: MutableList<Int>? = null
    private var myLexemeIndex = 0
    private var lastTokenText:CharSequence? = null

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        myBuffer = buffer
        myBufferStart = startOffset
        myBufferEnd = endOffset
        val parsedTree = MarkdownParserManager.parseContent(buffer.subSequence(startOffset, endOffset), MdxFlavourDescriptor)
        myLexemes = ArrayList()
        myStartOffsets = ArrayList()
        myEndOffsets = ArrayList()
        parsedTree.accept(LexerBuildingVisitor())
        myLexemeIndex = 0
    }

    override fun getState(): Int {
        return myLexemeIndex
    }

    override fun getTokenType(): IElementType? {
        return if (myLexemeIndex >= myLexemes!!.size) {
            null
        } else myLexemes!![myLexemeIndex]
    }

    override fun getTokenStart(): Int {
        return if (myLexemeIndex >= myLexemes!!.size) {
            myBufferEnd
        } else myBufferStart + myStartOffsets!![myLexemeIndex]
    }

    override fun getTokenEnd(): Int {
        return if (myLexemeIndex >= myLexemes!!.size) {
            myBufferEnd
        } else myBufferStart + myEndOffsets!![myLexemeIndex]
    }

    override fun advance() {
        myLexemeIndex++
    }

    override fun getBufferSequence(): CharSequence {
        return myBuffer!!
    }

    override fun getBufferEnd(): Int {
        return myBufferEnd
    }

    private inner class LexerBuildingVisitor : RecursiveVisitor() {
        override fun visitNode(node: ASTNode) {
            if (node.startOffset == node.endOffset) {
                return
            }
            val children = node.children
            if (children.isEmpty()) {
                val subSequence = myBuffer?.subSequence(node.startOffset, node.endOffset)
                if (myLexemes?.isNotEmpty()!! &&
                        myLexemes?.last() == MarkdownElementType.platformType(MdxTokenTypes.JSX_BLOCK_CONTENT) &&
                        node.type == MdxTokenTypes.JSX_BLOCK_CONTENT) {
                    if (!subSequence?.trim().isNullOrEmpty()) {
                        myEndOffsets?.removeAt(myEndOffsets?.lastIndex!!)
                        myEndOffsets?.add(node.endOffset)
                    }
                    else if (lastTokenText?.endsWith("\n") != true){
                        myEndOffsets?.removeAt(myEndOffsets?.lastIndex!!)
                        myEndOffsets?.add(node.endOffset)
                    } else {
                        myLexemes?.add(MarkdownElementType.platformType(node.type))
                        myStartOffsets?.add(node.startOffset)
                        myEndOffsets?.add(node.endOffset)
                    }
                } else {
                    myLexemes?.add(MarkdownElementType.platformType(node.type))
                    myStartOffsets?.add(node.startOffset)
                    myEndOffsets?.add(node.endOffset)
                }
                lastTokenText = subSequence
            } else {
                super.visitNode(node)
            }
        }
    }
}