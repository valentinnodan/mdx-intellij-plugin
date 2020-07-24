package org.intellij.plugin.mdx.lang.psi

import org.intellij.markdown.IElementType
import org.intellij.markdown.MarkdownElementType
import org.intellij.plugins.markdown.lang.MarkdownTokenTypes

class MdxTokenTypes: MarkdownTokenTypes {
    companion object {
        @JvmField val JSX_BLOCK_CONTENT: IElementType = MarkdownElementType("JSX_BLOCK_CONTENT", true)
    }
}