package fr.utt.data;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.net.Uri;

public class JSONFileManager {

	/* Attributs */
	
	private Context context;
	
	/* Constructeur */
	
	public JSONFileManager(Context context) {
		
		this.context = context;
	}
	
	/* Méthodes */
	
	public File ImportTempFileFromURL(String url) {
		
	    File file = null;
	    
	    try {
	        			
	    	String fileName = Uri.parse(url).getLastPathSegment();
	        file = File.createTempFile(fileName, null, this.context.getCacheDir());
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return file;
	}
	
	public void CreateFileOnInternalStorage(String fileName, String content) {
			
		FileOutputStream outputStream;
		
		try {
			
			outputStream = context.openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE); 
			outputStream.write(content.getBytes());
			outputStream.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String ReadFileFromInternalStorage(String fileName) {
		
		
		String content = "";
		File file = new File(this.context.getFilesDir() + "/" + fileName);
				
		try {
		
			FileInputStream stream = new FileInputStream(file);
		    int count;
		    byte[] buffer = new byte[1024];
		    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(stream.available());

		    while (true) {
		        count = stream.read(buffer);
		        if (count <= 0)
		            break;
		        byteStream.write(buffer, 0, count);
		    }

		    content = byteStream.toString();
		    
		} 
		catch (IOException e) {
		    e.printStackTrace();
		}
		
		return content;
	}
}