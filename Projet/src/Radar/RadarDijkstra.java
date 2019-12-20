package Radar;

import tools.TerrainTools;
import geometrie.Vecteur;
import Circuit.Circuit;
import Circuit.Terrain;
import Voiture.Voiture;

public class RadarDijkstra implements Radar{
	
	private double[] thetas = {0,-0.9,Math.PI/2,-Math.PI/2,-0.6,-0.3,0.3,0.6,0.9,-(Math.PI/12),-(Math.PI/20),(Math.PI/20),(Math.PI/12),(Math.PI/8),-(Math.PI/8),(Math.PI/30),-(Math.PI/30)};
//	private double[] thetas = {0,0.04,-0.04,0.03,-0.03,0.05,-0.05,0.1,-0.1,0.15,-0.15,0.25,-0.25,0.2,-0.2,0.35,-0.35,0.4,-0.4,0.45,-0.45,-0.9,Math.PI/2,-Math.PI/2,-0.6,-0.3,0.3,0.6,0.9,-(Math.PI/12),-(Math.PI/20),(Math.PI/20),(Math.PI/12),(Math.PI/8),-(Math.PI/8),(Math.PI/30),-(Math.PI/30)};
	
	private double[] distPix = new double[thetas.length];
	
	private Voiture v;
	private Circuit c;
	private int bestIndex=0;
	private double[][] distDij;
	
	public RadarDijkstra(Voiture v,Circuit c,double[][] d){
		this.v = v;
		this.c = c;
		distDij=d;
		distPix = scores();
		triTab();
		
	}
	
	public  void triTab(){
		int i = thetas.length;
		boolean test;
		do{
			test=false;
			for(int j=0;j<i-1;j++){
				if(Math.abs(thetas[j])>Math.abs(thetas[j+1])){
				double t = thetas[j];
				thetas[j] = thetas[j+1];
				thetas[j+1] = t;
				test=true;
				}
			}
		i--;		
		}while(test==true);
		
	}
	

	public double[] scores(){	
		Vecteur dir;
		Vecteur pos;
		int currentX;
		int currentY;
		
		double score = Double.POSITIVE_INFINITY;
		double bestScore = Double.POSITIVE_INFINITY;
		bestIndex = 0;
		
		
		for(int i=0 ; i < distPix.length ; i++){
			pos = v.getPosition();
			currentX = (int) pos.getX();
			currentY = (int) pos.getY();
			
			dir= v.getDirection();
			dir.rotation(thetas[i]);
			
			
			//   **************************
			//   **************************
			//   /!\
//			dir = dir.multiplicationReturn(0.09);
			//   /!\	
			//   **************************
			//   **************************	
			
			
			while(TerrainTools.isRunnable(c.getTerrain((int) pos.getX(), (int) pos.getY()))){
				if(c.getTerrain(pos) == Terrain.EndLine)
					if(dir.scalaire(c.getDirectionArrivee()) > 0){
						score = 0;
						break;
					}
					else{
						score = Double.POSITIVE_INFINITY;
						break;
					}
				pos.additionVoid(dir);
				
				// si la distance Dij de la position actuelle est inférieure à la meilleure distance actuelle, on remplace cette meilleure distance
				if(distDij[(int) pos.getX()][(int) pos.getY()] < distDij[currentX][currentY]){
					currentX= (int) pos.getX();
					currentY= (int) pos.getY();
				}
				score = distDij[currentX][currentY];
			}
			
			if(score < bestScore){
				bestScore = distDij[currentX][currentY];
				bestIndex = i;
			}

			
			distPix[i] = (Math.sqrt( Math.pow( pos.getX() - v.getPosition().getX() , 2) + Math.pow( pos.getY() - v.getPosition().getY() , 2)));
			if(score == bestScore){
				if(distPix[i] < distPix[bestIndex])
					bestIndex = i;
			}
			
		}
		return distPix;
	}
	
	@Override 
	public double[] distancesInPixels() {
		return distPix;
	}

	@Override
	public int getBestIndex() {
		return bestIndex;
	}

	@Override
	public double[] thetas() {
		return thetas;
	}

	
	
}
