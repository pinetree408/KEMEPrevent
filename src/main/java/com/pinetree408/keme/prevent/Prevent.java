package com.pinetree408.keme.prevent;

/** Created by user on 2016-12-28. */
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.pinetree408.keme.util.Util;
import com.pinetree408.keme.util.ModeErrorLogger;

public class Prevent implements NativeKeyListener {

  static Robot robot;
  static Util util;
  /** buffer writer to save log */
  private static ModeErrorLogger meLogger;

  static String prevTopProcess;
  static String nowTopProcess;
  static String nowLanguage;

  private static int preventState;
  private static final int checking = 0;
  private static final int prevent = 1;
  private static final int preChecking = 2;

  public Prevent() {

    // Initialize Robot for prevent word injection
    try {
      robot = new Robot();
    } catch (AWTException ex) {
      // TODO Auto-generated catch block
      ex.printStackTrace();
    }

    util = new Util();
    meLogger = new ModeErrorLogger("result.txt");

    prevTopProcess = "initial";
    nowTopProcess = "initial";
    nowLanguage = "initial";

    preventState = checking;
  }

  public void nativeKeyPressed(NativeKeyEvent e) {

    switch (preventState) {
      case checking:
        break;
      case prevent:
        if (util.nowLanguage().equals("ko")) {
          try {
            if (util.getJavaKeyCode(e.getKeyCode()) == KeyEvent.VK_S) {
              preventState = preChecking;
            }
          } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        } else {
          try {
            if (util.getJavaKeyCode(e.getKeyCode()) == KeyEvent.VK_N) {
              preventState = preChecking;
            }
          } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }
        }
        break;
      case preChecking:
        if (util.nowLanguage().equals("ko")) {
          robot.keyPress(KeyEvent.VK_BACK_SPACE);
          robot.keyRelease(KeyEvent.VK_BACK_SPACE);
          robot.keyPress(KeyEvent.VK_BACK_SPACE);
          robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        } else {
          robot.keyPress(KeyEvent.VK_BACK_SPACE);
          robot.keyRelease(KeyEvent.VK_BACK_SPACE);
          robot.keyPress(KeyEvent.VK_BACK_SPACE);
          robot.keyRelease(KeyEvent.VK_BACK_SPACE);
          robot.keyPress(KeyEvent.VK_BACK_SPACE);
          robot.keyRelease(KeyEvent.VK_BACK_SPACE);
        }
        try {
          robot.keyPress(util.getJavaKeyCode(e.getKeyCode()));
          robot.keyRelease(util.getJavaKeyCode(e.getKeyCode()));
        } catch (Exception e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
        preventState = checking;
        break;
    }
    meLogger.log(e, nowLanguage, nowTopProcess, String.valueOf(preventState), "null");
  }

  public static void main(String[] args) {

    // Set jnativehook logger level to off state
    Logger EventLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
    EventLogger.setLevel(Level.OFF);

    try {
      GlobalScreen.registerNativeHook();
    } catch (NativeHookException ex) {
      System.err.println("There was a problem registering the native hook.");
      System.err.println(ex.getMessage());

      System.exit(1);
    }

    GlobalScreen.addNativeKeyListener(new Prevent());

    Timer jobScheduler = new Timer();
    jobScheduler.schedule(
        new TimerTask() {
          @Override
          public void run() {

            nowTopProcess = util.nowTopProcess();

            if (!nowTopProcess.equals("")) {

              if (!prevTopProcess.equals(nowTopProcess)) {

                preventState = prevent;

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
        },
        0,
        100);
  }

  public void nativeKeyReleased(NativeKeyEvent e) {
    //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
  }

  public void nativeKeyTyped(NativeKeyEvent e) {
    //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
  }
}
