package org.intellij.plugin.mdx.highlighting

import com.intellij.lang.ecmascript6.JSXHarmonyFileType
import com.intellij.lang.javascript.DialectOptionHolder
import com.intellij.lang.javascript.dialects.ECMA6SyntaxHighlighterFactory
import com.intellij.openapi.editor.colors.EditorColorsScheme
import com.intellij.openapi.editor.ex.util.LayerDescriptor
import com.intellij.openapi.editor.ex.util.LayeredLexerEditorHighlighter
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import org.intellij.plugin.mdx.lang.parse.MdxTokenTypes
import org.intellij.plugins.markdown.lang.MarkdownElementType
import org.intellij.plugins.markdown.lang.MarkdownTokenTypes

class MdxEditorHighlighter(colors: EditorColorsScheme)
    : LayeredLexerEditorHighlighter(MdxSyntaxHighlighter(), colors) {
    init {
        // create main highlighter

        // highlighter for outer lang
        val outerHighlighter = ECMA6SyntaxHighlighterFactory.ECMA6SyntaxHighlighter(DialectOptionHolder.JSX, false)
        //here must be jsx tokens
        registerLayer(MarkdownElementType.platformType(MdxTokenTypes.JSX_BLOCK_CONTENT), LayerDescriptor(outerHighlighter, ""))
        registerLayer(MarkdownTokenTypes.HTML_TAG, LayerDescriptor(outerHighlighter, ""))
    }

}