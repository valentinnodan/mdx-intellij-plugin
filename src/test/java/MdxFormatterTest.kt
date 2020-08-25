import com.intellij.psi.formatter.FormatterTestCase

class MdxFormatterTest: FormatterTestCase() {
    override fun getTestDataPath(): String = "src/test/testData"
    override fun getBasePath(): String = "format"
    override fun getFileExtension(): String = "mdx"

    override fun getTestName(lowercaseFirstLetter: Boolean): String {
        return super.getTestName(false)
    }

    fun testFormatting() = doTest()
}