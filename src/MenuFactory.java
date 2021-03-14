import java.awt.Cursor;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class MenuFactory {
	static ActionViewBinder exitClearTrack;
	static ActionViewBinder openDxf;
	static ActionViewBinder loadVehicle;
	static ActionViewBinder loadTrack;
	static ActionViewBinder saveSelected;
	static ActionViewBinder viewAll;
	static ActionViewBinder clearDxf;
	static ActionViewBinder clearAll;
	static ActionViewBinder dxfScale;
	static ActionViewBinder removeItem;
	static ActionViewBinder driveAlongTrack;
	static ActionViewBinder showTurnDiagram;
	static ActionViewBinder track;
	static ActionViewBinder orientation;
	static ActionViewBinder position;
	static ActionViewBinder zoomRange;
	static ActionViewBinder zoomPosition;
	static ActionViewBinder select;
	static ActionViewBinder trackCreator;
	static ActionViewBinder pointInserter;
	static ActionViewBinder applicationInfo;
	//--------------------------Neu-------------------------------------
		static ActionViewBinder verHalter;
		static ActionViewBinder verHauska;
		static ActionViewBinder verSchneider;
		static ActionViewBinder verTenner;
		static ActionViewBinder verHammer;
		static ActionViewBinder verEverlingSchoss;
	//--------------------------Neu-------------------------------------

	public void createFileMenu(JToolBar paramJToolBar, JMenu paramJMenu,
			Controller paramController) {
		URL localURL = null;
		JButton localJButton = null;

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Open24.gif");
		openDxf = new ActionViewBinder(paramController, null);
		openDxf.action = new ClearTrackAction("DXF öffnen", new ImageIcon(
				localURL), openDxf);
		openDxf.menuItem = paramJMenu.add(openDxf.action);
		openDxf.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(openDxf.action);
		localJButton.setToolTipText("DXF öffnen");
		localJButton.setText(null);

		paramJMenu.addSeparator();

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Open24.gif");
		loadVehicle = new ActionViewBinder(paramController, null);
		loadVehicle.action = new ClearTrackAction("Fahrzeug öffen",
				new ImageIcon(localURL), loadVehicle);
		loadVehicle.menuItem = paramJMenu.add(loadVehicle.action);
		loadVehicle.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(loadVehicle.action);
		localJButton.setToolTipText("Fahrzeug öffen");
		localJButton.setText(null);

		paramJMenu.addSeparator();

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Open24.gif");
		loadTrack = new ActionViewBinder(paramController, null);
		loadTrack.action = new ClearTrackAction("Fahrspur öffnen",
				new ImageIcon(localURL), loadTrack);
		loadTrack.menuItem = paramJMenu.add(loadTrack.action);
		loadTrack.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(loadTrack.action);
		localJButton.setToolTipText("Fahrspur öffnen");
		localJButton.setText(null);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Save24.gif");
		saveSelected = new ActionViewBinder(paramController, null);
		saveSelected.action = new ClearTrackAction("Sichern", new ImageIcon(
				localURL), saveSelected);
		saveSelected.menuItem = paramJMenu.add(saveSelected.action);
		saveSelected.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(saveSelected.action);
		localJButton.setToolTipText("Fahrspur sichern");
		localJButton.setText(null);
		saveSelected.setEnabled(false);

		paramJMenu.addSeparator();

		exitClearTrack = new ActionViewBinder(paramController, null);
		exitClearTrack.action = new ClearTrackAction("ClearTrack schliessen",
				null, exitClearTrack);
		exitClearTrack.menuItem = paramJMenu.add(exitClearTrack.action);
		exitClearTrack.menuItem.setIcon(null);
	}

	public void createEditMenu(JToolBar paramJToolBar, JMenu paramJMenu,
			Controller paramController) {
		URL localURL = null;
		JButton localJButton = null;

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Remove24.gif");
		removeItem = new ActionViewBinder(paramController, null);
		removeItem.action = new ClearTrackAction(
				"Ausgewähltes Object entfernen", new ImageIcon(localURL),
				removeItem);
		removeItem.menuItem = paramJMenu.add(removeItem.action);
		removeItem.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(removeItem.action);
		localJButton.setToolTipText("Ausgewähltes Object entfernen");
		localJButton.setText(null);
		removeItem.setEnabled(false);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Remove24.gif");
		clearDxf = new ActionViewBinder(paramController, null);
		clearDxf.action = new ClearTrackAction("DXF Zeichnung entfernen",
				new ImageIcon(localURL), clearDxf);
		clearDxf.menuItem = paramJMenu.add(clearDxf.action);
		clearDxf.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(clearDxf.action);
		localJButton.setToolTipText("DXF Zeichnung entfernen");
		localJButton.setText(null);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Remove24.gif");
		clearAll = new ActionViewBinder(paramController, null);
		clearAll.action = new ClearTrackAction("Alles entfernen",
				new ImageIcon(localURL), clearAll);
		clearAll.menuItem = paramJMenu.add(clearAll.action);
		clearAll.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(clearAll.action);
		localJButton.setToolTipText("Alles entfernen");
		localJButton.setText(null);

		paramJMenu.addSeparator();
		paramJToolBar.addSeparator();

		localURL = getClass().getResource(
				"toolbarButtonGraphics/media/Movie24.gif");
		driveAlongTrack = new ActionViewBinder(paramController, null);
		driveAlongTrack.action = new ClearTrackAction(
				"Entlang Fahrspur fahren", new ImageIcon(localURL),
				driveAlongTrack);
		driveAlongTrack.menuItem = paramJMenu.add(driveAlongTrack.action);
		driveAlongTrack.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(driveAlongTrack.action);
		localJButton.setToolTipText("Entlang Fahrspur fahren");
		localJButton.setText(null);
		driveAlongTrack.setEnabled(false);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Information24.gif");
		showTurnDiagram = new ActionViewBinder(paramController, null);
		showTurnDiagram.action = new ClearTrackAction(
				"Einschlag - Weg Diagramm", new ImageIcon(localURL),
				showTurnDiagram);
		showTurnDiagram.menuItem = paramJMenu.add(showTurnDiagram.action);
		showTurnDiagram.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(showTurnDiagram.action);
		localJButton.setToolTipText("Einschlag - Weg Diagramm");
		localJButton.setText(null);
		showTurnDiagram.setEnabled(false);

		paramJMenu.addSeparator();

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Edit24.gif");
		dxfScale = new ActionViewBinder(paramController, null);
		dxfScale.action = new ClearTrackAction("DXF Massstab", new ImageIcon(
				localURL), dxfScale);
		dxfScale.menuItem = paramJMenu.add(dxfScale.action);
		dxfScale.menuItem.setIcon(null);
	}

	public void createZoomMenu(JToolBar paramJToolBar, JMenu paramJMenu,
			Controller paramController) {
		URL localURL = null;
		JButton localJButton = null;

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/ZoomOut24.gif");
		viewAll = new ActionViewBinder(paramController, null);
		viewAll.action = new ClearTrackAction("Alles ansehen", new ImageIcon(
				localURL), viewAll);
		viewAll.menuItem = paramJMenu.add(viewAll.action);
		viewAll.menuItem.setIcon(null);
		localJButton = paramJToolBar.add(viewAll.action);
		localJButton.setToolTipText("Alles ansehen");
		localJButton.setText(null);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/ZoomIn24.gif");
		zoomRange = new ActionViewBinder(paramController,
				new ZoomRangeListener(paramController));
		zoomRange.action = new ClearTrackAction("Zoom",
				new ImageIcon(localURL), zoomRange);
		zoomRange.button = new JToggleButton(zoomRange.action);
		zoomRange.button.setText(null);
		paramJToolBar.add(zoomRange.button);
		zoomRange.button.setToolTipText("Zoom");
		zoomRange.menuItem = paramJMenu.add(zoomRange.action);
		zoomRange.cursor = new Cursor(6);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/AlignCenter24.gif");
		zoomPosition = new ActionViewBinder(paramController,
				new ZoomPositionListener(paramController));
		zoomPosition.action = new ClearTrackAction("Zoom position",
				new ImageIcon(localURL), zoomPosition);
		zoomPosition.button = new JToggleButton(zoomPosition.action);
		zoomPosition.button.setText(null);
		paramJToolBar.add(zoomPosition.button);
		zoomPosition.button.setToolTipText("Zoom position");
		zoomPosition.menuItem = paramJMenu.add(zoomPosition.action);
		zoomPosition.cursor = new Cursor(13);
	}

	public void createToolsMenu(JToolBar paramJToolBar, JMenu paramJMenu,
			Controller paramController) {
		URL localURL = null;

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/ContextualHelp24.gif");
		select = new ActionViewBinder(paramController, new SelectListener(
				paramController));
		select.action = new ClearTrackAction("Auswahl",
				new ImageIcon(localURL), select);
		select.button = new JToggleButton(select.action);
		select.button.setText(null);
		paramJToolBar.add(select.button);
		select.button.setToolTipText("Object auswählen");
		select.menuItem = paramJMenu.add(select.action);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Export24.gif");
		position = new ActionViewBinder(paramController, new PositionListener(
				paramController));
		position.action = new ClearTrackAction("Positionieren", new ImageIcon(
				localURL), position);
		position.button = new JToggleButton(position.action);
		position.button.setText(null);
		paramJToolBar.add(position.button);
		position.button.setToolTipText("Ausgewältes Objekt positionieren");
		position.menuItem = paramJMenu.add(position.action);
		position.cursor = new Cursor(1);
		position.setEnabled(false);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/media/StepForward24.gif");
		track = new ActionViewBinder(paramController, new TrackListener(
				paramController));
		track.action = new ClearTrackAction("Fahren (Maus geführt)",
				new ImageIcon(localURL), track);
		track.button = new JToggleButton(track.action);
		track.button.setText(null);
		paramJToolBar.add(track.button);
		track.button.setToolTipText("Fahren (Maus geführt)");
		track.menuItem = paramJMenu.add(track.action);
		track.cursor = new Cursor(12);
		track.setEnabled(false);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Refresh24.gif");
		orientation = new ActionViewBinder(paramController,
				new OrientationListener(paramController));
		orientation.action = new ClearTrackAction("Orientieren", new ImageIcon(
				localURL), orientation);
		orientation.button = new JToggleButton(orientation.action);
		orientation.button.setText(null);
		paramJToolBar.add(orientation.button);
		orientation.button.setToolTipText("Fahrzeug orientieren");
		orientation.menuItem = paramJMenu.add(orientation.action);
		orientation.cursor = new Cursor(12);
		orientation.setEnabled(false);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/development/JarAdd24.gif");
		trackCreator = new ActionViewBinder(paramController, new TrackCreator(
				paramController));
		trackCreator.action = new ClearTrackAction("Neue Fahrspur",
				new ImageIcon(localURL), trackCreator);
		trackCreator.button = new JToggleButton(trackCreator.action);
		trackCreator.button.setText(null);
		paramJToolBar.add(trackCreator.button);
		trackCreator.button.setToolTipText("Neue Fahrspur");
		trackCreator.menuItem = paramJMenu.add(trackCreator.action);
		trackCreator.cursor = new Cursor(12);

		localURL = getClass().getResource(
				"toolbarButtonGraphics/development/BeanAdd24.gif");
		pointInserter = new ActionViewBinder(paramController,
				new PointInserter(paramController));
		pointInserter.action = new ClearTrackAction("Punkt einfügen",
				new ImageIcon(localURL), pointInserter);
		pointInserter.button = new JToggleButton(pointInserter.action);
		pointInserter.button.setText(null);
		paramJToolBar.add(pointInserter.button);
		pointInserter.button.setToolTipText("Punkt einfügen");
		pointInserter.menuItem = paramJMenu.add(pointInserter.action);
		pointInserter.cursor = new Cursor(1);
		pointInserter.setEnabled(false);
	}

	public void createHelpMenu(JToolBar paramJToolBar, JMenu paramJMenu,
			Controller paramController) {
		JButton localJButton = null;
		URL localURL = getClass().getResource(
				"toolbarButtonGraphics/general/Information24.gif");
		applicationInfo = new ActionViewBinder(paramController, null);
		applicationInfo.action = new ClearTrackAction("Über ClearTrack",
				new ImageIcon(localURL), applicationInfo);
		applicationInfo.menuItem = paramJMenu.add(applicationInfo.action);
		localJButton = paramJToolBar.add(applicationInfo.action);
		localJButton.setToolTipText("Über ClearTrack");
		localJButton.setText(null);
	}
	
	//--------------------------Neu-------------------------------------
	public void createVerfahrenMenu(JToolBar paramJToolBar, JMenu paramJMenu, Controller paramController)
	{	
		ButtonGroup bg = new ButtonGroup();
		
		verHalter = new ActionViewBinder(paramController, null);
		verHalter.action = new ClearTrackAction("nach Halter", null, verHalter);
		verHalter.menuItem = new JRadioButtonMenuItem(verHalter.action);
		bg.add(verHalter.menuItem);
		paramJMenu.add(verHalter.menuItem);
		
		verHauska = new ActionViewBinder(paramController, null);
		verHauska.action = new ClearTrackAction("nach Hauska", null, verHauska);
		verHauska.menuItem = new JRadioButtonMenuItem(verHauska.action);
		bg.add(verHauska.menuItem);	
		paramJMenu.add(verHauska.menuItem);
		
		verSchneider = new ActionViewBinder(paramController, null);
		verSchneider.action = new ClearTrackAction("nach Schneider", null, verSchneider);
		verSchneider.menuItem = new JRadioButtonMenuItem(verSchneider.action);
		bg.add(verSchneider.menuItem);
		paramJMenu.add(verSchneider.menuItem);
		
		
		verTenner = new ActionViewBinder(paramController, null);
		verTenner.action = new ClearTrackAction("nach Tenner", null, verTenner);
		verTenner.menuItem = new JRadioButtonMenuItem(verTenner.action);
		bg.add(verTenner.menuItem);
		paramJMenu.add(verTenner.menuItem);
		
		
		verHammer = new ActionViewBinder(paramController, null);
		verHammer.action = new ClearTrackAction("nach Hammer", null, verHammer);
		verHammer.menuItem = new JRadioButtonMenuItem(verHammer.action);
		bg.add(verHammer.menuItem);
		paramJMenu.add(verHammer.menuItem);
		
		verEverlingSchoss = new ActionViewBinder(paramController, null);
		verEverlingSchoss.action = new ClearTrackAction("nach Everling/Schoss", null, verEverlingSchoss);
		verEverlingSchoss.menuItem = new JRadioButtonMenuItem(verEverlingSchoss.action);
		bg.add(verEverlingSchoss.menuItem);
		paramJMenu.add(verEverlingSchoss.menuItem);
		
		
		if (Settings.calMethod instanceof CalculationMethodHalter)
			verHalter.setSelected(true);
		else if (Settings.calMethod instanceof CalculationMethodHauska)
			verHauska.setSelected(true);
		else if (Settings.calMethod instanceof CalculationMethodSchneider)
			verSchneider.setSelected(true);
		else if (Settings.calMethod instanceof CalculationMethodTenner)
			verTenner.setSelected(true);
		else if (Settings.calMethod instanceof CalculationMethodHammer)
			verHammer.setSelected(true);
		else if (Settings.calMethod instanceof CalculationMethodEverlingSchoss)
			verEverlingSchoss.setSelected(true);
		else{
			Settings.calMethod =new CalculationMethodTenner();
			verTenner.setSelected(true);}
	}

//--------------------------Neu-------------------------------------
}
