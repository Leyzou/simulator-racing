package tools;

import java.io.*;
import java.util.ArrayList;

import Circuit.Terrain;
import Voiture.Commande;

public class ToolsFichiers {

	public static void saveListeCommande(ArrayList<Commande> liste, String filename){
        try {
                DataOutputStream os = new DataOutputStream(new FileOutputStream(filename));
                for(Commande c:liste){
                        os.writeDouble(c.getAcc());
                        os.writeDouble(c.getTurn());
                }
                os.close();
                System.out.println("Fichier logs \""+filename+"\" cree avec succes.");
        } catch (IOException e) {
                e.printStackTrace();
        }
	}
	
	@SuppressWarnings("resource")
	public static ArrayList<Commande> loadListeCommande(  String filename) throws IOException{
        ArrayList<Commande> liste = null;
        DataInputStream os;
        try {
                os = new DataInputStream(new FileInputStream(filename));

                liste = new ArrayList<Commande>();
                double a,t;
                while(true){ // on attend la fin de fichier
                        a = os.readDouble();
                        t = os.readDouble();
                        liste.add(new Commande(a,t));
                }

        } catch (EOFException e){
                return liste;
        }
	
	}
	   public static void fichierFromTerrain(Terrain[][] terrain, String filename){
		   try {
			   FileWriter fw = new FileWriter(filename, false);
			   BufferedWriter output = new BufferedWriter(fw);
			   output.write(terrain[0].length+"\n");
			   output.write(terrain.length+"\n");
			   for(int i =0; i < terrain.length ; i++){
				   for(int j = 0; j< terrain[0].length ; j++){
						output.write(TerrainTools.charFromTerrain(terrain[i][j]));
					}
				   output.write("\n");
			   }
			   output.flush();
			   output.close();
			   System.out.println(filename + " créé avec succès.");
		   }catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
	   }		   

}

