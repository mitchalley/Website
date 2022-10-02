
//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           PO4 ExceptionalLibrary
// Files:           ExceptionalLibrary.java
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
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;

/**
 * This class models a simple book library. The main method of this class
 * implements the management system for this library.
 *
 */
public class ExceptionalLibrary {
	// instance fields
	private String address; // Street address of this library
	private Librarian librarian; // this library's librarian. This library must have only ONE
									// librarian
	private ArrayList<Book> books; // list of the books in this library
	private ArrayList<Subscriber> subscribers; // list of this library's subscribers

	/**
	 * Creates a new Library and initializes all its instance fields. Initially both
	 * books and subscribers lists are empty.
	 * 
	 * @param address           Address of this Library
	 * @param librarianUsername username of the librarian of this book library
	 * @param librarianPassword password of the librarian of this book library
	 */
	public ExceptionalLibrary(String address, String librarianUsername, String librarianPassword) {
		this.address = address;
		this.librarian = new Librarian(librarianUsername, librarianPassword);// creates a new Librarian
		// creates the librarian
		// of this library
		books = new ArrayList<Book>(); // creates an empty arraylist of books
		subscribers = new ArrayList<Subscriber>(); // creates an empty arraylist of subscribers
	}

	/**
	 * Returns the librarian of this library
	 * 
	 * @return the librarian
	 */
	public Librarian getLibrarian() {
		return librarian;
	}

	/**
	 * Returns the address of this library
	 * 
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Returns a Book given a book identifier if found, and null otherwise. If the
	 * book is not found, this method displays the following message: "Error: this
	 * book identifier didn't match any of our books identifiers."
	 * 
	 * @param bookId identifier of the book to find
	 * @return reference to the Book if found and null otherwise
	 */
	public Book findBook(int bookId) {
		// traverse the list of books and look for a match with bookId
		for (int i = 0; i < books.size(); i++)
			if (books.get(i).getID() == bookId) // match found
				return books.get(i);
		// book not found: display an error message and return null
		System.out.println("Error: this book identifier didn't match any of our books identifiers.");
		return null;
	}

	/**
	 * Returns the list of books having a given title in this library. The
	 * comparison used by this method is case insensitive
	 * 
	 * @param title title of the book(s) to find
	 * @return ArrayList of the books having a given title in this library (0 or
	 *         more books can be found)
	 */
	public ArrayList<Book> findBookByTitle(String title) {
		ArrayList<Book> foundBooks = new ArrayList<>(); // create an empty ArrayList to store found
														// books
		// traverse the ArrayList books looking for books matching with the provided
		// title
		for (int i = 0; i < books.size(); i++)
			if (books.get(i).getTitle().equalsIgnoreCase(title))
				foundBooks.add(books.get(i));
		return foundBooks; // return found books ArrayList. It may be empty
	}

	/**
	 * Returns the list of books having a given author. The comparison used by this
	 * method is case insensitive
	 * 
	 * @param author author of the book(s) to find
	 * @return ArrayList of the books having a given author (0 or more books can be
	 *         found)
	 */
	public ArrayList<Book> findBookByAuthor(String author) {
		ArrayList<Book> foundBooks = new ArrayList<>();// create an empty ArrayList to store found books
		// traverse the ArrayList books looking for books matching with the provided
		// author
		for (int i = 0; i < books.size(); i++)
			if (books.get(i).getAuthor().equalsIgnoreCase(author))
				foundBooks.add(books.get(i));
		return foundBooks; // return found books ArrayList. It may be empty
	}

	/**
	 * Adds a new book to the library (to the books list). This method displays the
	 * following message: "Book with Title " + title + " is successfully added to
	 * the library."
	 * 
	 * @param title  title of the new book
	 * @param author author of the new book
	 */
	public void addBook(String title, String author) {
		books.add(new Book(title, author));// calls the add method from Book
		System.out.println("Book with Title " + title + " is successfully added to the library.");
	}

	/**
	 * Removes a book given its identifier from the library (from books list)
	 * 
	 * @param bookId identifier of the book to remove
	 * @return a reference to the removed book, and null if the book is not found in
	 *         this library or if it is not available
	 */
	public Book removeBook(int bookId) {
		// find the book
		Book book = findBook(bookId); // findBook displays an error message if book not found
		if (book != null) { // book found --> remove the book
			if (book.isAvailable()) // check if the book is available
				books.remove(book);
			else { // display error message, book not available
				System.out.println("You cannot remove a non available book. This book has been "
						+ "checked out by the subscriber nÂ° " + book.getBorrowerCardBarCode() + " and is not "
						+ "yet returned.");
				return null;
			}
		}
		return book;
	}

