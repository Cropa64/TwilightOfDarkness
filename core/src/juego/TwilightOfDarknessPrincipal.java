package juego;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import gestorMapas.GestorMapas;
import gestorMapas.Mapa;
import pantallas.CargadorPantallas;
import pantallas.MenuOpciones;
import pantallas.MenuPrincipal;
import pantallas.PantallaPrincipal;
import personajes.PersonajePrincipal;
import utilidades.Archivos;
import utilidades.DatosJuego;
import utilidades.Entrada;
import utilidades.Hilo;
import utilidades.Utiles;

public class TwilightOfDarknessPrincipal extends Game {
	
	private Entrada entrada;
	private PersonajePrincipal jugador;
	private Hilo hilo;
	private GestorMapas gestorMapas;
	public Archivos archivos;
	private DatosJuego datosJuego;
	
	@Override
	public void create() {
		
		datosJuego = new DatosJuego(this);
		archivos = new Archivos(datosJuego);
		//archivos.cargarPartida();
		
		gestorMapas = new GestorMapas();
		jugador = new PersonajePrincipal();
		jugador.crearAnimaciones();
		entrada = new Entrada();
		Gdx.input.setInputProcessor(entrada);
		gestorMapas.crearMapas(jugador);
		setMenuPrincipal();
		hilo = new Hilo();
		hilo.startHilo();
		System.out.println("Hola!");
	}
	
	public void setPantallaPrincipal(Mapa mapa) {
		CargadorPantallas.pantallaPrincipal = new PantallaPrincipal(entrada, this, mapa, jugador);
		setScreen(CargadorPantallas.pantallaPrincipal);
	}
	
	public void setMenuOpciones() {
		CargadorPantallas.menuOpciones = new MenuOpciones(entrada, this);
		setScreen(CargadorPantallas.menuOpciones);
	}
	
	
	public void setMenuPrincipal() {
		CargadorPantallas.menuPrincipal = new MenuPrincipal(entrada, this, gestorMapas);
		setScreen(CargadorPantallas.menuPrincipal);
	}
	
}
