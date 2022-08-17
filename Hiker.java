package project5;

import java.util.*;

/**
 * Class to represent a Hiker's supplies.
 * Stores the label and the count of supplies
 * @author Sarath Kareti
 * @version May 2, 2021
 */
public class Hiker {
	//Initialize variable to represent hiker's supplies 

	public int food;
	public int axe;
	public int raft;

	//Set supply values to 0

	public Hiker() {
		this.food = 0;
		this.axe = 0;
		this.raft = 0;
	}
}