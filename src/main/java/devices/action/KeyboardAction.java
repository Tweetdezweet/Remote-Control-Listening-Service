package devices.action;


import xml.KeyMappings;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: KoenG
 * Date: 17/02/13
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 */
public class KeyboardAction {
    private int key;
    private List<Integer> modifiers;

    private static final KeyMappings keyMap = new KeyMappings();

    public KeyboardAction(String key, List<String> modifiers){
        this.modifiers = new ArrayList<Integer>();
        this.key = keyMap.getKeyCode(key);
        for(String modifier : modifiers){
            this.modifiers.add(keyMap.getKeyCode(modifier));
        }
    }

    public void performActionOn(Robot robot){
        System.out.println("modifiers: "+ modifiers);
        System.out.println("key: " + key);
        for(int modifier : modifiers){
            robot.keyPress(modifier);
        }
        robot.keyPress(key);

        robot.keyRelease(key);
        for(int modifier : modifiers){
            robot.keyRelease(modifier);
        }

    }
}
