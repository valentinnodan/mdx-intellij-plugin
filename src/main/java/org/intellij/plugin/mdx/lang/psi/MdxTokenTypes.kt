package org.intellij.plugin.mdx.lang.psi

import org.intellij.markdown.IElementType
import org.intellij.plugins.markdown.lang.MarkdownTokenTypes

class MdxTokenTypes: MarkdownTokenTypes {
    companion object {
        @JvmField val JSX_BLOCK_CONTENT: IElementType = MdxElementType("JSX_BLOCK_CONTENT")
    }
}