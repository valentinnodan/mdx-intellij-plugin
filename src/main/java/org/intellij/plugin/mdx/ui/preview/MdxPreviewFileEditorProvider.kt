package org.intellij.plugin.mdx.ui.preview

import com.intellij.ide.scratch.ScratchUtil
import com.intellij.lang.LanguageUtil
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.fileEditor.WeighedFileEditorProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.intellij.plugin.mdx.lang.MdxFileType
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugins.markdown.ui.preview.MarkdownHtmlPanelProvider
import org.intellij.plugins.markdown.ui.preview.MarkdownPreviewFileEditor

class MdxPreviewFileEditorProvider : WeighedFileEditorProvider() {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        val fileType = file.fileType
        return (fileType === MdxFileType.INSTANCE ||
                ScratchUtil.isScratch(file) && LanguageUtil.getLanguageForPsi(project, file) === MdxLanguage) &&
                MarkdownHtmlPanelProvider.hasAvailableProviders()
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return MdxPreviewFileEditor(project, file)
    }

    override fun getEditorTypeId(): String {
        return "markdown-preview-editor"
    }

    override fun getPolicy(): FileEditorPolicy {
        return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR
    }

}