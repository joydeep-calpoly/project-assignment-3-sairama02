package news;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsAPIStreamParser implements Parser{
    private final ObjectMapper mapper;

    public NewsAPIStreamParser() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Article> parse(String source) {
        List<Article> articles = new ArrayList<>();
        try (Scanner scanner = new Scanner(source)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                Content content = mapper.readValue(line, Content.class);
                for (Article article : content.getArticles()) {
                    if (article.isValid()) {
                        articles.add(article);
                    }
                }
            }
        } catch (Exception e) {
            // Handle logging externally
        }
        return articles;
    }
}