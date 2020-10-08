package utilidades;

import java.util.EventListener;

import objetos.Item;

public interface InventarioEventListener extends EventListener{

	public void organizarInventario(Item[] hotbar, int[] contHotBar);
	
}
