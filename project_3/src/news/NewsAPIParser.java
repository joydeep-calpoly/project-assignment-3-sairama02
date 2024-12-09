package news;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;

public class NewsAPIParser implements Parser {
    private final ObjectMapper mapper;

    public NewsAPIParser() {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Article> parse(String source) throws JsonProcessingException {
        Content content = mapper.readValue(source, Content.class);
        List<Article> articles = new ArrayList<>();
        for (Article article : content.getArticles()) {
            if (article.isValid()) {
                articles.add(article);
            }
        }
        return articles;
    }
}
