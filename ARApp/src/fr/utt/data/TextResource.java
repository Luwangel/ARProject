package fr.utt.data;

public class TextResource extends Resource {
	
	/* Attributs */
	
	private String content;

	/* Constructeur */

	public TextResource() {
		
	}
	
	/* Méthodes */

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
}
