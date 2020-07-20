package org.intellij.plugin.mdx.lang.psi

import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementType
import org.intellij.plugins.markdown.lang.MarkdownElementTypes

class MdxElementTypes : MarkdownElementTypes {
    companion object {
        @JvmField val JSX_BLOCK: IElementType = MarkdownElementType("JSX_BLOCK")
    }
}