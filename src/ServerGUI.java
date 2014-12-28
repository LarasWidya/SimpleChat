import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerGUI extends JFrame implements ActionListener, WindowListener {
	
	private static final long serialVersionUID = 1L;
	private JButton stopStart;
	private JTextArea chat, event;
	private JTextField tPortNumber;
	private Server server;
	
	
	
	ServerGUI(int port) {
		super("Chat Server");
		server = null;
		
		JPanel utara = new JPanel();
		utara.add(new JLabel("Port number: "));
		tPortNumber = new JTextField("  " + port);
		utara.add(tPortNumber);
		
		stopStart = new JButton("Start");
		stopStart.addActionListener(this);
		utara.add(stopStart);
		add(utara, BorderLayout.NORTH);
		

		JPanel center = new JPanel(new GridLayout(2,1));
		chat = new JTextArea(80,80);
		chat.setEditable(false);
		appendRoom("Ruang Chat.\n");
		center.add(new JScrollPane(chat));
		event = new JTextArea(80,80);
		event.setEditable(false);
		appendEvent("Aktifitas.\n");
		center.add(new JScrollPane(event));	
		add(center);
		

		addWindowListener(this);
		setSize(400, 600);
		setVisible(true);
	}		


	void appendRoom(String str) {
		chat.append(str);
		chat.setCaretPosition(chat.getText().length() - 1);
	}
	void appendEvent(String str) {
		event.append(str);
		event.setCaretPosition(chat.getText().length() - 1);
		
	}
	

	public void actionPerformed(ActionEvent e) {
	
		if(server != null) {
			server.stop();
			server = null;
			tPortNumber.setEditable(true);
			stopStart.setText("Mulai");
			return;
		}
	
		int port;
		try {
			port = Integer.parseInt(tPortNumber.getText().trim());
		}
		catch(Exception er) {
			appendEvent("Port Number Salah");
			return;
		}

		server = new Server(port, this);
	
		new ServerRunning().start();
		stopStart.setText("Berhenti");
		tPortNumber.setEditable(false);
	}
	

	public static void main(String[] arg) {
	
		new ServerGUI(9999);
	}


	public void windowClosing(WindowEvent e) {
	
		if(server != null) {
			try {
				server.stop();			
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		
		dispose();
		System.exit(0);
	}

	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}


	class ServerRunning extends Thread {
		public void run() {
			server.start();         
			stopStart.setText("Mulai");
			tPortNumber.setEditable(true);
			appendEvent("Server Crash\n");
			server = null;
		}
	}

}

