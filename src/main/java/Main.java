import com.google.gson.Gson;

import java.awt.*;
import java.awt.event.KeyEvent;
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
public class Main {



    private static final int PORT = 4444;

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static String message;
    private static ClientMessage clientMessage;
    private static Gson gson = new Gson();

    private static Robot robot;

    public static void main(String[] args){

        createRobot();
        createServerSocket();

        while (true){

            waitForClientToConnect();

            while ( isClientConnected()){
                clientMessage = gson.fromJson(message, ClientMessage.class);

                System.out.println("Message received:");
                System.out.println(message);

                clientMessage.performAction();
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
            } catch (IOException e) {
                System.out.println("Something went wrong with establishing a connection to the client");
            }

        }
    }

    private static boolean isClientConnected(){
        try {
            return (message = bufferedReader.readLine()) != null;
        } catch (IOException e) {
            System.out.println("Something went wrong with reading from the bufferedReader");
            return false;
        }
    }
//    private static void handleVLC(){
//        switch (clientMessage.getEventAction()){
//            case MessageCode.VLC_VOLUME_DOWN    :   vlcVolumeDown();
//                                                    break;
//            case MessageCode.VLC_VOLUME_UP      :   vlcVolumeUp();
//                                                    break;
//        }
//    }
//
//    private static void vlcVolumeUp(){
////        robot.keyPress(KeyEvent.VK_SPACE);
////        robot.keyRelease(KeyEvent.VK_SPACE);
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_UP);
//
//        robot.keyRelease(KeyEvent.VK_UP);
//        robot.keyRelease(KeyEvent.VK_CONTROL);
//    }
//
//    private static void vlcVolumeDown(){
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_DOWN);
//
//        robot.keyRelease(KeyEvent.VK_UP);
//        robot.keyRelease(KeyEvent.VK_DOWN);
//    }
}
