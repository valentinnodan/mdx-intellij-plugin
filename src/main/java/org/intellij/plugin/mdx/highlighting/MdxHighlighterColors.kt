package org.intellij.plugin.mdx.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors.KEYWORD
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.XmlHighlighterColors
import com.intellij.openapi.editor.XmlHighlighterColors.XML_TAG
import com.intellij.openapi.editor.colors.TextAttributesKey
import org.intellij.plugins.markdown.highlighting.MarkdownHighlighterColors

class MdxHighlighterColors : MarkdownHighlighterColors() {
    companion object {
        val JSX_CODE_HIGHLIGHT = TextAttributesKey.createTextAttributesKey("JSX_BLOCK", XML_TAG)
        val TAG_HIGHLIGHT = TextAttributesKey.createTextAttributesKey("JSX_TAG", XmlHighlighterColors.HTML_TAG)
    }
}