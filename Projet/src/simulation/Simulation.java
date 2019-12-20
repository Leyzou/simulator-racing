package simulation;

import geometrie.Vecteur;

import java.util.ArrayList;

import strategy.Strategy;
import tools.TerrainTools;
import Voiture.Commande;
import Voiture.Voiture;
import Voiture.VoitureException;
import Circuit.Circuit;
import Circuit.Terrain;
import IHM.UpdateEventListener;
import IHM.UpdateEventSender;

public class Simulation implements UpdateEventSender{

	private ArrayList<Vecteur> trajet = new ArrayList<Vecteur>();
	private Circuit c;
	private Voiture v;
	private Strategy s;
	private ArrayList<Commande> record = new ArrayList<Commande>();
	private ArrayList<UpdateEventListener> list = new ArrayList<UpdateEventListener>();
	private boolean reussi = false;
	private boolean animated = false;
	private int i =0;
	private Vecteur lastDij;
	
	public Simulation(Circuit c, Voiture v, Strategy s){
		this.c=c;
		this.v=v;
		this.s=s;
		
	}
	
	public int play() throws VoitureException{
		while(animated && TerrainTools.isRunnable(c.getTerrain((int)v.getPosition().getX(), (int)v.getPosition().getY())) &&
				c.getTerrain((int)v.getPosition().getX(),(int)v.getPosition().getY()) != Terrain.EndLine){
			i++;
			trajet.add(new Vecteur(v.getPosition().getX(),v.getPosition().getY()));
			Commande com = s.getCommande();
			lastDij = v.getPosition();
			v.drive(com);
			update();
			record.add(com);
			trajet.add(new Vecteur(v.getPosition().getX(),v.getPosition().getY()));
		}
		if(c.getTerrain(v.getPosition()) == Terrain.EndLine && v.getDirection().scalaire(c.getDirectionArrivee()) > 0)
			reussi = true;
		else
			reussi = false;
		System.out.println(reussi);
		return i;
	}
	
	public ArrayList<Vecteur> getList(){
			return trajet;
	}
	
	public ArrayList<Commande> getRecord(){
		     return record;
	}
	
	@Override
	public void add(UpdateEventListener listener) {
		list.add(listener);
		
	}
	
	@Override
	public void update() {
		for(UpdateEventListener listener : list)
				listener.manageUpdate();
		
	}
	
	public boolean isReussi(){
		return reussi;
	}
	

	public void jouer(){
		Thread t = new Thread(){
				public void run(){
					try{
						animated = true;
						play();
						System.out.println(i);
					} catch (VoitureException e1){
						e1.printStackTrace();
					}
				}
			};
			t.start();
	}
	
	public void init(Strategy s, Circuit c,Voiture v){
		this.c = c;
		this.s = s;
		this.v = v;
		i = 0;
	}


	public Circuit getCircuit() {
		return c;
	}

	public Voiture getVoiture() {
		return v;
	}
	
	public Strategy getStrategy(){
		return s;
	}

	
	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}

	public int getI(){
		return i;
	}
	
	public Vecteur getLastDij(){
		return lastDij;
	}
}

