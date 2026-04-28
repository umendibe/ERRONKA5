import java.io.File;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class HTMLSortu {

    public static void main(String[] args) {
        try {
            // Fitxategiak
            File xmlFitxategia = new File("WEB ORRIA/xml/donazioak.xml");
            File xsltFitxategia = new File("WEB ORRIA/donazioak.xsl");
            File htmlIrteera = new File("WEB ORRIA/index.html");

            // XSLT aplikatu HTML sortzeko
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFitxategia));

            transformer.transform(new StreamSource(xmlFitxategia), new StreamResult(htmlIrteera));

            System.out.println("HTML fitxategia sortuta: " + htmlIrteera.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

