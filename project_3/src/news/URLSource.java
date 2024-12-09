package news;

// URLSource class
public class URLSource extends SourceFormat {
    private final String url;

    public URLSource(String url) {
        this.url = url;
    }

    @Override
    public String getContent() {
        return fetchLiveData(url);
    }

    private static String fetchLiveData(String url) {
        try (java.util.Scanner scanner = new java.util.Scanner(new java.net.URL(url).openStream(), "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return null;
        }
    }


}
