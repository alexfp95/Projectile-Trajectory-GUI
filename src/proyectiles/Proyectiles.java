/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase Proyectiles. Panel en el que se dibuja la escala, y las trayectorias de los misiles.
 */

package proyectiles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Proyectiles extends JPanel {
	
	final int ORIGEN_X = 60;                          // Sera el origen de la coordenada X
	final int LIMITE_Y = 0;                           // Limite donde termina Y
	final int TAM_PROYECTIL = 3;                      
	final int AJUSTE = TAM_PROYECTIL / 2;             // Ajuste para colocar el misil exactam. en el 0,0
	final int LARGO_ESCALA = 10;                      // Largo de cada palito de la escala
	final int AJUSTE_ESCALA = 15;                     // Ajuste para colocar el numero junto a cada escala
	final int INCREMENTO_ESCALA = 50;      
	final double TIEMPO_OBSERVACION = 0.01;           // Cada periodo en el que se observa la posicion del proyectil
	final int VEL_DEFECTO = 50;
	final int ANG_DEFECTO = 50;
	final int HOLGURA_REDIMENSIONABLE = 30;           // Holgura que se establece una vez redimensiado
	final int AJUSTE_ORIGEN_Y = 40;
	final Color[] COLOR = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.PINK, Color.ORANGE, Color.CYAN};
	final int NUM_COLORES = 7;
	final int NOVENTA_GRADOS = 90;
	final int TAM_CANON = 25;
	final int TAM_BASE = 5;
	
	private double velocidad;
	private double angulo;
	private double altura;
	private Vista frame;
	private ArrayList<ArrayList<Punto>> lanzamientos;
	private ArrayList<MovParabolico> memoriaTiros;
	private boolean traza = false;
	private MovParabolico misilActual;
	private MiTimer timer;
	
	public Proyectiles (Vista f) {
		velocidad = VEL_DEFECTO;
		angulo = ANG_DEFECTO;
		altura = 0;
		frame = f;
		lanzamientos = new ArrayList<ArrayList<Punto>> ();
		memoriaTiros = new ArrayList<MovParabolico> ();
		timer = new MiTimer (this);
	}
	
	/**
	 * Lanza un misil. Si el misil se sale del tamaño de la ventana, se redimensiona para poder lanzarlo posteriormente.
	 */
	
	public void lanzarMisil () {
		misilActual = new MovParabolico(getAngulo(), getVelocidad(), getAltura());
		
		int nuevoAncho = getWidth();
		int nuevoAlto = getHeight();
		
		if((ORIGEN_X + misilActual.getAlcanceHorizontal()) > getWidth()) {
			nuevoAncho = (int)(ORIGEN_X + misilActual.getAlcanceHorizontal() + HOLGURA_REDIMENSIONABLE);
			System.out.println("Necesita Ancho: " + misilActual.getAlcanceHorizontal());
		} 
		
		if((misilActual.getAlturaMaxima()) > (getHeight() - AJUSTE_ORIGEN_Y)) {
			nuevoAlto = (int)(misilActual.getAlturaMaxima() + AJUSTE_ORIGEN_Y + HOLGURA_REDIMENSIONABLE);
			System.out.println("Necesita Alto: " + misilActual.getAlturaMaxima());
		}
		
		if((nuevoAncho != getWidth()) || (nuevoAlto != getHeight())) {
			getFrame().setSize(nuevoAncho, nuevoAlto + (getFrame().getHeight() - this.getHeight()));
		} else {
			ArrayList<Punto> puntos = obtenerPuntos(misilActual);
			getLanzamientos().add(puntos);
			getMemoria().add(misilActual);
		}
	}
	
	/**
	 * Obtiene el conjunto de puntos de la trayectoria de un misil con un mov parabolico
	 */
	
	public ArrayList<Punto> obtenerPuntos (MovParabolico mp) {
		int x = 0;
		int y = getOrigenY() - 1;
		double tiempo = 0;
		ArrayList<Punto> puntos = new ArrayList<Punto> ();
		while((x < (ORIGEN_X + mp.getAlcanceHorizontal())) && (y <= getOrigenY())) {
			x = (int)(ORIGEN_X + mp.getPosicionX(tiempo));
			y = (int)(getOrigenY() - mp.getPosicionY(tiempo));
			if(y > getOrigenY())
				y = getOrigenY();
			puntos.add(new Punto(x, y));
			tiempo += TIEMPO_OBSERVACION;
		}
		return puntos;
	}
	
	/**
	 * Getters y setters
	 */
	
	public double getVelocidad () {
		return velocidad;
	}
	
	public void setVelocidad (double v) {
		velocidad = v;
	}
	
	public double getAngulo () {
		return angulo;
	}
	
	public void setAngulo (double alfa) {
		angulo = alfa;
	}
	
	public double getAltura () {
		return altura;
	}
	
	public Vista getFrame () {
		return frame;
	}
		
	public int getOrigenY () {
		return getHeight() - AJUSTE_ORIGEN_Y;
	}
		
	public ArrayList<ArrayList<Punto>> getLanzamientos () {
		return lanzamientos;
	}
	
	public boolean getTraza () {
		return traza;
	}
	
	public void setTraza (boolean estado) {
		traza = estado;
	}
	
	public ArrayList<MovParabolico> getMemoria () {
		return memoriaTiros;
	}
	
	public MovParabolico getMisilActual () {
		return misilActual;
	}
	
	public MiTimer getTimer () {
		return timer;
	}
	
	/**
	 * Pausa la simulacion del disparo
	 */
	
	public void pausar () {
		if(getTimer().getPausa())
			getTimer().setPausa(false);
		else
			getTimer().setPausa(true);
	}
	
	/**
	 * Limpia las trayectorias realizadas
	 */
	
	public void borrar () {
		getMemoria().clear();
		repaint();
	}
	
	/**
	 * Dibuja el cañon que dispara el misil
	 * @param g
	 */
	
	public void dibujarCanon (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke (5));
		double y = (TAM_CANON * getAngulo()) / NOVENTA_GRADOS;
		double x =TAM_CANON - y;
		Line2D cannon = new Line2D.Double (ORIGEN_X, getOrigenY(), ORIGEN_X + x, getOrigenY() - y);
		g2d.draw(cannon);
		Ellipse2D base = new Ellipse2D.Double (ORIGEN_X - TAM_BASE, getOrigenY() - TAM_BASE, TAM_BASE*2, TAM_BASE*2);
		g2d.fill(base);
	}
	
	/**
	 * Dibuja la escala sobre la que se representa la simulacion, y trayectorias guardadas si esta activada la traza
	 */
	
	protected void paintComponent (Graphics g) {
		super.paintComponent(g);
		g.drawLine(ORIGEN_X + AJUSTE, getOrigenY() + AJUSTE, ORIGEN_X + AJUSTE, LIMITE_Y + AJUSTE);
		g.drawLine(ORIGEN_X + AJUSTE,  getOrigenY() + AJUSTE, getWidth(), getOrigenY() + AJUSTE);
		
		for(int i = 0; i < getWidth(); i += INCREMENTO_ESCALA) {
			g.drawLine(ORIGEN_X + AJUSTE + i, getOrigenY() + AJUSTE, ORIGEN_X + AJUSTE + i, getOrigenY() + AJUSTE + LARGO_ESCALA);
			g.drawString("" + i, ORIGEN_X + AJUSTE + i, getOrigenY() + AJUSTE + LARGO_ESCALA + AJUSTE_ESCALA);
			g.drawLine(ORIGEN_X + AJUSTE, getOrigenY() + AJUSTE - i, ORIGEN_X + AJUSTE - LARGO_ESCALA, getOrigenY() + AJUSTE - i);
			g.drawString("" + i, ORIGEN_X + AJUSTE - LARGO_ESCALA - AJUSTE_ESCALA, getOrigenY() + AJUSTE - i);
		}
		
		if(getTraza()) {
			for(int i = 0; i < getMemoria().size(); i++) {
				g.setColor(COLOR[i % NUM_COLORES]);
				MovParabolico mp = getMemoria().get(i);
				ArrayList<Punto> tiro = obtenerPuntos(mp);
				for(int j = 0; j < tiro.size(); j++) {
					Punto p = tiro.get(j);
					g.fillRect(p.getX(), p.getY(), TAM_PROYECTIL, TAM_PROYECTIL);
				}
			}
		}
		
		g.setColor(Color.BLACK);
		dibujarCanon(g);
	}
}
