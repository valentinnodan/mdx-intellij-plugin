package org.intellij.plugin.mdx.lang.psi;

import org.intellij.markdown.IElementType;
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor;
import org.intellij.markdown.flavours.commonmark.CommonMarkMarkerProcessor;
import org.intellij.markdown.html.GeneratingProvider;
import org.intellij.markdown.lexer.MarkdownLexer;
import org.intellij.markdown.parser.LinkMap;
import org.intellij.markdown.parser.MarkerProcessor;
import org.intellij.markdown.parser.MarkerProcessorFactory;
import org.intellij.markdown.parser.ProductionHolder;
import org.intellij.markdown.parser.constraints.CommonMarkdownConstraints;
import org.intellij.markdown.parser.markerblocks.MarkerBlockProvider;
import org.intellij.markdown.parser.markerblocks.providers.*;
import org.intellij.markdown.parser.sequentialparsers.SequentialParserManager;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.List;
import java.util.Map;

public class MdxFlavourDescriptor extends CommonMarkFlavourDescriptor {

    private CommonMarkFlavourDescriptor myCommonMarkFlavourDescriptor = new CommonMarkFlavourDescriptor();

    MarkerProcessorFactory markerProcessorFactory = new MdxProcessFactory();

    SequentialParserManager sequentialParserManager = myCommonMarkFlavourDescriptor.getSequentialParserManager();

    // URI may be null!
    @Override
    public Map<IElementType, GeneratingProvider> createHtmlGeneratingProviders(LinkMap linkMap, URI baseURI) {
        return myCommonMarkFlavourDescriptor.createHtmlGeneratingProviders(linkMap, baseURI);
    }

    @Override
    public MarkdownLexer createInlinesLexer() {
        return myCommonMarkFlavourDescriptor.createInlinesLexer();
    }

    private class MdxProcessFactory implements MarkerProcessorFactory {

        @NotNull
        @Override
        public MarkerProcessor<?> createMarkerProcessor(@NotNull ProductionHolder productionHolder) {
            return new MdxMarkerProcessor(productionHolder);
        }
    }

    private class MdxMarkerProcessor extends CommonMarkMarkerProcessor {

        public MdxMarkerProcessor(@NotNull ProductionHolder productionHolder) {
            super(productionHolder, CommonMarkdownConstraints.Companion.getBASE());
        }

        private List<MarkerBlockProvider<MarkerProcessor.StateInfo>> markerBlockProviders =
                List.of(new CodeBlockProvider(),
                        new HorizontalRuleProvider(),
                        new CodeFenceProvider(),
                        new JsxBlockProvider(),
                        new SetextHeaderProvider(),
                        new BlockQuoteProvider(),
                        new ListMarkerProvider(),
                        new HtmlBlockProvider(),
                        new AtxHeaderProvider(false));

        @Override
        protected List<MarkerBlockProvider<MarkerProcessor.StateInfo>> getMarkerBlockProviders() {
            return markerBlockProviders;
        }
    }
}
