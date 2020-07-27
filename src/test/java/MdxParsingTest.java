import com.intellij.lang.Language;
import com.intellij.mock.MockProjectEx;
import com.intellij.mock.MockPsiManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.PsiFileFactoryImpl;
import com.intellij.testFramework.ParsingTestCase;
import org.intellij.plugin.mdx.lang.MdxLanguage;
import org.intellij.plugin.mdx.lang.parse.MdxParserDefinition;

public class MdxParsingTest extends ParsingTestCase {
    private final Language myLanguage = MdxLanguage.INSTANCE;
    private Project myProject = new MockProjectEx(getTestRootDisposable());
    private String text = "<Test>test</Test>";
    private PsiManager myPsiManager = new MockPsiManager(myProject);
    private PsiFileFactory myFileFactory = new PsiFileFactoryImpl(myPsiManager);

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
