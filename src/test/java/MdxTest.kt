import com.intellij.codeInsight.actions.OptimizeImportsAction
import com.intellij.ide.DataManager
import com.intellij.openapi.fileEditor.FileDocumentManager
import junit.framework.TestCase

class MdxTest : MdxTestBase() {

    fun testResolve() {
        myFixture.configureByText("my.mdx", "export const hello = \"hello\"")
        myFixture.configureByText("test.mdx", "import {hello} from \'my.mdx\'\n<div>{h<caret>ello}</div>")
        val ref = myFixture.getReferenceAtCaretPosition()
        TestCase.assertNotNull(ref?.resolve())
    }

    fun testFindUsages() {
        val usageInfos = myFixture.testFindUsages("FindUsagesTestData.mdx", "FindUsagesTestData.kt")
        TestCase.assertEquals(1, usageInfos.size)
    }

    fun testFormatter() {
        myFixture.configureByFile("FormatterTestData.mdx")
        OptimizeImportsAction.actionPerformedImpl(DataManager.getInstance().getDataContext(myFixture.editor.contentComponent))
        FileDocumentManager.getInstance().saveAllDocuments()
        myFixture.checkResultByFile("DefaultTestData.mdx")
    }

    fun testFoldingImports() {
        doTestFolding()
    }

    fun testFoldingOneImport() {
        doTestFolding()
    }

    fun testFoldingMultilineImport() {
        doTestFolding()
    }

    private fun doTestFolding() {
        myFixture.testFoldingWithCollapseStatus(testDataPath + "/" + getTestName(false) + ".mdx")
    }
}