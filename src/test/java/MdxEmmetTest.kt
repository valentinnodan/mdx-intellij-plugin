import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateManagerImpl
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.command.WriteCommandAction

class MdxEmmetTest: MdxTestBase() {
    fun testTemplates() {
        doTest("a:link<caret>", "<a href=\"http://\"></a>")
    }

    fun testTagNameInference() {
        doTest("ul>.item*3<caret>", """<ul><li></li><li></li><li></li></ul>""")
        doTest("<ul>.item*3<caret></ul>", """<ul><li className="item"></li><li className="item"></li><li className="item"></li></ul>""")
    }

    fun testDoubleBracket() {
        doTest("<inp<caret>", "<inp")
    }

    private fun doTest(input: String, expectedOutput: String) {
        myFixture.configureByText("a.mdx", input)
        TemplateManagerImpl.setTemplateTesting(getTestRootDisposable())
        WriteCommandAction.runWriteCommandAction(myFixture.getProject()) { TemplateManager.getInstance(myFixture.getProject()).startTemplate(myFixture.getEditor(), TemplateSettings.TAB_CHAR) }
        myFixture.checkResult(expectedOutput)
    }
}