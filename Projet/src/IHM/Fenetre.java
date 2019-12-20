package IHM;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import optimisation.GenereGenome;
import optimisation.Genome;
import dijkstra.Dijkstra;
import Radar.Radar;
import Radar.RadarDijkstra;
import Voiture.Voiture;
import Voiture.VoitureFactory;
import simulation.Simulation;
import strategy.Strategy;
import strategy.StrategyGenome;
import Circuit.Circuit;
import Circuit.CircuitFactoryFromFile;
import IHM.Swing.IHMSwing;
import IHM.Swing.ManageIHM;

public class Fenetre extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private IHMSwing ihm;
	
	String file = "Tracks/1_safe.trk-perso";
	String strcirc = "1_safe.trk";
	
	private JComboBox<String> circuitBox = new JComboBox<String>();
	private JComboBox<String> circuitBox2 = new JComboBox<String>();
	private JComboBox<String> circuitPersoBox = new JComboBox<String>();
	private JButton go = new JButton("Go");
	private JButton stop = new JButton("Stop");
	private JButton swap = new JButton("Swap");
	private JButton save = new JButton("Save");
	private JButton reset = new JButton("Reset");
	private JButton swap2 = new JButton("Swap");
	private JPanel fondfond = new JPanel();
	private JPanel centre2;
	private ImagePerso imp; 
	private JLabel lab;
	
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton petit = new JRadioButton();
	private JRadioButton moyen = new JRadioButton();
	private JRadioButton grand = new JRadioButton();
	private JLabel jpetit;
	private JLabel jmoyen;
	private JLabel jgrand;
	
	
	private Simulation sim;
	private Circuit circuit = CircuitFactoryFromFile.build("Tracks/1_safe.trk");
	private Circuit circuitSwap = CircuitFactoryFromFile.build("Tracks/1_safe.trk");
	private Strategy s;
	private Voiture v;
	private Radar r;
	private Dijkstra dij;
	private int stratSelector = 1;
	private int circuitSelector = 1;
	private int circuitPersoSelector = 0;
	
	private CardLayout cl = new CardLayout();
	
	public Fenetre(Simulation sim, IHMSwing ihm) throws FileNotFoundException{
		this.setSize(1920,1080);
		this.setPreferredSize(new Dimension(1920, 1024));
		this.setMaximumSize(new Dimension(1920, 1024));
		this.setMinimumSize(new Dimension(1920, 1024));
		this.setLocation(0, 0);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.sim = sim;
		this.ihm = ihm;
		this.v = sim.getVoiture();
		this.s = sim.getStrategy();
		circuit = sim.getCircuit();
		circuitSwap = sim.getCircuit();
		bg.add(petit);
		bg.add(moyen);
		bg.add(grand);
		
		ImageIcon ipetit = new ImageIcon("src/icons/petit.png");
		jpetit = new JLabel("", ipetit, JLabel.CENTER);
		ImageIcon imoyen = new ImageIcon("src/icons/moyen.png");
		jmoyen = new JLabel("", imoyen, JLabel.CENTER);
		ImageIcon igrand = new ImageIcon("src/icons/grand.png");
		jgrand = new JLabel("", igrand, JLabel.CENTER);
		
		petit.setBackground(new Color(255,170,0));
		moyen.setBackground(new Color(255,170,0));
		grand.setBackground(new Color(255,170,0));
		petit.setSelected(true);
		petit.addActionListener(new StateListener());
		moyen.addActionListener(new StateListener());
		grand.addActionListener(new StateListener());
		
		
		//----------------------------------BOUTONS + COMBOBOXS-------------------------------
		
		go.setPreferredSize(new Dimension(80, 30));
		go.addActionListener(new BoutonListener()); 
	    go.setBackground(Color.red);
	    stop.setPreferredSize(new Dimension(80, 30));
	    stop.setBackground(Color.green);
	    stop.addActionListener(new Bouton2Listener());
	    swap.setPreferredSize(new Dimension(120,120));;
	    swap.addActionListener(new SwapListener());
	    swap.setForeground(Color.BLUE);
	    swap.setBackground(new Color(255,170,0));
	    boutonsSet();
	    
		save.setPreferredSize(new Dimension(80, 30));
		save.addActionListener(new SaveListener()); 
	    save.setBackground(new Color(102,255,0));
	    reset.setPreferredSize(new Dimension(80, 30));
	    reset.setBackground(new Color(166, 64, 64));
	    reset.addActionListener(new ResetListener());
	    swap2.setPreferredSize(new Dimension(120,120));;
	    swap2.addActionListener(new Swap2Listener());
	    swap2.setForeground(new Color(255,170,0));
	    swap2.setBackground(Color.blue);
	    
		
	    ihm.setBackground(Color.black);
		//selection circuit
	    circuitBox.setPreferredSize(new Dimension(100, 20));
		circuitBox.addItem("1_safe");
		circuitBox.addItem("2_safe");
		circuitBox.addItem("3_safe");
		circuitBox.addItem("4_safe");
		circuitBox.addItem("5_safe");
		circuitBox.addItem("6_safe");
		circuitBox.addItem("7_safe");
		circuitBox.addItem("8_safe");
		circuitBox.addItem("aufeu");
		circuitBox.addItem("bond");
		circuitBox.addItem("Een2");
		circuitBox.addItem("labymod");
		circuitBox.addItem("labyperso");
		circuitBox.addItem("perso");
		circuitBox.addItem("t260_safe");
		circuitBox.addItem("t2009");
	    circuitBox.addActionListener(new CircuitBoxListener());
	    
	    circuitBox2.setPreferredSize(new Dimension(100, 20));
		circuitBox2.addItem("1_safe");
		circuitBox2.addItem("2_safe");
		circuitBox2.addItem("3_safe");
		circuitBox2.addItem("4_safe");
		circuitBox2.addItem("5_safe");
		circuitBox2.addItem("6_safe");
		circuitBox2.addItem("7_safe");
		circuitBox2.addItem("8_safe");
		circuitBox2.addItem("aufeu");
		circuitBox2.addItem("bond");
		circuitBox2.addItem("Een2");
		circuitBox2.addItem("labymod");
		circuitBox2.addItem("labyperso");
		circuitBox2.addItem("perso");
		circuitBox2.addItem("t260_safe");
		circuitBox2.addItem("t2009");
	    circuitBox2.addActionListener(new CircuitBox2Listener());
	    
	    circuitPersoBox.setPreferredSize(new Dimension(100, 20));
	    circuitPersoBox.addItem("1_safe-perso");
		circuitPersoBox.addItem("2_safe-perso");
		circuitPersoBox.addItem("3_safe-perso");
		circuitPersoBox.addItem("4_safe-perso");
		circuitPersoBox.addItem("5_safe-perso");
		circuitPersoBox.addItem("6_safe-perso");
		circuitPersoBox.addItem("7_safe-perso");
		circuitPersoBox.addItem("8_safe-perso");
		circuitPersoBox.addItem("aufeu-perso");
		circuitPersoBox.addItem("bond-perso");
		circuitPersoBox.addItem("Een2-perso");
		circuitPersoBox.addItem("labymod-perso");
		circuitPersoBox.addItem("labyperso-perso");
		circuitPersoBox.addItem("perso-perso");
		circuitPersoBox.addItem("t260_safe-perso");
		circuitPersoBox.addItem("t2009-perso");
		circuitPersoBox.addActionListener(new CircuitPersoBoxListener());
	    
	    
	    
	    
	    
//------------------------------------------------------------------------------------------------------------------------------------------
//******************************************************************************************************************************************			
//-------------------------------------------------------------TEMPS REEL-----------------------------------------------------------------
//******************************************************************************************************************************************		
//------------------------------------------------------------------------------------------------------------------------------------------
	    
	    //---------------------------------------------------GAUCHE BLEU------------------------------------------------------------------
	    
		JPanel gauche = new JPanel();
		gauche.setMinimumSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		gauche.setPreferredSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		gauche.setBackground(Color.blue);
		GridBagLayout layoutg = new GridBagLayout();
		gauche.setLayout(layoutg);
		GridBagConstraints gcg = new GridBagConstraints();
//		gcg.fill = GridBagConstraints.BOTH;
		gcg.gridheight = 1;
		gcg.gridwidth = 1;
		
		gcg.gridx = 0;
		gcg.gridy= 0;
		JLabel jCircuit = new JLabel("Circuit");
		jCircuit.setForeground(Color.orange);
		jCircuit.setPreferredSize(new Dimension(100,20));
		gauche.add(jCircuit,gcg);
		
		gcg.gridx = 1;
		gcg.gridwidth = GridBagConstraints.REMAINDER;

		
		gcg.gridx = 0;
		gcg.gridy = 1;
		gauche.add(circuitBox,gcg);
		

		
		gcg.gridy=2;
		gcg.gridx=0;
		gcg.gridwidth = GridBagConstraints.REMAINDER;
		JLabel jCircuitPerso = new JLabel("Circuit-perso");
		jCircuitPerso.setForeground(Color.orange);
		gauche.add(jCircuitPerso,gcg);
		gcg.gridy = 3;
		gcg.gridwidth = GridBagConstraints.REMAINDER;
		gauche.add(circuitPersoBox,gcg);
		
		
		
		
		
		
//--------------------------------------------------------------CENTRE CIRCUIT---------------------------------------------------
		JPanel centre = new JPanel();
		centre.setMinimumSize(new Dimension(768,this.getHeight()));
		centre.setPreferredSize(new Dimension(768,this.getHeight()));
		centre.setBackground(Color.black);
		centre.setAlignmentY(TOP_ALIGNMENT);
		centre.add(ihm);

		
		
//------------------------------------------------------------DROITE BLEU----------------------------------------------------------
		JPanel droite = new JPanel();
		droite.setMinimumSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		droite.setPreferredSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		droite.setBackground(Color.blue);
		GridBagLayout layoutd = new GridBagLayout();
		droite.setLayout(layoutd);
		GridBagConstraints gcd = new GridBagConstraints();
		gcd.fill = GridBagConstraints.BOTH;
		gcd.gridx = 0;
		gcd.gridy= 0;
		gcd.gridheight = 1;
		gcd.gridwidth = 1;
		droite.add(go,gcd);
		gcd.gridx = 1;	
		gcd.gridwidth = GridBagConstraints.REMAINDER;
		droite.add(stop,gcd);
		
		gcd.gridx = 0;
		gcd.gridy= 1;
		gcd.anchor = GridBagConstraints.LINE_START;
		gcd.fill = GridBagConstraints.HORIZONTAL;
		gcd.gridwidth = GridBagConstraints.REMAINDER;
		droite.add(swap,gcd);
		
//-----------------------------------------------------------BANDES ORANGES---------------------------------------------------
		JPanel orangeLayout = new JPanel();
		orangeLayout.setMinimumSize(new Dimension(5,this.getHeight()));
		orangeLayout.setPreferredSize(new Dimension(5,this.getHeight()));
		orangeLayout.setBackground(new Color(255,170,0));

		
		JPanel orangeLayout2 = new JPanel();
		orangeLayout2.setMinimumSize(new Dimension(5,this.getHeight()));
		orangeLayout2.setPreferredSize(new Dimension(5,this.getHeight()));
		orangeLayout2.setBackground(new Color(255,170,0));
		
		
		
		
//---------------------------------------------------------FOND CONTENU IHM------------------------------------------------------
		JPanel fond = new JPanel();
		fond.setBackground(Color.blue);
		fond.setSize(new Dimension(this.getWidth(),this.getHeight()));
		fond.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		GridBagLayout layout = new GridBagLayout();
		fond.setLayout(layout);
		
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		fond.add(gauche,gc);
		
		gc.gridx = 1;
		fond.add(orangeLayout,gc);
		gc.gridx = 2;
		fond.add(ihm,gc);
		gc.gridx = 3;
		fond.add(orangeLayout2,gc);
		gc.gridx = 4;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		fond.add(droite,gc);
		
		
		
		
		
		
//------------------------------------------------------------------------------------------------------------------------------------------
//******************************************************************************************************************************************			
//-------------------------------------------------------------MODIFICATION-----------------------------------------------------------------
//******************************************************************************************************************************************		
//------------------------------------------------------------------------------------------------------------------------------------------		
	
		
		
//--------------------------------------------------------------GAUCHE---------------------------------------------------------------------
		JPanel gauche2 = new JPanel();
		gauche2.setLayout(new BoxLayout(gauche2,BoxLayout.LINE_AXIS));
		gauche2.setMinimumSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		gauche2.setPreferredSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		gauche2.setBackground(new Color(255,170,0));
		
		JPanel spaceg = new JPanel();
		spaceg.setBackground(new Color(255,170,0));
		spaceg.setPreferredSize(new Dimension(((this.getWidth()-768)/2)-5-50,20));
		
		JPanel spaced = new JPanel();
		spaced.setBackground(new Color(255,170,0));
		spaced.setPreferredSize(new Dimension(((this.getWidth()-768)/2)-5-50,20));
		
		JPanel intbp = new JPanel();
		intbp.setPreferredSize(new Dimension(1,20));
		intbp.setMaximumSize(new Dimension(1,20));
		intbp.setBackground(new Color(255,170,0));
		
		JPanel boxpetit = new JPanel();
		boxpetit.setBackground(new Color(255,170,0));
		boxpetit.setLayout(new BoxLayout(boxpetit,BoxLayout.LINE_AXIS));
		boxpetit.add(petit);
		JPanel intp = new JPanel();
		intp.setMaximumSize(new Dimension(55,1));
		intp.setMinimumSize(new Dimension(55,1));
		intp.setBackground(new Color(255,170,0));
		boxpetit.add(intp);
		boxpetit.add(jpetit);
		JPanel intp2 = new JPanel();
		intp2.setMaximumSize(new Dimension(15,1));
		intp2.setMinimumSize(new Dimension(15,1));
		intp2.setBackground(new Color(255,170,0));
		boxpetit.add(intp2);
		
		JPanel intpm = new JPanel();
		intpm.setPreferredSize(new Dimension(1,8));
		intpm.setMaximumSize(new Dimension(1,8));
		intpm.setBackground(new Color(255,170,0));
		
		JPanel boxmoyen = new JPanel();
		boxmoyen.setBackground(new Color(255,170,0));
		boxmoyen.setLayout(new BoxLayout(boxmoyen,BoxLayout.LINE_AXIS));
		boxmoyen.add(moyen);
		JPanel intm = new JPanel();
		intm.setMaximumSize(new Dimension(49,1));
		intm.setMinimumSize(new Dimension(49,1));
		intm.setBackground(new Color(255,170,0));
		boxmoyen.add(intm);
		JPanel intm2 = new JPanel();
		intm2.setMaximumSize(new Dimension(6,1));
		intm2.setMinimumSize(new Dimension(6,1));
		intm2.setBackground(new Color(255,170,0));
		boxmoyen.add(jmoyen);
		boxmoyen.add(intm2);
		
		JPanel intmg = new JPanel();
		intmg.setPreferredSize(new Dimension(1,5));
		intmg.setMaximumSize(new Dimension(1,5));
		intmg.setBackground(new Color(255,170,0));
		
		JPanel boxgrand = new JPanel();
		boxgrand.setBackground(new Color(255,170,0));
		boxgrand.setLayout(new BoxLayout(boxgrand,BoxLayout.LINE_AXIS));
		boxgrand.add(grand);
		JPanel intg = new JPanel();
		intg.setMaximumSize(new Dimension(40,1));
		intg.setMinimumSize(new Dimension(40,1));
		intg.setBackground(new Color(255,170,0));
		boxgrand.add(intg);
		boxgrand.add(jgrand);
		
		JPanel megabox = new JPanel();
		megabox.setBackground(new Color(255,170,0));
		megabox.setLayout(new BoxLayout(megabox, BoxLayout.PAGE_AXIS));
		megabox.setMaximumSize(new Dimension(100,140));
		megabox.add(circuitBox2);
		megabox.add(intbp);
		megabox.add(boxpetit);
		megabox.add(intpm);
		megabox.add(boxmoyen);
		megabox.add(intmg);
		megabox.add(boxgrand);
		
		gauche2.add(spaceg);
		gauche2.add(megabox);
		gauche2.add(spaced);
		
		
		
//--------------------------------------------------------------CENTRE--------------------------------------------------------------------
		
		centre2 = new JPanel();
		centre2.setMinimumSize(new Dimension(768,this.getHeight()));
		centre2.setPreferredSize(new Dimension(768,this.getHeight()));
		centre2.setLayout(new BoxLayout(centre2,BoxLayout.PAGE_AXIS));
		imp = new ImagePerso(circuit);
		
		lab = new JLabel(new ImageIcon(imp.getImage()));
		
		centre2.add(lab);
		lab.addMouseListener(imp);
		lab.addMouseMotionListener(imp);


		
		
		
//--------------------------------------------------------------DROITE--------------------------------------------------------------------
		JPanel droite2 = new JPanel();
		droite2.setMinimumSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		droite2.setPreferredSize(new Dimension(((this.getWidth()-768)/2)-5,this.getHeight()));
		droite2.setBackground(new Color(255,170,0));
		GridBagLayout layoutd2 = new GridBagLayout();
		droite2.setLayout(layoutd2);
		GridBagConstraints gcd2 = new GridBagConstraints();
		gcd2.fill = GridBagConstraints.BOTH;
		gcd2.gridx = 0;
		gcd2.gridy= 0;
		gcd2.gridheight = 1;
		gcd2.gridwidth = 1;
		droite2.add(save,gcd2);
		gcd2.gridx = 1;	
		gcd2.gridwidth = GridBagConstraints.REMAINDER;
		droite2.add(reset,gcd2);
		
		gcd2.gridx = 0;
		gcd2.gridy= 1;
		gcd2.anchor = GridBagConstraints.LINE_START;
		gcd2.fill = GridBagConstraints.HORIZONTAL;
		gcd2.gridwidth = GridBagConstraints.REMAINDER;
		droite2.add(swap2,gcd2);
		
		
		
//--------------------------------------------------------------BARRES BLEUES-----------------------------------------------------------
		
		JPanel blueLayout = new JPanel();
		blueLayout.setMinimumSize(new Dimension(5,this.getHeight()));
		blueLayout.setPreferredSize(new Dimension(5,this.getHeight()));
		blueLayout.setBackground(Color.blue);

		
		JPanel blueLayout2 = new JPanel();
		blueLayout2.setMinimumSize(new Dimension(5,this.getHeight()));
		blueLayout2.setPreferredSize(new Dimension(5,this.getHeight()));
		blueLayout2.setBackground(Color.blue);
		
		
		
		
//------------------------------------------------------------FOND-----------------------------------------------------------------------
		JPanel changes = new JPanel();
		changes.setSize(new Dimension(this.getWidth(),this.getHeight()));
		changes.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
		changes.setBackground(new Color(255,170,0));
		
		
		

		GridBagLayout layout2 = new GridBagLayout();
		changes.setLayout(layout2);
		
		
		GridBagConstraints gc2 = new GridBagConstraints();
		gc2.fill = GridBagConstraints.BOTH;
		gc2.gridx = 0;
		gc2.gridy = 0;
		gc2.gridheight = 1;
		gc2.gridwidth = 1;
		changes.add(gauche2,gc2);
		
		gc2.gridx = 1;
		changes.add(blueLayout,gc2);
		gc2.gridx = 2;
		changes.add(centre2,gc2);
		gc2.gridx = 3;
		changes.add(blueLayout2,gc2);
		gc2.gridx = 4;
		gc2.gridwidth = GridBagConstraints.REMAINDER;
		changes.add(droite2,gc2);
		
		
		
		
//------------------------------------------------------------FOND GLOBAL (FOND IHM + FOND MODIF) ----------------------------------------------
		fondfond.setBackground(Color.blue);
		fondfond.setLayout(cl);
		fondfond.add(fond);
		fondfond.add(changes);
		

		
		this.setContentPane(fondfond);
		this.pack();
		this.setVisible(true);
	}

	

	class CircuitBoxListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int indice = circuitBox.getSelectedIndex()+1;
			if(indice == circuitSelector) return;
			
			
			sim.setAnimated(false);
			boutonsSet();
			
			// /!\ RESPECTER ORDRE CIRCUIT-VOITURE-STRAT /!\
			 //changement circuit
			 try { selecCircuit(indice,""); } catch (FileNotFoundException e) {e.printStackTrace();}
			
			 //nouvelle voiture
			 v = VoitureFactory.build(circuit.getPointDepart(), circuit.getDirectionDepart());
			
			 //nouvelle strat associée à la nouvelle voiture +circuit(pour radar) si nécessaire
			 selecStrat(stratSelector);
			 
			 
			 sim.init(s,circuit,v);
			 ManageIHM.manageIHM(ihm, circuit,sim.getVoiture(),r);
			
			 repaint(); 
			 }
			
		}
	
	class CircuitPersoBoxListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int indice = circuitPersoBox.getSelectedIndex()+1;
			if(indice == circuitPersoSelector) return;
			
			
			sim.setAnimated(false);
			boutonsSet();
			
			// /!\ RESPECTER ORDRE CIRCUIT-VOITURE-STRAT /!\
			 //changement circuit
			 try { selecCircuit(indice,"-perso"); } catch (FileNotFoundException e) {e.printStackTrace();}
			
			 //nouvelle voiture
			 v = VoitureFactory.build(circuit.getPointDepart(), circuit.getDirectionDepart());
			
			 //nouvelle strat associée à la nouvelle voiture +circuit(pour radar) si nécessaire
			 selecStrat(stratSelector);
			 
			 
			 sim.init(s,circuit,v);
			 ManageIHM.manageIHM(ihm, circuit,sim.getVoiture(),r);
			
			 repaint(); 
			 }
			
		}
		
		
	class BoutonListener implements ActionListener{
			  public void actionPerformed(ActionEvent arg0) {
				  sim.setAnimated(true);
				  boutonsSet();
				  sim.jouer();
			  }
		  }
		  
	class Bouton2Listener implements ActionListener{
			  public void actionPerformed(ActionEvent e) {
				  sim.setAnimated(false);
				  boutonsSet();
			  }	
		  }    
		
	class SwapListener implements ActionListener{
	      public void actionPerformed(ActionEvent event){
	    	sim.setAnimated(false);
	    	boutonsSet();
	        cl.next(fondfond);
	      }
	    }
			  
	class SaveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			imp.save(file);

			
		}

	}
	
	class ResetListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			imp.Reset();
			lab.setIcon(new ImageIcon(imp.getImage()));
					
		}
		
	}
	
	class Swap2Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			cl.next(fondfond);
		}
		
	}
	
	class CircuitBox2Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int indice = circuitBox2.getSelectedIndex()+1;
			 try { selecCircuit2(indice); } catch (FileNotFoundException e) {e.printStackTrace();}
			 imp.setImage(circuitSwap);
			 lab.setIcon(new ImageIcon(imp.getImage()));
			
			 }
			
		}
	
	private void boutonsSet(){
		if(sim.isAnimated()){
			go.setEnabled(false);
		    stop.setEnabled(true);
			   stop.setBackground(Color.red);
			   go.setBackground(Color.gray);
		}
		else{
			stop.setEnabled(false);
		    go.setEnabled(true);
		    go.setBackground(Color.green);
		    stop.setBackground(Color.gray);
		}
	}
		
	private void selecStrat(int i){
		GenereGenome gen = new GenereGenome(strcirc);
		Genome<Double> g = new Genome<Double>();
		for(double x:gen.getDefault()){
			g.add(x);
		}

		s = new StrategyGenome(v, new RadarDijkstra(v, circuit, dij.getMatrice()), g,dij,strcirc);
	}
		
	private void selecCircuit(int i,String string) throws FileNotFoundException{
			if(string==""){
				if(i == circuitSelector)
					return;
				else 
					circuitSelector = i;
			}
			else{
				if(circuitPersoSelector == i)
					return;
				else
					circuitPersoSelector = i;
			}
			switch(i){
			case 1 :
				circuit = CircuitFactoryFromFile.build("Tracks/1_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "1_safe.trk"+string;
				break;
			case 2 :
				circuit = CircuitFactoryFromFile.build("Tracks/2_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "2_safe.trk"+string;
				break;
			case 3 :
				circuit = CircuitFactoryFromFile.build("Tracks/3_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "3_safe.trk"+string;
				break;
			case  4:
				circuit = CircuitFactoryFromFile.build("Tracks/4_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "4_safe.trk"+string;
				break;
			case 5 :
				circuit = CircuitFactoryFromFile.build("Tracks/5_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "5_safe.trk"+string;
				break;
			case 6 :
				circuit = CircuitFactoryFromFile.build("Tracks/6_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "6_safe.trk"+string;
				break;
			case 7 :
				circuit = CircuitFactoryFromFile.build("Tracks/7_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "7_safe.trk"+string;
				break;
			case 8 :
				circuit = CircuitFactoryFromFile.build("Tracks/8_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "8_safe.trk"+string;
				break;
			case 9 :
				circuit = CircuitFactoryFromFile.build("Tracks/aufeu.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "aufeu.trk"+string;
				break;
			case 10 :
				circuit = CircuitFactoryFromFile.build("Tracks/bond_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "bond_safe.trk"+string;
				break;
			case 11 :
				circuit = CircuitFactoryFromFile.build("Tracks/Een2.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "Een2.trk"+string;
				break;
			case 12 :
				circuit = CircuitFactoryFromFile.build("Tracks/labymod.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "labymod.trk"+string;
				break;
			case 13 :
				circuit = CircuitFactoryFromFile.build("Tracks/labyperso.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "labyperso.trk"+string;
				break;
			case 14 :
				circuit = CircuitFactoryFromFile.build("Tracks/perso.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "perso.trk"+string;
				break;
			case 15 :
				circuit = CircuitFactoryFromFile.build("Tracks/t260_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "t260_safe.trk"+string;
				break;
			case 16 :
				circuit = CircuitFactoryFromFile.build("Tracks/t2009.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "t2009.trk"+string;
				break;
			default :
				circuit = CircuitFactoryFromFile.build("Tracks/1_safe.trk"+string);
				dij = new Dijkstra(circuit);
				strcirc = "1_safe.trk";
			}
		}
	
	private void selecCircuit2(int i) throws FileNotFoundException{
			switch(i){
			case 1 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/1_safe.trk");
				file = "Tracks/1_safe.trk-perso";
				break;
			case 2 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/2_safe.trk");
				file = "Tracks/2_safe.trk-perso";
				break;
			case 3 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/3_safe.trk");
				file = "Tracks/3_safe.trk-perso";
				break;
			case  4:
				circuitSwap = CircuitFactoryFromFile.build("Tracks/4_safe.trk");
				file = "Tracks/4_safe.trk-perso";
				break;
			case 5 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/5_safe.trk");
				file = "Tracks/5_safe.trk-perso";
				break;
			case 6 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/6_safe.trk");
				file = "Tracks/6_safe.trk-perso";
				break;
			case 7 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/7_safe.trk");
				file = "Tracks/7_safe.trk-perso";
				break;
			case 8 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/8_safe.trk");
				file = "Tracks/8_safe.trk-perso";
				break;
			case 9 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/aufeu.trk");
				file = "Tracks/aufeu.trk-perso";
				break;
			case 10 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/bond_safe.trk");
				file = "Tracks/bond_safe.trk-perso";
				break;
			case 11 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/Een2.trk");
				file = "Tracks/Een2.trk-perso";
				break;
			case 12 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/labymod.trk");
				file = "Tracks/labymod.trk-perso";
				break;
			case 13 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/labyperso.trk");
				file = "Tracks/labyperso.trk-perso";
				break;
			case 14 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/perso.trk");
				file = "Tracks/perso.trk-perso";
				break;
			case 15 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/t260_safe.trk");
				file = "Tracks/t260_safe.trk-perso";
				break;
			case 16 :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/t2009.trk");
				file = "Tracks/t2009.trk-perso";
				break;
			default :
				circuitSwap = CircuitFactoryFromFile.build("Tracks/1_safe.trk");
				file = "Tracks/1_safe.trk-perso";
			}
		}
		class StateListener implements ActionListener{
			private int a;
			public void actionPerformed(ActionEvent e) {
				if(petit.isSelected()) a = 0;
				if(moyen.isSelected()) a = 1;
				if(grand.isSelected()) a = 2;
				
				imp.setChoix(a);
		    }
		} 
	}

