// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.intellij.plugin.mdx.lang.psi

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider
import com.intellij.psi.tree.IElementType
import gnu.trove.THashSet
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugins.markdown.lang.parser.MarkdownParserManager


class MdxFileViewProvider(manager: PsiManager, virtualFile: VirtualFile, eventSystemEnabled: Boolean)
    : MultiplePsiFilesPerDocumentFileViewProvider(manager, virtualFile, eventSystemEnabled), TemplateLanguageFileViewProvider {

    private val myRelevantLanguages = THashSet<Language>()

    init {
        myRelevantLanguages.add(baseLanguage)
        myRelevantLanguages.add(templateDataLanguage)
    }

    override fun createFile(lang: Language): PsiFile? {
        if (lang === MdxLanguage.INSTANCE) {
            return super.createFile(lang)?.apply {
                putUserData(MarkdownParserManager.FLAVOUR_DESCRIPTION, MdxFlavourDescriptor)
            }
        }

        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang) ?: return null

        val psiFile = parserDefinition.createFile(this)
        if (lang === templateDataLanguage && psiFile is PsiFileImpl) {
            val debugName = lang.displayName.toUpperCase().replace(' ', '_')
            val mdxTemplate =
                    templateDataElementType(debugName)
            psiFile.contentElementType = mdxTemplate
        }

        return psiFile
    }

    private fun templateDataElementType(debugName: String): TemplateDataElementType {
        return MdxTemplateDataElementType
    }

    override fun getBaseLanguage(): Language = MdxLanguage.INSTANCE

    override fun getLanguages(): Set<Language> = myRelevantLanguages

    override fun getTemplateDataLanguage(): Language = com.intellij.lang.javascript.JavaScriptSupportLoader.JSX_HARMONY

    override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider =
            MdxFileViewProvider(manager, fileCopy, false)
}

class MdxFileViewProviderFactory : FileViewProviderFactory {
    override fun createFileViewProvider(file: VirtualFile,
                                        language: Language,
                                        manager: PsiManager,
                                        eventSystemEnabled: Boolean): FileViewProvider =
            MdxFileViewProvider(manager, file, eventSystemEnabled)
}