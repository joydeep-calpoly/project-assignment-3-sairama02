package news;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Article {
    static class Source {
        private final String id;
        private final String name;

        @JsonCreator
        private Source(@JsonProperty("id") String id,
                      @JsonProperty("name") String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Returns the ID of the source.
         *
         * @return the source ID, or null if not specified
         */
        @JsonProperty("id")
        public String getId() {
            return id;
        }

        /**
         * Returns the name of the source.
         *
         * @return the source name, or null if not specified
         */
        @JsonProperty("name")
        public String getName() {
            return name;
        }

    }
    private final Source source;
    private final String title;
    private final String description;
    private final String url;
    private final LocalDateTime publishedAt;

    /**
     * Constructs an Article instance with the specified attributes.
     *
     * @param source      the source of the article
     * @param title       the title of the article
     * @param description the description of the article
     * @param url         the URL of the article
     * @param publishedAt the publication date and time of the article
     */
    @JsonCreator
    public Article(@JsonProperty("source") Source source,
                   @JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("url") String url,
                   @JsonProperty("publishedAt") LocalDateTime publishedAt){
        this.source = source;
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
    }

//    /**
//     * Returns the source of the article.
//     *
//     * @return the Source object representing the article's source
//     */
//    @JsonProperty("source")
//    public Source getSource() {
//        return source;
//    }

    /**
     * Returns the title of the article.
     *
     * @return the title of the article
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Returns the description of the article.
     *
     * @return the description of the article
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Returns the publication date and time of the article.
     *
     * @return the publication date and time as a LocalDateTime object
     */
    @JsonProperty("publishedAt")
    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    /**
     * Returns the URL of the article.
     *
     * @return the URL of the article
     */
    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    /**
     * Returns the ID of the source.
     *
     * @return the source ID, or null if the source is null
     */
    public String getSourceId() {
        return source != null ? source.getId() : null;
    }

    /**
     * Returns the name of the source.
     *
     * @return the source name, or null if the source is null
     */
    public String getSourceName() {
        return source != null ? source.getName() : null;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n Description: " + description + "\n Published at: " + publishedAt + "\n URL:" + url + "\n";
    }

    /**
     * Validates the article's fields for completeness.
     *
     * @return true if all required fields are non-null; false otherwise
     */
    public boolean isValid() {
        return title != null && description != null &&
                publishedAt != null && url != null &&
                (source == null || source.name != null);
    }
}
