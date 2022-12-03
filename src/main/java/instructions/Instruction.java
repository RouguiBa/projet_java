package instructions;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.geom.Coordinate;

import appli.Scene;
import eu.ensg.osm.PointInteret;
import eu.ensg.portail.MapPanel;


public class Instruction {
	private ArrayList<Double> angle= new ArrayList<Double>();
	private ArrayList<String> instruction = new ArrayList<String>();
	
	public Instruction() {
	}

	public void setInstru(ArrayList<String> instruction) {
		this.instruction = instruction;
	}
	
	public ArrayList<String> getInstru(){
		return this.instruction;
	}
	
	
	public ArrayList<Double> angle(MapPanel map, String start, String end) throws Exception {
		/**Cette méthode nous permet de calculer des angles, ce qui nous permettra de générer les instructions de navigation
		 *  
		 * @authors Rouguiatou Ba, Clélia Ségouin, Joséphine Bocquet
		 * @return l'angle A calculé entre trois points de décision succesifs 
		 * @param MapPanel map
		 * 		  String Start
		 * 		  String end
		 */
		double A = 0;
		Scene s = Scene.getInstance();
		ArrayList<double[]> ptDec = s.getPtDecision();
		
		//Transformation des coordonnées String en Point
		Point start_s = map.coord(start);
		Point end_s = map.coord(end);
	
		//Transformation repère screen vers repère carto 		
		Point start_c = s.vecTranslation(map,start_s);
		Point end_c = s.vecTranslation(map,end_s);

		//Transformation repère carto vers repère géo 
		Point2D.Double start_g = map.getLongitudeLatitude(start_c);
		Point2D.Double end_g = map.getLongitudeLatitude(end_c);
		
		try {
			//Tous les points doivent être convertis vers un système métrique afin de pouvoir utiliser la méthode angleBetweenOriented
			Coordinate start_2154 = eu.ensg.osm.Point.conversion(new double[] {start_g.getX(), start_g.getY()}, "EPSG:4326", "EPSG:2154");
			Coordinate end_2154 = eu.ensg.osm.Point.conversion(new double[] {end_g.getX(), end_g.getY()}, "EPSG:4326", "EPSG:2154");
			
			for (int i = 0; i < ptDec.size()-1; i++) {
				if (i == 0) {
					Coordinate pt1 = start_2154;
					Coordinate pt2 = eu.ensg.osm.Point.conversion(ptDec.get(i), "EPSG:4326", "EPSG:2154");
					Coordinate pt3 = eu.ensg.osm.Point.conversion(ptDec.get(i+1), "EPSG:4326", "EPSG:2154");
					
					A = Angle.angleBetweenOriented(pt1,pt2,pt3)*180/Math.PI;
					angle.add(A);
				}
				
				else if (i == ptDec.size() - 1) {
					Coordinate pt1 = eu.ensg.osm.Point.conversion(ptDec.get(i-1), "EPSG:4326", "EPSG:2154");
					Coordinate pt2 = eu.ensg.osm.Point.conversion(ptDec.get(i), "EPSG:4326", "EPSG:2154");
					Coordinate pt3 = end_2154;
					
					A = Angle.angleBetweenOriented(pt1,pt2,pt3)*180/Math.PI;
					angle.add(A);
				}
				
				else {
					Coordinate pt1 = eu.ensg.osm.Point.conversion(ptDec.get(i-1), "EPSG:4326", "EPSG:2154");
					Coordinate pt2 = eu.ensg.osm.Point.conversion(ptDec.get(i), "EPSG:4326", "EPSG:2154");
					Coordinate pt3 = eu.ensg.osm.Point.conversion(ptDec.get(i+1), "EPSG:4326", "EPSG:2154");
					
					A = Angle.angleBetweenOriented(pt1,pt2,pt3)*180/Math.PI;
					angle.add(A);
				}
			}
		} catch (Exception e) {
						e.printStackTrace();
		}
		return angle;	
	}
	
	
	public String instru(double dist1, double dist2, double A, PointInteret candidats) {
		
		/**Cette méthode permet de calculer deux distances, une entre un point de décision et point candidat, et l'autre entre le point de décision d’avant et le même point candidat
		 * @version 
		 * @author Rouguiatou BA, Clélia Ségouin, Joséphine Bocquet
		 * @param MapPanel map
		 * 		  String Start
		 * 		  String end
		 */
		
		String instru = "";
		System.out.println("Dist1 : " +dist1);
		System.out.println("dist2 : " +dist2);

		if(dist1>dist2) {
			

			if(A<30 && A>=0 || A>(-30) && A<=0 || A>=(-180) && A<(-150) || A>150 && A<=180) {
				instru = (" \n Continuez tout droit au "+ candidats.getNom());
			}

			if(A<=150 && A>=30) {
				instru = ("\n Tounez à gauche" + " après " + candidats.getNom() );
			}

			if(A<=(-30) && A>=(-150)) {
				instru =  ("\n Tounez à droite" + " après " + candidats.getNom() );

			}
		}
		if(dist1<=dist2) {
			if(A<30 && A>=0 || A>(-30) && A<=0 || A>=(-180) && A<(-150) || A>150 && A<=180) {
				instru =  (" \n Continuez tout droit au "+ candidats.getNom());
				}

				if(A<=150 && A>=30) {
				instru = ("\n Tounez à gauche" + " avant " + candidats.getNom() );

				}

				if(A<=(-30) && A>=(-150)) {
				instru = ("\n Tounez à droite" + " avant " + candidats.getNom() );

				}
		}	
		
		return instru;
}
		
	
	public void genereInstruction(MapPanel map, String start, String end) throws Exception{
		/**Cette méthode permet de générer les instructions de navigations pour guider l'utilisateur
		 *  
		 * @author Rouguiatou BA
		 * @return une phrase indiquant les instructions de navigations
		 * @param double dist1,dist2 correspondent aux distances calculés dans la  méthode génère instruction
		 		  double A correspond à la mesure de l'angle entre les points de décision
		 */
		Scene scene = Scene.getInstance();
		Point start_s = map.coord(start);
	
		//Transformation repere screen => repere carto 		
		Point start_c = scene.vecTranslation(map,start_s);
		
		//Transformation repère carto => repère géo 
		Point2D.Double start_g = map.getLongitudeLatitude(start_c);
		ArrayList<Double> A = angle( map,  start,  end);
		ArrayList<PointInteret> liste_candidats = scene.getCandidat();
		ArrayList<double[]> liste_ptdecision = scene.getPtDecision();
				
			for (int i = 0; i< liste_ptdecision.size(); i++) {
				double[] ptdecision = liste_ptdecision.get(i);
				eu.ensg.osm.Point pt_d = new eu.ensg.osm.Point (ptdecision[0], ptdecision[1]);
			
					if (i==0) {
						//Calcul distance entre le point de départ et le point de décision  
						double dist1 = pt_d.calculDistanceEucl( new eu.ensg.osm.Point (start_g.getX(), start_g.getY()));
						
						double [] candidat = new double[2];
						candidat[0] = liste_candidats.get(i).getX();
						candidat[1] = liste_candidats.get(i).getY();
						eu.ensg.osm.Point pt_c = new eu.ensg.osm.Point (candidat[0], candidat[1] );

						//Calcul distance entre le point de départ et le candidat 
						double dist2 = pt_c.calculDistanceEucl( new eu.ensg.osm.Point (start_g.getX(), start_g.getY()));
						
						instruction.add(instru( dist1,  dist2,  A.get(i), liste_candidats.get(i)));
						
					}
					if (i == liste_ptdecision.size()-1) {
						instruction.add("");
						break;
						
					}
					if (liste_ptdecision.size() >= 2 && i>0 && i < liste_ptdecision.size()-1) {
						
						double[] ptdecision_av = liste_ptdecision.get(i-1);
						double [] candidat = new double[2];
						candidat[0] = liste_candidats.get(i).getX();
						candidat[1] = liste_candidats.get(i).getY();

						eu.ensg.osm.Point pt_c = new eu.ensg.osm.Point (candidat[0], candidat[1] );

						double dist1 = pt_d.calculDistanceEucl( new eu.ensg.osm.Point ( ptdecision_av[0], ptdecision_av[1] )  );
						double dist2 = pt_c.calculDistanceEucl( new eu.ensg.osm.Point ( ptdecision_av[0], ptdecision_av[1] )  );
						instruction.add(instru( dist1,  dist2,  A.get(i), liste_candidats.get(i)));
						System.out.println(A.get(i));
						}
					}
			setInstru(instruction);
	}			
}


