package org.intellij.plugin.mdx.lang.psi

import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugins.markdown.lang.MarkdownElementType

object MdxTemplateDataElementType : TemplateDataElementType("MDX_TEMPLATE_JSX",
        MdxLanguage.INSTANCE,
        MarkdownElementType.platformType(MdxTokenTypes.JSX_BLOCK_CONTENT),
        IElementType("OUTER_BLOCK", MdxLanguage.INSTANCE)) {

    override fun getTemplateDataInsertionTokens(): TokenSet {
        return TokenSet.forAllMatching(IElementType.TRUE)
    }
}