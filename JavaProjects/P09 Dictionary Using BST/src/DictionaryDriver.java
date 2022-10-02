
// Title:           PO9 Dictionary Using BST
// Files:           DictionaryDriver.java
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
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * driver for this program
 * 
 * @author mitchalley, Abhiram Koganti
 *
 */
public class DictionaryDriver {

	/**
	 * menu that is printed
	 */
	private static void printMenu() {
		String userCommands = "\n=========================== Dictionary ============================\n"
				+ "Enter one of the following options:\n"
				+ "[A <word> <meaning>] to add a new word and its definition in the dictionary\n"
				+ "[L <word>] to search a word in the dictionary and display its definition\n"
				+ "[G] to print all the words in the dictionary in sorted order\n"
				+ "[S] to get the count of all words in the dictionary\n"
				+ "[H] to get the height of this dictionary implemented as a binary search tree\n"
				+ "[Q] to quit the program\n"
				+ "======================================================================\n";
		System.out.println(userCommands);

	}

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String input;
		DictionaryBST it = new DictionaryBST();
		do {
			printMenu();
			System.out.print("Please enter your command: ");
			input = sc.nextLine().trim();

			if (input.substring(0, 1).equalsIgnoreCase(("a"))) {
				String[] in = input.split(" ");// splits the input
				if (!(in.length >= 3))
					System.out.println("WARNING: Syntax Error for [A <word> <meaning>] command line.");
				else
					it.addWord(in[1], input.substring(in[1].length() + 3, input.length()));
			} // adds the word

			else if (input.substring(0, 1).equalsIgnoreCase("l")) {
				String[] in = input.split(" ");
				try {
					System.out.println(in[1] + ": " + it.lookup(in[1]));// obtains the word
				} catch (NoSuchElementException e) {
					System.out.println("No definition found for the word " + in[1]);// if the word is not there
				}

			}

			else if (input.substring(0, 1).equalsIgnoreCase("g")) {// prints out all of the words
				ArrayList<String> in = it.getAllWords();
				if (in.isEmpty())
					System.out.println("Dictionary is empty.");
				else {
					for (int i = 0; i < in.size(); i++) {
						System.out.print(in.get(i));
						if (i != in.size() - 1)
							System.out.print(", ");
					}
					System.out.println();
				}
			}

			else if (input.substring(0, 1).equalsIgnoreCase("s")) {// obtains the size of the tree
				System.out.println(it.size());
			} else if (input.substring(0, 1).equalsIgnoreCase("h")) {// obtains the height of the tree
				System.out.println(it.height());
			}
		} while (!input.substring(0, 1).equalsIgnoreCase("q"));// quits the program
		sc.close();
		System.out.print("============================== END ===================================");
	}
}
