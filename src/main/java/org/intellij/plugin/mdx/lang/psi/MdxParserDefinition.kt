package org.intellij.plugin.mdx.lang.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.IStubFileElementType
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer
import org.intellij.plugins.markdown.lang.parser.MarkdownParserAdapter
import org.intellij.plugins.markdown.lang.parser.MarkdownParserDefinition
import org.intellij.plugins.markdown.lang.stubs.MarkdownStubElementType


class MdxParserDefinition : MarkdownParserDefinition() {
    override fun getFileNodeType(): IFileElementType {
        return IStubFileElementType<PsiFileStub<PsiFile>>("MDX", MdxLanguage.INSTANCE)
    }

    override fun createLexer(project: Project): Lexer {
        return MarkdownToplevelLexer(MdxFlavourDescriptor)
    }

    override fun createParser(project: Project): PsiParser {
        return MarkdownParserAdapter(MdxFlavourDescriptor)
    }

    override fun createFile(viewProvider: FileViewProvider?): PsiFile {
        return MdxFile(viewProvider)
    }

    override fun createElement(node: ASTNode): PsiElement {
        val type = node.elementType
        return if (type is MarkdownStubElementType<*, *>) type.createElement(node) else MdxPsiFactory.INSTANCE.createElement(node)
    }
}

