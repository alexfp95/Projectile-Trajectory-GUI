/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase MiTimer. Timer que se utiliza para dibujar en la interfaz.
 */

package proyectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

/**
 * Clase MiTimer
 * @author Alexis Daniel Fuentes Pérez
 */

public class MiTimer {
	
	final int TAM_PROYECTIL = 3;
	final Color[] COLOR = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.CYAN};
	final int NUM_COLORES = 7;
	final int DELAY = 10;                    // Delay del timer
	
	private Proyectiles proyectiles;
	private Graphics g;
	private Integer indice;
	private Integer tiro;
	private Timer timer;
	private double tiempo;
	private boolean pausa;
	
	public MiTimer (Proyectiles p) {
		timer = new Timer(DELAY, new TimerListener());
		proyectiles = p;
		indice = 0;
		tiro = 0;
		pausa = false;
		timer.start();
	}
	
	/**
	 * Getters y setters
	 */
	
	public Proyectiles getProyectiles () {
		return proyectiles;
	}
	
	public Integer getIndice () {
		return indice;
	}
	
	public void setIndice (int i) {
		indice = i;
	}

	public Integer getTiro () {
		return tiro;
	}
	
	public void siguienteTiro () {
		tiro += 1;
	}
	
	public void setGraphics (Graphics gr) {
		g = gr;
	}
	
	public Graphics getGraphics () {
		return g;
	}
	
	public double getTiempo () {
		return tiempo;
	}
	
	public void setTiempo (double t) {
		tiempo = t;
	}
	
	public void setPausa (boolean estado) {
		pausa = estado;
	}
	
	public boolean getPausa () {
		return pausa;
	}
	
	/**
	 * Clase interna TimerListener. Dibuja el trayecto del misil, y actualiza la informacion
	 * @author Alexis Daniel Fuentes Pérez
	 */
	
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!getPausa()) {
				if(getProyectiles().getTraza()) {
					timer.setDelay(DELAY);
				} else {
					timer.setDelay(1);
				}
				
				setTiempo(getTiempo() + DELAY);
				
				if(getIndice() == 0) {
					setTiempo(0);
				}
				
				if(getTiro() < getProyectiles().getLanzamientos().size()) {
					ArrayList<Punto> puntos = getProyectiles().getLanzamientos().get(getTiro());
					setGraphics(getProyectiles().getGraphics());
				  getGraphics().setColor(COLOR[getTiro() % NUM_COLORES]);
					if(getIndice() < puntos.size()) {
						Punto p = puntos.get(getIndice());
						
						if(getProyectiles().getTraza() == false)
							getProyectiles().repaint();
						
						getGraphics().fillRect(p.getX(), p.getY(), TAM_PROYECTIL, TAM_PROYECTIL);
						actualizarInformacion(p.getX(), p.getY());
						setIndice(getIndice() + 1);
						
						if(getProyectiles().getTraza() == false) {
							try {
								Thread.sleep(DELAY);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					} else {
						setIndice(0);
						siguienteTiro();
						getProyectiles().repaint();
					}
				}
			}
    }
		
		/**
		 * Actualiza la informacion
		 * @param x Coordenada
		 * @param y Coordenada
		 */
		
		public void actualizarInformacion (int x, int y) {
			Vista v = getProyectiles().getFrame();
			JLabel tLabel = v.getTiempo();
			String formato = String.format("%1$.3f", (getTiempo() / 1000));
			tLabel.setText("t = " + formato + " s");
			JLabel xLabel = v.getDistHorizontal();
			xLabel.setText("x = " + (x - getProyectiles().ORIGEN_X) + " m");
			JLabel yLabel = v.getDistVertical();
			yLabel.setText("y = " + (getProyectiles().getOrigenY() - y) + " m");
			MovParabolico mp = getProyectiles().getMisilActual();
			JLabel ymaxLabel = v.getAltura();
			formato = String.format("%1$.2f", mp.getAlturaMaxima());
			ymaxLabel.setText("y-max = " + formato + " m");
		}
	}
}
