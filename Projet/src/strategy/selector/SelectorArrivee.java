package strategy.selector;

import Voiture.Voiture;
import geometrie.Vecteur;
import tools.TerrainTools;
import Circuit.Circuit;
import Circuit.Terrain;

public class SelectorArrivee implements Selector{

	private double[] thetas = {0,-Math.PI/8,Math.PI/8};
	private Circuit c;
	private Voiture v;
	private boolean sel;
	
	public SelectorArrivee(Circuit c, Voiture v){
		this.c = c;
		this.v = v;
	}
	
	
	private void endLineIsVisible(){
		Vecteur dir;
		Vecteur pos;
		for(int i=0 ; i < thetas.length ; i++){
			pos = v.getPosition();
			dir= v.getDirection();
			dir.rotation(thetas[i]);

			while(TerrainTools.isRunnable(c.getTerrain(pos)) && c.getTerrain(pos) != Terrain.EndLine){
				pos.additionVoid(dir.multiplicationReturn(0.1));
			}
			if(c.getTerrain(pos) == Terrain.EndLine){
				sel = true;
				break;
			}
			else
				sel = false;
		}
	}
	
	public boolean isSelected() {
		endLineIsVisible();
		return sel;
	}

}
