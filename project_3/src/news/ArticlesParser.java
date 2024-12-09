package news;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.logging.Logger;

public class ArticlesParser {
    private final Logger logger;

    public ArticlesParser(Logger logger) {
        this.logger = logger;
    }

    public List<Article> parse(String source, SourceFormat sourceFormat) throws JsonProcessingException {
        Parser parser = getParser(sourceFormat);
        try {
            return parser.parse(source);
        } catch (Exception e) {
            logger.warning("Parsing error: " + e.getMessage());
            throw e;
        }
    }

    private Parser getParser(SourceFormat sourceFormat) {
        switch (sourceFormat.getFormat()) {
            case NEWS_API:
                NewsAPIParserVisitor newsAPIVisitor = new NewsAPIParserVisitor();
                sourceFormat.accept(newsAPIVisitor);
                return new NewsAPIParser();
            case SIMPLE:
                SimpleParserVisitor simpleVisitor = new SimpleParserVisitor();
                sourceFormat.accept(simpleVisitor);
                return new SimpleJSONParser();
            default:
                throw new IllegalArgumentException("Unsupported format type");
        }
    }
}