	/**
	 * Adds a new subscriber to this library (to subscribers list). This method
	 * displays the following message: "Library card with bar code " + card bar code
	 * + " is successfully issued to the new subscriber " + name + "."
	 * 
	 * @param name        name of the new subscriber
	 * @param pin         4-digit personal identifier number of the new subscriber
	 * @param address     address of the new subscriber
	 * @param phoneNumber phone number of the new subscriber
	 * @throws InstantiationException if a new subscriber cannot be created
	 */
	public void addSubscriber(String name, int pin, String address, String phoneNumber) throws InstantiationException {
		// create a new subscriber
		Subscriber newSubscriber = new Subscriber(name, pin, address, phoneNumber);
		// may throw
		// an InstantiationException
		subscribers.add(newSubscriber); // add new subscriber
		System.out.println("Library card with bar code " + newSubscriber.getCARD_BAR_CODE()
				+ " is successfully issued to the new subscriber " + name + ".");
	}

	/**
	 * Finds a subscriber given its cardBarCode. This method displayed the following
	 * message: "Error: this card bar code didn't match any of our records." and
	 * returns null if the provided cardBarCode did not match with any of the
	 * subscribers' card bar codes
	 * 
	 * @param cardBarCode of the subscriber to find
	 * @return a reference to the subscriber if found, otherwise null
	 */
	public Subscriber findSubscriber(int cardBarCode) {
		// traverse the list of subscribers looking for a subscriber having the provided
		// cardBarCode
		for (int i = 0; i < subscribers.size(); i++)
			if (subscribers.get(i).getCARD_BAR_CODE() == cardBarCode)
				return subscribers.get(i);
		System.out.println("Error: this card bar code didn't match any of our records.");
		return null;
	}

	/**
	 * Displays a list of books
	 * 
	 * @param books ArrayList of books
	 */
	public static void displayBooks(ArrayList<Book> books) {
		// if the list books is empty display "No books found"
		if (books.isEmpty())
			System.out.println("No books found.");
		// books list not empty
		// Traverse the list of books and display book id, title, author, and
		// availability of each book
		for (int i = 0; i < books.size(); i++) {
			System.out.print("<Book ID>: " + books.get(i).getID() + " ");
			System.out.print("<Title>: " + books.get(i).getTitle() + " ");
			System.out.print("<Author>: " + books.get(i).getAuthor() + " ");
			System.out.println("<Is Available>: " + books.get(i).isAvailable());
		}
	}

	/**
	 * Checks if an array of command arguments has the correct length with respect
	 * to a provided count
	 * 
	 * @param commands      an array of Strings that stores the arguments extracted
	 *                      from a user command line
	 * @param validArgCount valid count of arguments of the provided commands
	 * @throws ParseException if commands length is different from validArgCount
	 *                        with default errorOffset equals to zero
	 */
	public void checkCommandArgumentsCount(String[] commands, int validArgCount) throws ParseException {
		if (commands.length != validArgCount)
			throw new ParseException(this.getSyntaxErrorMsg(), 0);
	}

