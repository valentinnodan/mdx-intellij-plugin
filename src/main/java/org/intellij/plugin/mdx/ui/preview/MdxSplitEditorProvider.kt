package org.intellij.plugin.mdx.ui.preview

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.TextEditor
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider
import org.intellij.plugins.markdown.ui.preview.MarkdownPreviewFileEditor
import org.intellij.plugins.markdown.ui.preview.MarkdownSplitEditor
import org.intellij.plugins.markdown.ui.split.SplitTextEditorProvider

class MdxSplitEditorProvider : SplitTextEditorProvider(PsiAwareTextEditorProvider(), MdxPreviewFileEditorProvider()) {
    override fun createSplitEditor(firstEditor: FileEditor, secondEditor: FileEditor): FileEditor {
        require(!(firstEditor !is TextEditor || secondEditor !is MdxPreviewFileEditor)) { "Main editor should be TextEditor" }
        return MarkdownSplitEditor(firstEditor, secondEditor)
    }
}