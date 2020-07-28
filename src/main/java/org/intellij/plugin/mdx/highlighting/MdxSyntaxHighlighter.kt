package org.intellij.plugin.mdx.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import org.intellij.plugin.mdx.lang.parse.MdxLexer
import org.intellij.plugin.mdx.lang.parse.MdxTokenTypes
import org.intellij.plugins.markdown.highlighting.MarkdownHighlighterColors
import org.intellij.plugins.markdown.highlighting.MarkdownSyntaxHighlighter
import org.intellij.plugins.markdown.lang.MarkdownElementType

class MdxSyntaxHighlighter : MarkdownSyntaxHighlighter() {
    override fun getHighlightingLexer(): Lexer {
        return MdxLexer()
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