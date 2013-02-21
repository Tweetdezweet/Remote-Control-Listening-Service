package gui;

import java.awt.*;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: koen
 * Date: 2/21/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ControllerSystemTray {


    private static PopupMenu popUp = new PopupMenu();
    private static Image iconGreen = Toolkit.getDefaultToolkit().getImage("androidLogo.png");
    private static Image iconOrange = Toolkit.getDefaultToolkit().getImage("androidLogoOrange.png");
    private static Image iconRed = Toolkit.getDefaultToolkit().getImage("androidLogoRed.png");

    private static SystemTray systemTray;

    private static Double trayHeight;
    private static int width;
    private static TrayIcon trayIcon;
    private static MenuItem aboutItem;
    private static CheckboxMenuItem listenCheckboxItem;
    private static CheckboxMenuItem cb2;
    private static Menu optionsMenu;
    private static MenuItem errorItem;
    private static MenuItem warningItem;
    private static MenuItem infoItem;
    private static MenuItem noneItem;
    private static MenuItem exitItem;

    private ControllerSystemTray(){
        systemTray = SystemTray.getSystemTray();
    }

    public static void buildSystemTray() throws IOException {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        initializeTrayIcon();

        createPopUpMenuComponents();
        addComponentsToPopUpMenu();
        trayIcon.setPopupMenu(popUp);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

    private static void initializeTrayIcon(){
        trayHeight = systemTray.getTrayIconSize().getHeight();

        width = new TrayIcon(iconGreen).getSize().width;int trayIconWidth = width;
        trayIcon = new TrayIcon(iconGreen.getScaledInstance(-1, trayHeight.intValue(), Image.SCALE_SMOOTH));
    }

    private static void createPopUpMenuComponents(){
        aboutItem = new MenuItem("About");
        optionsMenu = new Menu("Options");
        exitItem = new MenuItem("Exit");
    }

    private static void addComponentsToPopUpMenu(){
        popUp.add(aboutItem);
        popUp.addSeparator();
        popUp.add(optionsMenu);

        //optionsMenu.add(errorItem);
        //optionsMenu.add(warningItem);
        //optionsMenu.add(infoItem);
        //optionsMenu.add(noneItem);
        popUp.add(exitItem);
    }
}
