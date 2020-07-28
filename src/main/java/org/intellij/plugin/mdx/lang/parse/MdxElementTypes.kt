package org.intellij.plugin.mdx.lang.parse

import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementType

class MdxElementTypes {
    companion object {
        @JvmField val JSX_BLOCK: IElementType = MarkdownElementType("JSX_BLOCK")
        @JvmField val MDX_FILE: IElementType = MarkdownElementType("MDX_FILE")
    }
}