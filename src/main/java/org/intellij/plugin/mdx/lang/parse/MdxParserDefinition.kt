package org.intellij.plugin.mdx.lang.parse

import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.IStubFileElementType
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugin.mdx.lang.psi.MdxFile
<<<<<<< HEAD:src/main/java/org/intellij/plugin/mdx/lang/parse/MdxParserDefinition.kt
import org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer
import org.intellij.plugins.markdown.lang.parser.MarkdownParserAdapter
=======
>>>>>>> d7099813e19fca5928e5e4f3fa8da4e22aa09b8d:src/main/java/org/intellij/plugin/mdx/lang/psi/MdxParserDefinition.kt
import org.intellij.plugins.markdown.lang.parser.MarkdownParserDefinition


class MdxParserDefinition : MarkdownParserDefinition() {
    override fun getFileNodeType(): IFileElementType {
        return IStubFileElementType<PsiFileStub<PsiFile>>("MDX", MdxLanguage.INSTANCE)
    }

    override fun createLexer(project: Project): Lexer {
        return MdxLexer()
    }

    override fun createParser(project: Project): PsiParser {
        return MdxParser()
    }

    override fun createFile(viewProvider: FileViewProvider?): PsiFile {
        return MdxFile(viewProvider)
    }
<<<<<<< HEAD:src/main/java/org/intellij/plugin/mdx/lang/parse/MdxParserDefinition.kt

=======
>>>>>>> d7099813e19fca5928e5e4f3fa8da4e22aa09b8d:src/main/java/org/intellij/plugin/mdx/lang/psi/MdxParserDefinition.kt
}
