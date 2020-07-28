package org.intellij.plugin.mdx.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import org.intellij.plugin.mdx.lang.parse.MdxFlavourDescriptor
import org.intellij.plugin.mdx.lang.parse.MdxTokenTypes
import org.intellij.plugins.markdown.highlighting.MarkdownHighlighterColors
import org.intellij.plugins.markdown.highlighting.MarkdownSyntaxHighlighter
import org.intellij.plugins.markdown.lang.MarkdownElementType
import org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer

class MdxSyntaxHighlighter : MarkdownSyntaxHighlighter() {
    override fun getHighlightingLexer(): Lexer {
        return MarkdownToplevelLexer(MdxFlavourDescriptor)
    }
    companion object {
        init {
            SyntaxHighlighterBase.safeMap(
                    ATTRIBUTES,
                    MarkdownElementType.platformType(MdxTokenTypes.JSX_BLOCK_CONTENT),
                    MarkdownHighlighterColors.HTML_BLOCK_ATTR_KEY)
        }
    }
}