package org.intellij.plugin.mdx.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.intellij.plugins.markdown.lang.psi.MarkdownPsiFactory;
import org.jetbrains.annotations.NotNull;

public enum MdxPsiFactory {
    INSTANCE;

    public PsiElement createElement(@NotNull ASTNode node) {
        final IElementType elementType = node.getElementType();
        if (elementType.equals(MdxElementTypes.JSX_BLOCK)) {
            return new MdxJsxBlockImpl(node);
        }
        return MarkdownPsiFactory.INSTANCE.createElement(node);
    }
}
