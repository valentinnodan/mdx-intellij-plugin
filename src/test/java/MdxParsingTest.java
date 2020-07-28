
import com.intellij.testFramework.ParsingTestCase;
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
