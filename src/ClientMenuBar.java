import java.awt.Desktop;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * @author Lazarus <lazarus.rs.king@gmail.com>
 * @since Jul 21, 2014
 */
@SuppressWarnings("serial")
public class ClientMenuBar extends JMenuBar implements ActionListener {

	public ClientMenuBar() {
		file = new JMenu("File");
		file.getPopupMenu().setLightWeightPopupEnabled(false);
		add(file);

		exit = new JMenuItem("Exit");
		file.add(exit);

		links = new JMenu("Links");
		links.getPopupMenu().setLightWeightPopupEnabled(false);
		add(links);

		forums = new JMenuItem("Community");
		vote = new JMenuItem("Vote");
		highscores = new JMenuItem("Highscores");
		advertise = new JMenuItem("Advertise");

		links.add(forums);
		links.add(vote);
		links.add(highscores);
		links.add(advertise);

		screenshot = new JButton("Screenshot");
		add(screenshot);

		setAction(exit, forums, vote, highscores, advertise, screenshot);
	}

	private void setAction(AbstractButton... button) {
		for (AbstractButton b : button) {
			b.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();
		if (name.equalsIgnoreCase("exit")) {
			System.exit(0);
		} else if (name.equalsIgnoreCase("community")) {
			openURL("http://scapesoft.org/community/index.php");
		} else if (name.equalsIgnoreCase("vote")) {
			openURL("http://scapesoft.org/community/index.php?pages/Vote/");
		} else if (name.equalsIgnoreCase("highscores")) {
			openURL("http://scapesoft.org/app/highscores/#");
		} else if (name.equalsIgnoreCase("advertise")) {
			openURL(Constants.PROMOTION_PAGE);
		} else if (name.equalsIgnoreCase("screenshot")) {
			File image = takeScreenshot();
			if (image != null) {
				int choice = JOptionPane.showOptionDialog(null, "View image?", "Screenshot Saved", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Yes", "No"}, "No");
				if (choice == 0) {
					try {
						Desktop.getDesktop().open(image.getParentFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		} else {
			System.out.println(name);
		}
	}

	private void openURL(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes a screenshot and saves it in the scapesoft user home directory. The
	 * screenshot {@code File} {@code Object} will be returned on successful
	 * saving.
	 * 
	 * @return
	 */
	public File takeScreenshot() {
		try {
			Window window = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusedWindow();
			Point point = window.getLocationOnScreen();
			int x = (int) point.getX();
			int y = (int) point.getY();
			int w = window.getWidth();
			int h = window.getHeight();
			Robot robot = new Robot(window.getGraphicsConfiguration().getDevice());
			Rectangle captureSize = new Rectangle(x, y, w, h);
			java.awt.image.BufferedImage bufferedimage = robot.createScreenCapture(captureSize);

			StringBuilder bldr = new StringBuilder();
			bldr.append(System.getProperty("user.home") + "/scapesoft_data/screenshots/");
			File directory = new File(bldr.toString());
			if (!directory.exists())
				directory.mkdirs();
			int files = directory.list().length;

			File file = new File(bldr.append("Screenshot " + (files + 1) + ".png").toString());
			ImageIO.write(bufferedimage, "png", file);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private final JMenu file, links;
	private final JMenuItem exit, forums, vote, highscores, advertise;
	private final JButton screenshot;

}
