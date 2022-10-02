// Title:           PO9 Dictionary Using BST
// Files:           DictionaryWord.java
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
 * This class creates a Word with a meaning
 * @author mitchalley, Abhiram Koganti
 *
 */
public class DictionaryWord {

	private final String word; // word that represents the search key for this dictionary word
	private final String meaning;   // The meaning of the word that this dictionary node defines
	private DictionaryWord leftChild;  // The leftChild of the the current WebPageNode
	private DictionaryWord rightChild; // The rightChild of the the current WebPageNode
	 
	// The following should be the only constructor for this class
	// Creates a new dictionary word with the provided word and its meaning pair
	// Throws IllegalArgumentException when the word or meaning are either references to an empty
	// string or null references. The thrown exception should include a significant error message 
	// describing which of these problems was encountred.
	/**
	 * constructor
	 * @param word - the word being placed in the dictionary
	 * @param meaning - definition of the word
	 */
	public DictionaryWord(String word, String meaning) { 
		if (word == null)
			throw new IllegalArgumentException("Word provided was null");
		if (word.equals(""))
			throw new IllegalArgumentException("Word was an empty string");
		if (meaning == null)
			throw new IllegalArgumentException("Meaning privided was null");
		if (meaning.equals(""))
			throw new IllegalArgumentException("Meaning was an empty string");
		
		this.word = word;
		this.meaning = meaning;
	}
	 
	 
	// Getter for the left child of this dictionary word
	/**
	 * 
	 * @return - the left node
	 */
	public DictionaryWord getLeftChild() { 
		return leftChild;
	}
	 
	// Setter for the left child of this dictionary word
	/**
	 * sets the left node
	 * @param leftChild - what the left node is being set to
	 */
	  public void setLeftChild(DictionaryWord leftChild) {
		  this.leftChild = leftChild;
	  }
	 
	// Getter for the right child of this dictionary word
	  /**
	   * 
	   * @return - the right node
	   */
	public DictionaryWord getRightChild() { 
		return rightChild;
	}
	 
	// Setter for the right child of this dictionary word
	/**
	 * sets the right node
	 * @param rightChild - what the right node is being set to
	 */
	public void setRightChild(DictionaryWord rightChild) { 
		this.rightChild = rightChild;
	}
	 
	// Getter for the word of this dictionary word
	/**
	 * gets the string word
	 * @return - the word
	 */
	public String getWord() { 
		return word;
	}
	 
	// Getter for the meaning of the word of this dictionary word
	/**
	 * gets the meaning
	 * @return - the meaning of the word
	 */
	public String getMeaning() { 
		return meaning;
	} 
	  
	// Returns a String representation of this DictionaryWord.
	// This String should be formatted as follows. "<word>: <meaning>"
	// For instance, for a dictionaryWord that has the String "Awesome"
	// for the instance field word and the String "adj. Inspiring awe; dreaded."
	// as value for meaning field, the String representing that dictionaryWord is
	// "Awesome: adj. Inspiring awe; dreaded."
	/**
	 * puts the word to a string
	 */
	public String toString() { 
		return word + ": " + meaning;
	}
	
	
}