	/**
	 * Parses the String argument as a phone number
	 * 
	 * @param s           String that represents a phone number
	 * @param errorOffset errorOffset for the ParseException if thrown
	 * @throws ParseException if the String argument cannot be parsed as a phone
	 *                        number
	 */
	public void parsePhoneNumber(String s, int errorOffset) throws ParseException {

		try {
			Long.parseLong(s); // parse the String argument s as a phone number (as long number)
		} catch (NumberFormatException e) { // syntax error
			throw new ParseException("Error: The phone number MUST be a NUMBER.\n", errorOffset); // throws an error if
																									// invalid
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to add a new book
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to add a new book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunLibrarianAddBookCommand(String[] commands) throws ParseException {

		checkCommandArgumentsCount(commands, 3);
		// commands[1]: book title, commands[2]: book author
		// check the syntax of the command line with respect to its arguments count
		this.checkCommandArgumentsCount(commands, 3); // checks if commands stores 3 arguments
		// create and add new Book
		this.addBook(commands[1], commands[2]);

	}

	/**
	 * Parses and runs add subscriber command line provided by a librarian
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian for adding a new
	 *                 subscriber
	 * @throws InstantiationException if a new instance of the Subscriber cannot be
	 *                                created
	 * @throws ParseException         if the syntax of the provided command line is
	 *                                incorrect
	 */
	public void parseRunLibrarianAddSubscriberCommand(String[] commands) throws InstantiationException, ParseException {

		// commands[1] name; commands[2] pin; commands[3] address; commands[4] phone
		// number
		checkCommandArgumentsCount(commands, 5);// checks the command count

		try {
			if (findSubscriber(Integer.parseInt(commands[1])) == null)// makes sure that the cardBarCode is valid
				throw new ParseException("error message in parseRunCheckoutheckoutBookCommand",
						parseCardBarCode(commands[1], 1));
			int phone = Integer.parseInt(commands[4]);// makes sure that the phone number only contains numbers
			this.addSubscriber(commands[1], Integer.parseInt(commands[2]), commands[3], commands[4]);// adds the
																										// subscriber

		} catch (InstantiationException e) {
			throw new InstantiationException();// throws the InstantiationException again
		} catch (NumberFormatException e) {
			throw new ParseException(commands[1], 1);// throws a ParseException
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to checkout a book for
	 * a subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to checkout a book
	 *                 for a subscriber
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (card bar code or book identifier)
	 */
	public void parseRunLibrarianCheckoutBookCommand(String[] commands) throws ParseException {
		// Check out a Book for a subscriber [3 <card bar code> <book ID>]

		checkCommandArgumentsCount(commands, 3);// checks the length of the commands to ensure a valid amount of
												// arguments
		try {
			if (findSubscriber(Integer.parseInt(commands[1])) == null)// ensures the cardBarCode is valid
				throw new ParseException("error message in parseRunCheckoutheckoutBookCommand",
						parseCardBarCode(commands[1], 1));
			Book book = this.findBook(Integer.parseInt(commands[2]));// locates the book referenced
			Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));// locates the subscriber
																						// referenced
			if (book != null && subscriber != null) { // book ID correct - book found && subscriber found
				subscriber.checkoutBook(book);
				// helps the subscriber to check out the book
			}
		} catch (NumberFormatException e) {
			throw new ParseException("error message in parseRunCheckoutheckoutBookCommand",
					parseCardBarCode(commands[1], 1));// throws a ParseException
		}

	}

	/**
	 * Parses and runs a command line provided by the librarian to return a book for
	 * a subscriber
	 * 
	 * @param commands commands an array of Strings that stores the arguments
	 *                 extracted from a command line provided by the librarian to
	 *                 return a book for a subscriber
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (card bar code or book identifier)
	 */
	public void parseRunLibrarianReturnBookCommand(String[] commands) throws ParseException {
		// Return a Book for a subscriber [4 <card bar code> <book ID>]
		checkCommandArgumentsCount(commands, 3);// ensures that there is a valid amount of arguments in commands
		try {

			if (findSubscriber(Integer.parseInt(commands[1])) == null)// ensures that the cardBarCode is valid
				throw new ParseException("error message in parseRunCheckoutheckoutBookCommand",
						parseCardBarCode(commands[1], 1));
			Book book = this.findBook(Integer.parseInt(commands[2]));// locates the book
			Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));// locates the subscriber

			if (book != null && (subscriber != null)) { // book ID correct - book found
														// subscriber found - correct card bar code
				subscriber.returnBook(book);
				// helps the subscriber to return a book
			}
		} catch (NumberFormatException e) {
			throw new ParseException("error message in parseRunLibrarianReturnCommand",
					parseCardBarCode(commands[1], 1));// throws a ParseException if anything goes wrong
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to display the
	 * personal information of a subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to display the
	 *                 personal info of a subscriber
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (card bar code)
	 */
	public void parseRunLibrarianDisplayPersonalInfoOfSubscriberCommand(String[] commands) throws ParseException {
		checkCommandArgumentsCount(commands, 2);
		try {

			if (findSubscriber(Integer.parseInt(commands[1])) == null) // validates the cardBarCode
				throw new ParseException("error message in parseRunCheckoutheckoutBookCommand",
						parseCardBarCode(commands[1], 1));
			Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));// located the subscriber
			if (subscriber != null) { // subscriber found
				subscriber.displayPersonalInfo();// shows the subscriber's personal info

			}
		} catch (NumberFormatException e) {
			throw new ParseException("error message in parseRunLibrarianDisplayPersonalInfoOfSubscriberCommand",
					parseCardBarCode(commands[1], 1));
		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to display the books
	 * checked out by a subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to display the books
	 *                 checked out by a subscriber * @throws ParseException if
	 *                 commands include any syntax error or invalid argument (card
	 *                 bar code)
	 */
	public void parseRunLibrarianDisplayBooksCheckedOutBySubscriberCommand(String[] commands) throws ParseException {
		checkCommandArgumentsCount(commands, 2);// ensures that the amount of arguments in commands is valid
		try {
			if (findSubscriber(Integer.parseInt(commands[1])) == null)// ensures that the cardBarCode is valid
				throw new ParseException("error message in parseRunCheckoutheckoutBookCommand",
						parseCardBarCode(commands[1], 1));

			Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));
			if (subscriber != null) { // subscriber found
				subscriber.displayBooksCheckedOut();

			}
		} catch (NumberFormatException e) {
			throw new ParseException("error message in parseRunLibrarianDisplayBooksCheckedOutBySubscriberCommand",
					parseCardBarCode(commands[1], 1));

		}
	}

