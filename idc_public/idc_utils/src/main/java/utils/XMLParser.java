package utils;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by DELL on 2017/8/16.
 */
public class XMLParser {
    private static SchemaFactory schFactory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
    private static Schema schema ;
    private XMLParser() {}

    public static Object unmarshal(InputStream xml, Class<?> clazz) {
        Object obj = null;

        try {
            JAXBContext jc = JAXBContext.newInstance(clazz.getPackage().getName());
            Unmarshaller u = jc.createUnmarshaller();
            obj = u.unmarshal(xml);
        } catch (JAXBException e) {
            throw new RuntimeException("Can't unmarshal the XML file, error message: " + e.getMessage());
        }

        return obj;
    }

    public static String marshal(Object obj, Class<?> clazz,String encoding) {
        String result = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz.getPackage().getName());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            StringWriter writer = new StringWriter();

            marshaller.marshal(obj, writer);

            result = writer.toString();
            /*此时需要将xmlns:xs替换成xmlns:xsd*/
            /*result = result.replaceFirst(XMLConstants.XMLNS_ATTRIBUTE+":xs", XMLConstants.XMLNS_ATTRIBUTE+":"+TicketFTPNameSpace.basicInfo_xsd_PREFIX);*/
        } catch (JAXBException e) {
            throw new RuntimeException("Can't marshal the XML file, error message: " + e.getMessage());
        }


        return result;
    }
}
