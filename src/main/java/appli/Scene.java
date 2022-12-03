package appli;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

import eu.ensg.ign.Itineraire;
import eu.ensg.osm.Morceau;
import eu.ensg.osm.PointDecision;
import eu.ensg.osm.PointInteret;
import eu.ensg.portail.MapPanel;


public class Scene {
	private String start;
	private String end;
	private ArrayList<PointInteret> candidat;
	private ArrayList<double[]> ptDecision;
	private Point centre;
	private int width;
	private int height;
	private static Scene instance;
	private Itineraire itineraire;

	/** La classe Scene sert d'interface entre le main et le MapPanel et donc, plus précisemment, 
	 * entre l'utilisateur et l'ordinateur. Elle permet de récupérer l'instance entrée par l'utilisateur
	 * dans la fenetre graphique pour faire appel aux services IGN et OSM avec afin de récupérer les points 
	 * de décisions et d'interet. Ceux-ci sont necessaires pour l'affichage graphique donc pour la classe
	 * MapPanel.
	 * @author josephine 
	 * 
	 */
	
	public Scene() {
		/** Constructeur de la classe Scene
		 */
	}
	public void setInstance(Scene instance) {
		Scene.instance = instance;
		/** Setteur de l'instance soit l'object de type scene représentant 
		 * l'itinéraire choisi par l'utilisateur.
		 * @param 	Scene contenant les informations sur l'itinéraire 
		 */
	}
	//SINGLETON
		public static Scene getInstance() {
			/** Guetteur permettant de récupérer l'instance dans les autres classes
			 * @return 	Instance le singleton de type Scene
			 */
			return instance;
		}
	
	public void setStart( String start) {
		/** Setteur  du point de départ permet d'initialiser dans l'object Scene
		 *  le texte comportant les coordonnées de départ de l'itinéraire.
		 * @param 	String contenant les coordonnées de départ de l'itinéraire
		 */
		if (start != null) {
			this.start = start;
		}
		else start = "0,0";
	}
	public void setEnd(String end) {
		/** Setteur  du point d'arrivée permet d'initialiser dans l'object Scene
		 *  le texte comportant les coordonnées de départ de l'itinéraire.
		 * @param 	String contenant les coordonnées de départ de l'itinéraire
		 */
		if (end != null) {
		this.end = end;
		}
		else end =  "0,0";
	}
	public void setCentreCarto(Point point) {
		this.centre = point;
		/** Setter du point centrale en coordonnées cartographique du centre de la fenetre graphique MapPanel
		 *  @param Point contenant les coordonnées cartographique du centre de la fenetre graphique MapPanel
		 */
	}
	public void setWidth(int width) {
		/** Setter de la largeur de la fenetre graphique MapPanel
		 *  @param int entier contenant la largeur de la fenetre graphique MapPanel
		 */
		this.width = width;
	}
	public void setHeigth(int height) {
		/** Setter de la hauteur  de la fenetre graphique MapPanel
		 *  @param int entier contenant la hauteur de la fenetre graphique MapPanel
		 */
		this.height = height;
	}
	public Point getCentreCarto() {
		/** Guetteur permettant de récupérer les coordonnées cartographique du point centrale 
		 * du centre de la fenetre graphique MapPanel
		 * @return Point centre 
		 */
		return centre;
	}
	

	public int getWidth() {
		/** Guetteur permettant de récupérer la largeur de la fenetre graphique MapPanel
		 * @return int largeur 
		 */
		return width;
	}

	public int getHeight() {
		/** Guetteur permettant de récupérer la hauteur de la fenetre graphique MapPanel
		 * @return int hauteur 
		 */
		return height;
	}

	
	public ArrayList<PointInteret> getCandidat() {
		/** Guetteur permettant de récupérer liste des points d'interets retournés par la méthode 
		 * MeilleurCandidat.
		 * @return ArrayList<PointInteret> liste d'object de type PointInteret
		 */
		return candidat;
	}
	
