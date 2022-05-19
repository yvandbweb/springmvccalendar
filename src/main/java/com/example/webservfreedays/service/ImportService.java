package com.example.webservfreedays.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Service
public class ImportService {
	
	
	private static final String COMMA_DELIMITER = null;
	private Set<String> getFileFromURL(String dir) throws IOException {
	    Set<String> fileList = new HashSet<>();
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
	        for (Path path : stream) {
	            if (!Files.isDirectory(path)) {
	                fileList.add(path.getFileName()
	                    .toString());
	            }
	        }
	    }
	    return fileList;
	   	   
	}	
	
	private JSONObject getJsonFromMap(Map<String, Object> map) throws JSONException {
	    JSONObject jsonData = new JSONObject();
	    for (String key : map.keySet()) {
	        Object value = map.get(key);
	        if (value instanceof Map<?, ?>) {
	            value = getJsonFromMap((Map<String, Object>) value);
	        }
	        jsonData.put(key, value);
	    }
	    return jsonData;
	}	

		
	

    public String home() throws IOException {
    	Set<String> fs=getFileFromURL("/home/geronimo/data/countries/");
    	Map<String, Object> data2 = new HashMap<>();
    	String nn="";
    	
    	ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    	mapper.findAndRegisterModules();
    	
    	
    	for (String f : fs) {
    		try {
	    		InputStream inputStream = new FileInputStream(new File("/home/geronimo/data/countries/"+f));
	    		Yaml yaml = new Yaml();
   		
	    		Map<String, Object> data = yaml.load(inputStream);
	    		
	    		var datajson=getJsonFromMap(data);
	    		datajson=(JSONObject) datajson.get("holidays");
	    		datajson=(JSONObject) datajson.get(f.replace(".yaml",""));
	    		
	    		//names
	    		var datajsonnames=(JSONObject) datajson.get("names");
	    		var datajsonname=datajsonnames.get("en");
	    		
	    		//names
	    		var datajsondays=(JSONObject) datajson.get("days");
	    		//var datajsonname=datajsonnames.get("en");	 
	    			    			    	    		            
	    		

	    		//System.out.println(datajsonname.toString());
	    		//System.out.println(datajson.toString());
	    		nn+=datajsonname.toString()+"<br>";
	    		Iterator<String> keys = datajsondays.keys();
	    		while(keys.hasNext()) {
	    		    String key = keys.next();
	    		    if (datajsondays.get(key) instanceof JSONObject && key.toString().length()==5) {
	    		    	
	    		    	nn+="&nbsp;&nbsp;"+key.toString()+"<br>";
	    		    	//System.out.println(key.toString()); 
	    		    }
	    		}	    		
			}
			catch(Exception e)
			{
				System.out.println("Exception ocuured for index "+f+" and exception is "+e.getMessage());
			}

    		
    	}
    	
        
        return nn;
    }	
    
    
    
    public String createmapnamedays() throws IOException {
    	String nn="";
		InputStream inputStream = new FileInputStream(new File("/home/geronimo/data/names.yaml"));
		Yaml yaml = new Yaml();
		Map<String, Object> data = yaml.load(inputStream);
		
		
		
		var datajson=new JSONObject(data);
		
		System.out.println(datajson.toString()); 
		
		datajson=(JSONObject) datajson.get("names");
				
		
		Iterator<String> keys = datajson.keys();
		while(keys.hasNext()) {
		    String key = keys.next();
		    if (datajson.get(key) instanceof JSONObject && key.toString().length()==5) {
		    	
		    	nn+="&nbsp;&nbsp;"+key.toString()+"<br>";
		    	JSONObject datajson2 = (JSONObject) datajson.get(key.toString());
		    	datajson2= (JSONObject) datajson2.get("name");
		    	
				nn+="&nbsp;&nbsp;"+datajson2.get("en")+"<br>";	
		    }
		}		
    	
        
        return nn;
    }
    
    
    public Map<String,String> countrySimple(String countr) throws IOException {
    	Map<String,String> nn = new HashMap<>();
		InputStream inputStream = new FileInputStream(new File("/home/geronimo/data/own/own.yaml"));
		Yaml yaml = new Yaml();
		Map<String, Object> data = yaml.load(inputStream);		
		
		var datajson=new JSONObject(data);
		
		//System.out.println(datajson.toString()); 
		
		datajson=(JSONObject) datajson.get(countr);
				
		
		Iterator<String> keys = datajson.keys();
		while(keys.hasNext()) {
		    String key = keys.next();	    
		    nn.put(key.toString(), datajson.get(key.toString()).toString());
		}		
    	
        
        return nn;
    }     
    
    
    public List<String> importeaster() throws FileNotFoundException, IOException{    
	    List<String> records = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(Paths.get("/home/geronimo/data/pasen2.csv").toString()))) {

	        String line;
	        Integer i=0;
	        while ((line = br.readLine()) != null) {
	        	String date = line.replace("\"", "");
	        	String finaldate="";

	        	String[] parts = date.split(" ");
	        	if (parts[0].contains("April")) {
	        		finaldate=parts[2]+"-04-"+parts[1].replace(",", "")+" 00:00:01";
	        	}else {
	        		finaldate=parts[2]+"-03-"+parts[1].replace(",", "")+" 00:00:01";
	        	}	        	
	        	
	        	try {
	            //String[] values = line.split(COMMA_DELIMITER);
	       		//LocalDate date = LocalDate.parse(line);
	            System.out.println(finaldate);
	            System.out.println(line);
	            //System.out.println(date);
	            records.add(finaldate);
	        	}catch (Exception e) {
	        		//System.out.println("Exception ocuured for index "+i+" and exception is "+e.getMessage());	        	
	        	}
	            
	        	i++;
	        }
	    }
		return records; 
    
    }
    
    
    public HashMap<String,String> generatetranslate() throws FileNotFoundException, IOException{    
	    HashMap<String,String> records = new HashMap<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(Paths.get("/home/geronimo/data/own/translate.csv").toString()))) {
	    	
	        String line;
	        Integer i=0;
	        while ((line = br.readLine()) != null) {
	        	String date = line.replace("\"", "");
	        	String[] values = line.split(";");
       	
	        	records.put(values[0],values[1]);

	        }
	    }
		return records; 
    
    }    

}
