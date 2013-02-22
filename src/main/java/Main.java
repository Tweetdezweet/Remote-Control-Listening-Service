import gui.ControllerSystemTray;
import main.NetworkService;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: koen
 * Date: 2/22/13
 * Time: 9:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args){
        NetworkService networkServiceProcess = new NetworkService();

        ControllerSystemTray systemTray = new ControllerSystemTray(networkServiceProcess);
        try {
            systemTray.buildSystemTray();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        Thread serviceThread = new Thread(networkServiceProcess);


    }
}
