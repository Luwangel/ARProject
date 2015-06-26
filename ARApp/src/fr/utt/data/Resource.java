package fr.utt.data;

public abstract class Resource {
	
	/* Attributs */
	
	private String author;
	private String description;
	private ResourceType type;
	
	/* Constructeur */

	public Resource() {
		
	}
	
	public Resource(String author, String description, ResourceType type) {
		
		this.setAuthor(author);
		this.setDescription(description);
		this.setType(type);		
	}
	
	/* Méthodes */
	
	/**
	 * @return the type
	 */
	public ResourceType getType() {
		return type;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ResourceType type) {
		this.type = type;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
}