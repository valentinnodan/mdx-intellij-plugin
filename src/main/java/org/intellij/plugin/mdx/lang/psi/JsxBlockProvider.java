package org.intellij.plugin.mdx.lang.psi;

import org.intellij.markdown.parser.LookaheadText;
import org.intellij.markdown.parser.MarkerProcessor;
import org.intellij.markdown.parser.ProductionHolder;
import org.intellij.markdown.parser.constraints.MarkdownConstraints;
import org.intellij.markdown.parser.markerblocks.MarkerBlock;
import org.intellij.markdown.parser.markerblocks.MarkerBlockProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JsxBlockProvider implements MarkerBlockProvider<MarkerProcessor.StateInfo> {
    @NotNull
    @Override
    public List<MarkerBlock> createMarkerBlocks(LookaheadText.@NotNull Position position, @NotNull ProductionHolder productionHolder, MarkerProcessor.@NotNull StateInfo stateInfo) {
        return null;
    }

    @Override
    public boolean interruptsParagraph(LookaheadText.@NotNull Position position, @NotNull MarkdownConstraints markdownConstraints) {
        return false;
    }
}
