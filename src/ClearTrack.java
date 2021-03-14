import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ClearTrack extends JFrame {
	MainController mainController;

	public ClearTrack() {
		super("ClearTrack");
		String pfad = new String("CT.png");
		Utils.icon = this.getToolkit().getImage(
				getClass().getClassLoader().getResource(pfad));
		this.setIconImage(Utils.icon);

		Screen localScreen = new Screen();

		localScreen.setLayout(new BorderLayout());

		ToolController localToolController = new ToolController(localScreen);
		this.mainController = new MainController(localScreen, this);
		this.mainController.screen = localScreen;

		JToolBar localJToolBar = new JToolBar();
		JMenu localJMenu1 = new JMenu("Datei");
		JMenu localJMenu2 = new JMenu("Bearbeiten");
		JMenu localJMenu3 = new JMenu("Tools");
		JMenu localJMenu4 = new JMenu("Zoom");
		JMenu localJMenu5 = new JMenu("Hilfe");

		MenuFactory localMenuFactory = new MenuFactory();

		localMenuFactory.createFileMenu(localJToolBar, localJMenu1,
				this.mainController);
		localJToolBar.addSeparator();
		localMenuFactory.createEditMenu(localJToolBar, localJMenu2,
				this.mainController);
		localJToolBar.addSeparator();
		localMenuFactory.createZoomMenu(localJToolBar, localJMenu4,
				localToolController);
		localJToolBar.addSeparator();
		localMenuFactory.createToolsMenu(localJToolBar, localJMenu3,
				localToolController);
		localJToolBar.addSeparator();
		localMenuFactory.createHelpMenu(localJToolBar, localJMenu5,
				this.mainController);

		JMenuBar localJMenuBar = new JMenuBar();
		localJMenuBar.add(localJMenu1);
		localJMenuBar.add(localJMenu2);
		localJMenuBar.add(localJMenu4);
		localJMenuBar.add(localJMenu3);

		// --------------------------Neu-------------------------------------
		// Dies kann gerne besser strukturiert werden. So waren weniger
		// genänderte Absätze
		JMenu localJMenu6 = new JMenu("Rechenverfahren");
		localJToolBar.addSeparator();
		localMenuFactory.createVerfahrenMenu(localJToolBar, localJMenu6,
				this.mainController);
		localJMenuBar.add(localJMenu6);
		// --------------------------Neu-------------------------------------

		localJMenuBar.add(localJMenu5);
		setJMenuBar(localJMenuBar);

		JPanel localJPanel = new JPanel();
		localJPanel.setBackground(Color.white);
		localJPanel.setLayout(new BorderLayout());
		localJPanel.setPreferredSize(new Dimension(800, 800));
		localJPanel.add(localJToolBar, "North");
		localJPanel.add(localScreen, "Center");
		setContentPane(localJPanel);

		setDefaultCloseOperation(0);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
				if (ClearTrack.this.mainController.canClose())
					System.exit(0);
			}

		});
	}

	public static void main(String[] paramArrayOfString) {
		final ClearTrack localClearTrack = new ClearTrack();
		Utils.parent = localClearTrack;
		// Splashscreen wird automatisch angezeigt und geschlossen wenn:
		// -splash:icon/ClearTrack.png bei Eclipse VM Arguments angegeben wird 
		// oder 
		// SplashScreen-Image: ClearTrack.png im Manifest definiert ist

		localClearTrack.pack();
		localClearTrack.setLocationRelativeTo(null); // Fenster zentrieren
		localClearTrack.setVisible(true);

	}

}