package org.intellij.plugin.mdx.codeInspection

import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.lang.javascript.inspections.JSXNamespaceValidationInspection
import com.intellij.psi.PsiElement

class MdxInspectionSuppressor : InspectionSuppressor {
    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
        return emptyArray()
    }

    override fun isSuppressedFor(element: PsiElement, name: String): Boolean {
        println(name)
        if (name.equals("JSXNamespaceValidation")) {
            return true
        }
        return false
    }
}
