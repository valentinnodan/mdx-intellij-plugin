import com.intellij.codeInspection.htmlInspections.HtmlUnknownAttributeInspection;
import com.intellij.codeInspection.htmlInspections.HtmlUnknownBooleanAttributeInspection;
import com.intellij.codeInspection.htmlInspections.HtmlUnknownTagInspection;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.sixrr.inspectjs.validity.BadExpressionStatementJSInspection;
import com.sixrr.inspectjs.validity.ThisExpressionReferencesGlobalObjectJSInspection;

abstract class MdxTestBase extends BasePlatformTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        myFixture.enableInspections(new HtmlUnknownTagInspection(),
        new HtmlUnknownAttributeInspection(),
        new BadExpressionStatementJSInspection(),
        new ThisExpressionReferencesGlobalObjectJSInspection(),
        new HtmlUnknownBooleanAttributeInspection()
        );
        }
}