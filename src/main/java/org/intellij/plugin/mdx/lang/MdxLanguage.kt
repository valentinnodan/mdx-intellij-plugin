package org.intellij.plugin.mdx.lang

import com.intellij.lang.Language
import com.intellij.psi.templateLanguages.TemplateLanguage
import org.intellij.plugins.markdown.lang.MarkdownLanguage

object MdxLanguage : Language(MarkdownLanguage.INSTANCE,"MDX"), TemplateLanguage
