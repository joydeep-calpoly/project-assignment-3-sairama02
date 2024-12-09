package news;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerConfig.configureLogger("ArticlesLogger", "log.txt");

        ArticlesParser parser = new ArticlesParser(logger);

        //Parsing from NewsAPI format
        String newsAPISource = new String(Files.readAllBytes(Paths.get("project_3/inputs/newsapi.txt")));
        Parser newsAPIParser = new NewsAPIParser();
        List<Article> newsAPIArticles = parser.parse(newsAPISource, newsAPIParser);
        for (Article article : newsAPIArticles) {
            System.out.println(article);
        }

        //Parsing from Simple format
        String simpleFormatSource = new String(Files.readAllBytes(Paths.get("project_3/inputs/simple.txt")));
        Parser simpleJSONParser = new SimpleJSONParser();
        List<Article> simpleFormatArticles = parser.parse(simpleFormatSource, simpleJSONParser);
        for (Article article : simpleFormatArticles) {
            System.out.println(article);
        }

        //Parsing from NewsAPI Stream
        final String NewsAPIKey = "990e16f281e549cfb7bbff887b7b99a5";
        String streamedData = fetchLiveData("http://newsapi.org/v2/top-headlines?country=us&apiKey=" + NewsAPIKey);
        if (streamedData != null) {
            Parser APIStreamParser = new NewsAPIStreamParser();
            List<Article> liveArticles = parser.parse(streamedData, APIStreamParser);
            for (Article article : liveArticles) {
                System.out.println(article);
            }
        } else {
            System.out.println("Failed to fetch live US headlines.");
        }
}

    private static String fetchLiveData(String url) {
        try (java.util.Scanner scanner = new java.util.Scanner(new java.net.URL(url).openStream(), "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            return null;
        }


    }
}
