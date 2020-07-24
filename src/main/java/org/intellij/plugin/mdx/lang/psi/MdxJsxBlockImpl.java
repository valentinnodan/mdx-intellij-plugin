package org.intellij.plugin.mdx.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import org.intellij.plugins.markdown.structureView.MarkdownBasePresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MdxJsxBlockImpl extends ASTWrapperPsiElement implements PsiElement {
    public MdxJsxBlockImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public ItemPresentation getPresentation() {
        return new MarkdownBasePresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return "JSX block";
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }
        };
    }
}
