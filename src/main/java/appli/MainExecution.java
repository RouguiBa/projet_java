package appli;

import java.awt.FlowLayout;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import eu.ensg.portail.InitCoord;
import eu.ensg.portail.MapPanel;
import instructions.Instruction;



public class MainExecution {
	

	public static void main(String[] args) throws Exception {
		
	
		// Look&Feel 
		try {
			String os = System.getProperty("os.name").toLowerCase();
			// For windows os
			if (os.contains("windows")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			}
			// For linux os
			if ((os.contains("linux")) || (os.contains("unix"))) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			}
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

        //Créations première fenetre
		
		final JFrame fen = new JFrame();
		fen.setSize(1000, 800);
		
		// just a JPanel extension, add to any swing/awt container
		final MapPanel mapPanel = new MapPanel(); 
		//mapPanel.setLayout( new FlowLayout( FlowLayout.RIGHT) );
		
		//Application Graphique
				

		fen.setContentPane(mapPanel); //remplace la fenetre par mapPanel
		fen.setLocationRelativeTo(null); // centre la fentre mapPanel
		fen.setResizable(false);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setTitle("Route instructions");
		
		
		JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fen != null) {
                    fen.dispose();
                }
            }
        });
        fileMenu.add(item);
        menuBar.add(fileMenu);
		
        
        JMenu tileServerMenu = new JMenu("Autres");
        menuBar.add(tileServerMenu);
        fen.setJMenuBar(menuBar);
        

		mapPanel.setZoom(18); // set some zoom level (1-18 are valid)
		//[ 3.05871 , 50.614391 ]
		double lon = 3.05871; //instancie la position de la mapPanel => centrée sur paris - lille? 
		double lat = 50.614391;
		Point position = mapPanel.computePosition1(new Point2D.Double(lon, lat));
		mapPanel.setCenterPosition(position); // sets to the computed position
		mapPanel.repaint(); // if already visible trigger a repaint here

		fen.setVisible(true);
		mapPanel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				System.out.println("Les coordonnées graphiques de la souris: " + p.x + "," + p.y);
				// mapPanel.repaint();
			}
			
			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}
		});
		
		
		//je créer une seconde fenetre d'initialisation 
		final InitCoord fen2 = new InitCoord();
		final JTextField instart = new JTextField("Start");
		final JLabel textStart = new JLabel("Rentrer les coordonnées (image) du point de départ :");
		final JTextField inend = new JTextField("End");
		final JLabel textEnd = new JLabel("Rentrer les coordonnées (image) du point d'arrivé :");
		final JButton valide = new JButton("Valider !");
						
		fen2.setLayout(new FlowLayout() );
		fen2.add(textStart);
		fen2.add(instart);
		fen2.add(textEnd);
		fen2.add(inend);
		fen2.add(valide);
		valide.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			
				/*  Je récupère le texte rempli par l'utilisateur dans la fenêtre
				 *  puis je créer une nouvelle scene dans laquelle j'initialise mes attributs start et end
				 */
				
				Scene scene = new Scene() ;
				scene.setInstance(scene);
				scene.setStart(instart.getText());
				scene.setEnd(inend.getText());

				// Je récupère ces informations 
				String start = scene.getStart();
				String end = scene.getEnd();
				String url = "";
					
					try {
						if (url != null) {
							/*Je récupère les informations du centre de la fenetre graphique afin de 
							 * pouvoir transformer mes coordonnées par la suite
							 */
							scene.setCentreCarto(mapPanel.getCenterPosition());
							scene.setHeigth(mapPanel.getHeight());
							scene.setWidth(mapPanel.getWidth());
							
							// J'appelle la fonction setURL qui permet d'initialiser celui-ci par rapport à la scene correspondante
							url = scene.setURL(mapPanel,start, end);						
							
						 
						try {
							// Initialisation de l'itinéraire calculé des candidats et pt de décisions
							scene.setCandidat(url);
							Instruction I = new Instruction();
							I.genereInstruction(mapPanel, start, end);
							ArrayList<String> instru = I.getInstru();
							for (int i =0; i< instru.size()-1; i++) {
							final JLabel text_instru = new JLabel((i+1) + "ème étape : "+instru.get(i)+ "\n");
							fen2.add(text_instru);
							}
							
							
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}}
					} catch (Exception e1) {
						e1.printStackTrace(); 
					}
				
				
						};});
		
		
				
		fen2.setVisible(true);
				
	
	

	
	}	}

