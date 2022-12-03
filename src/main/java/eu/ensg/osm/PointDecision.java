package eu.ensg.osm;

import java.util.ArrayList;
import java.util.List;

import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

/**
 * Classe qui représente un point de décision
 * @author Clélia
 *
 */
public class PointDecision extends Point{
	
	public PointDecision(double x, double y) {
		super(x,y);	
	}
	

	public List<Double> getCoord(){
		List<Double> coord = new ArrayList<Double>();
		coord.add(this.x);
		coord.add(this.y);
		return coord;
	}
	
	/**
	 * 
	 * @param rayon du buffer que l'on veut calculer autour du point de décision
	 * @return un buffer. C'est un polygone
	 * @throws Exception
	 */
	public Geometry calculBuffer(int rayon) throws Exception {

		WKTReader2 wktRdr = new WKTReader2();
		Geometry geometry = wktRdr.read("POINT ( " + this.x + " " + this.y + " )");
	
		// avant de creer le buffer, il faut se projeter dans un système de coordonnées métriques (Lambert 93 par exemple)
		CoordinateReferenceSystem sourceCRS = CRS.decode("EPSG:4326"); 
		CoordinateReferenceSystem targetCRS = CRS.decode("EPSG:2154");
		MathTransform tr = CRS.findMathTransform(sourceCRS, targetCRS);
		Geometry geom = JTS.transform(geometry, tr);
		
		//Calcul du buffer autour du point de décision
		Geometry buffer = geom.buffer(rayon);
		return buffer;
	}
	
	/**
	 * @return retourne les coordonnées du point de décision
	 */
	@Override 
	public String toString() {
		return "Point de décision : " + super.toString();
	}
	

}