	public String getStart() {
		/** Getteur des coordonnées de départ contenues dans l'object de type scene
		 * return String start texte contenant les coordonnées écrans du départ
		 */
		if (start != null) {
			return this.start;
		}
		else return "0,0";
	}
	public String getEnd(){
		/** Getter des coordonnées d'arrivée contenues dans l'object de type scene
		 * return String start texte contenant les coordonnées écrans d'arrivée
		 */
		if (end != null) {
			return this.end;
		}
		else return "0,0";
	}


	public ArrayList<double[]> getPtDecision() {
		
		/** Guetter permettant de récupérer liste des points de décision retournés par la méthode 
		 * MeilleurCandidat.
		 * return String start texte contenant les coordonnées écrans du départ
		 */
		return ptDecision;
	}
	
	public Itineraire getIti() {
		/** Guetter permettant de récupérer l'itinéraire calculé
		 * return Itineraire calculé 
		 */
		return this.itineraire;
	}
	
	public void setCandidat(String url) throws Exception{
		
		/** Cette méthode permet de faire appel au service itinéraire de l'IGN afin initialiser celui ci ainsi que
		 * les points de décision récupérés par la méthode CalculItineraire(url) et ensuite MeilleurCandidat(rayon, ptDecision). 
		 * @param	String texte comportant le lien d'appel au service itinéraire de l'IGN 
		 */
		
		//Création du nouvel itinéraire
		Itineraire iti = new Itineraire(url);
		
		this.itineraire = iti;
		
		//Récupération des points de décision grâce à calcul IGN de géoportail
		ArrayList<double[]> ptDecision = iti.CalculItineraire(url); 
		
		int rayon = 50;
		
		this.ptDecision = ptDecision;
					
		this.candidat = MeilleurCandidat(rayon, ptDecision);
		}
	
