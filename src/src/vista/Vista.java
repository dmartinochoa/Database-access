package vista;

import controlador.*;

import modelo.Elemento;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private Control control;

	private int xx, xy; // Position to move window
	private JLabel lblExit;
	private JLabel lblMinimize;
	private JButton btnShowDb;
	private JTextArea txtBox;
	private JScrollPane scrollPane;
	private JButton btnShowFile;
	private JButton btnDbToFile;
	private JButton btnFileToDb;
	private JTextField txtId;
	private JTextField txtNombre;
	private JTextField txtDesc;
	private JTextField txtCara;
	private JLabel lblId;
	private JLabel lblNombre;
	private JLabel lblDescripcion;
	private JLabel lblCaracteristicas;
	private JButton btnWriteToDb;
	private JButton btnWriteToFile;
	private JButton btnRemoveFromDb;
	private JButton btnRemoveFromFm;
	private JButton btnRemoveEntryDb;
	private JButton btnRemovEntryFM;

	public Vista() {
		setTitle("Acceso a Datos");
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40)); // Border radius
		getContentPane().setLayout(null);

//Txt		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 200, 730, 257);
		getContentPane().add(scrollPane);
		txtBox = new JTextArea();
		txtBox.setFont(new Font("SansSerif", Font.PLAIN, 16));
		scrollPane.setViewportView(txtBox);

		txtId = new JTextField();
		txtId.setFont(new Font("SansSerif", Font.PLAIN, 11));
		txtId.setBounds(55, 76, 48, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
		txtNombre.setColumns(10);
		txtNombre.setBounds(168, 76, 94, 20);
		getContentPane().add(txtNombre);

		txtDesc = new JTextField();
		txtDesc.setFont(new Font("SansSerif", Font.PLAIN, 11));
		txtDesc.setColumns(10);
		txtDesc.setBounds(128, 107, 134, 20);
		getContentPane().add(txtDesc);

		txtCara = new JTextField();
		txtCara.setFont(new Font("SansSerif", Font.PLAIN, 11));
		txtCara.setColumns(10);
		txtCara.setBounds(128, 135, 134, 20);
		getContentPane().add(txtCara);

//Buttons
		// Show database
		btnShowDb = new JButton("Show Dbs");
		btnShowDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtBox.setText(control.showAllDb());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnShowDb.setBounds(35, 25, 125, 23);
		getContentPane().add(btnShowDb);

		// Show file
		btnShowFile = new JButton("Show File Data");
		btnShowFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBox.setText(control.ShowAllFm());
			}
		});
		btnShowFile.setBounds(170, 25, 125, 23);
		getContentPane().add(btnShowFile);

		// Db to file
		btnDbToFile = new JButton("Write Db To File");
		btnDbToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					control.addDbToFile();
					txtBox.setText("Db info written into file");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDbToFile.setBounds(305, 25, 153, 23);
		getContentPane().add(btnDbToFile);

		// File to db
		btnFileToDb = new JButton("Write File To Db");
		btnFileToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBox.setText("File info written into db");
				control.addFileToDb();
			}
		});
		btnFileToDb.setBounds(468, 25, 138, 23);
		getContentPane().add(btnFileToDb);

		// Write entry to File
		btnWriteToFile = new JButton("Write Entry To File");
		btnWriteToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Elemento element = new Elemento(Integer.parseInt(txtId.getText()), txtNombre.getText(),
						txtDesc.getText(), txtCara.getText());
				control.addElementToFile(element);
				txtBox.setText("Entry Written Into Db: " + element.toString());
			}
		});
		btnWriteToFile.setBounds(272, 76, 153, 23);
		getContentPane().add(btnWriteToFile);

		// Write entry to DB
		btnWriteToDb = new JButton("Write Entry To Db");
		btnWriteToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Elemento element = new Elemento(txtNombre.getText(), txtDesc.getText(), txtCara.getText());
				control.addElementToDb(element);
				txtBox.setText("Entry Written Into Db: " + element.toString());
			}
		});
		btnWriteToDb.setBounds(271, 107, 153, 23);
		getContentPane().add(btnWriteToDb);

		// Delete all DB
		btnRemoveFromDb = new JButton("Delete All DB");
		btnRemoveFromDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Do you want to Delete the DB?", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					txtBox.setText("You have deleted the DB, you better have a back up");
					control.deleteDB();
				} else {
					txtBox.setText("You have not deleted the DB");
				}

			}
		});
		btnRemoveFromDb.setBounds(442, 76, 125, 23);
		getContentPane().add(btnRemoveFromDb);

		// Delete all FM
		btnRemoveFromFm = new JButton("Delete All File");
		btnRemoveFromFm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Do you want to Delete the File?", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					txtBox.setText("You have deleted the File, you better have a back up");
					control.deleteFM();
				} else {
					txtBox.setText("You have not deleted the File");
				}
			}
		});
		btnRemoveFromFm.setBounds(577, 76, 125, 23);
		getContentPane().add(btnRemoveFromFm);

		// Remove element DB
		btnRemoveEntryDb = new JButton("Delete by Id DB");
		btnRemoveEntryDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.deleteElementDB(Integer.parseInt(txtId.getText()));
				txtBox.setText("Entry Removed");

			}
		});
		btnRemoveEntryDb.setBounds(442, 107, 125, 23);
		getContentPane().add(btnRemoveEntryDb);

		// Remove elemento File
		btnRemovEntryFM = new JButton("Delete by Id File");
		btnRemovEntryFM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.deleteElementFM(Integer.parseInt(txtId.getText()));
				txtBox.setText("Entry Removed");
			}
		});
		btnRemovEntryFM.setBounds(577, 107, 125, 23);
		getContentPane().add(btnRemovEntryFM);

//Labels 
		lblId = new JLabel("Id:");
		lblId.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblId.setBounds(35, 79, 48, 14);
		getContentPane().add(lblId);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblNombre.setBounds(113, 79, 48, 14);
		getContentPane().add(lblNombre);

		lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblDescripcion.setBounds(35, 107, 94, 14);
		getContentPane().add(lblDescripcion);

		lblCaracteristicas = new JLabel("Caracteristicas:");
		lblCaracteristicas.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblCaracteristicas.setBounds(35, 138, 94, 14);
		getContentPane().add(lblCaracteristicas);

// EXIT
		lblExit = new JLabel("x");
		lblExit.setForeground(Color.BLACK);
		lblExit.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		lblExit.setBounds(752, 11, 38, 33);
		getContentPane().add(lblExit);
		lblExit.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e) {
				lblExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

// Minimize
		lblMinimize = new JLabel("-");
		lblMinimize.setForeground(Color.BLACK);
		lblMinimize.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 40));
		lblMinimize.setBounds(715, 11, 30, 33);
		getContentPane().add(lblMinimize);
		lblMinimize.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				setState(ICONIFIED);
			}

			public void mouseEntered(MouseEvent e) {
				lblMinimize.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});

// Listeners para mover la ventana
		getContentPane().addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				xx = e.getX();
				xy = e.getY();
			}
		});

		getContentPane().addMouseMotionListener(new MouseMotionAdapter() {

			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				Vista.this.setLocation(x - xx, y - xy);
			}
		});
	}

//Getter
	public void setControl(Control control) {
		this.control = control;
	}
}