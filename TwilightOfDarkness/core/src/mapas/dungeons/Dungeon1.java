package mapas.dungeons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import enemigos.Alien;
import mapas.gestorMapas.GestorMapas;
import mapas.gestorMapas.Mapa;
import personajes.Entidad;
import personajes.PersonajePrincipal;
import utilidades.Utiles;

public class Dungeon1 extends Mapa{

	private MapObjects aliensObj;
	private Array<Alien> aliens2;
	private Rectangle rectanguloPiso;
	private Array<Rectangle> colisiones; 
	private boolean cambioMapa;
	protected Alien alienPelea;
	private MapaPeleaDungeon1 mapaPelea;
	private boolean vieneDePelea;
	private Vector2 posicionPelea;
	
	public Dungeon1(PersonajePrincipal jugador, GestorMapas gestorMapas) {
		super(jugador, gestorMapas);
		posicionPelea = new Vector2();
		jugador.setPosicion(160, 140);
	}

	@Override
	public void setPosicionJugador() {
		if(vieneDePelea) {
			jugador.setPosicion(posicionPelea.x, posicionPelea.y);
			vieneDePelea = false;
		}else {
			jugador.setPosicion(160, 140);
		}
	}

	@Override
	public void renderizar() {
		
		update();
		
		renderer.render();
		jugador.movimiento();
		
		dibujarAliens();
	}

	public void dibujarAliens(){
		Utiles.sr.begin(ShapeType.Line);
		for (int i = 0; i < aliens2.size; i++) {
			aliens2.get(i).comportamiento(jugador, this);
			aliens2.get(i).mostrarColisiones();
		}
		Utiles.sr.end();
		
		for (int j = 0; j < aliens2.size; j++) {
			if(!aliens2.get(j).isMuerto()) {
				aliens2.get(j).barraVida();
			}
		}
	}
	
	private void update() {
		comprobarVidaAliens();
	}

	private void comprobarVidaAliens() {
		for (int i = 0; i < aliens2.size; i++) {
			if(aliens2.get(i).getVida() == 0) {
				aliens2.get(i).setMuerto(true);
				eliminarAliens(i);
			}
		}
	}

	private void eliminarAliens(int posAlien) {
		aliens2.removeIndex(posAlien);
	}

	@Override
	public void crear() {
		mapaPelea = new MapaPeleaDungeon1(jugador, gestorMapas);
		mapaPelea.crear();
		mapa = cargadorMapa.load("mapas/dungeons/dungeon1/dungeon1.tmx");
		crearEnemigos();
		crearCapas();
		renderer = new OrthogonalTiledMapRenderer(mapa);
		camara.update();
	}

	public void crearCapas() {
		
		colisiones = new Array<Rectangle>();
		
		MapObjects piso = mapa.getLayers().get("pisoObj").getObjects();
		MapObjects colisionables = mapa.getLayers().get("colisionables").getObjects();
		
		for (int i = 0; i < colisionables.getCount(); i++) {
			RectangleMapObject rectColiObj = (RectangleMapObject) colisionables.get(i);
			Rectangle rectColi = rectColiObj.getRectangle();
			colisiones.add(new Rectangle(rectColi.getX(), rectColi.getY(), rectColi.getWidth(), rectColi.getHeight()));
		}
		
		System.out.println(piso.getCount());
		RectangleMapObject rectPiso = (RectangleMapObject) piso.get(0);

		rectanguloPiso = rectPiso.getRectangle();
		
	}
	
	public void mostrarColisiones() {
		Utiles.sr.begin(ShapeType.Line);
		
			Utiles.sr.setColor(Color.BLUE);
			Utiles.sr.rect(0,0,getLimiteMapa().getWidth(), getLimiteMapa().getHeight());
			
			for(int i = 0 ; i < getRectColision().size ; i++) {
				Utiles.sr.rect(getRectColision().get(i).getX(), getRectColision().get(i).getY(), getRectColision().get(i).getWidth(), getRectColision().get(i).getHeight());
			}
			
		Utiles.sr.end();
	}
	
	public void crearEnemigos() {
		
		aliensObj = new MapObjects();
		aliensObj = mapa.getLayers().get("aliens").getObjects();
		aliens2 = new Array<Alien>();
		
		for (int i = 0; i < aliensObj.getCount(); i++) {
			aliens2.add(new Alien());
			aliens2.get(i).setPosicion((float) aliensObj.get(i).getProperties().get("x"), (float) aliensObj.get(i).getProperties().get("y"));
			aliens2.get(i).getRectangulo().setX((float) aliensObj.get(i).getProperties().get("x"));
			aliens2.get(i).getRectangulo().setY((float) aliensObj.get(i).getProperties().get("y"));
			aliens2.get(i).getCirculoAlien().setX((float) aliensObj.get(i).getProperties().get("x"));
			aliens2.get(i).getCirculoAlien().setY((float) aliensObj.get(i).getProperties().get("y"));
//			aliens[i] = new Alien();
//			aliens[i].setPosicion((float) aliensObj.get(i).getProperties().get("x"), (float) aliensObj.get(i).getProperties().get("y"));
//			aliens[i].getRectangulo().setX((float) aliensObj.get(i).getProperties().get("x"));
//			aliens[i].getRectangulo().setY((float) aliensObj.get(i).getProperties().get("y"));
//			aliens[i].getCirculoAlien().setX((float) aliensObj.get(i).getProperties().get("x"));
//			aliens[i].getCirculoAlien().setY((float) aliensObj.get(i).getProperties().get("y"));
		}
		
	}

	@Override
	public boolean comprobarColision(Entidad entidad) {
		boolean colision = false;
		
		for (int i = 0; i < colisiones.size; i++) {
			if(Intersector.overlaps(entidad.getRectangulo(), colisiones.get(i))) {
				colision = true;
			}
		}
		
		if(!rectanguloPiso.contains(entidad.getRectangulo())) {
			System.out.println("Fuera del mapa");
			colision = true;
		}
		return colision;
	}

	@Override
	public boolean comprobarSalidaMapa() {
		cambioMapa = false;
		int i = 0;
		do {
			if(aliens2.notEmpty()) {
				if(!aliens2.get(i).isMuerto()) {
					if(Intersector.overlaps(jugador.getRectangulo(), aliens2.get(i).getRectangulo())) {
						cambioMapa = true;
						alienPelea = aliens2.get(i);
						break;
					}else {
						cambioMapa = false;
					}
				}
			}
				
		}while(++i < aliens2.size);
		return cambioMapa;
	}

	@Override
	public Mapa cambioMapa() {
		mapaPelea.pasarEnemigo(alienPelea);
		posicionPelea.x = jugador.getPosicion().x;
		posicionPelea.y = jugador.getPosicion().y;
		vieneDePelea = true;
		return mapaPelea;
	}

	@Override
	public Array<Rectangle> getRectColision() {
		return colisiones;
	}

	@Override
	public Array<Polygon> getPoliColision() {
		
		return null;
	}

	@Override
	public Array<Rectangle> getZonasCambioMapa() {
		
		return null;
	}

	@Override
	public Rectangle getLimiteMapa() {
		return rectanguloPiso;
	}

}
