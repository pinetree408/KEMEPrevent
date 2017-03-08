package com.pinetree408.keme.prevent;

/** Created by user on 2016-12-28. */
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.pinetree408.keme.util.Util;

public class Prevent {

  static Util util;

  private static int preventState;
  private static final int checking = 0;
  private static final int prevent = 1;
  private static final int preChecking = 2;

  public Prevent() {

    util = new Util();

    preventState = checking;
  }

  public void keyPressed(NativeKeyEvent e, Robot robot) {
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
  }

  public String getPreventState() {
    return String.valueOf(preventState);
  }

  public void injection(String nowLanguage, Robot robot) {
    preventState = prevent;

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
