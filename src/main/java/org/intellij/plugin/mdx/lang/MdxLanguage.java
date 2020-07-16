package org.intellij.plugin.mdx.lang;

import com.intellij.lang.Language;

public class MdxLanguage extends Language {
    private static final String ID = "MDX";
    public static final MdxLanguage INSTANCE = new MdxLanguage();

    private MdxLanguage() {
        super(ID);
    }
}
