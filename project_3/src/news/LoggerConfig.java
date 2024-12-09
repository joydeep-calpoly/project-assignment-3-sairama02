package news;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class for configuring a Logger with a file handler and formatter.
 */
public class LoggerConfig {
    private LoggerConfig() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * Configures and returns a Logger instance.
     *
     * @param loggerName the name of the logger
     * @param logFilePath the path to the log file
     * @return the configured Logger instance
     */
    public static Logger configureLogger(String loggerName, String logFilePath) {
        Logger logger = Logger.getLogger(loggerName);
        try {
            // Disable console output
            logger.setUseParentHandlers(false);

            // Set up a file handler to log messages to a file
            FileHandler fileHandler = new FileHandler(logFilePath, true); // Append to file
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to configure logger: " + loggerName, e);
        }
        return logger;
    }
}
