package xml;

import com.sun.org.apache.xerces.internal.jaxp.JAXPConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 17/02/13
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public class KeyMappings {
    private static final String XML_PATH =
            "C:\\Users\\KoenG\\Documents\\Android-projects\\PcControllerService\\keyboard.xml";


    protected Document dom;

    public KeyMappings(){
        getDocument();
    }

    private void getDocument(){

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(new File(XML_PATH));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public int getKeyCode(String key){
        Element element = dom.getElementById(key);
        String stringValue = element.getTextContent();
        return Integer.valueOf(stringValue);
    }
}
