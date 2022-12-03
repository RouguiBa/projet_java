 package eu.ensg.ign;

import java.util.List;

public class Resultat {
	/**
	 * @version 
	 * @author Rouguiatou BA
	 */
	
	private String profile;
	private List<Portion> portions;
	
	// GET AND SET
	
	public void setProfile(String profile) { this.profile = profile; }
	public String getProfile() { return this.profile; }
	
	public List<Portion> getPortions() { return this.portions; }
	public void setPortions(List<Portion> portions) { this.portions = portions; }
}
