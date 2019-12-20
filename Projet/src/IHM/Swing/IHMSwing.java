package IHM.Swing;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import IHM.UpdateEventListener;

public class IHMSwing extends JPanel implements UpdateEventListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1222617095106103800L;
	private ArrayList<ObserverSWING> list;
	
	public IHMSwing(){
		list = new ArrayList<ObserverSWING>();
	}

	//gestion des évènements standard
	@Override
	public void manageUpdate() {
		
		try {
			Thread.sleep(4);
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
										public void run() {
											repaint();
										}
									});
	}
	
	//surcharge de la méthode d'affichage du composant
	public void paint(Graphics g){
		super.paint(g);
		//code à ajouter pour dessiner ce que l'on veut
		for(ObserverSWING o : list){
			o.print(g);
		}
	}
	
	
	public void add(ObserverSWING o){
		list.add(o);
	}

	public void reset() {
		list.clear();
		
	}
	
}
