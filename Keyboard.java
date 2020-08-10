//キーボードの配列とエラー処理

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;

class KConstant {
	public final static char[] keys = new char[] { '7', '8', '9', '+', '-', '4', '5', '6', '*', '/', '1', '2', '3', '(',
			')', '0', '.', 'C', '%', '=' };
	public final static long INTERVAL = 100;
}

public class Keyboard extends InputStream {
	final private JPanel panel = new JPanel();;

	public Keyboard() {
		super();
		panel.setLayout(new GridLayout(4, 5));
		for (char c : KConstant.keys) {
			JButton button = new JButton(String.valueOf(c));
			button.setFont(button.getFont().deriveFont(36.0f));
			button.addActionListener(new ButtonAction());
			panel.add(button);
		}
	}

	public JPanel getPanel() {
		return panel;
	}

	public ActionListener getCloseAction() {
		return new CloseAction();
	}

	class CloseAction implements ActionListener {
		public CloseAction() {
		}

		public void actionPerformed(ActionEvent event) {
			queue.addLast(-1);
		}
	}

	class ButtonAction implements ActionListener {
		public ButtonAction() {
		}

		public void actionPerformed(ActionEvent event) {
			queue.addLast((int) (event.getActionCommand().charAt(0)));
		}
	}

	private LinkedList<Integer> queue = new LinkedList<Integer>();

	@Override
	public int read() throws IOException {
		try {
			while (queue.isEmpty()) {
				Thread.sleep(KConstant.INTERVAL);
			}
		} catch (Exception e) {
		}
		return queue.remove();
	}

	@Override
	public int available() throws IOException {
		return 0;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (len == 0) {
			return 0;
		}
		int c = read();
		if (c == -1) {
			return -1;
		}
		b[off] = (byte) c;
		return 1;
	}
}
