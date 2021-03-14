import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DragShape extends ClearTrackShapeAdapter {
	private Area area;
	Vehicle vehicle;
	HashMap owners;
	double maxTurn;
	Vector increments;

	DragShape(Vehicle paramVehicle) {
		this.area = null;
		this.vehicle = paramVehicle;
		this.owners = new HashMap();
		this.increments = new Vector();
		this.maxTurn = 0.0D;
	}

	Area getArea() {
		return this.area;
	}

	void addTrackIncrement(double paramDouble1, double paramDouble2) {
		this.increments.add(new TrackIncrement(paramDouble1, paramDouble2));
		if (Math.abs(paramDouble2) > this.maxTurn)
			this.maxTurn = Math.abs(paramDouble2);
	}

	void save(File paramFile, AffineTransform paramAffineTransform) {
		BufferedReader localBufferedReader = null;
		BufferedWriter localBufferedWriter = null;
		try {
			localBufferedReader = new BufferedReader(new FileReader(new File(
					"dxf.tpl")));
			localBufferedWriter = new BufferedWriter(new FileWriter(paramFile));
			String str = localBufferedReader.readLine();
			while (!str.equals("ENTITIES")) {
				localBufferedWriter.write(str, 0, str.length());
				localBufferedWriter.newLine();
				str = localBufferedReader.readLine();
			}
			localBufferedWriter.write(str, 0, str.length());

			str = "\n  0\nPOLYLINE\n  8\n_ClearTrack\n  6\nContinuous\n 62\n7\n 70\n1\n 66\n1";
			localBufferedWriter.write(str, 0, str.length());

			PathIterator localPathIterator = this.area
					.getPathIterator(paramAffineTransform);
			while (!localPathIterator.isDone()) {
				double[] arrayOfDouble = { 0.0D, 0.0D };
				int i = localPathIterator.currentSegment(arrayOfDouble);
				if ((1 == i) || (0 == i)) {
					str = "\n  0\nVERTEX\n  8\n1\n 10\n";
					str = str + Utils.round(arrayOfDouble[0], 2);
					str = str + "\n 20\n";
					str = str + Utils.round(arrayOfDouble[1], 2);
					str = str + "\n 30\n0";
					localBufferedWriter.write(str, 0, str.length());
				}
				localPathIterator.next();
			}
			str = "\n  0\nSEQEND\n";
			localBufferedWriter.write(str, 0, str.length());

			str = localBufferedReader.readLine();
			while (!str.equals("EOF")) {
				localBufferedWriter.write(str, 0, str.length());
				localBufferedWriter.newLine();
				str = localBufferedReader.readLine();
			}
			localBufferedWriter.write(str, 0, str.length());
			localBufferedWriter.newLine();
			localBufferedWriter.close();
			localBufferedReader.close();
		} catch (FileNotFoundException localFileNotFoundException) {
			Utils.showError("DXF Vorlage (dxf.tpl) nicht gefunden!", null);
			System.out.println(localFileNotFoundException);
		} catch (IOException localIOException) {
			Utils.showError(localIOException, null);
			System.out.println(localIOException);
		}
	}

	void addDragShapeItem(Shape paramShape, ClearTrackShape paramClearTrackShape) {
		if (null != paramShape) {
			Vector localVector1 = null;
			if (this.owners.containsKey(paramClearTrackShape)) {
				localVector1 = (Vector) this.owners.get(paramClearTrackShape);
			} else {
				localVector1 = new Vector();
				this.owners.put(paramClearTrackShape, localVector1);
			}
			int i = localVector1.size();

			if (null != this.area) {
				this.area.add(new Area(paramShape));
			} else {
				this.area = new Area(paramShape);
			}
			int j = 0;
			PathIterator localPathIterator = paramShape.getPathIterator(null);
			while (!localPathIterator.isDone()) {
				float[] arrayOfFloat = { 0.0F, 0.0F };
				int k = localPathIterator.currentSegment(arrayOfFloat);
				if ((1 == k) || (0 == k)) {
					Vector localVector2 = null;
					if (i > j) {
						localVector2 = (Vector) localVector1.get(j);
					} else {
						localVector2 = new Vector(4, 2);
						localVector1.add(localVector2);
					}

					localVector2.add(arrayOfFloat);
					j++;
				}
				localPathIterator.next();
			}
		}
	}

	void finishShape() {
		try {
			Collection localCollection = this.owners.values();
			Iterator localIterator = localCollection.iterator();
			while (true) {
				Vector localVector1 = (Vector) localIterator.next();

				for (int i = 0; localVector1.size() / 2 > i; i++) {
					GeneralPath localGeneralPath = new GeneralPath();

					Vector localVector2 = (Vector) localVector1.get(i);

					float[] arrayOfFloat = (float[]) localVector2.get(0);
					localGeneralPath.moveTo(arrayOfFloat[0], arrayOfFloat[1]);
					for (int j = 1; localVector2.size() > j; j++) {
						arrayOfFloat = (float[]) localVector2.get(j);
						localGeneralPath.lineTo(arrayOfFloat[0],
								arrayOfFloat[1]);
					}

					localVector2 = (Vector) localVector1.get(localVector1
							.size() - i - 1);
					for (int k = localVector2.size() - 1; -1 < k; k--) {
						arrayOfFloat = (float[]) localVector2.get(k);
						localGeneralPath.lineTo(arrayOfFloat[0],
								arrayOfFloat[1]);
					}

					localGeneralPath.closePath();

					if (null != this.area)
						this.area.add(new Area(localGeneralPath));
					else
						this.area = new Area(localGeneralPath);
				}
			}
		} catch (Exception localException) {
		}
	}

	public void paint(Graphics2D paramGraphics2D) {
		if (null != this.area)
			paramGraphics2D.draw(this.area);
	}

	public void evaluateRange(ShapeRange paramShapeRange) {
		paramShapeRange.evaluateRange(this.area);
	}

	public boolean contains(Point2D.Double paramDouble) {
		if (null != this.area)
			return this.area.contains(paramDouble);
		return false;
	}

	public String getInfo() {
		return this.vehicle.name + ". Max Einschlag = "
				+ Utils.round(Math.toDegrees(this.vehicle.maxTurn), 1)
				+ "°. Maximum in Schleppkurve = "
				+ Utils.round(Math.toDegrees(this.maxTurn), 1) + "°.";
	}

	void showTurnDiagram() {
		DragShape.TurnAngleScreen localTurnAngleScreen = new DragShape.TurnAngleScreen();
		final JFrame localJFrame = new JFrame("Einschlag - Diagramm, "
				+ this.vehicle.name);
		localJFrame.setIconImage(Utils.icon);
		localJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel unten = new JPanel();
		unten.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton export = new JButton("Excel-Export");
		JButton close = new JButton("Schließen");
		unten.add(export);
		unten.add(close);

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				localJFrame.dispose();
			}
		});

		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser xls = new JFileChooser(Settings.dxfFile);
				xls.setFileFilter(Utils.getFileFilter("Excel Dateien (.xls)",
						"xls"));
				if (xls.showSaveDialog(Utils.parent) == 0) {
					Settings.xlsFile = xls.getSelectedFile();
					if (!GenericFileFilter.getExtension(Settings.xlsFile)
							.equals("xls"))
						Settings.xlsFile = new File(Settings.xlsFile.getPath()
								+ ".xls");

					Settings.save();
					if (Settings.xlsFile.exists()) {
						if (0 == Utils.askUser("Bestehende Datei ersetzen?",
								null)) {
							writeExcel(Settings.xlsFile);
						}
					} else {
						writeExcel(Settings.xlsFile);
					}
				}
			}

			private void writeExcel(File f) {

				// Ausgabedatei ereugen
				HSSFWorkbook wb = null;
				FileOutputStream out = null;
				try {
					out = new FileOutputStream(f);

					// Neue Mappe erzeugen
					wb = new HSSFWorkbook();
					// Neue Tabelle erzeugen
					HSSFSheet sh = wb.createSheet();

					double d = 0.0;
					double angle = 0.0;

					// Zeile erzeugen
					HSSFRow row = sh.createRow(0);
					// Zelle erzeugen
					HSSFCell cell = row.createCell(0);
					cell.setCellValue(DragShape.this.vehicle.name);
					cell = row.createCell(1);
					cell.setCellValue("maximaler Einschlag:");
					cell = row.createCell(2);
					cell.setCellValue(Math
							.toDegrees(DragShape.this.vehicle.maxTurn));
					row = sh.createRow(1);
					cell = row.createCell(0);
					// Werte schreiben
					cell.setCellValue("Wegstrecke [m]");
					// Zelle erzeugen
					cell = row.createCell(1);
					// Werte schreiben
					cell.setCellValue("Einschlagwinkel [°]");
					row = sh.createRow(2);
					cell = row.createCell(0);
					cell.setCellValue(0);
					cell = row.createCell(1);
					cell.setCellValue(0);

					// Werte 0 und 0 in Excel-Datei schreiben
					for (int i = 0; DragShape.this.increments.size() > i; i++) {
						TrackIncrement localObject = (TrackIncrement) DragShape.this.increments
								.get(i);
						d = d + localObject.distance;
						angle = Math.toDegrees(localObject.angle);
						// Werte in ExcelDatei schreiben
						row = sh.createRow(i + 3);
						cell = row.createCell(0);
						cell.setCellValue(d / 1000);
						cell = row.createCell(1);
						cell.setCellValue(angle);
					}

					// Diagramm erstellen funktioniert nur mit xlsx! Die POI-jar
					// für
					// xlsx hat Probleme mit den bisher genutzten jar's. Daher
					// kein
					// Diagramm

					// Drawing drawing = sh.createDrawingPatriarch();
					// ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 5,
					// 0,
					// 15, 20);

					// Chart chart = drawing.createChart(anchor);
					// ChartLegend legend = chart.getOrCreateLegend();
					// //-------------------> nicht notwendig
					// legend.setPosition(LegendPosition.TOP_RIGHT);
					// //-------------------> nicht notwendig

					// LineChartData data =
					// chart.getChartDataFactory().createLineChartData();

					// // Use a category axis for the bottom axis.
					// ChartAxis bottomAxis =
					// chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
					// //---------> Achstitel?
					// ValueAxis leftAxis =
					// chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
					// //---------> Achstitel?
					// leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
					//
					// int NUM_OF_COLUMNS = 10;
					// ChartDataSource<Number> xs =
					// DataSources.fromNumericCellRange(sh, new
					// CellRangeAddress(0,
					// 0, 0, NUM_OF_COLUMNS - 1));
					// ChartDataSource<Number> ys1 =
					// DataSources.fromNumericCellRange(sh, new
					// CellRangeAddress(1,
					// 1, 0, NUM_OF_COLUMNS - 1));
					//
					//
					// data.addSerie(xs, ys1);
					//
					// chart.plot(data, bottomAxis, leftAxis);

					// Mappe schreiben und Datei schließen

					wb.write(out);
				} catch (IOException e) {
					Utils.showError(
							"Die Datei kann nicht erzeugt werden: "
									+ e.getLocalizedMessage(), null);
				} finally {
					if (null != out) {
						try {
							out.close();
							Utils.showInformation("Excel-Datei erstellt.", null);
						} catch (IOException e) {
							Utils.showError(
									"Die Datei kann nicht abgeschlossen werden: "
											+ e.getLocalizedMessage(), null);
						}
					}
					if (null != wb) {
						try {
							wb.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

		});

		localJFrame.setSize(new Dimension(800, 400));
		Container c = localJFrame.getContentPane();
		c.setLayout(new BorderLayout(5, 5));
		c.add(localTurnAngleScreen, BorderLayout.CENTER);
		c.add(unten, BorderLayout.SOUTH);
		// localJFrame.setContentPane(localTurnAngleScreen);
		localJFrame.setVisible(true);
	}

	class TurnAngleScreen extends JPanel {
		TurnAngleScreen() {
			setBackground(Color.white);
		}

		public void paint(Graphics paramGraphics) {
			super.paint(paramGraphics);
			Graphics2D localGraphics2D = (Graphics2D) paramGraphics;

			GeneralPath localGeneralPath = new GeneralPath();
			localGeneralPath.moveTo(0.0F, 0.0F);
			float f = 0.0F;

			for (int i = 0; DragShape.this.increments.size() > i; i++) {
				TrackIncrement localObject = (TrackIncrement) DragShape.this.increments
						.get(i);
				f = (float) (f + ((TrackIncrement) localObject).distance);
				localGeneralPath.lineTo(f, (float) Math
						.toDegrees(((TrackIncrement) localObject).angle));
			}

			Dimension dimension = getSize();

			double d1 = Math.toDegrees(DragShape.this.vehicle.maxTurn);
			double d2 = f;
			double d3 = 2.0D * d1;
			double d4 = dimension.width / d2;
			double d5 = -1.0D * dimension.height / d3;

			int j = ((Dimension) dimension).width / 2;
			int k = ((Dimension) dimension).height / 2;
			j = (int) (j - d4 * (d2 / 2.0D));

			AffineTransform localAffineTransform = new AffineTransform();
			localAffineTransform.translate(j, k);
			localAffineTransform.scale(d4, d5);

			Point2D.Double localDouble1 = new Point2D.Double(0.0D,
					-((int) d1 / 10) * 10);
			Point2D.Double localDouble2 = new Point2D.Double(f,
					-((int) d1 / 10) * 10);
			Point2D.Double localDouble3 = new Point2D.Double(0.0D, 0.0D);
			Point2D.Double localDouble4 = new Point2D.Double(0.0D, 0.0D);

			while (localDouble1.y < d1) {
				localAffineTransform.transform(localDouble1, localDouble3);
				localAffineTransform.transform(localDouble2, localDouble4);
				localGraphics2D.draw(new Line2D.Double(localDouble3,
						localDouble4));
				localGraphics2D.drawString(localDouble1.y + "°", 10,
						(int) localDouble4.y);
				localDouble1.y += 10.0D;
				localDouble2.y += 10.0D;
			}

			localDouble1.x = 10000.0D;
			localDouble1.y = d1;
			localDouble2.x = 10000.0D;
			localDouble2.y = (-d1);

			while (localDouble1.x < f) {
				localAffineTransform.transform(localDouble1, localDouble3);
				localAffineTransform.transform(localDouble2, localDouble4);
				localGraphics2D.draw(new Line2D.Double(localDouble3,
						localDouble4));
				localGraphics2D.drawString(localDouble1.x / 1000.0D + "m",
						(int) localDouble4.x, (int) localDouble4.y - 10);
				localDouble1.x += 10000.0D;
				localDouble2.x += 10000.0D;
			}

			localGraphics2D.setColor(Color.red);
			localGraphics2D.setStroke(new BasicStroke(4.0F));
			localGraphics2D.draw(localGeneralPath
					.createTransformedShape(localAffineTransform));
		}
	}
}