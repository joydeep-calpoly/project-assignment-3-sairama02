package news;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

public class SimpleJSONParser implements Parser {
    private final ObjectMapper mapper;
    private final Logger logger;

    public SimpleJSONParser() {
        this.logger = LoggerConfig.configureLogger("ArticlesLogger", "log.txt");
        this.mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")));
        this.mapper.registerModule(module);
    }

    @Override
    public List<Article> parse(String source) throws JsonProcessingException {
        Article article = mapper.readValue(source, Article.class);
        if(article.isValid()) {
            return List.of(article);
        } else {
            logger.warning("Invalid article in simple JSON format: " + article + "\n");
            return List.of();
        }
    }
}
