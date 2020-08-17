import com.intellij.codeInsight.completion.CompletionType
import com.intellij.lang.javascript.inspections.JSUnresolvedVariableInspection
import com.intellij.lang.javascript.modules.TypeScriptCheckImportInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedFunctionInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedVariableInspection
import com.intellij.lang.typescript.inspections.TypeScriptValidateTypesInspection
import junit.framework.TestCase
import org.intellij.plugin.mdx.lang.MdxFileType

class MdxTest : MdxTestBase() {

    fun testResolve() {
        myFixture.configureByText("my.mdx", "export const hello = \"hello\"")
        myFixture.configureByText("test.mdx", "import {hello} from \'my.mdx\'\n<div>{h<caret>ello}</div>")
        val ref = myFixture.getReferenceAtCaretPosition()
        TestCase.assertNotNull(ref?.resolve())
    }
}