/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase Vista. Vista de la aplicación.
 * Puede ejecutarse como Applet o StandAlone
 */

package proyectiles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class Vista extends JApplet {
	
	final int VGAP = 20;
	final int HGAP = 20;
	final int FILAS = 1;
	final int COLUMNAS = 3;
	final int FIL_BOTONERA = 0;
	final int COL_BOTONERA = 1;
	final int FIL_DATOS = 0;
	final int COL_DATOS = 1;
	final int FIL_OPCIONES = 0;
	final int COL_OPCIONES = 1;
	final int FIL_INFORMACION = 0;
	final int COL_INFORMACION = 1;
	final int MAX_VEL_SCROLL = 115;
	final int MAX_GRADO_SCROLL = 89;
	final int VALOR_DEFECTO =  50;			// m/s  y  grados
	
	private JPanel central;
	private JPanel inferior;
	private JPanel botonera;
	private JPanel datos;
	private JPanel opciones;
	private Proyectiles proyectiles;
	private JPanel informacion;
	
	private JButton lanzar;
	private JButton pausa;
	private JButton borrar;
	private JCheckBox rastro;
	private JScrollBar velocidadScroll;
	private JScrollBar anguloScroll;
	private JLabel velocidad;
	private JLabel angulo;
	private JLabel tiempo;
	private JLabel distHorizontal;
	private JLabel distVertical;
	private JLabel altura;
	
	private boolean esStandalone = false;
	
	public void init () {
		
		if(!getStandalone()) {
			// Obtener parametros
		}
		
		setLayout(new BorderLayout (HGAP, VGAP));
		
		inferior = new JPanel ();
		inferior.setLayout(new GridLayout (FILAS, COLUMNAS, HGAP, VGAP));
		
		botonera = new JPanel ();
		botonera.setLayout(new GridLayout (FIL_BOTONERA, COL_BOTONERA, 0, 0));
		JPanel pLanzar = new JPanel ();
		lanzar = new JButton ("Lanzar");
		pLanzar.add(lanzar);
		botonera.add(pLanzar);
		JPanel pPausa = new JPanel ();
		pausa = new JButton ("Pausa");
		pPausa.add(pausa);
		botonera.add(pPausa);
		JPanel pBorrar = new JPanel ();
		borrar = new JButton ("Borrar");
		pBorrar.add(borrar);
		botonera.add(pBorrar);
		inferior.add(botonera);
		
		datos = new JPanel ();
		datos.setLayout(new GridLayout (FIL_DATOS, COL_DATOS, HGAP, 0));
		JPanel pVelocidad = new JPanel ();
		velocidadScroll = new JScrollBar (JScrollBar.HORIZONTAL, VALOR_DEFECTO, 0, 1, MAX_VEL_SCROLL);
		velocidadScroll.setPreferredSize(new Dimension (100, 20));
		velocidad = new JLabel ("Velocidad inicial " + velocidadScroll.getValue() + " m/s");
		pVelocidad.add(velocidad);
		pVelocidad.add(velocidadScroll);
		datos.add(pVelocidad);
		JPanel pAngulo = new JPanel ();
		anguloScroll = new JScrollBar (JScrollBar.HORIZONTAL, VALOR_DEFECTO, 0, 1, MAX_GRADO_SCROLL);
		anguloScroll.setPreferredSize(new Dimension (100, 20));
		angulo = new JLabel ("Angulo inicial " + velocidadScroll.getValue() + " grados");
		pAngulo.add(angulo);
		pAngulo.add(anguloScroll);
		datos.add(pAngulo);
		inferior.add(datos);
		
		opciones = new JPanel ();
		opciones.setLayout(new FlowLayout (FlowLayout.CENTER, HGAP, 0));
		rastro = new JCheckBox ("Mostrar rastro");
		opciones.add(rastro);
		inferior.add(opciones);
		
		proyectiles = new Proyectiles (this);
		proyectiles.setLayout(new BorderLayout (HGAP, VGAP));
		proyectiles.setBackground(Color.LIGHT_GRAY);
		
		informacion = new JPanel ();
		informacion.setLayout(new GridLayout (FIL_INFORMACION, COL_INFORMACION, HGAP, 0));
		informacion.setOpaque(false);
		JPanel pTiempo = new JPanel();
		tiempo = new JLabel ("t = 0.0 s");
		pTiempo.add(tiempo);
		pTiempo.setOpaque(false);
		informacion.add(pTiempo);
		JPanel pDistH = new JPanel();
		distHorizontal = new JLabel("x = 0.0 m");
		pDistH.add(distHorizontal);
		pDistH.setOpaque(false);
		informacion.add(pDistH);
		JPanel pDistV = new JPanel();
		distVertical = new JLabel("y = 0.0 m");
		pDistV.add(distVertical);
		pDistV.setOpaque(false);
		informacion.add(pDistV);
		JPanel pAltura = new JPanel();
		altura = new JLabel ("y-max = 0.0 m");
		pAltura.add(altura);
		pAltura.setOpaque(false);
		informacion.add(pAltura);
				
		proyectiles.add(informacion, BorderLayout.EAST);
		
		add(proyectiles, BorderLayout.CENTER);
		add(inferior, BorderLayout.SOUTH);
		
		insertarListeners ();
	}
	
	/**
	 * Insertar los listeners en los elementos de la vista
	 */
	
	public void insertarListeners () {
		getLanzar().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	getProyectiles().lanzarMisil();
      }
    });
		getPausa().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	getProyectiles().pausar();
      }
    });
		getBorrar().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      	getProyectiles().borrar();
      }
    });
		getRastro().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(getRastro().isSelected()) {
        	getProyectiles().setTraza(true);
        } else {
        	getProyectiles().setTraza(false);
        }
     }
	  });
	  getVelocidadScroll().addAdjustmentListener(new AdjustmentListener() {
      public void adjustmentValueChanged(AdjustmentEvent e) {
      	getVelocidad().setText("Velocidad inicial " + getVelocidadScroll().getValue() + " m/s");
      	getProyectiles().setVelocidad(getVelocidadScroll().getValue());
      }
    });
	  getAnguloScroll().addAdjustmentListener(new AdjustmentListener() {
     public void adjustmentValueChanged(AdjustmentEvent e) {
     	getAngulo().setText("Angulo inicial " + getAnguloScroll().getValue() + " grados");
     	getProyectiles().setAngulo(getAnguloScroll().getValue());
     	getProyectiles().repaint();
     }
    });
	}
	
	/**
	 * Getters y setters
	 */
	
	public Proyectiles getProyectiles () {
		return proyectiles;
	}
	
	public JButton getLanzar () {
		return lanzar;
	}
	
	public JButton getPausa () {
		return pausa;
	}
	
	public JButton getBorrar () {
		return borrar;
	}
	
	public JScrollBar getVelocidadScroll () {
		return velocidadScroll;
	}
	
	public JScrollBar getAnguloScroll () {
		return anguloScroll;
	}
	
	public JPanel getPanelInferior () {
		return inferior;
	}
	
	public JCheckBox getRastro () {
		return rastro;
	}
	
	public JLabel getTiempo () {
		return tiempo;
	}
	
	public JLabel getDistHorizontal () {
		return distHorizontal;
	}
	
	public JLabel getDistVertical () {
		return distVertical;
	}
	
	public JLabel getAltura () {
		return altura;
	}
	
	public JLabel getVelocidad () {
		return velocidad;
	}
	
	public JLabel getAngulo () {
		return angulo;
	}
	
	public void setStandalone (boolean estado) {
		esStandalone = estado;
	}
	
	public boolean getStandalone () {
		return esStandalone;
	}
	
	public void getParametrosLineaComandos (String[] args) {
		// Obtener parametros
	}
}
