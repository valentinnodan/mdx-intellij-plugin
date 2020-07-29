package org.intellij.plugin.mdx.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import org.intellij.plugin.mdx.lang.parse.MdxFlavourDescriptor
import org.intellij.plugin.mdx.lang.parse.MdxTokenTypes
import org.intellij.plugins.markdown.highlighting.MarkdownSyntaxHighlighter
import org.intellij.plugins.markdown.lang.MarkdownElementType
import org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer


class MdxSyntaxHighlighter : MarkdownSyntaxHighlighter() {
    private val myHighlightingLexer = MdxHighlightingLexer()

    override fun getHighlightingLexer(): Lexer {
        return myHighlightingLexer
    }
}