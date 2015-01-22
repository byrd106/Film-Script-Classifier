package beyes;

import java.io.*;


public class main {
	public static void main(String args[]) throws FileNotFoundException
	{
		String pathToScriptsFolder = "scripts/";
		
		scriptClassifier classifyScript = new scriptClassifier(pathToScriptsFolder);
		/* we give a path to a folder of scripts, the program will use training set to identify the genre of each, 
		 * and output confidence levels of each genre for each script we give it in the console
		 */
		
		
	}
}



