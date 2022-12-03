package eu.ensg.osm;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Classe quo représente un morceau de l'itinéraire étudié 
 * @author Clélia
 *
 */
public class Morceau {
	private List<PointInteret> listePointInteret = new ArrayList<PointInteret>();
	private PointDecision ptd;
	
	public Morceau(List<PointInteret> pt, PointDecision ptd) {
		this.listePointInteret = pt;
		this.ptd = ptd;
	}
	
	
	public void add(PointInteret pt) {
		/**
		 * 
		 * @param pt est un point d'intérêt que l'on veut ajouter à l'attribut liste de points d'intérêts du morceau
		 */
		this.listePointInteret.add(pt);
	}
	
	public List<PointInteret> getList(){
		return this.listePointInteret;
	}
	
	
	@Override
	public String toString() {
		/**
		 * @return la liste de point d'intérêt du morceau 
		 */
		return listePointInteret.toString();
	}
	
	
	public int getSize() {
		/**
		 * 
		 * @return la taille de la liste de points d'intérêt 
		 */
		return listePointInteret.size();
	}
	
	
	//Fonction qui utilise OSM pour rechercher tous les candidats dans un cercle de rayon r autour du point de décision
	
	
	public void rechercheCandidat(double E, double N, double S, double O) throws ParserConfigurationException, IOException {
		/** Cette méthode permet de rechercher tous les points d'intérêt dans un buffer défini et de remplir la liste des points d'intérêts du morceau
		 * 
		 * @param E correspond à la coordonnée Est du buffer
		 * @param N correspond à la coordonnée Nord du buffer
		 * @param S correspond à la coordonnée Sud du buffer
		 * @param O correspond à la coordonnée Ouest du buffer
		 * @throws ParserConfigurationException
		 * @throws IOException
		 */
		String dataRequest = "<osm-script>"
				+ "<union>"
				+ "<query type=\"node\">"
				+ "<bbox-query e=\"" + E + "\" n=\"" + N + "\" s=\"" + S + "\" w=\"" + O + "\" />"
				+ "</query>"
				+ "</union>"
				+ "<print mode=\"meta\"/>"
				+ "</osm-script>";
		
		String xmldata = HttpClientOsm.getOsmXML(dataRequest);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		try {
			Document doc = builder.parse(new ByteArrayInputStream(xmldata.getBytes()));
			doc.getDocumentElement().normalize();
		    Element root = (Element) doc.getElementsByTagName("osm").item(0);
		    int nbNoeuds = root.getElementsByTagName("node").getLength();
		    for (int i = 0; i < nbNoeuds; i++) {

		    	Element elem = (Element) root.getElementsByTagName("node").item(i);

		    	// On récupère son ID
		    	long id = Long.valueOf(elem.getAttribute("id"));

		    	// on récupère sa géométrie
		    	double lat = Double.valueOf(elem.getAttribute("lat"));
		    	double lon = Double.valueOf(elem.getAttribute("lon"));
		    	
		    	for (int j = 0; j < elem.getElementsByTagName("tag").getLength(); j++) {
					Element tagElem = (Element) elem.getElementsByTagName("tag").item(j);
					String cle = tagElem.getAttribute("k");
					String val = tagElem.getAttribute("v");
					
					if (cle.equals("name")) {
						for (int j1 = 0; j1 < elem.getElementsByTagName("tag").getLength(); j1++) {
							Element tagElem1 = (Element) elem.getElementsByTagName("tag").item(j1);
							String cle1 = tagElem1.getAttribute("k");
							String val1 = tagElem1.getAttribute("v");
							if (cle1.equals("amenity")) {
								PointInteret p = new PointInteret(lon, lat, val, val1);
								if (!this.listePointInteret.contains(p)) {
									this.listePointInteret.add(p);
								}
							}
						//On crée un nouveau point d'intérêt et on l'ajoute à la liste des candidats potentiels.
							else {
								PointInteret p = new PointInteret(lon, lat, val, "");
								if (!this.listePointInteret.contains(p)) {
									this.listePointInteret.add(p);
							}
						}
					}
		    	}
		    }
		}
	} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public PointInteret meilleurCandidat() throws Exception {
		/** Cette méthode permet de choisir le candidat le plus proche du point de décision.
		 * 
		 * @return  le point d'intérêt le plus proche du point de décision pour lequel on a calculé le buffer 
		 * @throws Exception
		 */ 
		double distance = this.listePointInteret.get(0).calculDistance(ptd);
		PointInteret meilleurPt = this.listePointInteret.get(0);
		for (PointInteret ptInt : this.listePointInteret) {
			if (ptInt.calculDistance(this.ptd) < distance) {
				distance = ptInt.calculDistance(ptd);
				meilleurPt = ptInt;	
			}	
		}
		
		return meilleurPt;
	}
	
}

