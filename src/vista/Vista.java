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

	public Vista() {
		setTitle("Acceso a Datos");
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setUndecorated(true); // Removes border
		setShape(new RoundRectangle2D.Double(0, 0, 800, 500, 40, 40)); // Border radius
		getContentPane().setLayout(null);

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

		JButton btnSql = new JButton("SQL View");
		btnSql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.goToView("db");

			}
		});
		btnSql.setBounds(319, 156, 128, 23);
		getContentPane().add(btnSql);

		JButton btnFile = new JButton("File View");
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.goToView("file");
			}
		});
		btnFile.setBounds(319, 190, 128, 23);
		getContentPane().add(btnFile);

		JButton btnHibernate = new JButton("Hibernate View");
		btnHibernate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.goToView("hibernate");
			}
		});
		btnHibernate.setBounds(319, 224, 128, 23);
		getContentPane().add(btnHibernate);

		JLabel lblTitle = new JLabel("Choose an option:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTitle.setBounds(309, 122, 185, 23);
		getContentPane().add(lblTitle);

		JButton btnJson = new JButton("Json View");
		btnJson.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				control.goToView("json");
			}
		});
		btnJson.setBounds(319, 258, 128, 23);
		getContentPane().add(btnJson);
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