package fr.utt.data;

import java.util.ArrayList;
import java.util.List;

public class POI {

	/* Attributs */
	
	private Location location;
	private String name;
	private List<Resource> resources;
	
	/* Constructeur */
	
	public POI() {
	
		this.setLocation(new Location());
		this.setName("");
		this.setResources(new ArrayList<Resource>());
	}

	/* Méthodes */
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the resources
	 */
	public List<Resource> getResources() {
		return resources;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

}
