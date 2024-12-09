package news;

// Abstract Source class
public abstract class SourceFormat {
    private final Source source;
    private final Format format;

    protected SourceFormat(Source source, Format format) {
        this.source = source;
        this.format = format;
    }

    public Source getSource() {
        return source;
    }

    public Format getFormat() {
        return format;
    }

    public void accept(ParserVisitor visitor){
        visitor.visit(this);
    }
}

