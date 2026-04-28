import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;

import org.xml.sax.SAXException;

public class XMLBaliozkotu {

    public static void main(String[] args) {
        try {
            // Fitxategiak
            File xmlFitxategia = new File("WEB ORRIA/xml/donazioak.xml");
            File xsdFitxategia = new File("WEB ORRIA/xsd/donazioak.xsd");

            // XML-a XSD-aren aurka baliozkotu
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(xsdFitxategia);
            Validator validator = schema.newValidator();

            try {
                validator.validate(new StreamSource(xmlFitxategia));
                System.out.println("XML-a baliozkoa da.");
            } catch (SAXException e) {
                System.out.println("XML-a EZ da baliozkoa: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
