package eu.ensg.ign;

import java.util.List;

public class Portion {
	
	/**
	 * @version 
	 * @author Rouguiatou BA
	 */

	private double distance;
	private String start;
	private String end;
	private List<Step> steps;
	
	//GET AND SET
	
	public double getDistance() { return this.distance; }
	public void setDistance(double distance) { this.distance = distance; }
	
	public String getStart() { return this.start; }
	public void setStart(String start) { this.start = start; }
	
	public String getEnd() { return this.end;}
	public void setEnd(String end) {this.end=end;}
	
	public String toString() {
		return "la distance entre le point de départ et "
				+ "celui d'arrivée est de " + distance + " m "+
				"\n le point de départ est "+ start+ 
				"\n le point d'arrivée est "+ end;
	}
	public List<Step> getSteps() {
		return steps;
	}
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}
		
	
	
	

}
