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

public class VistaHibernate extends JFrame {

	private Control control;

	private int xx, xy; // Position to move window
	private JLabel lblExit;
	private JLabel lblMinimize;
	private JButton btnShow;
	private JTextArea txtBox;
	private JScrollPane scrollPane;
	private JButton btnDbToJson;
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
	private JButton btnRemoveFromDb;
	private JButton btnRemoveEntryDb;
	private JButton btnShowByIdDB;
	private JButton btnUpdateDB;
	private JLabel lblTitle;
	private JButton btnHbToJson;
	private JButton btnJsonToHb;

	public VistaHibernate() {
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
		btnShow = new JButton("show json");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtBox.setText(control.showAll());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnShow.setBounds(35, 25, 125, 23);
		getContentPane().add(btnShow);

		// Db to file
		btnDbToJson = new JButton("Write Db to Json");
		btnDbToJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					control.addDbToFileHibernate();
					txtBox.setText("Db info written into file");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDbToJson.setBounds(170, 25, 153, 23);
		getContentPane().add(btnDbToJson);

		// File to db
		btnFileToDb = new JButton("Write File To Json");
		btnFileToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBox.setText("File info written into db");
				control.addFileToDbHibernate();
			}
		});
		btnFileToDb.setBounds(333, 25, 138, 23);
		getContentPane().add(btnFileToDb);

		// Write entry to DB
		btnWriteToDb = new JButton("Write Entry To Json");
		btnWriteToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Elemento element = new Elemento(Integer.parseInt(txtId.getText()), txtNombre.getText(),
						txtDesc.getText(), txtCara.getText());
				control.addElement(element);
				txtBox.setText("Entry Written Into Db: " + element.toString());
			}
		});
		btnWriteToDb.setBounds(272, 76, 153, 23);
		getContentPane().add(btnWriteToDb);

		// Delete all DB
		btnRemoveFromDb = new JButton("Delete All ");
		btnRemoveFromDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Do you want to Delete the DB?", "Confirm",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					txtBox.setText("You have deleted the DB, you better have a back up");
					control.deleteAll();
				} else {
					txtBox.setText("You have not deleted the DB");
				}

			}
		});
		btnRemoveFromDb.setBounds(442, 76, 125, 23);
		getContentPane().add(btnRemoveFromDb);

		// Remove element DB
		btnRemoveEntryDb = new JButton("Delete by Id");
		btnRemoveEntryDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtId.getText().equals("")) {
					txtBox.setText(control.deleteElement(Integer.parseInt(txtId.getText())));
				} else {
					txtBox.setText("You must enter an id");
				}
			}
		});
		btnRemoveEntryDb.setBounds(442, 105, 125, 23);
		getContentPane().add(btnRemoveEntryDb);

		// Show by id DB
		btnShowByIdDB = new JButton("Show by Id");
		btnShowByIdDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtId.getText().equals("")) {
					txtBox.setText(control.showEntry(Integer.parseInt(txtId.getText())));
				} else {
					txtBox.setText("You must enter an id");
				}
			}
		});
		btnShowByIdDB.setBounds(577, 76, 125, 23);
		getContentPane().add(btnShowByIdDB);

		// Update element DB
		btnUpdateDB = new JButton("Update by id ");
		btnUpdateDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtId.getText().equals("")) {
					Elemento element = new Elemento(Integer.parseInt(txtId.getText()), txtNombre.getText(),
							txtDesc.getText(), txtCara.getText());
					txtBox.setText(control.updateEntry(element));
				} else {
					txtBox.setText("You must enter an id");
				}
			}
		});
		btnUpdateDB.setBounds(577, 104, 125, 23);
		getContentPane().add(btnUpdateDB);
		
		//Hibernate to Db
		btnHbToJson = new JButton("Hibernate To Json");
		btnHbToJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.addHibernateToDb();
				txtBox.setText("Hibernate Data Moved to SQL");
			}
		});
		btnHbToJson.setBounds(35, 166, 125, 23);
		getContentPane().add(btnHbToJson);
		
		//Db to hibernate
		btnJsonToHb = new JButton("Json To Hibernate");
		btnJsonToHb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.addDbToHibernate();
				txtBox.setText("Db Data Moved To Hibernate Db");
			}
		});
		btnJsonToHb.setBounds(169, 166, 125, 23);
		getContentPane().add(btnJsonToHb);
		
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

		lblTitle = new JLabel("Json View");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitle.setBounds(522, 24, 138, 19);
		getContentPane().add(lblTitle);

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
				VistaHibernate.this.setLocation(x - xx, y - xy);
			}
		});
	}

//Getter
	public void setControl(Control control) {
		this.control = control;
	}
}