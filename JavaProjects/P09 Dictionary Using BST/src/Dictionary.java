// Title:           PO9 Dictionary Using BST
// Files:           Dictionary.java
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


/**
 * interface for the project
 * @author mitchalley, Abhiram Koganti
 *
 */
public interface Dictionary {

	  // checks whether the dictionary is empty or not
		/**
		 * 
		 * @return - whether tree is empty
		 */
	  public boolean isEmpty();
	  
	  // adds this word definition (word and the provided meaning) to the dictionary
	  // Returns true if the word was successfully added to this dictionary
	  // Returns false if the word was already in the dictionary
	  // Throws IllegalArgumentException if either word or meaning is null or an empty
	  // String
	  /**
	   * 
	   * @param word - word being placed into tree
	   * @param meaning - definition of word
	   * @return - whether or not the word was added successfully
	   */
	  public boolean addWord(String word, String meaning);
	  
	  // Returns the meaning of the word s if it is present in this dictionary
	  // Throws a NoSuchElementException if the word s was not found in this dictionary  
	  /**
	   * 
	   * @param s - word being looked up
	   * @return - the meaning of the word
	   */
	  public String lookup(String s);
	  
	  // Returns the number of words stored in this dictionary
	  /**
	   * 
	   * @return - the size of the tree
	   */
	  public int size();
	
}
