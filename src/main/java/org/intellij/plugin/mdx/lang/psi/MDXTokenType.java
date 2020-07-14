package org.intellij.plugin.mdx.lang.psi;

import com.intellij.psi.tree.IElementType;
import org.intellij.plugin.mdx.lang.MDXLanguage;
import org.jetbrains.annotations.NotNull;

public class MDXTokenType extends IElementType {
    public MDXTokenType(@NotNull String debugName) {
        super(debugName, MDXLanguage.INSTANCE);
    }
}
