package news;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ArticlesParserTest {
    private ArticlesParser parser;

    @BeforeEach
    void setup() {
        Logger testLogger = LoggerConfig.configureLogger("ArticlesLogger", "log.txt");
        parser = new ArticlesParser(testLogger);
    }

    @Test
    public void testValidArticles() throws JsonProcessingException {
        String validJson = "{\"status\": \"ok\", \"articles\": [" +
                "{\"title\": \"Title\", \"description\": \"Desc\", \"publishedAt\": \"2021-03-24T22:32:00\", \"url\": \"http://example.com\", " +
                "\"source\": {\"id\": \"cnn\", \"name\": \"CNN\"}}]}";

        List<Article> articles = parser.parse(validJson, new NewsAPIParser());

        assertEquals(1, articles.size());

        Article article = articles.get(0);
        assertEquals("Title", article.getTitle());
        assertEquals("Desc", article.getDescription());
        assertEquals(LocalDateTime.parse("2021-03-24T22:32:00"), article.getPublishedAt());
        assertEquals("http://example.com", article.getUrl());
        assertEquals("cnn", article.getSourceId());
        assertEquals("CNN", article.getSourceName());
    }

    @Test
    public void testNewsAPIFormat() throws IOException {
        String source = new String(Files.readAllBytes(Paths.get("project_3/inputs/newsapi.txt")));
        List<Article> articles = parser.parse(source, new NewsAPIParser());
        assertEquals(20, articles.size());
        assertEquals("The latest on the coronavirus pandemic and vaccines: Live updates - CNN", articles.get(0).getTitle());
    }

    @Test
    void testNewsAPIData() throws JsonProcessingException {
        String source = """
            {
              "status": "ok",
              "totalResults": 38,
              "articles": [
                {
                  "source": {
                    "id": "cnn",
                    "name": "CNN"
                  },
                  "author": "By <a href=\\"/profiles/julia-hollingsworth\\">Julia Hollingsworth</a>, CNN",
                  "title": "The latest on the coronavirus pandemic and vaccines: Live updates - CNN",
                  "description": "The coronavirus pandemic has brought countries to a standstill. Meanwhile, vaccinations have already started in some countries as cases continue to rise. Follow here for the latest.",
                  "url": "https://www.cnn.com/world/live-news/coronavirus-pandemic-vaccine-updates-03-24-21/index.html",
                  "urlToImage": "https://cdn.cnn.com/cnnnext/dam/assets/200213175739-03-coronavirus-0213-super-tease.jpg",
                  "publishedAt": "2021-03-24T22:32:00Z",
                  "content": "A senior European diplomat is urging caution over the use of proposed new rules that would govern exports of Covid-19 vaccines to outside of the EU. The rules were announced by the European Commissio… [+2476 chars]"
                },
                {
                  "source": {
                    "id": "cnn",
                    "name": "CNN"
                  },
                  "author": "Ralph Ellis, CNN",
                  "description": "Boulder, Colorado, continued to mourn fallen Officer Eric Talley on Wednesday.",
                  "url": "https://www.cnn.com/2021/03/24/us/officer-talley-procession/index.html",
                  "urlToImage": "https://cdn.cnn.com/cnnnext/dam/assets/210322232935-officer-eric-talley-headshot-super-tease.jpg",
                  "publishedAt": "2021-03-24T22:20:12Z",
                  "content": null
                }
              ]
            }
                """;

        List<Article> articles = parser.parse(source, new NewsAPIParser());
        assertEquals(1, articles.size());
    }

    @Test
    public void testSimpleFormatValid() throws IOException {
        String source = new String(Files.readAllBytes(Paths.get("project_3/inputs/simple.txt")));
        List<Article> articles = parser.parse(source, new SimpleJSONParser());
        assertEquals(1, articles.size());
        assertEquals("Assignment #2", articles.get(0).getTitle());
    }

    @Test
    public void testSimpleFormatInvalid() throws JsonProcessingException {
        String source = """
                {
                    "description": "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                    "publishedAt": "2021-04-16 09:53:23.709229",
                    "url": "https://canvas.calpoly.edu/courses/55411/assignments/274503"
                }
                """;
        List<Article> articles = parser.parse(source, new SimpleJSONParser());
        assertTrue(articles.isEmpty());
    }

    @Test
    public void testParseStreamedDataValid() throws JsonProcessingException {
        String jsonStream = """
                {"status":"ok","totalResults":1,"articles":[{"source":{"id":"cnn","name":"CNN"},"author":"Author Name","title":"Test Article","description":"Description","url":"http://example.com","urlToImage":"http://example.com/image.jpg","publishedAt":"2024-12-08T00:00:00Z","content":"Article content"}]}
                {"status":"ok","totalResults":1,"articles":[{"source":{"id":"bbc","name":"BBC"},"author":"Another Author","title":"Another Article","description":"Another description","url":"http://example.com","urlToImage":"http://example.com/image2.jpg","publishedAt":"2024-12-08T01:00:00Z","content":"Another article content"}]}
                """;

        List<Article> articles = parser.parse(jsonStream, new NewsAPIStreamParser());

        // Assertions
        assertEquals(2, articles.size());  // Two articles in total
        assertEquals("Test Article", articles.get(0).getTitle());  // First article title
        assertEquals("Another Article", articles.get(1).getTitle());  // Second article title
    }

    @Test
    void testParseStreamedDataInvalid() throws JsonProcessingException {
        String jsonStream = """
                {"status":"ok","totalResults":1,"articles":[{"source":{"id":"cnn","name":"CNN"},"author":"Author Name","description":"Description","url":"http://example.com","urlToImage":"http://example.com/image.jpg","publishedAt":"2024-12-08T00:00:00Z","content":"Article content"}]}
                {"status":"ok","totalResults":1,"articles":[{"source":{"id":"bbc","name":"BBC"},"author":"Another Author","title":"Another Article","description":"Another description","url":"http://example.com","urlToImage":"http://example.com/image2.jpg","publishedAt":"2024-12-08T01:00:00Z","content":"Another article content"}]}
                """;
        List<Article> articles = parser.parse(jsonStream, new NewsAPIStreamParser());

        assertEquals(1, articles.size());
    }

    @Test
    void testParseStreamedDataInvalidJSON() throws JsonProcessingException {
        String jsonStream = """
                {
                    "articles": [
                        {"title": "Title 2", "author": "Author 2", "publishedAt": "2024-12-08T12:34:56"}
                    ]
                }
                """;
        List<Article> articles = parser.parse(jsonStream, new NewsAPIStreamParser());

        assertTrue(articles.isEmpty(), "Invalid articles should be filtered out");
    }

    @Test
    void testNotExistentFile() {
        String invalidSource = "dne.txt";
        assertThrows(Exception.class, () -> parser.parse(invalidSource, new NewsAPIParser()));
    }
}
