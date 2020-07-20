package org.intellij.plugin.mdx.lang;

import com.intellij.lang.Language;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import org.intellij.plugins.markdown.lang.MarkdownLanguage;

public class MdxLanguage extends Language implements TemplateLanguage {
    private static final String ID = "MDX";
    public static final MdxLanguage INSTANCE = new MdxLanguage();

    private MdxLanguage() {
        super(MarkdownLanguage.INSTANCE, ID);
    }
}
