package fr.utt.data;

public class ImageResource extends Resource {
	
	/* Attributs */
	
	private String URLImage;

	/* Constructeur */

	public ImageResource() {
		
	}
	
	/* Méthodes */
	
	/**
	 * @return the uRLImage
	 */
	public String getURLImage() {
		return URLImage;
	}

	/**
	 * @param uRLImage the uRLImage to set
	 */
	public void setURLImage(String URLImage) {
		this.URLImage = URLImage;
	}
	
}
