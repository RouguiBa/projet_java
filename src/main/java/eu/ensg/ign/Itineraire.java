package eu.ensg.ign;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Itineraire {
	
	private ArrayList<double[]> points_of_decision=new ArrayList<double[]>();
	private String url;
	private Resultat resultat;
	
	public Itineraire(String url) {
		this.url=url;
			
		}
	
	public Resultat getResultat() {
		return this.resultat;
	}
	
	public ArrayList<double[]> CalculItineraire(String url) {
		
		/**
		 * @version 
		 * @author Rouguiatou BA
		 * @return une liste contenant les points de décisions
		 * @param url correspond à l'url contenant le calcul de l'itinéraire 
		 */
		
		String txtJson = HttpClientIgn.request(url);
		Gson gson = new GsonBuilder().create();
		Resultat resultat = gson.fromJson(txtJson, Resultat.class);
		this.resultat = resultat;

		for (Portion portion : resultat.getPortions()) {
			String[] start = portion.getStart().split(",");
			double lon = Double.parseDouble(start[0]);
			double lat = Double.parseDouble(start[1]);
		}
		
		int number_of_steps = resultat.getPortions().get(0).getSteps().size();
		ArrayList<double[]> points_of_decision=new ArrayList<double[]>();
		
        for(int i=0;i<number_of_steps;i++) {
			int number_coordinates= resultat.getPortions().get(0).getSteps().get(i).getGeometry().getCoordinates().length;
			double[] coord = resultat.getPortions().get(0).getSteps().get(i).getGeometry().getCoordinates()[number_coordinates-1];
			ArrayList<double[]> pointsdecision=new ArrayList<double[]>();
			pointsdecision.add(coord);
			System.out.println("[ " + pointsdecision.get(0)[0] + " , " + pointsdecision.get(0)[1]+ " ]");
			points_of_decision.add(coord);
			}
        
		return points_of_decision;
		
		
		
	}
	
	public String toString() {
		return "[ " + points_of_decision.get(0)[0] + " , " +points_of_decision.get(0)[1]+ " ]";
		
	}
	
}
