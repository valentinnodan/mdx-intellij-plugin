package org.intellij.plugin.mdx.highlighting

import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import org.intellij.plugins.markdown.highlighting.MarkdownColorSettingsPage
import org.jetbrains.annotations.NotNull

class MdxColorSettingsPage : MarkdownColorSettingsPage() {
    private val DESCRIPTORS = arrayOf(
            AttributesDescriptor("JSX Block", MdxHighlighterColors.JSX_CODE_HIGHLIGHT)
    )

    override fun getHighlighter(): SyntaxHighlighter {
        return MdxSyntaxHighlighter()
    }

    override fun getDisplayName(): String {
        return "MDX"
    }

    override fun getDemoText(): String {
        return "import React from \'react\';\n\n" +
                "#hello\n\n"+
                "<Button>Press the button</Button>"
    }

    @NotNull
    override fun getAttributeDescriptors(): Array<@NotNull AttributesDescriptor> {
        val mdDescriptors = super.getAttributeDescriptors().clone()
        return mdDescriptors.plus(DESCRIPTORS)
    }
}