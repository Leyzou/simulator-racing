package Circuit;

import geometrie.Vecteur;

import java.util.ArrayList;

import dijkstra.Dijkstra;

public class CircuitImpl implements Circuit{
	private int width;
	private int height;
	private Terrain[][] terrain;
	private final Vecteur dirDepart = new Vecteur(0,1);
	private final Vecteur dirArrivee = new Vecteur(0,1);
	private Dijkstra dijk;
	
	public CircuitImpl(Terrain[][] t, int width, int height){
		terrain=t;
		this.width = width;
		this.height = height;
		dijk= new Dijkstra(this);
		
	}
	
	public Terrain getTerrain(int i, int j){
		return terrain[i][j];
	}
	
	public Terrain getTerrain(Vecteur v){
		return terrain[(int) v.getX()][(int) v.getY()];
	}
	
	
    public Vecteur getPointDepart(){
    	
    	
    	for(int i=0 ; i < height ; i++){
			   for(int j=0; j < width ; j++){
				   if (terrain[i][j]==Terrain.StartPoint){
					   return new Vecteur(i,j);
				   }
			   }
    	}
    	return new Vecteur(0,0);
    }

    public Vecteur getDirectionDepart(){
    	return dirDepart;
    }
    
    public Vecteur getDirectionArrivee(){
    	return dirArrivee;
    }
	
	public int getWidth() {
		return width;
	}
	
    public int getHeight(){
    	return height;
    }
    
	public ArrayList<Vecteur> getArrivees(){
    	ArrayList<Vecteur> list = new ArrayList<Vecteur>();
    	for(int i=0 ; i < height ; i++){
			   for(int j=0; j < width ; j++){
				   if (terrain[i][j]==Terrain.EndLine){
					   list.add(new Vecteur(i,j));
				   }
			   }
    	}
    return list;
    }
  
    public double getDist(int i, int j){
		return dijk.getMatrice()[i][j];
    	
    }
    
    public Circuit clone(){
    	return new CircuitImpl(terrain,width,height);
    }

}