	/**
	 * Parses and runs a command line provided by a librarian to remove a book
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by the librarian to remove a book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument (arguments count, book id)
	 */
	public void parseRunLibrarianRemoveBookCommand(String[] commands) throws ParseException {
		checkCommandArgumentsCount(commands, 2);// makes sure that the amount of arguments in commands is valid
		try {
			this.removeBook(Integer.parseInt(commands[1]));// removes the book
		} catch (NumberFormatException e) {// remove a book given its id
			throw new ParseException("error message in parseRunLibrarianRemoveBookCommand",
					parseCardBarCode(commands[1], 1));

		}
	}

	/**
	 * Parses and runs a command line provided by a subscriber to checkout a book
	 * 
	 * @param commands   an array of Strings that stores the arguments extracted
	 *                   from a command line provided by a subscriber to checkout a
	 *                   book
	 * @param subscriber reference to the subscriber who is going to check out a
	 *                   book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberCheckoutBookCommand(String[] commands, Subscriber subscriber) throws ParseException {
		checkCommandArgumentsCount(commands, 2);// ensures a valid amount of commands
		try {

			Book book = this.findBook(Integer.parseInt(commands[1]));// locates the book
			if (book != null)
				subscriber.checkoutBook(book);// checks out the book so long as there is not a null reference
		} catch (NumberFormatException e) {
			throw new ParseException("error message in parseRunSubscriberCheckoutBookCommand",
					parseCardBarCode(commands[1], 1));

		}
	}

	/**
	 * Parses and runs a command line provided by a subscriber to return a book
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   return a book
	 * @param subscriber reference to the subscriber who is going to return a book
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberReturnBookCommand(String[] commands, Subscriber subscriber) throws ParseException {
		checkCommandArgumentsCount(commands, 2);

		try {
			// look for the book and return it if it is already checked out by the
			// subscriber
			Book book = this.findBook(Integer.parseInt(commands[1]));
			if (book != null)
				subscriber.returnBook(book);// returns the book for the subscriber
		} catch (NumberFormatException e) {
			throw new ParseException("error message in parseRunSubscriberReturnBookCommand",
					parseCardBarCode(commands[1], 1));

		}

	}

	/**
	 * Parses and runs a command line provided by a subscriber to update his phone
	 * number After updating the phone number of the subscriber, this method
	 * displays the following message: "Phone number successfully updated."
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   update his phone number
	 * @param subscriber reference to the subscriber who is going to update his
	 *                   phone number
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberUpdatePhoneNumberCommand(String[] commands, Subscriber subscriber)
			throws ParseException {
		checkCommandArgumentsCount(commands, 2);// validates the amount of commands
		try {
			Integer.parseInt(commands[1]);// ensures there are no letters in commands[1]
			subscriber.setPhoneNumber(commands[1]); // sets the phone number for the subscriber

			System.out.println("Phone number successfully updated.");
		} catch (NumberFormatException e) {
			throw new ParseException("error message in parseRunSubscriberUpdatePhoneNumberCommand",
					parseCardBarCode(commands[1], 1));
		}

	}

	/**
	 * Parses and runs a command line provided by a subscriber to find a list of
	 * books by title. This method calls findBookByTitle() method and displays the
	 * content of the returned ArrayList of Books if it is not empty. If no books
	 * match the search criteria (findBookByTitle() returned an empty list), this
	 * method displays the following message: "No books match your search."
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   find a list of books by title
	 * @param subscriber reference to the subscriber who is going to search for
	 *                   books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberFindBooksByTitleCommand(String[] commands, Subscriber subscriber)
			throws ParseException {
		// Search a book by title commands[1]: title
		this.checkCommandArgumentsCount(commands, 2);// checks that amount of commands
		ArrayList<Book> bookList = this.findBookByTitle(commands[1]);// locates the book given the title
		if (!bookList.isEmpty())
			displayBooks(bookList);// displays the list of book so long as the list is not empty
		else
			System.out.println("No books match your search.");
	}

	/**
	 * Parses and runs a command line provided by a subscriber to a list of books by
	 * author This method makes call of findBookByAuthor() method and displays the
	 * content of the returned ArrayList of Books if it is not empty. If no books
	 * match the search criteria (findBookByAuthor() returned an empty list), this
	 * method displays the following message: "No books match your search."
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to a
	 *                   list of books by author
	 * @param subscriber reference to the subscriber who is going to search for
	 *                   books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberFindBooksByAuthorCommand(String[] commands, Subscriber subscriber)
			throws ParseException {
		// Search a book by author commands[1]: author
		this.checkCommandArgumentsCount(commands, 2);// validates the amount of arguments
		ArrayList<Book> bookList = this.findBookByAuthor(commands[1]);// locates books, given the author
		if (!bookList.isEmpty())
			displayBooks(bookList);// displays the list of books
		else
			System.out.println("No books match your search.");
	}

	/**
	 * Parses and runs a command line provided by a subscriber to update its home
	 * address This method displays "Address successfully updated." after the
	 * subscriber's address is updated
	 * 
	 * @param commands   commands an array of Strings that stores the arguments
	 *                   extracted from a command line provided by a subscriber to
	 *                   update his address
	 * @param subscriber reference to the subscriber who is going to update his
	 *                   address
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunSubscriberUpdateAddressCommand(String[] commands, Subscriber subscriber) throws ParseException {
		// Update address commands[1]: address
		this.checkCommandArgumentsCount(commands, 2);// validates the amount of commands
		subscriber.setAddress(commands[1]);// sets the address for the given subscriber

		System.out.println("Address successfully updated.");
	}

	/**
	 * Parses and runs a command line provided by a librarian to save the titles and
	 * authors of the current list of books
	 * 
	 * @param commands commands an array of Strings that stores the arguments
	 *                 extracted from a command line provided by a librarian to save
	 *                 the current list of books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunLibrarianSaveBooksCommand(String[] commands) throws ParseException {

		if (commands.length != 2)// checks if the length is exactly 2
			throw new ParseException("Syntax error", 1);

		try {
			File file = new File(commands[1]);// creates the new file
			PrintWriter p = new PrintWriter(file);// printer to the file
			for (int i = 0; i < books.size(); i++) {// iterates through books
				p.println(books.get(i).getTitle() + ":" + books.get(i).getAuthor());// prints <title:author>
				p.flush();// flushes the printWriter to the file
			}

			p.close();// closes the printWriter
		} catch (FileNotFoundException e) {
			System.out.println("error, no file");
		} catch (IOException e) {
			System.out.println("error, IOException");
		}

	}

	/**
	 * Parses and runs a command line provided by a librarian to load a list of
	 * books (title:author) from a file given its filename and new books with these
	 * pairs of title/author to the current list of books
	 * 
	 * @param commands commands an array of Strings that stores the arguments
	 *                 extracted from a command line provided by a librarian to load
	 *                 a list of books from a file and add it to the current list of
	 *                 books
	 * @throws ParseException if commands include any syntax error or invalid
	 *                        argument
	 */
	public void parseRunLibrarianLoadBooksCommand(String[] commands) throws ParseException {

		if (commands.length != 2)// ensures that the length of commands is exactly 2
			throw new ParseException("error, commands is supposed to be a length of 2", 1);

		try {
			File file = new File(commands[1]);// creates the new file
			Scanner sc = new Scanner(file);// scans the file

			while (sc.hasNextLine()) {// scans the whole file
				try {

					String com = sc.nextLine();// gets the whole line
					String command[] = com.split(":");// splits the code into the command array by semicolon
					if (command.length == 2) {// ensures the length of command is 2
						command[1] = command[1].trim();// get rid of whitespace
						command[0] = command[0].trim();
						addBook(command[0], command[1]);// adds the book to books
					}
				} catch (NullPointerException e) {
					System.out.println("error, nullPointerException");
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("error");
				}
			}
			sc.close();// closes the scanner
		} catch (FileNotFoundException e) {
			System.out.println("error, file not found");
		} catch (IOException e) {
			System.out.println("error, IOException");
		}

	}

