//Node構文チェッククラス

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

class MyLabel extends JLabel {
	class Constant {
		public final static int LABELWIDTH = 200;
	}

	public MyLabel() {
		super("0", RIGHT);
		Dimension d = getSize();
		d.width = Constant.LABELWIDTH;
		setSize(d);
	}

	public ActionListener getActionListener() {
		return new Modifier();
	}

	class Modifier implements ActionListener {
		public Modifier() {
		}

		public void actionPerformed(ActionEvent event) {
			setText(event.getActionCommand());
		}
	}
}

class MyFrame extends JFrame {
	class Constant {
		public final static int WIDTH = 1000;
		public final static int HEIGHT = 800;
		public final static String TITLE = "電卓";
	}

	public void setCloseActionListener(ActionListener a) {
		closeActionList.add(a);
	}

	private final LinkedList<ActionListener> closeActionList = new LinkedList<ActionListener>();

	class MyWindowListener extends WindowAdapter {
		public MyWindowListener() {
		}

		public void windowClosed(WindowEvent e) {
			for (ActionListener listener : closeActionList) {
				listener.actionPerformed(
						new ActionEvent(
								this, ActionEvent.ACTION_PERFORMED, "close"));
			}
		}
	}

	public MyFrame() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new MyWindowListener());
		setSize(Constant.WIDTH, Constant.HEIGHT);
		setTitle(Constant.TITLE);
	}
}

class Main {
	public static void main(String[] arg) throws IOException, ParseException {
		final MyFrame frame = new MyFrame();
		final Keyboard keyboard = new Keyboard();
		frame.setCloseActionListener(keyboard.getCloseAction());
		final Container container = frame.getContentPane();
		container.add(keyboard.getPanel(), BorderLayout.CENTER);
		final MyLabel label = new MyLabel();
		label.setFont(label.getFont().deriveFont(100.0f));
		container.add(label, BorderLayout.NORTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		while (true) {
			try {
				Parser parser = new Parser(keyboard);
				parser.setActionListener(label.getActionListener());
				Node tree = parser.start();
			} catch (TokenMgrError e) {
				label.setText("0");
			} catch (Exception e) {
				System.out.println(e);
				label.setText("Error");
			}
		}
	}
}
