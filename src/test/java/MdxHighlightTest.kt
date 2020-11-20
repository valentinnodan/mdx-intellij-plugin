import com.intellij.lang.javascript.inspections.JSUnresolvedVariableInspection
import com.intellij.lang.javascript.modules.TypeScriptCheckImportInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedFunctionInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedVariableInspection
import com.intellij.lang.typescript.inspections.TypeScriptValidateTypesInspection
import org.intellij.plugin.mdx.lang.MdxFileType

class MdxHighlightTest : MdxTestBase() {
    fun testJsxSimple() {
        doTestHighlighting("<Button>Press the button</Button>")
    }
    fun testUnresolvedVariable() {
        val text = """import {h} from 'hello.mdx'
        
        <div>{<weak_warning descr="Unresolved variable or type hello">hello</weak_warning>}</div>
        """.trimMargin()
        doTestHighlighting(text)
    }

    fun doTestHighlighting(text: String) {
        myFixture.enableInspections(TypeScriptCheckImportInspection(),
                TypeScriptValidateTypesInspection(),
                TypeScriptUnresolvedVariableInspection(),
                TypeScriptUnresolvedFunctionInspection(),
                JSUnresolvedVariableInspection())

        myFixture.configureByText(
                "foo.mdx",
                text
        )

        myFixture.testHighlighting()
    }
}