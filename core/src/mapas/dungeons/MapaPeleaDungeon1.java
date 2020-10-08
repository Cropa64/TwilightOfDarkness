package mapas.dungeons;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import enemigos.Alien;
import mapas.gestorMapas.GestorMapas;
import mapas.gestorMapas.Mapa;
import personajes.Entidad;
import personajes.PersonajePrincipal;
import utilidades.Utiles;

public class MapaPeleaDungeon1 extends Dungeon1{

	private TiledMapImageLayer capaImagen;
	private TiledMapTileLayer capaSuelo;
	private Rectangle limiteMapa;
	private MapObjects limite;
	private Alien alienPelea;
	
	public MapaPeleaDungeon1(PersonajePrincipal jugador, GestorMapas gestorMapas) {
		super(jugador, gestorMapas);
	}

	@Override
	public void setPosicionJugador() {
		jugador.setPosicion(75, 33);
		alienPelea.setPosicion(300, 36);
		alienPelea.getCirculoAlien().setX(alienPelea.getPosicion().x);
		alienPelea.getCirculoAlien().setY(alienPelea.getPosicion().y);
	}

	@Override
	public void renderizar() {
		renderizarImagen();
		renderizarSuelo();
		jugador.movimiento();

		dibujarAlien();
	}

	private void dibujarAlien() {
		alienPelea.setAtacando(false);
		
		Utiles.sr.begin(ShapeType.Line);
		alienPelea.comportamiento(jugador, this);
		alienPelea.mostrarColisiones();
		Utiles.sr.end();
		
		alienPelea.barraVida();
	}

	@Override
	public void crear() {
		
		mapa = cargadorMapa.load("mapas/dungeons/dungeon1/mapaPelea/dungeon1Pelea.tmx");
		
		MapProperties propiedadesMapa = mapa.getProperties();
		int anchoTile = (int) propiedadesMapa.get("tilewidth");
		int altoTile = (int) propiedadesMapa.get("tileheight");
		int anchoMapaEnTiles = (int) propiedadesMapa.get("width");
		int altoMapaEnTiles = (int) propiedadesMapa.get("height");
		int anchoMapaPixels = anchoTile*anchoMapaEnTiles;
		int altoMapaPixels = altoTile*altoMapaEnTiles;
		
		crearCapas();
		renderer = new OrthogonalTiledMapRenderer(mapa);
		camara.position.set(anchoMapaPixels, altoMapaPixels, 0);
		camara.update();
		
	}

	public void crearCapas() {
		capaImagen = (TiledMapImageLayer) mapa.getLayers().get("imagenFondo");
		capaSuelo = (TiledMapTileLayer) mapa.getLayers().get("capaSuelo");
		limite = mapa.getLayers().get("limiteMapa").getObjects();
		
		RectangleMapObject rectanguloLimite = (RectangleMapObject) limite.get(0);
		limiteMapa = rectanguloLimite.getRectangle();
	}
	
	public void renderizarImagen() {
		renderer.getBatch().begin();
		renderer.renderImageLayer(capaImagen);
		renderer.getBatch().end();
	}
	
	public void renderizarSuelo() {
		renderer.getBatch().begin();
		renderer.renderTileLayer(capaSuelo);
		renderer.getBatch().end();
	}
	
	public void pasarEnemigo(Alien alienPelea) {
		this.alienPelea = alienPelea;
	}
	
	@Override
	public boolean comprobarColision(Entidad entidad) {
		boolean fueraMapa = false;
		
		if(!limiteMapa.contains(jugador.getRectangulo())) {
			fueraMapa = true;
		}
		
		return fueraMapa;
	}
	
	@Override
	public boolean comprobarSalidaMapa() {
		alienPelea.quitarVida(0.2f);
		if(alienPelea.getVida() == 0){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Mapa cambioMapa() {
		return gestorMapas.getMapaDungeon1();
	}

	@Override
	public void mostrarColisiones() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Array<Rectangle> getRectColision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array<Polygon> getPoliColision() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array<Rectangle> getZonasCambioMapa() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getLimiteMapa() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
