package strategy;

import tools.TerrainTools;
import geometrie.Vecteur;
import Circuit.Circuit;
import Circuit.Terrain;
import Voiture.Commande;
import Voiture.Voiture;

public class StrategyPointToPoint implements Strategy{
	
	private Circuit c;
	private Voiture v;
	private Vecteur[] points;
	private double[][] distDij;
	private final double cst = 0.5;
	private int bestIndex = -1;

	public StrategyPointToPoint(Circuit c, Voiture v, Vecteur[] points, double[][] distDij) {
		this.c = c;
		this.v = v;
		this.points = points;
		this.distDij = distDij;
	}
	
	
	
	private boolean isVisible(Vecteur cible){
		Vecteur pos = v.getPosition();
		Vecteur u = cible.soustractionReturn(pos);
		u.normalisation();
		if(v.getDirection().scalaire(u) < 0) return false;
		
		while(pos.distanceTo(cible) > cst){
			pos.additionVoid(u.multiplicationReturn(0.1));
			if(c.getTerrain(pos) == Terrain.EndLine && u.scalaire(c.getDirectionArrivee()) < 0)
				return false;
			if(!TerrainTools.isRunnable(c.getTerrain(pos))){
				return false;
			}
		}
		return true;
	}
	
	private void getBestIndex(){

		for(int i = 0 ; i < points.length ; i++){
			if(!isVisible(points[i])){
				continue;
			}
			else{
				if(bestIndex == -1){
					bestIndex = i;
					continue;
				}
				//si le point courant est plus proche de l'arrivee que le meilleur point, on le selectionne comme meilleur point
				if(distDij[(int) points[i].getX()][(int) points[i].getY()] < distDij[(int) points[bestIndex].getX()][(int) points[bestIndex].getY()]){
					bestIndex = i;
				}	
			}
		}
	}
	
	public Vecteur getBestPoint(){
		return points[bestIndex];
	}
	
	public Commande getCommande() {
		getBestIndex();
		if(bestIndex == -1)
			return new Commande(1,0);
		Vecteur pos = v.getPosition();
		Vecteur dir = points[bestIndex].soustractionReturn(pos);
		//dir.normalisation();
		double angle = Vecteur.angle(dir, v.getDirection());
		if(v.getDirection().vectoriel(dir) < 0)
			angle = -angle;
		double c = angle;
		if(v.getVitesse() >= 0.3 && c != 0)
			return new Commande(-1,Math.signum(c)*v.getMaxTurn());
		if(v.getVitesse() == 0)
			return new Commande(1,0);
		if(c == 0)
			return new Commande(1,0);

		return new Commande(0.1,Math.signum(c)*v.getMaxTurn());
	}

}
