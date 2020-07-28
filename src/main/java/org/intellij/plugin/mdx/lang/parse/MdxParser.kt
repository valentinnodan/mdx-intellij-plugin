package org.intellij.plugin.mdx.lang.parse

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.psi.tree.IElementType
import org.intellij.plugins.markdown.lang.parser.MarkdownParserAdapter

class MdxParser : MarkdownParserAdapter(MdxFlavourDescriptor) {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val rootMarker = builder.mark()

        val parsedTree = MdxParserManager.parseContentMdx(builder.originalText)

        MdxPsiBuilderFillingVisitor(builder).visitNode(parsedTree)
        assert(builder.eof())

        rootMarker.done(root)

        return builder.treeBuilt
    }
}

