package gui;

import main.NetworkService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: koen
 * Date: 2/21/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class ControllerSystemTray {

    private static NetworkService networkService;

    private static PopupMenu popUp = new PopupMenu();
    private static Image iconGreen = Toolkit.getDefaultToolkit().getImage("androidLogo.png");
    private static Image iconOrange = Toolkit.getDefaultToolkit().getImage("androidLogoOrange.png");
    private static Image iconRed = Toolkit.getDefaultToolkit().getImage("androidLogoRed.png");

    private static SystemTray systemTray;

    private static TrayIcon trayIcon;

    private static MenuItem aboutItem;

    private static Menu optionsMenu;
    private static MenuItem errorItem;
    private static MenuItem warningItem;
    private static MenuItem infoItem;
    private static MenuItem noneItem;
    private static MenuItem exitItem;

    public ControllerSystemTray(NetworkService networkServiceProcess){
        networkService = networkServiceProcess;
        systemTray = SystemTray.getSystemTray();
    }

    public void buildSystemTray() throws IOException {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        scaleIconImages();

        createPopUpMenuComponents();
        addComponentsToPopUpMenu();

        initializeTrayIcon();
    }

    private static void scaleIconImages(){
        Double trayHeight = systemTray.getTrayIconSize().getHeight();
        iconRed = iconRed.getScaledInstance(-1, trayHeight.intValue(), Image.SCALE_SMOOTH);
        iconOrange = iconOrange.getScaledInstance(-1, trayHeight.intValue(), Image.SCALE_SMOOTH);
        iconGreen = iconGreen.getScaledInstance(-1, trayHeight.intValue(), Image.SCALE_SMOOTH);
    }

    private static void initializeTrayIcon(){
        trayIcon = new TrayIcon(iconRed);
        trayIcon.setPopupMenu(popUp);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }

    private static void createPopUpMenuComponents(){
        aboutItem = new TrayMenuItem("About");
        optionsMenu = new Menu("Options");
        exitItem = new TrayMenuItem("Exit");

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

    public void showClientIsConnected(){
        trayIcon.setImage(iconGreen);
    }

    public void showListeningForClients(){
        trayIcon.setImage(iconOrange);
    }

    public void showErrorIcon(){
        trayIcon.setImage(iconRed);
    }

    /**
     * Created with IntelliJ IDEA.
     * User: koen
     * Date: 2/21/13
     * Time: 3:11 PM
     * To change this template use File | Settings | File Templates.
     */
    public static class TrayMenuItem extends MenuItem implements ActionListener {

        public TrayMenuItem(String title){
            super(title);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String actionCommand = actionEvent.getActionCommand();
            if(actionCommand.equals("About")) {
                networkService.onClickAbout();
            } else if (actionCommand.equals("Exit")) {
                networkService.onClickExit();
            }
            //System.out.println("User clicked on " + actionCommand);
        }
    }
}
