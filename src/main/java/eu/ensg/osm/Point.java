package eu.ensg.osm;
import java.util.ArrayList;

import java.util.List;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

/**
 * Classe qui représente un point
 * @author Clélia
 *
 **/
public class Point extends Coordinate{
	
	public Point(double x, double y) {
		super(x,y);
	}
	
	public List<Double> getCoord() {
		List<Double> coord = new ArrayList<Double>();
		coord.add(this.x);
		coord.add(this.y);
		return coord;
	}

	/**
	 * 
	 * @return Retourne les coordonnées du point
	 **/
	@Override
	public String toString() {
		return "Coordonnées du point :" + this.x + ", " + this.y;
	}

	
	/**
	 * @param pt de type Point
	 * @return distance euclidienne entre deux points 
	 * @throws Exception
	 */
	public double calculDistanceEucl(Point pt) throws Exception{
		Coordinate coord_int = conversion(new double[] {pt.getX(), pt.getY()},  "EPSG:4326", "EPSG:2154");
		Coordinate coord_dec = conversion(new double[] {this.x, this.y}, "EPSG:4326", "EPSG:2154");

		return Math.sqrt(Math.pow(coord_int.getX() - coord_dec.getX(),2) + Math.pow(coord_int.getX() - coord_dec.getX(), 2));
		
	}
	
	/**
	 * 
	 * @param pt1 correspond au point que l'on veut reprojeter
	 * @param sourceCRS correspond au système de référence de départ 
	 * @param targetCRS correspond au système de référence d'arrivée
	 * @return les coordonnées reprojetées du point
	 * @throws Exception
	 */
	public static Coordinate conversion(double[] pt1,  String sourceCRS, String targetCRS)  throws Exception {
		//Création d'une géométrie pour le point
		WKTReader2 wktRdr = new WKTReader2();
		Geometry geometry1 = wktRdr.read("POINT(" + pt1[0] + " " + pt1[1] +")");
		
		//Changement de projection de la géométrie
		CoordinateReferenceSystem source = CRS.decode(sourceCRS); 
		CoordinateReferenceSystem target = CRS.decode(targetCRS);
		MathTransform tr = CRS.findMathTransform(source, target);
		Geometry geom1 = JTS.transform(geometry1, tr);
		Coordinate pt= new Coordinate(geom1.getCoordinate().getOrdinate(0),geom1.getCoordinate().getOrdinate(1));
		
		return pt;
	}
	
	
	}
