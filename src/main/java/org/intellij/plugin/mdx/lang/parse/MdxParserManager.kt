package org.intellij.plugin.mdx.lang.parse

import com.sun.istack.NotNull
import org.intellij.markdown.ast.CompositeASTNode
import org.intellij.plugins.markdown.lang.parser.MarkdownParserManager

class MdxParserManager : MarkdownParserManager() {
    companion object {
        fun parseContentMdx(@NotNull buffer: CharSequence): org.intellij.markdown.ast.ASTNode {
            val node = parseContent(buffer, MdxFlavourDescriptor)
            // ?? the same constructor with node
            val newASTNode = CompositeASTNode(MdxElementTypes.MDX_FILE, node.children)
            return newASTNode;
        }
    }
}