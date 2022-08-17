package project5;

import java.util.*;
import java.io.*;

/**
 * Main Class to handle the input and output the path in required format
 * @author Sarath Kareti
 * @version May 2, 2021
 */
public class BSTMountainAdventure {
	/**
	 * Parses the list of words to proper stop supplies
	 * @param words
	 */
	public static RestStop getStop(String[] words) {
		RestStop stop = new RestStop(words[0]);
		for (int i = 1; i < words.length; i++) { //iterate through words to find how much of food/axes/rafts/fallen trees/rivers there are in the mountain
			if (words[i].compareTo("food") == 0) { //looks for word 'food'
				stop.food++;
			}
			else if (words[i].compareTo("axe") == 0) { //looks for word 'axe'
				stop.axe++;
			}
			else if (words[i].compareTo("raft") == 0) { //looks for word 'raft'
				stop.raft++;
			}
			else if (words[i].compareTo("fallen") == 0) { //looks for word 'fallen'
				if (i + 1 < words.length && words[i + 1].compareTo("tree") == 0) {
					stop.fallenTree = true;
					i++;
				}
			}
			else if (words[i].compareTo("river") == 0) { //looks for word 'river'
				stop.river = true;
			}
		}

		return stop;
	}

	public static void main(String[] args) {
		//check for if there are no arguments in the command line
		if (args.length < 1) {
			System.out.println("Usage: java BSTMountainAdventure <input_file>");
			return;
		}


		//initalize BSTMountain
		BSTMountain mountain = new BSTMountain();


		//set filename to command line argument
		String filename = args[0];
		try {
			File file = new File(filename);
			Scanner reader = new Scanner(file);
			while (reader.hasNextLine()) {
				//read each line of file
				String line = reader.nextLine();
				String[] words = line.split("\\s+");
				RestStop stop = getStop(words);
				mountain.add(stop);
			}
			reader.close();
		} catch (FileNotFoundException e) {
	    	System.out.println("File not found");
	    	return;
	    }

	    ArrayList<ArrayList<String>> allPaths = mountain.findPath();

	    boolean noPath = true;

	    for (ArrayList<String> currentPath: allPaths) {
	    	if (currentPath.size() == 0) {
	    		continue;
	    	}
	    	noPath = false;
			for (int i = 0; i < currentPath.size() - 1; i++) {
				System.out.print(currentPath.get(i) + " ");
			}
			System.out.print(currentPath.get(currentPath.size() - 1));
			System.out.println();
		}
	}
}