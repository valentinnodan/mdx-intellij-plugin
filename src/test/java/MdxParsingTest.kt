import com.intellij.lang.javascript.dialects.ECMA6ParserDefinition
import com.intellij.testFramework.ParsingTestCase
import org.intellij.plugin.mdx.js.MdxJSParserDefinition
import org.intellij.plugin.mdx.lang.parse.MdxParserDefinition
import org.intellij.plugins.markdown.lang.parser.MarkdownParserDefinition

class MdxParsingTest : ParsingTestCase("", "mdx", MdxParserDefinition(), MarkdownParserDefinition(), MdxJSParserDefinition(), ECMA6ParserDefinition()) {
    fun testParsingTestData() {
        doTest(true)
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