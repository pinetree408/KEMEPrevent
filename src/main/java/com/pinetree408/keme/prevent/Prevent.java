package com.pinetree408.keme.prevent;

/**
 * Created by user on 2016-12-28.
 */

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.pinetree408.keme.util.Util;

public class Prevent implements NativeKeyListener {

    static Robot robot;
    static Util util;
    static String prevTopProcess;
    static String nowTopProcess;
    static String nowLanguage;

    public Prevent() {

        // Initialize Robot for prevent word injection
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        util = new Util();

        prevTopProcess = "initial";
        nowTopProcess = "initial";
        nowLanguage = "initial";

    }

    public void nativeKeyPressed(NativeKeyEvent e) {

        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {

            System.out.println("test");

        }
    }

    public static void main(String[] args) {

        // Set jnativehook logger level to off state
        Logger EventLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        EventLogger.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new Prevent());

        while (true) {

            nowTopProcess =  util.nowTopProcess();

            if (nowTopProcess.equals("")) {
                continue;
            }

            if (!prevTopProcess.equals(nowTopProcess)) {

                prevTopProcess = util.nowTopProcess();

                nowLanguage = util.nowLanguage();

                robot.delay(200);
                if (nowLanguage.equals("ko")) {
                    robot.keyPress(KeyEvent.VK_G);
                    robot.keyRelease(KeyEvent.VK_G);
                    robot.keyPress(KeyEvent.VK_K);
                    robot.keyRelease(KeyEvent.VK_K);
                    robot.keyPress(KeyEvent.VK_S);
                    robot.keyRelease(KeyEvent.VK_S);
                } else {
                    robot.keyPress(KeyEvent.VK_E);
                    robot.keyRelease(KeyEvent.VK_E);
                    robot.keyPress(KeyEvent.VK_N);
                    robot.keyRelease(KeyEvent.VK_N);
                }
                robot.waitForIdle();

            }

        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

}