	/**
	 * Parses and runs a command line provided by a user to login to the application
	 * as librarian
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by a user to login as a librarian
	 * @param scanner  a reference to a Scanner object to read the librarian command
	 *                 lines after a successful login into the application
	 * @throws ParseException thrown if the command line provided by the user to
	 *                        login as a librarian is invalid or contains a syntax
	 *                        error
	 */
	public void parseRunLoginAsLibrarian(String[] commands, Scanner scanner) throws ParseException {

		checkCommandArgumentsCount(commands, 2);// validates the amount of commands
		if (this.librarian.checkPassword(commands[1])) { // check if the password is correct
			// read and process librarian commands
			readProcessLibrarianCommand(scanner);
		} else { // wrong password
			System.out.println("Error: Password incorrect!");
		}
	}

	/**
	 * Parses and runs a command line provided by a user to login to the application
	 * as subscriber
	 * 
	 * @param commands an array of Strings that stores the arguments extracted from
	 *                 a command line provided by a user to login as a subscriber
	 * @param scanner  a reference to a Scanner object to read the subscriber
	 *                 command lines after a successful login into the application
	 * @throws ParseException thrown if the command line provided by the user to
	 *                        login as a subscriber is invalid or contains a syntax
	 *                        error
	 */
	public void parseRunLoginAsSubscriber(String[] commands, Scanner scanner) throws ParseException {
		checkCommandArgumentsCount(commands, 3);// validates the amount of commands

		try {
			Subscriber subscriber = this.findSubscriber(Integer.parseInt(commands[1]));// locates the subscriber
			if (subscriber != null) {
				if (subscriber.getPin() == Integer.parseInt(commands[2])) // correct PIN
					// read and process subscriber commands
					readProcessSubscriberCommand(subscriber, scanner);
				else
					System.out.println("Error: Incorrect PIN.");
			}
		} catch (NumberFormatException e) {
			throw new ParseException("error in parseRunLoginAsSubscriber", parseCardBarCode(commands[1], 1));
		}

	}

