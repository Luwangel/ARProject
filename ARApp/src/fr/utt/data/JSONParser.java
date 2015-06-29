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
	
	public static POI ImportPOI(String jsonStr) throws Exception {
		
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
                        
                    	JSONObject ressJSONObj = resources.getJSONObject(i); //Représente une ressource
                        
                    	//Données globales
                    	
                        String type = ressJSONObj.getString(TAG_RESOURCES_TYPE);
                        String author = ressJSONObj.getString(TAG_RESOURCES_AUTHOR);
                        String description = ressJSONObj.getString(TAG_RESOURCES_DESCRIPTION);
                        
                        Resource ress = null;

                    	//Choix du type de ressource
                    	                      
                        if(type.equalsIgnoreCase(ResourceType.IMAGE.name())) {
                        	
                        	ress = new ImageResource();
                        	((ImageResource)ress).setURLImage(ressJSONObj.getString(TAG_RESOURCES_URL));
                        	
                        }
                        else if(type.equalsIgnoreCase(ResourceType.IMAGEAR.name()))	{
                        	
                        	ress = new ImageARResource();
                        	((ImageARResource)ress).setURLImage(ressJSONObj.getString(TAG_RESOURCES_URL));
                        }
                        else if(type.equalsIgnoreCase(ResourceType.TEXT.name())) {
                        	
                        	ress = new TextResource();
                        	((TextResource)ress).setContent(ressJSONObj.getString(TAG_RESOURCES_CONTENT));
                        }
                        
                        if(ress != null) {
                        	
                        	ress.setAuthor(author);
                        	ress.setDescription(description);
                            listResources.add(ress);
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
		else {
			throw new Exception("JSon file is null of empty.");
		}
	}
	
	public static String exportPOI(POI poi) throws Exception {
				
		JSONObject jsonObj = new JSONObject();
		
		if(poi != null) {
			
			//NAME
			jsonObj.put(TAG_NAME, poi.getName());
			
			//LOCATION
			JSONObject location = new JSONObject();
			location.put(TAG_LOCATION_ALTITUDE, poi.getLocation().getAltitude());
			location.put(TAG_LOCATION_LATITUDE, poi.getLocation().getLatitude());
			location.put(TAG_LOCATION_LONGITUDE, poi.getLocation().getLongitude());
			
			jsonObj.put(TAG_LOCATION, location);
			
			//RESSOURCES
			JSONArray resources = new JSONArray();
			
			for(Resource r : poi.getResources()) {
				
				JSONObject ress = new JSONObject();
				
				ress.put(TAG_RESOURCES_TYPE, r.getType());
				ress.put(TAG_RESOURCES_AUTHOR, r.getAuthor());
				ress.put(TAG_RESOURCES_DESCRIPTION, r.getDescription());
				
				switch(r.getType()) {
				
				case IMAGE:
					ress.put(TAG_RESOURCES_URL, ((ImageResource)r).getURLImage());
					break;
				case TEXT:
					ress.put(TAG_RESOURCES_DESCRIPTION, ((TextResource)r).getContent());
					break;
				case IMAGEAR:
					ress.put(TAG_RESOURCES_URL, ((ImageARResource)r).getURLImage());
					break;
				}
									
				resources.put(ress);
			}
			
			jsonObj.put(TAG_RESOURCES, resources);
			
		}
		else
		{
			throw new Exception("POI is null.");
		}
		
		return jsonObj.toString();	
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
