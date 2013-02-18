package xml;

import devices.action.KeyboardAction;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 17/02/13
 * Time: 10:39
 * To change this template use File | Settings | File Templates.
 */
public class ActionMappings {
    /*private static final String XML_BASE_PATH =
            "C:\\Users\\KoenG\\Documents\\Android-projects\\PcControllerService\\";*/
    private static final String XML_BASE_PATH =
            "home/koen/repositories/pccontrollerservice/";
    private String xmlFileName;
    private Element element;


    protected Document dom;

    public ActionMappings(String xmlFileName){
        this.xmlFileName = xmlFileName;
        getDocument();
    }

    private void getDocument(){

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);
        dbf.setIgnoringElementContentWhitespace(true);
        try{
            DocumentBuilder db = dbf.newDocumentBuilder();
            // on windows platform
            //dom = db.parse(new File(XML_BASE_PATH + xmlFileName + ".xml"));
            // on linux platform
            dom = db.parse(new File(xmlFileName + ".xml"));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public KeyboardAction getKeyboardAction(String action){
        element = dom.getElementById(action);
        return new KeyboardAction(getKey(),getModifiers());

    }

    private String getKey(){
        return element.getFirstChild().getTextContent();
    }

    private List getModifiers(){
        List<String> modifiers = new ArrayList<String>();

        NodeList nodeList = element.getElementsByTagName("modifier");

        for(int i = 0; i < nodeList.getLength(); i++){
            modifiers.add(nodeList.item(i).getTextContent());
        }
        return modifiers;
    }
}
