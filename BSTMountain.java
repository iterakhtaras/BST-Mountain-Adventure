package project5;

import java.util.*;

/**
 * Class to represent a Mountain using a BST.
 * @author Sarath Kareti
 * @version May 2, 2021
 */
public class BSTMountain {
	/**
	 * Class to represent a BSTNode with data of type RestStop.
	 */
	class BSTNode {
		//Hold data in RestStop to store how much supplies/how many obstacles are present
		//Initialize left and right nodes for BST
		RestStop data;
		BSTNode left;
		BSTNode right;

		public BSTNode(RestStop data) {
			this.data = data;
			this.left = this.right = null; 
		}
	}

	BSTNode root;
	int totalHeight;

	/**
	 * Constructor to initialize the root of the tree
	 */
	public BSTMountain() {
		this.root = null;
	}

	/**
	 * This method adds a new node to the tree
	 * @param data to be added of type RestStop
	 */
	public void add(RestStop data) {
		if (data == null) 
			return;
		//add data to root
		this.root = add(data, root);
	}

	/**
	 * Recursive function to add a new node to the current position
	 * @param data to be added of type RestStop
	 * @param node current node to locate the position of new node
	 */
	BSTNode add(RestStop data, BSTNode node) {
		if (node == null) {
			return new BSTNode(data); 
		}

		if (data.label.compareTo(node.data.label) < 0) //compare nodes to determine where data should be added
			node.left = add(data, node.left);
		else if (data.label.compareTo(node.data.label) > 0) //compare nodes to determine where data should be added
			node.right = add(data, node.right);

		return node;
	}

	public void inorder() {
		inorder(this.root);
	}

	void inorder(BSTNode node) {
		if (node == null)
			return;

		inorder(node.left);
		System.out.println(node.data.label + " " + node.data.food + " " + node.data.fallenTree + " " + node.data.river);
		inorder(node.right);
	}

	/**
	 * Calculate height of the mountain
	 * @return height of the mountain
	 */
	public int height() {
		totalHeight = height(this.root);
		return totalHeight;
	}

	/**
	 * Recursive method to add a new the current position
	 * @param node current node while calculating height
 	 * @return height of the with node as root
	 */
	int height(BSTNode node) {
		if (node == null) {
			return 0;
		}

		return Math.max(height(node.left), height(node.right)) + 1;
	}

	/**
	 * Find the all possible paths
	 * @return list of all possible paths
	 */
	public ArrayList<ArrayList<String>> findPath() {
		//initialize arralist to hold possible paths
		ArrayList<String> currentPath = new ArrayList<>();
		Hiker hiker = new Hiker(); //initialize hiker
		ArrayList<ArrayList<String>> allPaths = new ArrayList<>();
		height();

		findPath(this.root, currentPath, 0, hiker, allPaths);
		return allPaths;
	}

	/**
	 * Recursive function to find all possible paths
	 * this function traverse through all node in DFS order (preorder)
	 * and check for the required supplies at each rest stop to travel further
	 * if currentHeight(distance travelled) == height of mountain then a path is found
	 * @param node current rest stop
	 * @param currentPath store the labels of the path so far
	 * @param hiker stores the supplies of hiker
	 * @param allPaths list to store all possible paths
	 * @return list of all possible paths
	 */
	void findPath(BSTNode node, ArrayList<String> currentPath, int currentHeight, Hiker hiker, ArrayList<ArrayList<String>> allPaths) {
		if (currentHeight == totalHeight) {
			ArrayList<String> x = new ArrayList<>(); 
			for (String label: currentPath) { //add labels of current path into arraylist
				x.add(label);
			}
			allPaths.add(x);
			return;
		}

		if (node == null) {
			return;
		}

		RestStop stop = node.data;
		pickup(hiker, stop);

		if (hiker.food < 0) { //if hiker doesnt have any food they cannot complete the path
			drop(hiker, stop);
			return;
		}
		if (stop.fallenTree) {
			if (hiker.axe <= 0) { //if the hiker does not have an axe to cut the tree they cannot complete the path
				drop(hiker, stop);
				return;
			}
			hiker.axe--;
		}
		if (stop.river) {
			if (hiker.raft <= 0) { //if the hiker does not have a raft to cross the river they cannot complete the path
				drop(hiker, stop);
				return;
			}
			hiker.raft--;
		}

		// System.out.println(stop.label + " " + hiker.food);
		hiker.food--;
		currentPath.add(stop.label);
		if (node.left == null && node.right == null) {
			findPath(node.left, currentPath, currentHeight + 1, hiker, allPaths);			
		}
		else {
			findPath(node.left, currentPath, currentHeight + 1, hiker, allPaths);
			findPath(node.right, currentPath, currentHeight + 1, hiker, allPaths);
		}

		hiker.food++;
		if (stop.fallenTree) {
			hiker.axe++;
		}
		if (stop.river) {
			hiker.raft++;
		}

		drop(hiker, stop);
		currentPath.remove(currentPath.size() - 1);
	}

	/**
	 * Add the supplies to hiker's supplies
	 * @param hiker 
	 * @param stop
	 */
	void pickup(Hiker hiker, RestStop stop) { 
		//increments supplies if hiker comes across food/axe/raft at a RestStop
		hiker.food += stop.food;
		hiker.axe += stop.axe;
		hiker.raft += stop.raft;		
	}

	/**
	 * Drops the supplies from hiker's supplies, required when backtracking to new path
	 * @param hiker 
	 * @param stop
	 */
	void drop(Hiker hiker, RestStop stop) {
		//decrement supplies if hiker uses supplies at a RestStop
		hiker.food -= stop.food;
		hiker.axe -= stop.axe;
		hiker.raft -= stop.raft;		
	}
}