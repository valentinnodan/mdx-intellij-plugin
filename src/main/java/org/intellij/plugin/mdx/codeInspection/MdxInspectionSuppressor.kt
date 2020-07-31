//package org.intellij.plugin.mdx.codeInspection
//
//import com.intellij.codeInspection.InspectionSuppressor
//import com.intellij.codeInspection.SuppressQuickFix
//import com.intellij.psi.PsiElement
////import com.intellij.lang.javascript.frameworks.react.
//
//class MdxInspectionSuppressor : InspectionSuppressor {
//    override fun isSuppressedFor(element: PsiElement, name: String): Boolean {
//        return MdxSuppressableInspectionTool.getElementToolSuppressedIn(element, name) != null
//    }
////
//    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<SuppressQuickFix> {
//        return MdxSuppressableInspectionTool.getSuppressActions(toolId)
//    }
//}