package fr.utt.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

	/* TAGS */
	
	public static String TAG_NAME = "name";
	
	public static String TAG_LOCATION = "location";
	public static String TAG_LOCATION_LATITUDE = "latitude";
	public static String TAG_LOCATION_LONGITUDE = "longitude";
	public static String TAG_LOCATION_ALTITUDE = "altitude";

	public static String TAG_RESOURCES = "resources";	
	public static String TAG_RESOURCES_AUTHOR = "author";
	public static String TAG_RESOURCES_CONTENT = "content";
	public static String TAG_RESOURCES_DESCRIPTION = "description";
	public static String TAG_RESOURCES_TYPE = "type";	
	public static String TAG_RESOURCES_URL = "url";
	
	/* Méthodes */
	
	public static POI ImportPOI(String jsonStr) {
		
		if (jsonStr != null && !jsonStr.isEmpty()) {
			
			POI poi = new POI();
			
			try {
                
				JSONObject jsonObj = new JSONObject(jsonStr);

				//NAME
                poi.setName(jsonObj.getString(TAG_NAME));
                
                //LOCATION
				JSONObject location = new JSONObject(jsonObj.getString(TAG_LOCATION));
				poi.setLocation(new Location(
										location.getDouble(TAG_LOCATION_LATITUDE),
										location.getDouble(TAG_LOCATION_LONGITUDE),
										location.getDouble(TAG_LOCATION_ALTITUDE)));
                		
				//RESOURCES
                JSONArray resources = jsonObj.getJSONArray(TAG_RESOURCES);

                if(resources != null && resources.length() > 0) {

                	ArrayList<Resource> listResources = new ArrayList<Resource>();
                	
                    for (int i = 0; i < resources.length(); i++) {
                        
                    	JSONObject c = resources.getJSONObject(i);
                        
                    	//Données globales
                    	
                        String type = c.getString(TAG_RESOURCES_TYPE);
                        String author = c.getString(TAG_RESOURCES_AUTHOR);
                        String description = c.getString(TAG_RESOURCES_DESCRIPTION);
                        
                        Resource r = null;

                    	//Choix du type de ressource
                    	
                        if(type.equalsIgnoreCase(ResourceType.IMAGE.name())) {
                        	
                        	r = new ImageResource();
                        	((ImageResource)r).setURLImage(c.getString(TAG_RESOURCES_URL));
                        	
                        }
                        else if(type.equalsIgnoreCase(ResourceType.IMAGEAR.name()))	{
                        	
                        	r = new ImageARResource();
                        	((ImageARResource)r).setURLImage(c.getString(TAG_RESOURCES_URL));
                        }
                        else if(type.equalsIgnoreCase(ResourceType.TEXT.name())) {
                        	
                        	r = new TextResource();
                        	((TextResource)r).setContent(c.getString(TAG_RESOURCES_CONTENT));
                        }
                        
                        if(r != null) {
                        	
                        	r.setAuthor(author);
                        	r.setDescription(description);
                            listResources.add(r);
                        }
                        	
                    }
                    
                    poi.setResources(listResources);

                }
                            
			} 
			catch (JSONException e) {
                e.printStackTrace();
            }
			
			return poi;
		}
		
		return null;
	}
	
}


/*
	{
		"name" : "valeur",
		"location" :  
					{
						"altitude" : "0.0",
						"latitude": "0.0", 
					    "longitude":"0.0"
					},
		"resources":
					[
						{
							"type" : "IMAGE",
							"author" : "AUTHOR",
							"description" : "",
							"url" : ""
						},
						
						{
							"type" : "TEXT",
							"author" : "AUTHOR",
							"description" : "",
							"content" : "eensfn klsdnlksfd fslk fsdlnkfklsfdnlkfsklsflk"
						}, 
					    
					    {
							"type" : "IMAGEAR",
							"author" : "AUTHOR",
							"description" : "",
							"url" : ""					
						}
					]
	}
 */
