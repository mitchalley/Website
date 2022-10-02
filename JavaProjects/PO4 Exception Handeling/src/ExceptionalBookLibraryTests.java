//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           PO4 ExceptionalLibrary
// Files:           ExceptionalBookLibraryTests.java
// Course:          CS 300
//
// Author:          Mitch Alley
// Email:           mgalley@wisc.edu
// Lecturer's Name: Gary Dahl
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;

public class ExceptionalBookLibraryTests {

	public static void main(String[] args) {
		System.out.println("testLibraryParseCardBarCode(): " + testLibraryParseCardBarCode());
		System.out.println("testLibraryParseRunLibrarianCheckoutBookCommand(): "
				+ testLibraryParseRunLibrarianCheckoutBookCommand());
		System.out.println("testLibraryParseRunSubscriberReturnBookCommand(): " + testLibraryParseRunSubscriberReturnBookCommand());
		System.out.println("testParseRunLibrarianCheckoutBookCommand(): " + testParseRunLibrarianCheckoutBookCommand());
		System.out.println("testParseRunSubscriberUpdateAddressCommand(): " + testParseRunSubscriberUpdateAddressCommand());

	}

	public static boolean testLibraryParseCardBarCode() {
		boolean passed = false;

		ExceptionalLibrary l = new ExceptionalLibrary("", "", "");
		try {
			l.parseCardBarCode("", 5);
		} catch (ParseException e) {
			passed = true;
		}
		return passed;
	}

	public static boolean testLibraryParseRunLibrarianCheckoutBookCommand() {
		boolean passed = false;
		String[] commands = { "3", "abc", "4" };

		try {
			ExceptionalLibrary l = new ExceptionalLibrary("", "", "");
			Subscriber s = new Subscriber("", 1234, "", "123456789");
			l.parseRunLibrarianCheckoutBookCommand(commands);
		} catch (InstantiationException e) {
			passed = false;
		} catch (ParseException e) {
			passed = true;
		}

		return passed;
	}

	public static boolean testLibraryParseRunSubscriberReturnBookCommand() {
		boolean passed = false;
		String[] commands = {"", "abc"};

		ExceptionalLibrary l = new ExceptionalLibrary("", "", "");
		try {
			Subscriber s = new Subscriber("", 1234, "", "123456789");
			l.parseRunSubscriberReturnBookCommand(commands, s);
		} catch (InstantiationException e) {
			
		} catch (ParseException e) {
			passed = true;
		}

		return passed;
	}
	
	public static boolean testParseRunLibrarianCheckoutBookCommand() {
		boolean passed = false;
		
		String[] commands = {"3", "123", "4"};
		try {
			ExceptionalLibrary l = new ExceptionalLibrary("", "", "");
			l.parseRunLibrarianCheckoutBookCommand(commands);
		} catch(ParseException e) {
			passed = true;
		}
		return passed;
	}
	
	public static boolean testParseRunSubscriberUpdateAddressCommand() {
		boolean passed = false;
		String[] commands = {""};
		try {
			ExceptionalLibrary l = new ExceptionalLibrary("", "", "");
			Subscriber s = new Subscriber("",1,"","");
			l.parseRunSubscriberUpdateAddressCommand(commands, s);
		} catch (InstantiationException e) {
			
			
		}catch (ParseException e) {
			passed = true;
		}
		
				
		
		return passed;
	}

}
