package main;

import com.google.gson.Gson;
import devices.action.KeyboardAction;
import gui.ControllerSystemTray;
import xml.ActionMappings;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 15/02/13
 * Time: 10:56
 * To change this template use File | Settings | File Templates.
 */
public class NetworkService implements Runnable{

    private static boolean keepProcessAlive = true;

    private static final int PORT = 4444;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static String message;
    private static ClientMessage clientMessage;
    private static Gson gson = new Gson();

    private static String previousDestination;
    private static ActionMappings actionMappings;

    private static Robot robot;

    public void start(){

        createRobot();
        createServerSocket();

        while (keepProcessAlive){

            waitForClientToConnect();

            while ( isClientConnected()){
                clientMessage = gson.fromJson(message, ClientMessage.class);

                System.out.println("Message received:");
                System.out.println(message);

                if(previousDestination == null || ! previousDestination.equals(clientMessage.getEventDestination())){
                    actionMappings = new ActionMappings(clientMessage.getEventDestination());
                }

                KeyboardAction keyboardAction = actionMappings.getKeyboardAction(clientMessage.getEventAction());
                keyboardAction.performActionOn(robot);
            }

            System.out.println("Client has disconnected");
            try {
                inputStreamReader.close();
                clientSocket.close();
            } catch (Exception e) {
                System.out.println("Could not close inputStreamReader and/or clientSocket");
            }

        }
    }

    private static void createRobot(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Robot could not be instantiated");
        }
    }

    private static void createServerSocket() {
        try {
            serverSocket = new ServerSocket(PORT);  //Server socket
        } catch (IOException e) {
            System.out.println("Could not create a server socket");
            e.printStackTrace();
        }
        System.out.println("Server started. Listening to the port " + PORT);
    }

    private static void waitForClientToConnect(){
        if(robot != null){
                System.out.println("Waiting to accept connection");
            try {
                clientSocket = serverSocket.accept();   //accept the client connection
                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                System.out.println("Client has connected, starting messageloop");
            } catch (Exception e) {
                System.out.println("Something went wrong with establishing a connection to the client");
            }

        }
    }

    private static boolean isClientConnected(){
        try {
            return (message = bufferedReader.readLine()) != null;
        } catch (Exception e) {
            System.out.println("Something went wrong with reading from the bufferedReader");
            return false;
        }
    }

    public void onClickAbout(){
        System.out.println("User clicked on About in tray menu");
    }

    public void onClickExit(){
        keepProcessAlive = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void run() {
        start();
    }
}
