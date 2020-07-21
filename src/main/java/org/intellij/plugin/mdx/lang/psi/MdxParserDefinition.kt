package org.intellij.plugin.mdx.lang.psi

import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.IStubFileElementType
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugins.markdown.lang.lexer.MarkdownToplevelLexer
import org.intellij.plugins.markdown.lang.parser.MarkdownParserAdapter
import org.intellij.plugins.markdown.lang.parser.MarkdownParserDefinition

class MdxParserDefinition : MarkdownParserDefinition() {
    override fun getFileNodeType(): IFileElementType {
        return IStubFileElementType<PsiFileStub<PsiFile>>("MDX", MdxLanguage.INSTANCE)
    }

    override fun createLexer(project: Project): Lexer {
        return MarkdownToplevelLexer()
    }

    override fun createParser(project: Project): PsiParser {
        return MarkdownParserAdapter(MdxFlavourDescriptor)
    }
}
//java.lang.LinkageError: loader constraint violation: when
//resolving method 'void org.intellij.markdown.parser.markerblocks.impl.HtmlBlockMarkerBlock.<init>
//(org.intellij.markdown.parser.constraints.MarkdownConstraints, org.intellij.markdown.parser.ProductionHolder,
//kotlin.text.Regex, org.intellij.markdown.parser.LookaheadText$Position)'
//the class loader com.intellij.ide.plugins.cl.PluginClassLoader @ 148094e6 of the current class,
//org/intellij/plugin/mdx/lang/psi/JsxBlockProvider, and the class loader com.intellij.ide.plugins.cl.PluginClassLoader @ 650745f4
//for the method's defining class, org/intellij/markdown/parser/markerblocks/impl/HtmlBlockMarkerBlock,
//have different Class objects for the type kotlin/text/Regex used in the signature (org.intellij.plugin.mdx.lang.psi.JsxBlockProvider
//is in unnamed module of loader com.intellij.ide.plugins.cl.PluginClassLoader @148094e6, parent loader 'bootstrap';
//org.intellij.markdown.parser.markerblocks.impl.HtmlBlockMarkerBlock is in unnamed module of
//loader com.intellij.ide.plugins.cl.PluginClassLoader @650745f4, parent loader 'bootstrap')
//at org.intellij.plugin.mdx.lang.psi.JsxBlockProvider.createMarkerBlocks(JsxBlockProvider.kt:16)
//at org.intellij.markdown.parser.MarkerProcessor.createNewMarkerBlocks(MarkerProcessor.kt: