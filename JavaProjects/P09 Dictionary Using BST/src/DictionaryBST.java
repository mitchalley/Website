
// Title:           PO9 Dictionary Using BST
// Files:           DictionaryBST.java
// Course:          CS 300
//
// Author:          Mitch Alley
// Email:           mgalley@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Abhiram Koganti
// Partner Email:   akoganti@wisc.edu
// Partner Lecturer's Name: Gary Dahl
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   X Write-up states that pair programming is allowed for this assignment.
//   X We have both read and understand the course Pair Programming Policy.
//   X We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         none
// Online Sources:  none
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

/**
 * 
 * @author mitchalley, Abhiram Koganti
 *
 */
public class DictionaryBST implements Dictionary {

	private DictionaryWord root;

	// This should be the only constructor of this class.
	// Creates an empty dictionaryBST.
	/**
	 * constructor for the class
	 */
	public DictionaryBST() {
	}

	// Methods defined in the Dictionary interface
	/**
	 * returns whether the tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * adds a new node
	 * 
	 * @param word    - the word being added
	 * @param meaning - the meaning of the word
	 */
	public boolean addWord(String word, String meaning) {
		DictionaryWord tmp = new DictionaryWord(word, meaning);
		if (root == null) {// assign to root of null
			root = tmp;
			return true;
		}

		return (addWordHelper(tmp, root));
	}

	/**
	 * finds a specified node
	 * 
	 * @param s - string being located
	 */
	public String lookup(String s) {
		return lookupHelper(s, root);
	}

	/**
	 * @return the size of the tree
	 */
	public int size() {
		return sizeHelper(root);
	}

	// Public methods not defined in the Dictionary interface
	/**
	 * Computes and returns the height of this dictionaryBST, as the number of nodes
	 * from root to the deepest leaf DictionaryWord node.
	 * 
	 * @return the height of this Binary Search Tree counting the number of
	 *         DictionaryWord nodes
	 */
	public int height() {
		return heightHelper(root);
	}

	/**
	 * Returns all the words within this dictionary sorted from A to Z
	 * 
	 * @return an ArrayList that contains all the words within this dictionary
	 *         sorted in the ascendant order
	 */
	public ArrayList<String> getAllWords() {
		return getAllWordsHelper(root);
	}

	// Recursive private helper methods
	// Each public method should make call to the recursive helper method with the
	// corresponding name

	/**
	 * Recursive helper method to add newWord in the subtree rooted at node
	 * 
	 * @param newWordNode a new DictionaryWord to be added to this dictionaryBST
	 * @param current     the current DictionaryWord that is the root of the subtree
	 *                    where newWord will be inserted
	 * @return true if the newWordNode is successfully added to this dictionary,
	 *         false otherwise
	 */
	private static boolean addWordHelper(DictionaryWord newWordNode, DictionaryWord current) {
		int comparison = newWordNode.getWord().toLowerCase().compareTo(current.getWord().toLowerCase());

		if (comparison == 0)// already added to dictionary
			return false;
		else if (comparison < 0) {// goes down left subtree
			if (current.getLeftChild() == null) {// if there is nothing assigned, the left child is set
				current.setLeftChild(newWordNode);
				return true;
			} else
				return addWordHelper(newWordNode, current.getLeftChild());// calls method again if current is not null
		} else {
			if (current.getRightChild() == null) {// if there is nothing assigned, the right child is set
				current.setRightChild(newWordNode);
				return true;
			} else {
				return addWordHelper(newWordNode, current.getRightChild());
			}
		}

	}

	/**
	 * Recursive helper method to lookup a word s in the subtree rooted at current
	 * 
	 * @param s       String that represents a word
	 * @param current pointer to the current DictionaryWord within this dictionary
	 * @return the meaning of the word s if it is present in this dictionary
	 * @throws NoSuchElementException if s is not found in this dictionary
	 */
	private static String lookupHelper(String s, DictionaryWord current) {
		if (current == null)
			throw new NoSuchElementException();
		if (current.getWord().equalsIgnoreCase(s))
			return current.getMeaning();
		int comparison = s.compareToIgnoreCase((current.getWord()));// compares the values and returns based on which
																	// tree it follows
		if (comparison < 0) {
			return lookupHelper(s, current.getLeftChild());
		} else {
			return lookupHelper(s, current.getRightChild());
		}

	}

	/**
	 * Recursive helper method that returns the number of dictionary words stored in
	 * the subtree rooted at current
	 * 
	 * @param current current DictionaryWord within this dictionaryBST
	 * @return the size of the subtree rooted at current
	 */
	private static int sizeHelper(DictionaryWord current) {
		if (current == null)
			return 0;
		else
			return (sizeHelper(current.getLeftChild()) + 1 + sizeHelper(current.getRightChild()));// increments the
																									// return value
																									// every time this
																									// method is called
	}

	/**
	 * Recursive helper method that computes the height of the subtree rooted at
	 * current
	 * 
	 * @param current pointer to the current DictionaryWord within this
	 *                DictionaryBST
	 * @return height of the subtree rooted at current counting the number of
	 *         DictionaryWord nodes from the current node to the deepest leaf in the
	 *         subtree rooted at current
	 */
	private static int heightHelper(DictionaryWord current) {
		if (current == null)
			return 0;

		int leftHeight = heightHelper(current.getLeftChild());// get the left subtree height
		int rightHeight = heightHelper(current.getRightChild());// gets the right subtree height
		return 1 + Math.max(leftHeight, rightHeight);// gets the max between rightHeight and leftHeight
	}

	/**
	 * Recursive Helper method that returns a list of all the words stored in the
	 * subtree rooted at current
	 * 
	 * @param current pointer to the current DictionaryWord within this
	 *                dictionaryBST
	 * @return an ArrayList of all the words stored in the subtree rooted at current
	 */
	private static ArrayList<String> getAllWordsHelper(DictionaryWord current) {
		ArrayList<String> allWords = new ArrayList<String>();
		if (current != null) {
			allWords.add(current.getWord());
			allWords.addAll(getAllWordsHelper(current.getLeftChild()));// adds the left subtree
			allWords.addAll(getAllWordsHelper(current.getRightChild()));// adds the right subtree
		}
		Collections.sort(allWords, String.CASE_INSENSITIVE_ORDER);
		return allWords;
	}
}
