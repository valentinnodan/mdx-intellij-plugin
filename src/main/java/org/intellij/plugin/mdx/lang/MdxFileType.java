package org.intellij.plugin.mdx.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.intellij.plugin.mdx.ide.MDXIcons;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MdxFileType extends LanguageFileType{
    public static final LanguageFileType INSTANCE = new MdxFileType();
    @NonNls
    public static final String DEFAULT_EXTENSION = "mdx";

    private MdxFileType() {
        super(MdxLanguage.INSTANCE);
    }

    @Override
    public @NotNull String getName() {
        return "MDX File";
    }

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Sentence) String getDescription() {
        return "MDX file";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Override
    public @Nullable Icon getIcon() {
        return MDXIcons.FILE;
    }
}
