import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

class MainController extends Controller {
	Dxf dxf;
	JFrame frame;

	public MainController(Screen paramScreen, JFrame paramJFrame) {
		super(paramScreen);
		this.frame = paramJFrame;
		this.dxf = new Dxf();
		Settings.load();
	}

	public void processAction(ActionViewBinder paramActionViewBinder) {
		if (null == paramActionViewBinder)
			return;
		this.frame.setCursor(new Cursor(3));
		Object localObject1;
		Object localObject2;
		Object localObject3;
		if (paramActionViewBinder == MenuFactory.openDxf) {
			localObject1 = new JFileChooser(Settings.dxfFile);
			((JFileChooser) localObject1).setFileFilter(Utils.getFileFilter(
					"DXF Dateien", "dxf"));
			if (((JFileChooser) localObject1).showOpenDialog(Utils.parent) == 0) {
				this.screen.clearDxf();
				Settings.dxfFile = ((JFileChooser) localObject1)
						.getSelectedFile();
				this.dxf.load(Settings.dxfFile);
				localObject2 = this.dxf.getShapes();
				localObject3 = new AffineTransform();
				((AffineTransform) localObject3).scale(
						this.dxf.document.getUnits(),
						this.dxf.document.getUnits());
				Settings.dxfTransformation = (AffineTransform) localObject3;
				for (int i = 0; i < ((Vector) localObject2).size(); i++) {
					this.screen
							.addDxfItem(((AffineTransform) localObject3)
									.createTransformedShape((Shape) ((Vector) localObject2)
											.get(i)));
				}

				this.screen.viewAll();
			}

		} else if (paramActionViewBinder == MenuFactory.loadVehicle) {
			localObject1 = new JFileChooser(Settings.vehicleFile);
			((JFileChooser) localObject1).setFileFilter(Utils.getFileFilter(
					"Fahrzeug Dateien", "xml"));
			if (((JFileChooser) localObject1).showOpenDialog(Utils.parent) == 0) {
				Settings.vehicleFile = ((JFileChooser) localObject1)
						.getSelectedFile();
				localObject2 = new VehicleReader();
				((VehicleReader) localObject2).load(Settings.vehicleFile);
				((VehicleReader) localObject2).getVehicle().setPosition(
						this.screen.getCenter());
				this.screen.addVehicle(((VehicleReader) localObject2)
						.getVehicle());

				setShape(this.screen.selectItem(null));
				Controller.toolController.activateTool(MenuFactory.position);
			}

		} else if (paramActionViewBinder == MenuFactory.loadTrack) {
			localObject1 = new JFileChooser(Settings.dxfFile);
			((JFileChooser) localObject1).setFileFilter(Utils.getFileFilter(
					"Fahrspur Dateien", "trk"));
			if (((JFileChooser) localObject1).showOpenDialog(Utils.parent) == 0) {
				Settings.dxfFile = ((JFileChooser) localObject1)
						.getSelectedFile();
				this.screen.addTrack(Track.load(Settings.dxfFile));
				setShape(this.screen.selectItem(null));
				Controller.toolController.activateTool(MenuFactory.position);
			}

		} else if (paramActionViewBinder == MenuFactory.saveSelected) {
			localObject1 = getTrack();
			localObject2 = getDragShape();
			if (null != localObject1) {
				if (null == ((Track) localObject1).file) {
					localObject3 = new JFileChooser(Settings.dxfFile);
					((JFileChooser) localObject3).setFileFilter(Utils
							.getFileFilter("Fahrspur Dateien", "trk"));
					if (((JFileChooser) localObject3)
							.showSaveDialog(Utils.parent) == 0) {
						Settings.dxfFile = ((JFileChooser) localObject3)
								.getSelectedFile();

						if (!GenericFileFilter.getExtension(Settings.dxfFile)
								.equals("trk"))
							Settings.dxfFile = new File(
									Settings.dxfFile.getPath() + ".trk");
						if (Settings.dxfFile.exists()) {
							if (0 == Utils.askUser(
									"Bestehende Datei ersetzen?", null)) {
								((Track) localObject1).file = Settings.dxfFile;
								((Track) localObject1).save();
							}
						} else {
							((Track) localObject1).file = Settings.dxfFile;
							((Track) localObject1).save();
						}
					}
				} else {
					((Track) localObject1).save();
				}
			} else if (null != localObject2) {
				localObject3 = new JFileChooser(Settings.dxfFile);
				((JFileChooser) localObject3).setFileFilter(Utils
						.getFileFilter("DXF Dateien", "dxf"));
				if (((JFileChooser) localObject3).showSaveDialog(Utils.parent) == 0) {
					Settings.dxfFile = ((JFileChooser) localObject3)
							.getSelectedFile();

					if (!GenericFileFilter.getExtension(Settings.dxfFile)
							.equals("dxf"))
						Settings.dxfFile = new File(Settings.dxfFile.getPath()
								+ ".dxf");
					try {
						if (Settings.dxfFile.exists()) {
							if (0 == Utils.askUser(
									"Bestehende Datei ersetzen?", null)) {
								((DragShape) localObject2).save(
										Settings.dxfFile,
										Settings.dxfTransformation
												.createInverse());
							}
						} else
							((DragShape) localObject2).save(Settings.dxfFile,
									Settings.dxfTransformation.createInverse());
					} catch (NoninvertibleTransformException localNoninvertibleTransformException) {
						Utils.showError(
								"Schleppkurve kann nicht in DXF Koordinatensystem transformiert werden!",
								null);
						System.out
								.println(localNoninvertibleTransformException);
					}
				}
			} else {
				Utils.showError("Keine Fahrspur oder Schleppkurve ausgewählt.",
						null);
			}

		} else if (paramActionViewBinder == MenuFactory.clearDxf) {
			this.screen.clearDxf();
			this.screen.viewAll();
		} else if (paramActionViewBinder == MenuFactory.clearAll) {
			this.screen.clearAll();
			setShape(this.screen.selectItem(null));
			this.screen.viewAll();
		} else if (paramActionViewBinder == MenuFactory.removeItem) {
			if (null != getTrack()) {
				if (getTrack().removePoint()) {
					this.screen.removeItem(getShape());
					setShape(this.screen.selectItem(null));
				}
			} else {
				this.screen.removeItem(getShape());
				setShape(this.screen.selectItem(null));
			}

		} else if (paramActionViewBinder == MenuFactory.dxfScale) {
			localObject1 = (AffineTransform) Settings.dxfTransformation.clone();
			localObject2 = DxfScaleDialog.initialize(Utils.parent,
					Settings.dxfTransformation, "DXF Masstab");
			Settings.dxfTransformation = ((DxfScaleDialog) localObject2)
					.showDialog(null);
			if (null == Settings.dxfTransformation) {
				Settings.dxfTransformation = (AffineTransform) localObject1;
			} else {
				this.screen.clearDxf();
				localObject3 = this.dxf.getShapes();
				for (int j = 0; j < ((Vector) localObject3).size(); j++)
					this.screen
							.addDxfItem(Settings.dxfTransformation
									.createTransformedShape((Shape) ((Vector) localObject3)
											.get(j)));
			}
		} else {
			Object localObject4;
			if (paramActionViewBinder == MenuFactory.driveAlongTrack) {
				localObject1 = getTrack();
				localObject2 = this.screen.getVehicles();
				if (null == localObject1) {
					Utils.showError("Keine Spur ausgewählt.", null);
				} else if (1 > ((Vector) localObject2).size()) {
					Utils.showError("Keine Fahrzeuge geöffnet.", null);
				} else {
					localObject3 = null;

					if (1 < ((Vector) localObject2).size()) {
						localObject4 = new HashMap();
						for (int k = 0; k < ((Vector) localObject2).size(); k++)
							((HashMap) localObject4)
									.put(((Vehicle) ((Vector) localObject2)
											.get(k)).name,
											((Vector) localObject2).get(k));
						SelectObjectDialog localSelectObjectDialog = SelectObjectDialog
								.initialize(Utils.parent,
										(HashMap) localObject4,
										"Fahrzeugauswahl",
										"Vorhandene Fahrzeuge:");
						localObject3 = (Vehicle) localSelectObjectDialog
								.showDialog(null, null);
					} else {
						localObject3 = (Vehicle) ((Vector) localObject2).get(0);
					}
					if (null != localObject3) {
						localObject4 = new DragShape((Vehicle) localObject3);
						((Vehicle) localObject3)
								.setDragShape((DragShape) localObject4);
						this.screen
								.addDragShape((ClearTrackShape) localObject4);
						// ((Track) localObject1).doTracking((Vehicle)
						// localObject3);
						// --------------------------Neu-------------------------------------
						((Track) localObject1).doTracking(
								(Vehicle) localObject3, Settings.calMethod);
						// --------------------------Neu-------------------------------------
						((DragShape) localObject4).finishShape();
					}
				}

			} else if (paramActionViewBinder == MenuFactory.showTurnDiagram) {
				if (null != Controller.getDragShape())
					Controller.getDragShape().showTurnDiagram();
				else if(null != Controller.getVehicle())
				{
					final HtmlDialog dialog = new HtmlDialog("Fahrzeug -Eigenschaften: "
							+ Controller.getVehicle().getName());
					dialog.setIconImage(Utils.icon);
					dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					dialog.setSize(new Dimension(300, 600));
					dialog.setContent(Controller.getVehicle().transformToHtml());
					dialog.setVisible(true);
				}
				else
					Utils.showError("Keine Schleppkurve oder Fahrzeug ausgewählt.", null);
			} else if (paramActionViewBinder == MenuFactory.exitClearTrack) {
				if (canClose())
					System.exit(0);
			} else if (paramActionViewBinder == MenuFactory.applicationInfo) {
				localObject1 = "draft";
				try {
					localObject2 = new JarInputStream(new FileInputStream(
							"ClearTrack.jar"));
					localObject3 = ((JarInputStream) localObject2)
							.getManifest();
					if (null != localObject3) {
						localObject4 = ((Manifest) localObject3)
								.getMainAttributes();
						localObject1 = ((Attributes) localObject4)
								.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
					}
				} catch (IOException localException) {
					System.out.println(localException);
				}

				Utils.showInformation(
						"\nClearTrack zur Visualisierung des\nPlatzbedarfs von Fahrzeugen im\nurbanen Raum.\n\n\nVersion "
								+ (String) localObject1
								+ "\n"
								+ "\nContributors:"
								+ "\nKurt Minder"
								+ "\nJörg Sothmann"
								+ "\n"
								+ "\nCopyright (c) 2002 - 2016 ByteStore"
								+ "\nSoftware engineering"
								+ "\nhttp://www.bytestore.ch/"
								+ "\n\nThis product includes software developed by the"
								+ "\nApache Software Foundation (http://www.apache.org/).",
						"About ClearTrack");
			}

			// --------------------------Neu-------------------------------------
			else if (paramActionViewBinder == MenuFactory.verHalter) {
				Settings.calMethod = new CalculationMethodHalter();
			} else if (paramActionViewBinder == MenuFactory.verHauska) {
				Settings.calMethod = new CalculationMethodHauska();
			} else if (paramActionViewBinder == MenuFactory.verSchneider) {
				Settings.calMethod = new CalculationMethodSchneider();
			} else if (paramActionViewBinder == MenuFactory.verTenner) {
				Settings.calMethod = new CalculationMethodTenner();
			} else if (paramActionViewBinder == MenuFactory.verHammer) {
				Settings.calMethod = new CalculationMethodHammer();
			} else if (paramActionViewBinder == MenuFactory.verEverlingSchoss) {
				Settings.calMethod = new CalculationMethodEverlingSchoss();
			}
			// --------------------------Neu-------------------------------------

		}

		this.screen.repaint();
		this.frame.setCursor(new Cursor(0));
	}

	public void listenerDone() {
	}

	boolean canClose() {
		Settings.save();

		return 0 == Utils.askUser("ClearTrack schon schliessen?", null);
	}
}
