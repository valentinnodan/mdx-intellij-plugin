package org.intellij.plugin.mdx.lang.parse

import com.intellij.psi.tree.IElementType
import kotlin.jvm.Synchronized;
import org.intellij.plugin.mdx.lang.MdxLanguage
import org.intellij.plugins.markdown.lang.MarkdownElementType
import org.jetbrains.annotations.Contract
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.Nullable

import java.util.*

class MdxElementType(@NonNls debugName: String) : IElementType(debugName, MdxLanguage.INSTANCE) {
    companion object {
        private val mdxToPlatformTypeMap: HashMap<org.intellij.markdown.IElementType, IElementType> = HashMap()

        @Contract("null -> null; !null -> !null")
        @Synchronized
        fun platformType(@Nullable markdownType: org.intellij.markdown.IElementType?): IElementType {
            val result: IElementType
            if (markdownType == MdxTokenTypes.JSX_BLOCK_CONTENT || markdownType == MdxElementTypes.JSX_BLOCK || markdownType == MdxElementTypes.MDX_FILE) {
                if (mdxToPlatformTypeMap.containsKey(markdownType)) {
                    return mdxToPlatformTypeMap[markdownType]!!
                }
                result = MdxElementType(markdownType.toString())
                mdxToPlatformTypeMap[markdownType] = result
                return result;
            }
            result = MarkdownElementType.platformType(markdownType);
            return result
        }
    }
}
