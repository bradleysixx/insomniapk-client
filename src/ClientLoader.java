import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.Properties;

import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ClientLoader extends JApplet implements Serializable, ActionListener, ComponentListener {
	private static final long serialVersionUID = -3815183950296997504L;

	public static void main(String[] strings) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ClientLoader loader = new ClientLoader();
				loader.deleteOldCaches();
				loader.doFrame();
			}
		});
	}

	public void deleteOldCaches() {
		for (double d = 0; d < Constants.VERSION; d++) {
			for (double i = 1; i <= 10; i++) {
				double decimal = i / 10;
				double number = new Double(d + decimal);
				if (number == Constants.VERSION) {
					break;
				}
				String versionInfo = (number < 1.0 ? "" : "v" + number);
				File file = new File(new StringBuilder(System.getProperty("user.home")).append(File.separator).append(Constants.NAME + "_Cache" + versionInfo).toString());
				deleteDirectory(file);
			}
		}
	}

	public boolean deleteDirectory(File path) {
		if (path.exists()) {
			System.out.println("Old unused cache existed, deleting!\t" + path);
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
	
	/**
	 * Prepares all features of the client frame and starts it
	 */
	public void doFrame() {
		setParams();
		openFrame();
		startClient();
	}

	/**
	 * Opens the client JFrame and initializes all aspects of it such as the
	 * icon image, look and feel, title, etc.
	 */
	private void openFrame() {
		try {
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(new org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		client_frame = new JFrame();
		client_frame.setTitle(Constants.TITLE);
		client_frame.setLayout(new BorderLayout());
		client_frame.addComponentListener(this);
		JPanel client_panel = new JPanel();
		client_panel.setLayout(new BorderLayout());
		client_panel.add(this);

		client_panel.setPreferredSize(new Dimension(765, 503));
		client_frame.getContentPane().add(client_panel, "Center");
		client_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client_frame.setIconImage(Utils.getImage("./resources/icon.png"));
		client_frame.setJMenuBar(new ClientMenuBar());
		client_frame.setVisible(true);
		client_frame.pack();

		client_frame.setLocationRelativeTo(null);
	}

	/**
	 * Starting the {@link Client}
	 */
	private void startClient() {
		GameStub.provideLoaderApplet(this);
		Client c = new Client();
		c.init();
		c.start();
	}

	/**
	 * Populating the {@link #properties} map with important client properties
	 */
	public void setParams() {
		properties = new Properties();
		properties.put("cabbase", "g.cab");
		properties.put("java_arguments", "-Xmx102m -Dsun.java2d.noddraw=true");
		properties.put("colourid", "0");
		properties.put("worldid", "1");
		properties.put("lobbyid", "1");
		properties.put("lobbyaddress", Constants.IP);
		properties.put("demoid", "0");
		properties.put("demoaddress", "");
		properties.put("modewhere", "0");
		properties.put("modewhat", "0");
		properties.put("lang", "0");
		properties.put("objecttag", "0");
		properties.put("js", "1");
		properties.put("game", "0");
		properties.put("affid", "0");
		properties.put("advert", "1");
		properties.put("settings", "wwGlrZHF5gJcZl7tf7KSRh0MZLhiU0gI0xDX6DwZ-Qk");
		properties.put("country", "0");
		properties.put("haveie6", "0");
		properties.put("havefirefox", "1");
		properties.put("cookieprefix", "");
		properties.put("cookiehost", Constants.IP);
		properties.put("cachesubdirid", "0");
		properties.put("crashurl", "");
		properties.put("unsignedurl", "");
		properties.put("sitesettings_member", "1");
		properties.put("frombilling", "false");
		properties.put("sskey", "");
		properties.put("force64mb", "false");
		properties.put("worldflags", "8");
	}

	@Override
	public void init() {
		setParams();
		startClient();
	}

	@Override
	public String getParameter(String paramString) {
		return (String) properties.get(paramString);
	}

	@Override
	public URL getDocumentBase() {
		return getCodeBase();
	}

	@Override
	public URL getCodeBase() {
		try {
			return new URL("http://" + Constants.IP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {

	}

	@Override
	public void componentResized(ComponentEvent arg0) {

	}

	@Override
	public void componentShown(ComponentEvent arg0) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String command = e.getActionCommand();
			if (command.equalsIgnoreCase("Exit")) {
				System.exit(1);
			} else {
				System.out.println(command);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static String getWebClientCachePath() {
		return new StringBuilder(System.getProperty("user.home")).append(File.separator).append(Constants.NAME + "_Cache" + "v" + Constants.VERSION).toString();
	}

	public static boolean newInterface(int id) {
		if (id > 1144)
			return true;
		switch (id) {
		case 320:
		case 751:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Client theme information
	 */
	private JFrame client_frame;

	/**
	 * Client information
	 */
	public static boolean isRS = false;
	public static final boolean DISABLE_USELESS_PACKETS = false;
	public static int PORT_ID = 43594;
	private Properties properties;
}