	/**
	 * Reads and processes the user commands with respect to the menu of this
	 * application
	 * 
	 * @param scanner Scanner object used to read the user command lines
	 */
	public void readProcessUserCommand(Scanner scanner) {
		String promptCommandLine = "ENTER COMMAND: ";
		displayMainMenu(); // display the library management system main menu
		System.out.print(promptCommandLine);
		String command = scanner.nextLine(); // read user command line
		String[] commands = command.trim().split(" "); // split user command
		while (!(commands[0].equals("3") && commands.length == 1)) { // 3: Exit the application
			try {
				switch (commands[0]) {
				case "1": // login as librarian commands[1]: password
					this.parseRunLoginAsLibrarian(commands, scanner);
					break;
				case "2": // login as subscriber commands[1]: card bar code commands[2]: 4-digit PIN
					this.parseRunLoginAsSubscriber(commands, scanner);
					break;
				default:
					System.out.println(this.getSyntaxErrorMsg());
				}
			} catch (ParseException e) { // This catch block catches only ParseException exceptions thrown
				// if the syntax of the user command line from the main menu is incorrect
				// All exceptions thrown from readProcessLibrarianCommand() or
				// readProcessSubscriberCommand()
				// methods must be handled using try catch blocks within those methods.
				String error;
				if (e.getErrorOffset() == 0) {
					error = " Arguments count is incorrect.";
				} else
					error = " Argument number " + e.getErrorOffset() + " within your command line is invalid.";
				System.out.println(e.getMessage() + error); // display the exception's error message
			}
			// read and split next user command line
			displayMainMenu(); // display the library management system main menu
			System.out.print(promptCommandLine);
			command = scanner.nextLine(); // read user command line
			commands = command.trim().split(" "); // split user command line
		}
	}

