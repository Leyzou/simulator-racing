package main;

import java.io.IOException;

import optimisation.CrossingOperator;
import optimisation.CrossingOperatorImpl;
import optimisation.FitnessOperator;
import optimisation.FitnessOperatorImpl;
import optimisation.GenereGenome;
import optimisation.GeneticAlgorithm;
import optimisation.Genome;
import optimisation.GenomeGenerator;
import optimisation.MutationOperator;
import optimisation.MutationOperatorImplDouble;
import Circuit.Circuit;
import Circuit.CircuitFactoryFromFile;
import Voiture.VoitureException;

public class MainGenome {
	public static void main(String[] args) throws VoitureException, IOException {
		
//		String fichier = Fonctions.selectionnerCircuit();
		String fichier = "1_safe.trk";
	    Circuit c = CircuitFactoryFromFile.build("Tracks/" + fichier);
//	    StrategyGenome s = new StrategyGenome(v,r,c);
	    GenomeGenerator<Double> sGen = new GenereGenome(fichier);
	    @SuppressWarnings("unused")
		Genome<Double> gen = sGen.build();
	    
	    int populationSize = 10;
	    double rate  = 0.1; // pour la mutation
	    MutationOperator<Double> muteOp = new MutationOperatorImplDouble(rate, ((GenereGenome) sGen).getSigma());
	    CrossingOperator<Double> crossOp = new CrossingOperatorImpl<Double>();
	    FitnessOperator<Double> fitnessOp = new FitnessOperatorImpl(c,fichier);	
	
	    GeneticAlgorithm<Double> ga = new GeneticAlgorithm<Double>(populationSize, sGen,muteOp, crossOp, fitnessOp);
	    Genome<Double> res = ga.optimize(10);
	
	    System.out.println("Best : "+res.getFitness());
	    for(int i=0; i<res.size(); i++) System.out.print(res.get(i)+", ");
	    System.out.println();
	}
}
