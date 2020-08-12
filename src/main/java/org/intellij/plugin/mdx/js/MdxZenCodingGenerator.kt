package org.intellij.plugin.mdx.js

import com.intellij.codeInsight.template.CustomTemplateCallback
import com.intellij.lang.javascript.frameworks.react.JSXZenCodingGenerator
import org.intellij.plugin.mdx.lang.psi.MdxFile

class MdxZenCodingGenerator: JSXZenCodingGenerator() {
    override fun isMyContext(callback: CustomTemplateCallback, wrapping: Boolean): Boolean {
        val context = callback.context
        if (context.containingFile !is MdxFile) {
            return false
        }

        return true
    }


}