package org.intellij.plugin.mdx.js

import com.intellij.javascript.JSModuleBaseReference
import com.intellij.lang.javascript.DialectDetector
import com.intellij.lang.javascript.frameworks.amd.JSModuleReference
import com.intellij.lang.javascript.frameworks.modules.JSBaseModuleReferenceContributor
import com.intellij.lang.javascript.frameworks.modules.JSModuleFileReferenceSet
import com.intellij.lang.javascript.psi.resolve.JSModuleReferenceContributor
import com.intellij.openapi.util.RecursionManager
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReference
import org.jetbrains.annotations.NotNull
import java.util.*


class MdxModuleReferenceContributor : JSBaseModuleReferenceContributor() {
    override fun isApplicable(host: PsiElement): Boolean {
        return !DialectDetector.isTypeScript(host)
    }

    override fun getReferences(unquotedRefText: String,
                               host: PsiElement,
                               offset: Int,
                               provider: PsiReferenceProvider?,
                               isCommonJS: Boolean): Array<out @NotNull FileReference> {
        if (!Regex(".*\\.mdx$").matches(unquotedRefText)) {
            return emptyArray()
        }
        val path = JSModuleReferenceContributor.getActualPath(unquotedRefText)
        val modulePath = path.second
        val resourcePathStartInd = path.first
        val index = resourcePathStartInd + offset
        val isSoft = isSoft(host, modulePath, isCommonJS)
        return getReferences(host, provider, modulePath, index, isSoft, null)

    }

    protected fun getReferences(host: PsiElement,
                                provider: PsiReferenceProvider?,
                                modulePath: String,
                                index: Int,
                                isSoft: Boolean,
                                templateName: String?): Array<out @NotNull FileReference> {

        return object : JSModuleFileReferenceSet(modulePath, host, index, provider, templateName) {
            override fun isSoft(): Boolean {
                return isSoft
            }

            override fun createFileReference(textRange: TextRange?, i: Int, text: String?): FileReference? {
                if (!Regex(".*$text$").matches(modulePath) || !Regex(".*\\.mdx$").matches(text!!)) {
                    return super.createFileReference(textRange, i, text)
                }

                return object : JSModuleReference(text, i, textRange!!, this, templateName, isSoft) {
                    private fun getFile(): PsiFile? {
                        val contexts = RecursionManager.doPreventingRecursion<Collection<PsiFileSystemItem>>(this, false) { getContexts() }
                                ?: //                            this.LOG.error("Recursion occurred for " + javaClass + " on " + element.text)
                                return null
                        for (context in contexts) {
                            if (context.isDirectory && context.virtualFile.findFileByRelativePath(text) != null) {
                                val virtualFile = context.virtualFile.findFileByRelativePath(text)
                                val jsPsi = context.manager.findFile(virtualFile!!)?.viewProvider?.getPsi(MdxJSLanguage.INSTANCE)
                                return jsPsi
                            }
                        }

                        return null
                    }

                    override fun innerResolve(caseSensitive: Boolean, containingFile: PsiFile): Array<ResolveResult> {
                        val myFile = getFile()
                        if (myFile == null) {
                            return super.innerResolve(caseSensitive, containingFile)
                        }
                        val result = LinkedHashSet<ResolveResult>()
                        result.add(PsiElementResolveResult((myFile as PsiFileSystemItem)))
                        return result.toTypedArray()

                    }
                }
            }
        }.allReferences
    }

}
