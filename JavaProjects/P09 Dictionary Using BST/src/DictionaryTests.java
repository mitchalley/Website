import java.util.ArrayList;

// Title:           PO9 Dictionary Using BST
// Files:           DictionaryTests.java
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
 * tests for the methods
 * @author mitchalley, Abhiram Koganti
 *
 */
public class DictionaryTests {

	/**
	 * 
	 * @return - whether the test passes
	 */
	public static boolean testHeightOfTree(){
		boolean passed = false;
		
		DictionaryBST s = new DictionaryBST();
		s.addWord("Blueberry", "Round");
		s.addWord("Cherry", "Round");
		s.addWord("Dane", "Dog");
		s.addWord("Aane", "Dog");
		s.addWord("Acane", "Dog");
		s.addWord("Zane", "Dog");
		s.addWord("Fane", "Dog");
		
		if (s.height() == 5)
			passed = true;
		
		return passed;
	}
	
	/**
	 * 
	 * @return - whether the test passes
	 */
	public static boolean testAddAndLookup() {
		boolean passed = false;
		
		DictionaryBST s = new DictionaryBST();
		s.addWord("famous", "Round");
		s.addWord("exceptinoal", "Round");
		s.addWord("Gigantic", "Dog");
		s.addWord("airplane", "something");
		s.addWord("sofa", "no idea");
		s.addWord("main", "person");
		s.addWord("allocate", "new tooth");
		s.addWord("success", "new tooth");
		s.addWord("a", "letter");
		
		if (s.lookup("success").equals("new tooth"))
			passed = true;
		
		return passed;
	}
	
	/**
	 * 
	 * @return - whether the test passes
	 */
	public static boolean testSize(){
		boolean passed = false;
		
		DictionaryBST s = new DictionaryBST();
		s.addWord("Blueberry", "Round");
		s.addWord("Cherry", "Round");
		s.addWord("Dane", "Dog");
		s.addWord("Aane", "something");
		s.addWord("Acane", "no idea");
		s.addWord("Zane", "person");
		s.addWord("Fane", "new tooth");
		s.addWord("Hane", "name");
		
		passed = s.size() == 8;
		
		return passed;		
	}
	
	public static boolean testIsEmpty() {
		boolean passed = false;
		
		DictionaryBST s = new DictionaryBST();
		if (s.isEmpty())
			passed = true;

		return passed;
		
		
	}
	
	public static boolean testGetAllWords() {
		boolean passed = false;
		
		DictionaryBST s = new DictionaryBST();

		if (s.getAllWords() instanceof ArrayList)
			passed = true;
		
		return passed;
	}
	
	//FIVE METHODS
	public static void main(String[] args) {
		System.out.println("testHeightOfTree(): " + testHeightOfTree());
		System.out.println("testAddAndLookup(): " + testAddAndLookup());
		System.out.println("testSize(): " + testSize());
		System.out.println("testIsEmpty() " + testIsEmpty());
		System.out.println("testGetAllWords(): " + testGetAllWords());
	}

}
