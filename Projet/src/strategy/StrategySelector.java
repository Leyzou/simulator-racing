/**
 * 
 */
package strategy;

import java.util.ArrayList;

import strategy.selector.Selector;
import Voiture.Commande;

public class StrategySelector implements Strategy {

	private ArrayList<Strategy> liStrategy;
	private ArrayList<Selector> liSelector;
	
	public StrategySelector(){
		liStrategy = new ArrayList<Strategy>();
		liSelector = new ArrayList<Selector>();
	}
	
	public void add(Strategy str, Selector select){
		liStrategy.add(str);
		liSelector.add(select);
	}
	
	public Commande getCommande() {
		for(int i=0; i<liStrategy.size();i++){
			if(liSelector.get(i).isSelected()){
				return liStrategy.get(i).getCommande();
			}
		}
		return liStrategy.get(liStrategy.size()-1).getCommande();
	}

}
