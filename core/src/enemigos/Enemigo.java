package enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;

import personajes.Entidad;

public abstract class Enemigo extends Entidad {

	protected int vida, vidaMax;
	
	public Enemigo(Sprite sprite) {
		super(sprite);
	}

	public abstract void hacerDa�o();
	
	public abstract void recibirDa�o(int cantidad);
	
	public int getVida() {
		return vida;
	}
	
	public int getVidaMax() {
		return vidaMax;
	}
	
}
