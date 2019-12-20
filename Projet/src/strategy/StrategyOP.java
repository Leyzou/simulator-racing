package strategy;

import Radar.Radar;
import Voiture.Commande;
import Voiture.Voiture;

public class StrategyOP implements Strategy{
	private Radar r;
	private Voiture v;
	
//	private double distSecu =46;
//	private double accSecu = -0.82;
	private double factAngle_Secu = 30;
	private double accVirage = -1;
	private double vitLim = 0.59;
	private double petitAngle = 1.5;
	
	
	public StrategyOP(Voiture v,Radar r){
		this.r=r;
		this.v=v;
	}
	
	
	public Commande getCommande() {
		r.scores();
		double acc = 1;
		double turn = r.thetas()[r.getBestIndex()] / v.getBraquage(); 		
		double maxTurn = v.getMaxTurn();
		double angle = turn;
		
		if(turn < -v.getMaxTurn())
			angle = -v.getMaxTurn();
		if(turn > v.getMaxTurn())
			angle = v.getMaxTurn();

		
		
//		if(r.distancesInPixels()[0] < distSecu){
//			acc=accSecu;
//		}
//		if(r.distancesInPixels()[r.getBestIndex()] < 1.5 * distSecu){
//			acc=accSecu;
//		}
		if(((factAngle_Secu * maxTurn) ) < Math.abs(turn)){
			acc=accVirage;
		}
		if(Math.abs(turn) < petitAngle && r.distancesInPixels()[r.getBestIndex()] > 100){
			acc=1;
		}
		if(v.getVitesse() < vitLim){
			acc=1;
		}
		

		return new Commande(acc,angle);
			
	}
	
//	public void setDistSecu(double distSecu) {
//		this.distSecu = distSecu;
//	}



//	public void setAccSecu(double accSecu) {
//		this.accSecu = accSecu;
//	}



	public void setFactAngle_Secu(double factAngle_Secu) {
		this.factAngle_Secu = factAngle_Secu;
	}



	public void setAccVirage(double accVirage) {
		this.accVirage = accVirage;
	}



	public void setVitLim(double vitLim) {
		this.vitLim = vitLim;
	}

}
