package beyes;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;

import com.google.common.collect.Maps;
public class scriptClassifier {

	HashMap<String,Integer> genreBagOfWords;
	HashMap<String,Integer> thisBagOfWords;
	HashMap<String,Integer> theGenres;
	HashMap<String,Integer> printedMap;
	HashMap<String,String> theFullMap;

	
	double highestVal = 0;
	int wordCount = 0;
	
	String keyWord;
	String path;
	
	
	public scriptClassifier(String path) throws FileNotFoundException
	{
		this.theFullMap = new HashMap<String,String>();	
		this.path = path;

		/* These Strings are the folder names of each different genre, 
		 * the last one is my test script, since the folder names are accurate, I use them for labels in the output as well. 
		 */
		
		String action = "Action";
		String comedy = "Comedy";
		String drama = "Drama";
		String horror = "Horror";
		String musical = "Musical";
		String mystery = "Mystery";
		String science = "Sci-Fi";
		
		genreBag actionBag = new genreBag(this.path+action);
		genreBag comedyBag = new genreBag(this.path+comedy);
		genreBag dramaBag = new genreBag(this.path+drama);
		genreBag horrorBag = new genreBag(this.path+horror);
		genreBag musicalBag = new genreBag(this.path+musical);
		genreBag scienceBag = new genreBag(this.path+science);
		genreBag mysteryBag = new genreBag(this.path+mystery);
		
		/* 
		 * we take the wordbag of each genre and add it to a global wordbag, 
		 * this will allow us to create a general wordbag, so we can words specific to each genre
		 */
		this.addWordBag(actionBag.wordBag, action);
		this.addWordBag(comedyBag.wordBag, comedy);
		this.addWordBag(dramaBag.wordBag, drama);
		this.addWordBag(horrorBag.wordBag, horror);
		this.addWordBag(musicalBag.wordBag, musical);
		this.addWordBag(scienceBag.wordBag, science);
		this.addWordBag(mysteryBag.wordBag, mystery);
		
		
		//folder of the scripts to be classified
		String scriptsToBeClassified = "scripts-to-classify"; 
		//String scriptsToBeClassified = "tests"; 
		
		// we iterate through each file in our directory...
		File dir = new File(this.path+scriptsToBeClassified);
		File[] directoryListing = dir.listFiles();	
		if (directoryListing != null) {
		
			for (File child : directoryListing) 
		    {
				if(!child.getName().equals(".DS_Store"))
				{
					genreBag newScript = new genreBag(child);
					
					/* ...and we print out our calculated scores, 
					 * the higher the score, the more confident the system is that 
					 * the script belongs to a certain genre, and we append our labels at the end of each print
					 */ 
					
					System.out.println(newScript.fileName+" -- ");
					System.out.println(this.genScore(newScript, actionBag)+" "+action);
					System.out.println(this.genScore(newScript, comedyBag)+" "+comedy);
					System.out.println(this.genScore(newScript, dramaBag)+" "+drama);
					System.out.println(this.genScore(newScript, horrorBag)+" "+horror);
					System.out.println(this.genScore(newScript, musicalBag)+" "+musical);
					System.out.println(this.genScore(newScript, scienceBag)+" "+science);
					System.out.println(this.genScore(newScript, mysteryBag)+" "+mystery);
					System.out.println();
					
				}
		    }
		}		
	}
	
	
	double genScore(genreBag testMap, genreBag genreMap) throws FileNotFoundException
	{	
		double weight = this.compareTwoMaps(testMap, genreMap);
		return weight;
	}
	
	
	
	public double compareTwoMaps(genreBag theScrip ,genreBag theGenr) throws FileNotFoundException
	{
		double score = 0.0;
		HashMap<String,Integer> theScript = theScrip.wordBag;
		HashMap<String,Integer> theGenre = theGenr.wordBag;
			
		Iterator<String> iter = theScript.keySet().iterator();
		while(iter.hasNext()) {
		    String key = (String)iter.next();
		    
		   
		    if(theGenre.get(key) != null && key!="-<-")
		    {
		    	/*
		    	 * The score calculation takes place here, for this key, we get a word count from the word bag and divide over total word count 
		    	 * in genre
		    	 */
		   
		    	double wordCountInGenre = theGenre.get(key);
		    	double totalWordCountInGenre = theGenre.get("-<-");
		    	double probability = wordCountInGenre/totalWordCountInGenre;
		    	double thisScriptValue = theScript.get(key);
		    	
		    	score = score + thisScriptValue * probability;	    	

		    	
		    	/* and we boost the score by checking if a word shows up in any other genres, and this improves accuracy by a large amount */
		    	if(this.theFullMap.get(key) != "multiGenre")
		    	{
		    		score = score + 1.0;
		    	}
		    	
		    }
		}

		return score;	
	}
	
	
	
	
	
	public void addWordBag(HashMap<String,Integer> addedMap,String theGenre)
	{
		Iterator<String> iter = addedMap.keySet().iterator();
		while(iter.hasNext()) 
		{
		    String key = (String)iter.next();
		    
		    if(this.theFullMap.get(key)==null)
		    {
		    	this.theFullMap.put(key, theGenre);
		    }
		    else
		    {
		    	/* our way of labeling words which appear in different genres */ 
		    	this.theFullMap.put(key, "multiGenre");
		    }
		}
		
	}
	
		
	
	
 
	
	
	
	
}
