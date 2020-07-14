package org.intellij.plugin.mdx.lang;

import com.intellij.lang.Language;

public class MDXLanguage extends Language {
    private static final String ID = "MDX";
    public static final MDXLanguage INSTANCE = new MDXLanguage();

    private MDXLanguage() {
        super(ID);
    }
}