	/**
	 * Reads and processes the librarian commands according to the librarian menu
	 * 
	 * @param scanner Scanner object used to read the librarian command lines
	 */
	public void readProcessLibrarianCommand(Scanner scanner) {

		String promptCommandLine = "ENTER COMMAND: ";
		ExceptionalLibrary.displayLibrarianMenu(); // display the library management system main menu
		System.out.print(promptCommandLine);
		String command = scanner.nextLine(); // read user command line
		String[] commands = command.trim().split(" "); // split user command

		while (!(commands[0].toUpperCase().equals("9") && commands.length == 1)) { // "9": Exit the
																					// application
			try {
				switch (commands[0].toUpperCase()) {
				case "1": // add a new book commands[1] title; commands[2] author
					parseRunLibrarianAddBookCommand(commands);
					break;
				case "2": // add a new subscriber: commands[1] name; commands[2] pin; commands[3] address;
							// commands[4] phone number

					parseRunLibrarianAddSubscriberCommand(commands);
					break;
				case "3": // Check out a Book for a subscriber [3 <card bar code> <book ID>]
					parseRunLibrarianCheckoutBookCommand(commands);
					break;
				case "4": // Return a Book for a subscriber [4 <card bar code> <book ID>]
					parseRunLibrarianReturnBookCommand(commands);
					break;
				case "5": // Display Personal Info of a Subscriber [5 <card bar code>]
					parseRunLibrarianDisplayPersonalInfoOfSubscriberCommand(commands);
					break;
				case "6": // [6 <card bar code>] Display Books Checked out by a Subscriber");

					parseRunLibrarianDisplayBooksCheckedOutBySubscriberCommand(commands);
					break;
				case "7": // [7] Display Books List
					ExceptionalLibrary.displayBooks(this.books);
					break;
				case "8": // [8 <book ID>] Remove a Book
					parseRunLibrarianRemoveBookCommand(commands);
					break;
				case "L": // [L <filename>] Load list of Books from a data file named filename
					parseRunLibrarianLoadBooksCommand(commands);
					break;
				case "S": // [S <filename>] Save list of Books to a data file named filename
					parseRunLibrarianSaveBooksCommand(commands);
					break;
				default:
					System.out.println(this.getSyntaxErrorMsg()); // Syntax Error

				}

			} catch (ParseException e) {
				System.out.println("Syntax Error: Please enter a valid command!");
			} catch (InstantiationException e) {
				System.out.println("Syntax Error: Please enter a valid command!");
			}

			displayLibrarianMenu(); // display the library management system main menu
			System.out.print(promptCommandLine);
			command = scanner.nextLine(); // read user command line
			commands = command.trim().split(" "); // split user command line
		}
	}

	/**
	 * Reads and processes the subscriber commands according to the subscriber menu
	 * 
	 * @param subscriber Current logged in subscriber
	 * @param scanner    Scanner object used to read the librarian command lines
	 */
	public void readProcessSubscriberCommand(Subscriber subscriber, Scanner scanner) {
		// This method should catch any ParseException that may be thrown
		// from any called method to parse and run the librarian command line
		String promptCommandLine = "ENTER COMMAND: ";
		ExceptionalLibrary.displaySubscriberMenu(); // display the library management system main menu
		System.out.print(promptCommandLine);
		String command = scanner.nextLine(); // read user command line
		String[] commands = command.trim().split(" "); // split user command
		while (!(commands[0].toUpperCase().equals("9") && commands.length == 1)) { // "9": Exit the
																					// Subscriber space

			try {
				switch (commands[0]) {
				case "1": // check out a book commands[1]: book id
					parseRunSubscriberCheckoutBookCommand(commands, subscriber);
					break;
				case "2": // return a book commands[1]: book id
					parseRunSubscriberReturnBookCommand(commands, subscriber);
					break;
				case "3": // Search a book by title commands[1]: title
					parseRunSubscriberFindBooksByTitleCommand(commands, subscriber);
					break;
				case "4": // Search a book by author commands[1]: author
					parseRunSubscriberFindBooksByAuthorCommand(commands, subscriber);

					break;
				case "5": // print lists of books checked out
					subscriber.displayBooksCheckedOut();
					;
					break;
				case "6": // print history of books returned
					subscriber.displayHistoryBooksReturned();
					;
					break;
				case "7": // Update address commands[1]: address
					parseRunSubscriberUpdateAddressCommand(commands, subscriber);
					break;
				case "8": // Update phone number commands[1]: phone number
					parseRunSubscriberUpdatePhoneNumberCommand(commands, subscriber);
					break;

				}

			} catch (ParseException e) {
				System.out.println("error in readProcessSubscriberCommand");
			}
			// read and split next user command line
			displaySubscriberMenu(); // display the library management system main menu
			System.out.print(promptCommandLine);
			command = scanner.nextLine(); // read user command line
			commands = command.trim().split(" "); // split user command line
		}
	}

