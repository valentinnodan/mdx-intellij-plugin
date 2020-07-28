<<<<<<< HEAD
import com.intellij.lang.Language;
import com.intellij.mock.MockProjectEx;
import com.intellij.mock.MockPsiManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.testFramework.ParsingTestCase;
import org.intellij.plugin.mdx.lang.MdxLanguage;
=======
import com.intellij.testFramework.ParsingTestCase;
>>>>>>> d7099813e19fca5928e5e4f3fa8da4e22aa09b8d
import org.intellij.plugin.mdx.lang.parse.MdxParserDefinition;

public class MdxParsingTest extends ParsingTestCase {
    public MdxParsingTest() {
        super("", "mdx", new MdxParserDefinition());
    }

    public void testParsingTestData() {
        doTest(true);
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/testData";
    }

    @Override
    protected boolean skipSpaces() {
        return false;
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}
