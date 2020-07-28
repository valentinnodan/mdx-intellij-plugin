package org.intellij.plugin.mdx.lang.parse

import com.intellij.psi.tree.IElementType
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.accept
import org.intellij.markdown.ast.visitors.RecursiveVisitor
import org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer
import java.util.*

/**
 * It is a copy-paste, only MarkdownParserManager changed on MdxParserManager
 *
 * @see org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer
 */

class MdxLexer : MarkdownToplevelLexer(MdxFlavourDescriptor) {
    private var myBuffer: CharSequence? = null
    private var myBufferStart = 0
    private var myBufferEnd = 0

    private var myLexemes: MutableList<IElementType>? = null
    private var myStartOffsets: MutableList<Int>? = null
    private var myEndOffsets: MutableList<Int>? = null

    private var myLexemeIndex = 0
    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        myBuffer = buffer
        myBufferStart = startOffset
        myBufferEnd = endOffset
        val parsedTree = MdxParserManager.parseContentMdx(buffer.subSequence(startOffset, endOffset))
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
                myLexemes!!.add(MdxElementType.platformType(node.type))
                myStartOffsets!!.add(node.startOffset)
                myEndOffsets!!.add(node.endOffset)
            } else {
                super.visitNode(node)
            }
        }
    }
}
