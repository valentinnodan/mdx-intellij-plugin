package org.intellij.plugin.mdx.lang.psi;

import com.intellij.psi.tree.IElementType;
import org.intellij.plugin.mdx.lang.MdxLanguage;
import org.jetbrains.annotations.NotNull;

public class MdxTokenType extends IElementType {
    public MdxTokenType(@NotNull String debugName) {
        super(debugName, MdxLanguage.INSTANCE);
    }
}
