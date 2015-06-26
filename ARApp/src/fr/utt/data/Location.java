package fr.utt.data;

public class Location {

	/* Attributs */
	
	private double latitude;
	private double longitude;
	private double altitude;
	
	/* Constructeur */
	
	/**
	 * Constructeur par défaut de la classe Location.
	 */
	public Location() {
		
		this.latitude = 0.0;
		this.longitude = 0.0;
		this.altitude = 0.0f;
	}
	
	/**
	 * Constructeur public de la classe Location.
	 * @param latitude
	 * @param longitude
	 * @param altitude
	 */
	public Location(double latitude, double longitude, double altitude) {
		
		this();
		
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setAltitude(altitude);
	}
	
	/* Méthodes */
	
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the altitude
	 */
	public double getAltirude() {
		return altitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		
		if(this.isLatitudeCorrect(latitude)) {
			this.latitude = latitude;
		}
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		
		if(this.isLongitudeCorrect(longitude)) {
			this.longitude = longitude;
		}
		
	}

	/**
	 * @param altirude the altitude to set
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * Latitude : -60° <=> 60°
	 * @param latitude
	 * @return
	 */
	private boolean isLatitudeCorrect(double latitude) {
		
		if(latitude > -60 && latitude < 60) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Longitude : -360° <=> 360°
	 * @param longitude
	 * @return
	 */
	private boolean isLongitudeCorrect(double longitude) {
		
		if(longitude > -360 && longitude < 360) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
