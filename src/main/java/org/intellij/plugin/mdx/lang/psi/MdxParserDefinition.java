package org.intellij.plugin.mdx.lang.psi;

import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IStubFileElementType;
import org.intellij.plugin.mdx.lang.MdxLanguage;
import org.intellij.plugins.markdown.lang.parser.MarkdownParserAdapter;
import org.intellij.plugins.markdown.lang.parser.MarkdownParserDefinition;
import org.jetbrains.annotations.NotNull;

public class MdxParserDefinition extends MarkdownParserDefinition {
    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return new IStubFileElementType<PsiFileStub<PsiFile>>("MDX", MdxLanguage.INSTANCE);
    }

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new MdxLexer();
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new MarkdownParserAdapter(new MdxFlavourDescriptor());
    }
}
