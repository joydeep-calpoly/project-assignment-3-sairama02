package news;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface Parser {
    List<Article> parse(String source) throws JsonProcessingException;
}