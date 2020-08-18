package org.intellij.plugin.mdx.lang.psi

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.xml.XMLLanguage
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.*
import com.intellij.psi.impl.source.PsiFileImpl
import com.intellij.psi.templateLanguages.OuterLanguageElement
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider
import com.intellij.util.ReflectionUtil
import com.intellij.xml.util.HtmlUtil
import org.intellij.plugin.mdx.js.MdxJSLanguage
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugin.mdx.lang.parse.MdxFlavourDescriptor
import org.intellij.plugins.markdown.lang.parser.MarkdownParserManager


class MdxFileViewProvider(manager: PsiManager, virtualFile: VirtualFile, eventSystemEnabled: Boolean)
    : MultiplePsiFilesPerDocumentFileViewProvider(manager, virtualFile, eventSystemEnabled), TemplateLanguageFileViewProvider {

    private val myRelevantLanguages = mutableSetOf(baseLanguage, templateDataLanguage)

    override fun createFile(lang: Language): PsiFile? {
        if (lang === MdxLanguage) {
            return super.createFile(lang)?.apply {
                putUserData(MarkdownParserManager.FLAVOUR_DESCRIPTION, MdxFlavourDescriptor)
            }
        }

        val parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(lang) ?: return null

        val psiFile = parserDefinition.createFile(this)
        if (lang === templateDataLanguage && psiFile is PsiFileImpl) {
            psiFile.contentElementType = MdxTemplateDataElementType
        }

        return psiFile
    }

    override fun getBaseLanguage(): Language = MdxLanguage

    override fun getLanguages(): Set<Language> = myRelevantLanguages

    override fun getTemplateDataLanguage(): Language = MdxJSLanguage.INSTANCE


    override fun cloneInner(fileCopy: VirtualFile): MultiplePsiFilesPerDocumentFileViewProvider =
            MdxFileViewProvider(manager, fileCopy, false)

    override fun findElementAt(offset: Int, lang: Class<out Language?>): PsiElement? {
        val mainRoot = getPsi(baseLanguage)!!
        var ret: PsiElement? = null
        for (language in languages) {
            if (!ReflectionUtil.isAssignable(lang, language.javaClass)){
                if (lang == XMLLanguage.INSTANCE.javaClass){
                        if (!HtmlUtil.supportsXmlTypedHandlers(getPsi(language)!!)){
                            continue
                        }
                } else {
                    continue
                }
            }
            if (lang == Language::class.java && !languages.contains(language)) continue
            val psiRoot = getPsi(language)!!
            val psiElement = AbstractFileViewProvider.findElementAt(psiRoot, offset)
            if (psiElement == null || psiElement is OuterLanguageElement) continue
            if (ret == null || psiRoot !== mainRoot) {
                ret = psiElement
            }
        }
        return ret
    }
}

class MdxFileViewProviderFactory : FileViewProviderFactory {
    override fun createFileViewProvider(file: VirtualFile,
                                        language: Language,
                                        manager: PsiManager,
                                        eventSystemEnabled: Boolean): FileViewProvider =
            MdxFileViewProvider(manager, file, eventSystemEnabled)
}