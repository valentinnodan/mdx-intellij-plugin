import com.intellij.codeInspection.htmlInspections.HtmlUnknownAttributeInspection
import com.intellij.codeInspection.htmlInspections.HtmlUnknownBooleanAttributeInspection
import com.intellij.codeInspection.htmlInspections.HtmlUnknownTagInspection
import com.intellij.lang.javascript.dialects.ECMA6ParserDefinition
import com.intellij.testFramework.ParsingTestCase
import com.sixrr.inspectjs.validity.BadExpressionStatementJSInspection
import com.sixrr.inspectjs.validity.ThisExpressionReferencesGlobalObjectJSInspection
import org.intellij.plugin.mdx.js.MdxJSParserDefinition
import org.intellij.plugin.mdx.lang.parse.MdxParserDefinition
import org.intellij.plugins.markdown.lang.parser.MarkdownParserDefinition

class MdxParsingTest : ParsingTestCase("", "mdx", MdxParserDefinition(), MarkdownParserDefinition(), MdxJSParserDefinition(), ECMA6ParserDefinition()) {
    fun testParsingTestData() {
        doTest(true)
    }

    @Throws(Exception::class)
    public override fun setUp() {
        super.setUp()
    }

    override fun getTestDataPath(): String {
        return "src/test/testData/parsing"
    }

    override fun skipSpaces(): Boolean {
        return false
    }

    override fun includeRanges(): Boolean {
        return true
    }
}