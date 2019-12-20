package main;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import simulation.Simulation;
import strategy.Strategy;
import strategy.StrategyArrivee;
import strategy.StrategyPrudente;
import strategy.StrategyRadarDijkstra;
import strategy.StrategySelector;
import strategy.selector.SelectorArrivee;
import strategy.selector.SelectorDijkstra;
import strategy.selector.SelectorPrudent;
import tools.DessinTools;
import tools.ToolsFichiers;
import Circuit.Circuit;
import Circuit.CircuitFactoryFromFile;
import Radar.Radar;
import Radar.RadarDijkstra;
import Voiture.Voiture;
import Voiture.VoitureException;
import Voiture.VoitureFactory;
import dijkstra.Dijkstra;

public class MainEasy {
	public static void main(String[] args) throws FileNotFoundException,IOException, VoitureException{
	
		String file = "7_safe.trk";
		
		
		
		
		Circuit  circuit = CircuitFactoryFromFile.build("Tracks/Tracks-Exam/"+file);
		
		Voiture v = VoitureFactory.build(circuit.getPointDepart(), circuit.getDirectionDepart());
		
		
		
		//----------Ajout bande goudron-(x1,y1,x2,y2)--------------------------------
//		ToolsFichiers.fichierFromTerrain(TerrainTools.ajouterCarreRoute(circuit,100,400,600,450), "Tracks/Tracks-Exam"+file+"-perso");
//		circuit = CircuitFactoryFromFile.build("Tracks/Tracks-Exam"+file+"-perso");
		//---------------------------------------------------------------------------
		
		
			
		
		
		Dijkstra dij =  new Dijkstra(circuit) ;
		DessinTools.dessinerDijkstra("Dijkstra-Exam/"+file, circuit, dij.getMax(), Color.orange);
		
		
		
		Radar r = new RadarDijkstra(v, circuit, dij.getMatrice());
		

		
		
		Strategy sdij = new StrategyRadarDijkstra(v, r);

		
		
		StrategySelector s = new StrategySelector();
		Strategy pru = new StrategyPrudente(sdij);
		Strategy el = new StrategyArrivee(circuit, v);
		
		s.add(el, new SelectorArrivee(circuit, v));
		s.add(pru, new SelectorPrudent(v, circuit));
		s.add(sdij, new SelectorDijkstra());
		
		

		
		Simulation sim = new Simulation (circuit, v, s);
		
//		IHMSwing ihm = new IHMSwing();
//		ManageIHM.manageIHM(ihm, circuit, v,r);
//		sim.add(ihm);
		
//		@SuppressWarnings("unused")
//		Fenetre fen = new Fenetre(sim, ihm);
		
		

		sim.setAnimated(true);
		sim.play();
		System.out.println(sim.getI());
		DessinTools.dessinerTrajet("Images-Exam/"+file, circuit, sim.getList(), Color.BLACK);
		ToolsFichiers.saveListeCommande(sim.getRecord(), "Logs-Exam/"+file);
	}
	
}
