/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase Punto. Representa un punto.
 */

package proyectiles;

public class Punto {
	private int coordX;
	private int coordY;
	
	public Punto (int x, int y) {
		coordX = x;
		coordY = y;
	}
	
	public Punto () {
		coordX = 0;
		coordY = 0;
	}
	
	/**
	 * Getters y setters
	 */
	
	public int getX () {
		return coordX;
	}
	
	public int getY () {
		return coordY;
	}
	
	public void setX (int x) {
		coordX = x;
	}
	
	public void setY (int y) {
		coordY = y;
	}
}
