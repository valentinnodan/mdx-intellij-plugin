import com.intellij.lang.javascript.inspections.JSUnresolvedReactComponentInspection
import com.intellij.lang.javascript.inspections.JSUnresolvedVariableInspection
import com.intellij.lang.javascript.modules.TypeScriptCheckImportInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedFunctionInspection
import com.intellij.lang.typescript.inspections.TypeScriptUnresolvedVariableInspection
import com.intellij.lang.typescript.inspections.TypeScriptValidateTypesInspection
import org.intellij.plugin.mdx.lang.MdxFileType

class MdxHighlightTest : MdxTestBase() {
    fun testJsxSimple() {
        doTestHighlighting("<<weak_warning>Button</weak_warning>>Press the button</Button>")
    }

    fun testUnresolvedVariable() {
        val text = """import {h} from 'hello.mdx'
        
        <div>{<weak_warning descr="Unresolved variable or type hello">hello</weak_warning>}</div>
        """.trimMargin()
        doTestHighlighting(text)
    }

    fun testComment() {
        doTestHighlighting(
            """
                <!--
                <Unknown>{`
                  .subheading {sad
                    --mediumdark: '#999999';
                    font-we1ight: 900;
                    font-size: 13px;ads
                  }
                `}</Unknown>
                -->
            """.trimIndent()
        )
    }

    fun doTestHighlighting(text: String) {
        myFixture.enableInspections(
            TypeScriptCheckImportInspection(),
            TypeScriptValidateTypesInspection(),
            JSUnresolvedReactComponentInspection(),
            TypeScriptUnresolvedVariableInspection(),
            TypeScriptUnresolvedFunctionInspection(),
            JSUnresolvedVariableInspection()
        )

        myFixture.configureByText(
            "foo.mdx",
            text
        )

        myFixture.testHighlighting()
    }
}