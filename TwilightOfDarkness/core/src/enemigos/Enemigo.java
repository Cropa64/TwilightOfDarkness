package enemigos;

import com.badlogic.gdx.graphics.g2d.Sprite;

import personajes.Entidad;
import personajes.PersonajePrincipal;

public abstract class Enemigo extends Entidad {

	protected int vida, vidaMax;
	
	public Enemigo(Sprite sprite) {
		super(sprite);
	}

	public abstract void hacerDanio(PersonajePrincipal jugador);
	
	public abstract void recibirDanio(int cantidad);
	
	public int getVida() {
		return vida;
	}
	
	public int getVidaMax() {
		return vidaMax;
	}
	
	public void quitarVida(float vida) {
		this.vida -= vida;
		
		if(this.vida < 0) {
			this.vida = 0;
		}
		if(this.vida > vidaMax) {
			this.vida = vidaMax;
		}
		
	}
	
}
