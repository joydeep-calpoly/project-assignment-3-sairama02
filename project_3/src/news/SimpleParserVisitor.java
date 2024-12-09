package news;

public class SimpleParserVisitor implements ParserVisitor {
    @Override
    public void visit(SourceFormat sourceFormat) {
        if (sourceFormat.getFormat() != Format.SIMPLE) {
            throw new IllegalArgumentException("SimpleParserVisitor supports only SIMPLE format.");
        }
        System.out.println("Configured for Simple JSON parsing.");
    }
}
