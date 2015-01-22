package beyes;

import java.io.*;
import java.util.*;

public class genreBag {
	
	HashMap<String,Integer> wordBag;
	
	String dPath;
	String fileName;
	
	int wordCount;
	int overallWordCount;
	int bagWC;
	int excitePoints;
	
	double wordAverage;
	double excitePointsAverage;
	double possibilityValue;
	
	
	genreBag(File child)
	{	
		this.bagWC = 0;
		this.wordBag = new HashMap<String,Integer>();	
		this.readThisFile(child);
	}
	

	genreBag(String path) throws FileNotFoundException	
	{
		
		this.dPath = path;
		this.wordBag = new HashMap<String,Integer>();
		this.read();
	}
	
	
	void read() throws FileNotFoundException
	{
		this.overallWordCount = 0;
		
		File dir = new File(this.dPath);
		File[] directoryListing = dir.listFiles();	
	
		if (directoryListing != null) {
		
			for (File child : directoryListing) 
		    {
				if(!child.getName().equals(".DS_Store"))
				{
				
				this.buildValues(child.getPath());
				//this creates the bag and the averages etc..
			
				}
		    }	
		}
	}
	
	
	void readThisFile(File child)
	{
		
		this.overallWordCount = 0;
		
				if(!child.getName().equals(".DS_Store"))
				{
				
				this.wordCount = 0;
				this.excitePoints = 0;
				this.buildValues(child.getPath());
				//this creates the word bag and gets us relevant data
				}
				
		this.fileName= child.getName();
	}
	
	

	
	
	private void buildValues(String posLoc) {
	     
		  // this generates the word bag and builds it out
	      BufferedReader br = null;
	      try {
	         br = new BufferedReader(new FileReader(new File(posLoc)));
	         String av;
	         while((av = br.readLine()) != null) {
	        	 
	        	 String[] words = av.split(" ");

	             for(int i=0; i<words.length; i++){
	                 if(words[i].trim().length() > 1) {
	                	
	                	String word = words[i].replaceAll("\\s|\\n","").replaceAll("[^!a-zA-Z ]", "").toLowerCase();
	                	this.addToBag(word);   	
	                	this.addToWordCount();
	                	this.overallWordCount++;
	                    bagWC++;
	                 }
	             }
	        	 this.wordBag.put("-<-", bagWC);
	        	 
	        	 
	         }
	      } catch (FileNotFoundException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } finally {
	         if (br != null) {
	            try {
	               br.close();
	            } catch (IOException e) {
	               e.printStackTrace();
	            }
	         }
	      }
	    
	  }
	
	
	
	void addToBag(String word)
	{
		if(this.wordBag.get(word) != null)
    		this.wordBag.put(word, this.wordBag.get(word)+1);
    	else
    		this.wordBag.put(word, 1);
	}
	
	void addToWordCount()
	{
		this.wordCount++;
	}
	
	
}