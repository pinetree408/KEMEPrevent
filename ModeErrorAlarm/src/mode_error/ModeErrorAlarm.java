package mode_error;

import mode_error.ModeErrorUtil;
import mode_error.ModeErrorUtil.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class ModeErrorAlarm extends JFrame implements WindowListener, NativeKeyListener {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 821193587029528109L;
	
	/** The text area to display event info. */
	private static JTextArea txtEventInfo;
	
	/** buffer writer to save log */
	private static Logger logger;
	
	public ModeErrorAlarm() {
		setTitle("ModeError Alarm");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(200, 100);
		addWindowListener(this);

		Dimension frameSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation((screenSize.width - frameSize.width), 0);
		
		txtEventInfo = new JTextArea();
		txtEventInfo.setEditable(false);
		txtEventInfo.setBackground(new Color(0xFF, 0xFF, 0xFF));
		txtEventInfo.setForeground(new Color(0x00, 0x00, 0x00));
		txtEventInfo.setText("");

		JScrollPane scrollPane = new JScrollPane(txtEventInfo);
		scrollPane.setPreferredSize(new Dimension(375, 125));
		add(scrollPane, BorderLayout.CENTER);
		
		GlobalScreen.setEventDispatcher(new SwingDispatchService());
		
		setVisible(true);
		
	}
	
	private void displayEventInfo(final NativeKeyEvent e) {
		txtEventInfo.append(NativeKeyEvent.getKeyText(e.getKeyCode()));

		try {
			//Clean up the history to reduce memory consumption.
			if (txtEventInfo.getLineCount() > 100) {
				txtEventInfo.replaceRange("", 0, txtEventInfo.getLineEndOffset(txtEventInfo.getLineCount() - 1 - 100));
			}

			txtEventInfo.setCaretPosition(txtEventInfo.getLineStartOffset(txtEventInfo.getLineCount() - 1));
		}
		catch (BadLocationException ex) {
			txtEventInfo.setCaretPosition(txtEventInfo.getDocument().getLength());
		}
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		
		txtEventInfo.append("----------------------\n");
		displayEventInfo(e);
		txtEventInfo.append("-" + ModeErrorUtil.nowlanguage());
		txtEventInfo.append("-" + ModeErrorUtil.nowTopProcess());
		txtEventInfo.append("\n");
		txtEventInfo.append("*********\n");
		logger.log(e);
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			txtEventInfo.append("Error: " + ex.getMessage() + "\n");
		}
		GlobalScreen.addNativeKeyListener(this);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.runFinalization();
		System.exit(0);
	}

	/**
	 * The ModeErrorAlarm project entry point.
	 *
	 * @param args unused.
	 * @throws IOException 
	 */
	public static void main(String[] args) {
		logger = new Logger("out.txt");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ModeErrorAlarm();
			}
		});
		
		String topProcess = null;
		
		while (true) {
			
			if (!ModeErrorUtil.nowTopProcess().equals(topProcess)) {
				topProcess = ModeErrorUtil.nowTopProcess();

				Robot robot = null;
				
				try {
					robot = new Robot();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (ModeErrorUtil.nowlanguage().equals("ko")) {
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
				
			}
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}