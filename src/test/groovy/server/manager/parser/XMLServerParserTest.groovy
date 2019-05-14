package server.manager.parser

import server.manager.db.Server
import server.manager.error.XMLParserError
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Test For XML parser")
class XMLServerParserTest extends Specification {

    @Subject
    XMLServerParser parser = new XMLServerParser()


    def "A well formed xml file is parsed without errors"() {
        given:
        File wellFormedXml = new File("src/test/resources/xml/well-formed.xml")

        when:
        List<Server> result = parser.parse(wellFormedXml)

        then:
        result
        result.size() == 2
    }


    def "A bad formed xml file fails when parsed"() {
        given:
        File badFormedXml = new File(file)

        when:
        parser.parse(badFormedXml)

        then:
        def exception = thrown(XMLParserError)
        exception.message.contains(message)

        where:
        file                                                  | message
        "src/test/resources/xml/bad-formed_missing_field.xml" | "One of '{id}' is expected"
        "src/test/resources/xml/bad-formed_empty_field.xml"   | "Value '' with length = '0' is not facet-valid"
    }
}


