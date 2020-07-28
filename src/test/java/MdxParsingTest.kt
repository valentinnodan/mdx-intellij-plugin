import com.intellij.testFramework.ParsingTestCase
import org.intellij.plugin.mdx.lang.parse.MdxParserDefinition

class MdxParsingTest : ParsingTestCase("", "mdx", MdxParserDefinition()) {
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