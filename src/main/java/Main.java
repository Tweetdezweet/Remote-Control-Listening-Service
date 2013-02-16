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
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static String message;
    private static ClientMessage clientMessage;

    private static Robot robot;

    private static boolean keepConnectionAlive = true;

    public static void main(String[] args){
        try {
            serverSocket = new ServerSocket(4444);  //Server socket

        } catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
        }

        System.out.println("Server started. Listening to the port 4444");

        Gson gson = new Gson();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println("Robot could not be instantiated");
        }

        if(robot != null){
            try {
                System.out.println("Waiting to accept connection");
                clientSocket = serverSocket.accept();   //accept the client connection
                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                System.out.println("Client has connected, starting messageloop");


                while ( (message = bufferedReader.readLine()) != null && keepConnectionAlive){

                    clientMessage = gson.fromJson(message, ClientMessage.class);

                    System.out.println("Message received:");
                    System.out.println(message);

                    handleJsonMessage();

                }
                System.out.println("Service is closing down");
                inputStreamReader.close();
                clientSocket.close();

            } catch (IOException ex) {
                System.out.println("Problem in message reading");
            }
        }
    }

    private static void handleJsonMessage(){
        switch (clientMessage.getEventClass()){
            case MessageCode.CLASS_SERVICE      :   killService();
                                                    break;
            case MessageCode.CLASS_TEXT_MESSAGE :   handleTextMessage();
                                                    break;
            case MessageCode.CLASS_PROGRAM:   handleProgram();
                                                    break;
            case MessageCode.CLASS_VLC          :   handleVLC();
                                                    break;
        }
    }

    private static void killService(){
        keepConnectionAlive = false;
    }

    private static void handleTextMessage(){
        System.out.println(clientMessage.getEventText());
    }

    private static void handleProgram(){
//        TODO: stub method
    }

    private static void handleVLC(){
        switch (clientMessage.getEventAction()){
            case MessageCode.VLC_VOLUME_DOWN    :   vlcVolumeDown();
                                                    break;
            case MessageCode.VLC_VOLUME_UP      :   vlcVolumeUp();
                                                    break;
        }
    }

    private static void vlcVolumeUp(){
//        robot.keyPress(KeyEvent.VK_SPACE);
//        robot.keyRelease(KeyEvent.VK_SPACE);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_UP);

        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }

    private static void vlcVolumeDown(){
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_DOWN);

        robot.keyRelease(KeyEvent.VK_UP);
        robot.keyRelease(KeyEvent.VK_DOWN);
    }
}
