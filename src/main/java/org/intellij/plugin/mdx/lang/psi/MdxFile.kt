package org.intellij.plugin.mdx.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.javascript.psi.JSFile
import com.intellij.lang.javascript.psi.JSSourceElement
import com.intellij.lang.javascript.psi.impl.JSFileBaseImpl
import com.intellij.lang.javascript.psi.impl.JSFileImpl
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.intellij.plugin.mdx.lang.MdxFileType
import org.intellij.plugin.mdx.lang.MdxLanguage
//PsiFileBase(viewProvider!!, MdxLanguage),
class MdxFile(viewProvider: FileViewProvider?) : PsiFileBase(viewProvider!!, MdxLanguage) {
    override fun getFileType(): FileType {
        return MdxFileType.INSTANCE
    }

    override fun toString(): String {
        return "MDX File"
    }
}