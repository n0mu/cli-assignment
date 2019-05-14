package server.manager.parser;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;
import server.manager.db.Server;
import server.manager.error.XMLParserError;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XMLServerParser {

    public static final String ID_FIELD = "id";
    public static final String NAME_FIELD = "name";
    public static final String DESCRIPTION_FIELD = "description";

    public static final String SCHEMA_DEFINITION_FILE = "xml/servers.xsd";
    public static final String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";


    /**
     * Parses the file passed by parameter to a List of servers.
     * If there is an error throws XMLParser error.
     *
     * @param file well formatted XML server file
     * @return a List of Server objects
     * @throws XMLParserError
     */
    public List<Server> parse(File file) throws XMLParserError {
        List<Server> servers = new ArrayList<>();
        try {
            SAXReader reader = getSAXReader();
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();

            Iterator itr = rootElement.elementIterator();

            while (itr.hasNext()) {
                servers.add(parseServer((Node) itr.next()));
            }
        } catch (Exception ex) {
            throw new XMLParserError(ex.getMessage(), ex);
        }

        return servers;
    }

    private Server parseServer(Node node) {
        String id = node.selectSingleNode(ID_FIELD).getText();
        String name = node.selectSingleNode(NAME_FIELD).getText();
        String description = node.selectSingleNode(DESCRIPTION_FIELD).getText();
        return new Server(id, name, description);
    }

    private SAXReader getSAXReader() throws ParserConfigurationException, SAXException {
        SAXParser parser = getSAXParser();
        SAXReader reader = new SAXReader(parser.getXMLReader());
        reader.setValidation(false);
        return reader;
    }

    private SAXParser getSAXParser() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        InputStream inXsd = this.getClass().getClassLoader().getResourceAsStream(SCHEMA_DEFINITION_FILE);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XML_SCHEMA);
        factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(inXsd)}));
        return factory.newSAXParser();
    }


}
