package strategy;

import dijkstra.Dijkstra;
import Voiture.Commande;
import Voiture.Voiture;

public class StrategyDebile implements Strategy {
	
	private Strategy s;
	private Voiture v;
	private double[][] tab;
	private double[] param = new double[] {
			
//	Exemple :
//		2140,2250,0.6,
//		1920,1980,0.4,
			
			
	};
	
	
	public StrategyDebile(Strategy s,Voiture v,Dijkstra dij){
		this.s = s;
		this.v = v;
		tab = dij.getMatrice();
		
	}
	public Commande getCommande() {

		int i = (int)v.getPosition().getX();
		int j = (int)v.getPosition().getY();
		double acc = 1;
		System.out.println(tab[i][j]);
		if(v.getVitesse() == 0){
			return new Commande(1,s.getCommande().getTurn());
		}
		
		for(int n = 0; n < param.length; n+=3){
			if(tab[i][j] > param[n] && tab[i][j] < param[n+1]){
				if(v.getVitesse() < param[n+2]){
					acc = 1;
				}
				else
					acc = -1;
				break;
			}
		}
        return new Commande(acc,s.getCommande().getTurn());
		
	}
	
	
}


