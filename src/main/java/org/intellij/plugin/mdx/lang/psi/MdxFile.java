package org.intellij.plugin.mdx.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.intellij.plugin.mdx.lang.MdxFileType;
import org.intellij.plugin.mdx.lang.MdxLanguage;
import org.jetbrains.annotations.NotNull;

public class MdxFile extends PsiFileBase {
    protected MdxFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, MdxLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return MdxFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "MDX File";
    }
}
