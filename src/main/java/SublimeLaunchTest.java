import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import xml.ActionMappings;
import xml.KeyMappings;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 16/02/13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class SublimeLaunchTest {

    public static void main(String[] args) {

//        Runtime run = Runtime.getRuntime();
//        try {
//            Process process = run.exec("C:\\Program Files\\Sublime Text 2\\sublime_text.exe");
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }


//        Document dom;
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        try{
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            dom = db.parse(new File("test.xml"));
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (SAXException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

//        String filePath = "C:\\Users\\KoenG\\Documents\\Android-projects\\PcControllerService\\vlc.xml";
//        String myXml = null;
//        try {
//            myXml = readFile(filePath);
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        System.out.println(myXml);

//        KeyMappings keyMappings = new KeyMappings();
//        System.out.println("KeyMappings Example");
//        System.out.println(keyMappings.getKeyCode("V"));

        System.out.println();
//        KeyEvent.VK_SHIFT
        System.out.println("ActionMappings Example");
        ActionMappings actionMappings = new ActionMappings("vlc");
        actionMappings.getKeyboardAction("volumeUp");
//        System.out.println(actionMappings.getKeyboardAction(stop));
    }

    private static String readFile(String path) throws IOException {
        FileInputStream stream = new FileInputStream(new File(path));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
    /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            stream.close();
        }
    }
}
