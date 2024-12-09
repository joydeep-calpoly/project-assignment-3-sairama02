package news;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Content {
    private final String status;
    private final Integer totalResults;
    private final List<Article> articles;

    @JsonCreator
    public Content(@JsonProperty("status") String status,
                   @JsonProperty("totalResults") Integer totalResults,
                   @JsonProperty("articles") List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    /**
     * Returns the list of articles in the content.
     *
     * @return the articles as a List of Article objects
     */
    @JsonProperty("articles")
    public List<Article> getArticles() {
        return articles;
    }

}
