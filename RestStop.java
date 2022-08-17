package project5;

import java.util.*;

/**
 * Class to represent a RestStop.
 * Stores the label and the count of supplies and if any of the obstruction
 * @author Sarath Kareti
 * @version May 2, 2021
 */
public class RestStop {

	//Initialize variables to represent supplies at each rest stop
	public String label;
	public int food;
	public int axe;
	public int raft;

	//Initialize variables to represent obstacles at each rest stop
	public boolean fallenTree;
	public boolean river;
	


	//set each obstacle to false and supply amounts to 0
	public RestStop(String label) {
		this.label = label;
		this.food = 0;
		this.axe = 0;
		this.raft = 0;
		this.fallenTree = false;
		this.river = false;
	}
}