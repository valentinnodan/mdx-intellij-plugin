import com.intellij.lang.javascript.inspections.JSUnresolvedVariableInspection
import com.intellij.lang.javascript.modules.TypeScriptCheckImportInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedFunctionInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedVariableInspection
import com.intellij.lang.typescript.inspections.TypeScriptValidateTypesInspection
import org.intellij.plugin.mdx.lang.MdxFileType

class MdxHighlightTest : MdxTestBase() {
    fun testJsxSimple() {
        myFixture.enableInspections(TypeScriptCheckImportInspection(),
                TypeScriptValidateTypesInspection(),
                TypeScriptUnresolvedVariableInspection(),
                TypeScriptUnresolvedFunctionInspection(),
                JSUnresolvedVariableInspection())

        myFixture.configureByText(
                MdxFileType.INSTANCE,
                "<weak_warning descr=\"Expression statement is not assignment or call\"><Button>\n" +
                        "Press the button</Button></weak_warning>"
        )

        myFixture.testHighlighting()
    }
}