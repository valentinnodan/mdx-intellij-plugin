package org.intellij.plugin.mdx.lang.psi

import com.intellij.lang.Language
import com.intellij.lexer.Lexer
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiFile
import com.intellij.psi.templateLanguages.TemplateDataElementType
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider
import com.intellij.psi.tree.IElementType
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.accept
import org.intellij.markdown.ast.visitors.RecursiveVisitor
import org.intellij.markdown.parser.MarkdownParser
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.markdown.IElementType as MarkdownIElementType

val INNER_ELEMENT = IElementType("INNER", MdxLanguage.INSTANCE)
val OUTER_ELEMENT = IElementType("OUTER", MdxLanguage.INSTANCE)


class MdxTemplate(private val templateLanguage: Language)
    : TemplateDataElementType(
        "MDX_TEMPLATE_${templateLanguage.displayName}",
        MdxLanguage.INSTANCE,
        INNER_ELEMENT,
        OUTER_ELEMENT) {

    override fun getTemplateFileLanguage(viewProvider: TemplateLanguageFileViewProvider?): Language {
        return templateLanguage
    }

    override fun createTemplateFile(psiFile: PsiFile,
                                    templateLanguage: Language,
                                    sourceCode: CharSequence,
                                    viewProvider: TemplateLanguageFileViewProvider?,
                                    rangeCollector: RangeCollector): PsiFile {
        val templateSourceCode = StringBuilder()
        val innerRanges = mutableListOf<TextRange>()
        val root = MarkdownParser(MdxFlavourDescriptor).parse(MarkdownIElementType("ROOT"), sourceCode.toString())

        var depth = 0
        root.accept(object: RecursiveVisitor() {
            override fun visitNode(node: ASTNode) {
//                if (node.type == MarkdownElementTypes.HTML_BLOCK && depth == 1) {
//                    val children = node.children
//                    children.indexOfFirst { it.type == MarkdownTokenTypes.FENCE_LANG}.takeIf { it != -1 }?.let { languageIndex ->
//                        if (getLanguage(children[languageIndex], sourceCode) != templateLanguage.id.toLowerCase()) return@let
//                        if (languageIndex + 3 >= children.size) return@let
//                        val range = TextRange(children[languageIndex + 1].startOffset, children[children.size - 2].endOffset)
//                        innerRanges.add(range)
//                        templateSourceCode.append(sourceCode.subSequence(range.startOffset, range.endOffset))
//                    }
//                }
                depth++
                super.visitNode(node)
                depth--
            }
        })
        var lhs = 0
        innerRanges.forEach { textRange ->
            if (lhs < textRange.startOffset) {
                rangeCollector.addOuterRange(TextRange(lhs, textRange.startOffset))
            }
            lhs = textRange.endOffset
        }
        if (lhs < sourceCode.length) {
            rangeCollector.addOuterRange(TextRange(lhs, sourceCode.length))
        }

        return createPsiFileFromSource(templateLanguage, templateSourceCode, psiFile.manager)
    }

    override fun createTemplateText(sourceCode: CharSequence, baseLexer: Lexer, rangeCollector: RangeCollector): CharSequence {
        throw NotImplementedError("This method shouldn't be called")
    }
}

//fun getLanguage(fenceLanguage: ASTNode, sourceCode: CharSequence): String? {
//    val text = sourceCode.substring(fenceLanguage.startOffset, fenceLanguage.endOffset).toLowerCase()
//    return RMarkdownPsiUtil.getExecutableFenceLanguage(text)
//}
