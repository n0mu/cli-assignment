package server.manager.error;

public class XMLParserError extends RuntimeException {

    public XMLParserError(String message, Throwable cause) {
        super(message, cause);
    }
}
