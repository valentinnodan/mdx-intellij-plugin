package org.intellij.plugin.mdx.lang.parse

import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementType
import org.intellij.plugin.mdx.lang.MdxLanguage

class MdxElementTypes {
    companion object {
        @JvmField
        val MDX_FILE: IElementType = MarkdownElementType("MDX_FILE")

        @JvmField
        val JSX_BLOCK: IElementType = MarkdownElementType("JSX_BLOCK")
    }
}