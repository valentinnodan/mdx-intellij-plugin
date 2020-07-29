import com.intellij.testFramework.ParsingTestCase
import org.intellij.plugin.mdx.lang.parse.MdxParserDefinition
import org.intellij.plugins.markdown.lang.parser.MarkdownParserDefinition

class MdxParsingTest : ParsingTestCase("", "mdx", MdxParserDefinition(), MarkdownParserDefinition(), com.intellij.lang.jsx.JSXHarmonyParserDefinition()) {
    fun testParsingTestData() {
        doTest(true)
    }

    override fun getTestDataPath(): String {
        return "src/test/testData"
    }

    override fun skipSpaces(): Boolean {
        return false
    }

    override fun includeRanges(): Boolean {
        return true
    }
}