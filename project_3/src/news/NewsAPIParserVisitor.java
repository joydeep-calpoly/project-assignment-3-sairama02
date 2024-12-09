package news;

public class NewsAPIParserVisitor implements ParserVisitor {
    @Override
    public void visit(SourceFormat sourceFormat) {
        if (sourceFormat.getFormat() != Format.NEWS_API) {
            throw new IllegalArgumentException("NewsAPIParserVisitor supports only NEWS_API format.");
        }
        System.out.println("Configured for NewsAPI parsing.");
    }
}
