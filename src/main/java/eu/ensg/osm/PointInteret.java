package eu.ensg.osm;

/**
 * Classe qui représente un point d'intérêt
 * @author Clélia
 *
 */
public class PointInteret extends Point{
	 private String nom; 
	 private String categorie;

	public PointInteret(double x, double y, String nom, String cat) {
		super(x,y);
		this.nom = nom;
		this.categorie = cat;	
	}
	
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * 
	 * @param pt de type Point
	 * @return retourne la distance normalisée entre le point d'intérêt et le point passé en entrée
	 * @throws Exception
	 */
	
	public double calculDistance(Point pt) throws Exception {
		double disteuclidienne = calculDistanceEucl(pt);
		double D = 1-(disteuclidienne/50);
		return D;
	}
	
	/**
	 * @return la catégorie, le nom et les coordonnées du point d'intérêt
	 */
	@Override
	public String toString() {
		return "Point d'intérêt : " + this.categorie + ", " + this.nom + " " + super.toString();
	}
}