	public String setURL(MapPanel map, String start, String end) throws Exception {
		
		/** La méthode setURL permet convertir les coordonnées écran start et end en coordonnées géographique 
		 * afin d'obtenir le lien d'appel au service d'itinéraire de l'IGN.
		 * @param	MapPanel est la fentre graphique correspondant à celle que voit l'utilisateur
		 * 			String start texte comportant les coordonnées érans du départ
		 *			String end texte comportant les coordonnées érans de l'arrivée
		 * @return  String texte contenant le lien d'appel au service d'itinéraire de l'IGN.
		 */
		
		// Conversion des coordonnées String -> Point 
		Point start_s = map.coord(start);
		Point end_s = map.coord(end);
	
		//Transformation repere screen => repere carto 		
		Point start_c = vecTranslation(map,start_s);
		Point end_c = vecTranslation(map,end_s);
		
		//Transformation repère carto => repère géo 
		Point2D.Double start_g = map.getLongitudeLatitude(start_c);
		Point2D.Double end_g = map.getLongitudeLatitude(end_c);
	
		
		//Création de l'URL
		 String url = "https://wxs.ign.fr/calcul/geoportail/itineraire/rest/1.0.0/route?resource=bdtopo-pgr"
			+ "&profile=pedestrian&optimization=fastest"
			+ "&start=" + start_g.x + "," + start_g.y + "&end=" + end_g.x + "," + end_g.y //Les coorodnnées sont données en EPSG 4326
			+ "&intermediates=&constraints={\"constraintType\":\"banned\",\"key\":\"wayType\",\"operator\":\"=\",\"value\":\"tunnel\"}"
			+ "&geometryFormat=geojson&crs=EPSG:4326&getSteps=true&getBbox=true&waysAttributes=nature";
		 return url; 
	}
	
	
	public ArrayList<PointInteret> MeilleurCandidat (int rayon, ArrayList<double[]> ptDecision) throws Exception {
		
		/**@author Clelia 
		 *@param rayon correspond au rayon du buffer autour du point d'intérêt
		 *@param ptDecision correspond à la liste des points de décision pour un itinéraire donné 
		 *@return une liste composé du meilleur point d'intérêt pour chaque point de décision  
		 * 
		 */
		
		ArrayList<PointInteret> meilleursCandidats = new ArrayList<PointInteret>();
		
		for (double[] pt : ptDecision) {
			PointDecision ptdecision = new PointDecision(pt[0], pt[1]);
			if (ptDecision.size() > 1 && ptDecision.indexOf(pt)> 1) {
				PointDecision ptdecision_1 = new PointDecision(ptDecision.get(ptDecision.indexOf(pt)-1)[0], ptDecision.get(ptDecision.indexOf(pt)-1)[1]);
				if (ptdecision.calculDistanceEucl(ptdecision_1) < rayon) {
					rayon = (int) Math.round(ptdecision.calculDistanceEucl(ptdecision_1));
				}
			}
			
			//Calcul du buffer autour d'un point de décision (récupérer par calcul itinéraire IGN) -> on détermine le rayon en fonction de la distance entre les deux points de décision successifs.
		
			Geometry buff = ptdecision.calculBuffer(rayon);
			//reprojection dans le système de coordonnées de départ
			CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:2154"); 
			CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:4326");
			MathTransform tr = CRS.findMathTransform(sourceCRS, targetCRS);
			Geometry buff_4326 = JTS.transform(buff, tr);
			
			//Recherche des candidats dans ce buffer 
			Coordinate[] coords_buff = buff_4326.getCoordinates();
			
				//On cherche les coordonnées N S O E du buffer
			double N = 0;
			double E = 0;
			for (Coordinate i: coords_buff) {
				if (i.y > N) {
					N = i.y;
				}
				if (i.x > E) {
					E = i.x;	
				}	
			}
			double S = N;
			double O = E;
			for (Coordinate i: coords_buff) {
				if (i.y < S) {
					S = i.y;
				}
				if (i.x < O) {
					O = i.x;
				}
			}
			
			
			//Ajouter ces candidats à une liste de point d'intérêts
			Morceau m = new Morceau(new ArrayList<PointInteret>(), new PointDecision(pt[0], pt[1]));
			m.rechercheCandidat(E, N, S, O);
			
			
			//Condition : s'il n'y a pas de candidats, on agrandit le buffer.
			if (m.getSize() == 0) {
				ArrayList<double[]> decisionException = new ArrayList<double[]>();
				decisionException.add(pt);
				rayon +=20;
				MeilleurCandidat(rayon, decisionException);
				}
			
			
			else { 
				System.out.println(m.toString());			
				PointInteret meilleur = m.meilleurCandidat();
				meilleursCandidats.add(meilleur);
			}
		}
		return meilleursCandidats;
		
	}


	
	public Point vecTranslation(MapPanel map,Point point) {
		
	/** Cette méthode permet de convertir les coordonnées écran vers des coordonnées cartographique du systeme de coordonnées
	 *la fentre graphique.
	 *@param	MapPanel est la fentre graphique correspondant à celle que voit l'utilisateur
	 * 			Point en coordonnées écrans
	 *@return	Point en coordonnées cartographiques
	 */
		
	Point centre_carto = map.getCenterPosition();
	
	Point vectTranslation = new Point((map.getWidth()/2) - centre_carto.x, (map.getHeight()/2) - centre_carto.y);
	
	int x = point.x - vectTranslation.x;
	int y = point.y - vectTranslation.y;
	
	Point nvx_pt = new Point(x, y);
	
	return nvx_pt;}

	
	public static Point pointForPoint2D(Point2D p) {
		/** Permet de transformer un type point 2D en type point 
		 * Documentation : https://www.demo2s.com/java/java-point-convert-point2d-to-point.html
		 */
        return point2DToPoint(p);

}

	public static Point point2DToPoint(Point2D p) {
		/** Permet de transformer un type point 2D en type point 
		 * Documentation : https://www.demo2s.com/java/java-point-convert-point2d-to-point.html
		 */
        return new Point((int) p.getX(), (int) p.getY());
	}	
}