	/**
	 * helper method that returns a general - Syntax error message
	 */
	private String getSyntaxErrorMsg() {
		return "Syntax Error: Please enter a valid command!\n";
	}

	/**
	 * Displays the main menu for this book library application
	 */
	private static void displayMainMenu() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("     Welcome to our Book Library Management System");
		System.out.println("--------------------------------------------------------");
		System.out.println("Enter one of the following options:");
		System.out.println("[1 <password>] Login as a librarian");
		System.out.println("[2 <card bar code> <4-digits pin>] Login as a Subscriber");
		System.out.println("[3] Exit"); // Exit the application
		System.out.println("--------------------------------------------------------");
	}

	/**
	 * Displays the menu for a Subscriber
	 */
	private static void displaySubscriberMenu() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("    Welcome to Subscriber's Space");
		System.out.println("--------------------------------------------------------");
		System.out.println("Enter one of the following options:");
		System.out.println("[1 <book ID>] Check out a book");
		System.out.println("[2 <book ID>] Return a book");
		System.out.println("[3 <title>] Search a Book by title");
		System.out.println("[4 <author>] Search a Book by author");
		System.out.println("[5] Print list of books checked out");
		System.out.println("[6] Print history of returned books");
		System.out.println("[7 <address>] Update address");
		System.out.println("[8 <phone number>] Update phone number");
		System.out.println("[9] Logout");
		System.out.println("--------------------------------------------------------");
	}

	/**
	 * Displays the menu for the Librarian
	 */
	private static void displayLibrarianMenu() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("    Welcome to Librarian's Space");
		System.out.println("--------------------------------------------------------");
		System.out.println("Enter one of the following options:");
		System.out.println("[1 <title> <author>] Add new Book");
		System.out.println("[2 <name> <pin> <address> <phone number>] Add new subscriber");
		System.out.println("[3 <card bar code> <book ID>] Check out a Book for a subscriber");
		System.out.println("[4 <card bar code> <book ID>] Return a Book for a subscriber");
		System.out.println("[5 <card bar code>] Display Personal Info of a Subscriber");
		System.out.println("[6 <card bar code>] Display Books Checked out by a Subscriber");
		System.out.println("[7] Display All Books");
		System.out.println("[8 <book ID>] Remove a Book");
		System.out.println("[L <filename.data>] Load list of Books from filename.data");
		System.out.println("[S <filename.data>] Save list of Books to filename.data");
		System.out.println("[9] Logout");
		System.out.println("--------------------------------------------------------");
	}

	/**
	 * Display the Application GoodBye and logout message.
	 */
	private static void displayGoodByeLogoutMessage() {
		System.out.println("\n--------------------------------------------------------");
		System.out.println("       Thanks for Using our Book Library App!!!!");
		System.out.println("--------------------------------------------------------");
	}

	// ensures that the pin is a valid pin
	public int parsePinCode(String s, int errorOffset) throws ParseException {
		try {
			int pin = Integer.parseInt(s);// makes sure that it is a number
			if (1000 <= pin && pin <= 9999) {
				return pin;// returns the pin if it is valid
			}
			throw new ParseException("error message", errorOffset);
		} catch (NumberFormatException e) {
			throw new ParseException("error message", errorOffset);
		}

	}

	// parses the card bar code is valid
	public int parseCardBarCode(String s, int errorOffset) throws ParseException {
		try {
			if (Subscriber.checkCardBarCode(Integer.parseInt(s))) {// checks the card bar code
				return Integer.parseInt(s);// returns the valud if it is valid
			}
			throw new ParseException("error message", errorOffset);
		}
		// check
		catch (NumberFormatException e) {
			throw new ParseException("error message", errorOffset);
		}
	}

	public int parseBookId(String s, int errorOffset) throws ParseException {
		try {
			return Integer.parseInt(s);// returns the int value, otherwise throws exception
		} catch (NumberFormatException e) {
			throw new ParseException("error message", errorOffset);
		}

	}

	/**
	 * Main method that represents the driver for this application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); // create a scanner object to read user inputs
		// create a new library object
		ExceptionalLibrary madisonLibrary = new ExceptionalLibrary("Madison, WI", "april", "abc");
		// read and process user command lines
		madisonLibrary.readProcessUserCommand(scanner);
		displayGoodByeLogoutMessage(); // display good bye message
		scanner.close();// close this scanner
	}

}