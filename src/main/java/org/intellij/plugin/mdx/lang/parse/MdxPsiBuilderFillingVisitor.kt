package org.intellij.plugin.mdx.lang.parse

import com.intellij.lang.PsiBuilder
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.ast.LeafASTNode
import org.intellij.markdown.ast.visitors.RecursiveVisitor
import org.intellij.plugins.markdown.lang.parser.PsiBuilderFillingVisitor


class MdxPsiBuilderFillingVisitor(builder: PsiBuilder) : PsiBuilderFillingVisitor(builder) {
    private val myBuilder = builder

    // COPYPASTE OF PsiBuilderFillingVisitor with change of MarkdownElementType to MdxElementType
    override fun visitNode(node: org.intellij.markdown.ast.ASTNode) {
        if (node is LeafASTNode) {
            /* a hack for the link reference definitions:
             * they are being parsed independent from link references and
             * the link titles and urls are tokens instead of composite elements
             */
            val type : org.intellij.markdown.IElementType = node.type
            if (type != MarkdownElementTypes.LINK_LABEL && type != MarkdownElementTypes.LINK_DESTINATION) {
                return;
            }
        }

        while (myBuilder.currentOffset < node.endOffset) {
            myBuilder.advanceLexer();
        }

        if (myBuilder.currentOffset != node.endOffset) {
            throw AssertionError("parsed tree and lexer are unsynchronized");
        }
        val marker : PsiBuilder.Marker= myBuilder.mark();

        RecursiveVisitor().visitNode(node);

        while (myBuilder.currentOffset < node.endOffset) {
            myBuilder.advanceLexer();
        }

        if (myBuilder.getCurrentOffset() != node.endOffset) {
            throw AssertionError("parsed tree and lexer are unsynchronized");
        }
        marker.done(MdxElementType.platformType(node.type));
    }
}