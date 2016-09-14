/*
 * Asignatura: Programación de Aplicaciones Interactivas (PAI)
 * Curso: 2016
 * Autor: Alexis Daniel Fuentes Pérez
 * Contacto: alu0100816761@ull.edu.es
 * 
 * Clase MovParabolico. Clase que contiene las formulas para describir un movimiento parabolico
 */

package proyectiles;

/**
 * Clase MovParabolico
 * @author Alexis Daniel Fuentes Pérez
 */

public class MovParabolico {

	private final double GRAVEDAD = 9.8;
	private final double UN_MEDIO = 0.5;
	private final double CUADRADO = 2;
	private final double DOBLE = 2;
	
	private double velInicialX;
	private double velInicialY;
	private double velocidadInicial;
	private double angulo;
	private double alturaInicial;
	
	public MovParabolico (double alfa, double velocidad, double altura) {
		angulo = Math.toRadians(alfa);
		velocidadInicial = velocidad;
		velInicialX = velocidadInicial * Math.cos(alfa);
		velInicialY = velocidadInicial * Math.sin(alfa);
		alturaInicial = altura;
	}
	
	/**
	 * Getters y setters
	 */
	
	public double getVelocidadInicial () {
		return velocidadInicial;
	}
	
	public double getAngulo () {
		return angulo;
	}
	
	public double getAlturaInicial () {
		return alturaInicial;
	}
	
	public double getVelInicialX () {
		return velInicialX;
	}
	
	public double getVelInicialY () {
		return velInicialY;
	}
	
	public double velocidadX () {
		return getVelInicialX();
	}
	
	public double velocidadY (double tiempo) {
		return (getVelocidadInicial() * Math.sin(getAngulo()) - (GRAVEDAD * tiempo)); 
	}
	
	/**
	 * Obtener la posicion en X en un tiempo determinado
	 * @param tiempo
	 * @return posicion
	 */
	
	public double getPosicionX (double tiempo) {
		return (getVelocidadInicial() * Math.cos(getAngulo()) * tiempo);
	}
	
	/**
	 * Obtener la posicion Y en un tiempo determinado
	 * @param tiempo
	 * @return posicion
	 */
	
	public double getPosicionY (double tiempo) {
		return (getAlturaInicial() + ((getVelocidadInicial() * Math.sin(getAngulo()) * tiempo) - (UN_MEDIO * GRAVEDAD * Math.pow(tiempo, CUADRADO))));
	}
	
	/**
	 * Getters
	 */
	
	public double getAlcanceHorizontal () {
		return ((Math.pow(getVelocidadInicial(), CUADRADO) * Math.sin(DOBLE * getAngulo())) / GRAVEDAD);
	}
	
	public double getAlturaMaxima () {
		return ((Math.pow(getVelocidadInicial(), CUADRADO) * Math.pow(Math.sin(getAngulo()), CUADRADO)) / (DOBLE * GRAVEDAD));
	}
	
}
