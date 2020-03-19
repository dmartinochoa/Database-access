package vista;

import controlador.*;
import main.*;

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
	private Control c;

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
	private JLabel lblCara;
	private JButton btnWriteToDb;
	private JButton btnWriteToFile;

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
		txtId.setBounds(68, 76, 61, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
		txtNombre.setColumns(10);
		txtNombre.setBounds(209, 76, 96, 20);
		getContentPane().add(txtNombre);

		txtDesc = new JTextField();
		txtDesc.setFont(new Font("SansSerif", Font.PLAIN, 11));
		txtDesc.setColumns(10);
		txtDesc.setBounds(140, 116, 165, 20);
		getContentPane().add(txtDesc);

		txtCara = new JTextField();
		txtCara.setFont(new Font("SansSerif", Font.PLAIN, 11));
		txtCara.setColumns(10);
		txtCara.setBounds(140, 160, 165, 20);
		getContentPane().add(txtCara);

//Buttons
		// Show database
		btnShowDb = new JButton("Show Dbs");
		btnShowDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBox.setText("");
				try {
					txtBox.setText(c.showAllDb());
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
				txtBox.setText(c.ShowAllFm());
			}
		});
		btnShowFile.setBounds(170, 25, 125, 23);
		getContentPane().add(btnShowFile);

		// Db to file
		btnDbToFile = new JButton("Write Db To File");
		btnDbToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					c.writeAllFm();
					txtBox.setText("Db info written into file");
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
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
				txtBox.setText("Db updated with file data");
				c.addFileToDb();
			}
		});
		btnFileToDb.setBounds(468, 25, 138, 23);
		getContentPane().add(btnFileToDb);

		// Write entry to File
		btnWriteToFile = new JButton("Write Entry To File");
		btnWriteToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.addToFile(c.createFileElement(Integer.parseInt(txtId.getText()), txtNombre.getText(),
						txtDesc.getText(), txtCara.getText()));
				txtBox.setText("Entry Written Into Db: " + c.createFileElement(Integer.parseInt(txtId.getText()),
						txtNombre.getText(), txtDesc.getText(), txtCara.getText()).toString());
			}
		});
		btnWriteToFile.setBounds(331, 76, 187, 23);
		getContentPane().add(btnWriteToFile);

		// Write entry to DB
		btnWriteToDb = new JButton("Write Entry To Db");
		btnWriteToDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.addToDb(c.createDbElement(txtNombre.getText(), txtDesc.getText(), txtCara.getText()));
				txtBox.setText("Entry Written Into Db: "
						+ c.createDbElement(txtNombre.getText(), txtDesc.getText(), txtCara.getText().toString()));
			}
		});
		btnWriteToDb.setBounds(331, 116, 187, 23);
		getContentPane().add(btnWriteToDb);

//Labels 
		lblId = new JLabel("Id:");
		lblId.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblId.setBounds(35, 79, 48, 14);
		getContentPane().add(lblId);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblNombre.setBounds(151, 79, 48, 14);
		getContentPane().add(lblNombre);

		lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblDescripcion.setBounds(35, 119, 94, 14);
		getContentPane().add(lblDescripcion);

		lblCara = new JLabel("Caracteristicas:");
		lblCara.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblCara.setBounds(35, 163, 94, 14);
		getContentPane().add(lblCara);

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

//Setter and getter
	public Control getC() {
		return c;
	}

	public void setC(Control c) {
		this.c = c;
	}
}