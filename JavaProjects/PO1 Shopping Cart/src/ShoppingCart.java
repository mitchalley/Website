//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           PO1 Shopping Cart
// Files:           ShoppingCart.java
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



import java.util.Scanner;
import java.text.DecimalFormat;

public class ShoppingCart {

// Define final parameters
	private static final int CART_CAPACITY = 20; // shopping cart max capacity
	private static final double TAX_RATE = 0.05; // sales tax

// a perfect-size two-dimensional array that stores the available items in the market
// MARKET_ITEMS[i][0] refers to a String that represents the description of the item
//                   identified by index i
// MARKET_ITEMS[i][1] refers to a String that represents the unit price of the item
//                   identified by index i in dollars.
	public static final String[][] MARKET_ITEMS = new String[][] { { "Apple", "$1.59" }, { "Avocado", "$0.59" },
			{ "Banana", "$0.49" }, { "Beef", "$3.79" }, { "Blueberry", "$6.89" }, { "Broccoli", "$1.79" },
			{ "Butter", "$4.59" }, { "Carrot", "$1.19" }, { "Cereal", "$3.69" }, { "Cheese", "$3.49" },
			{ "Chicken", "$5.09" }, { "Chocolate", "$3.19" }, { "Cookie", "$9.5" }, { "Cucumber", "$0.79" },
			{ "Eggs", "$3.09" }, { "Grape", "$2.29" }, { "Ice Cream", "$5.39" }, { "Milk", "$2.09" },
			{ "Mushroom", "$1.79" }, { "Onion", "$0.79" }, { "Pepper", "$1.99" }, { "Pizza", "$11.5" },
			{ "Potato", "$0.69" }, { "Spinach", "$3.09" }, { "Tomato", "$1.79" } };

	private static String getItemDescription(int index) {
		return MARKET_ITEMS[index][1];
	}

	//gets the index of the item in the cart
	private static int indexOf(String item, String[] cart, int count) {

		int indexOfItem = -1;

		//iterates through the cart to find the item and leaves the loop once the item is found
		for (int index = 0; index < count; index++) {
			if (cart[index].equals(item)) {
				indexOfItem = index;
				break;
			}
		}

		//return the index of the item in the cart
		return indexOfItem;

	}

	//calculates the total
	private static double getTotal(double subTotal, double tax) {
		//adds the sub total and tax
		double total = subTotal + tax;
		
		//used to round two decimal places
		DecimalFormat df = new DecimalFormat("###.##");
		
		//returns total rounded two decimal places
		return Double.parseDouble(df.format(total));
	}

	//calculates the tax
	private static double getTax(double rate, double total) {
		//the value that will be returned
		double returnVal = total * rate;
		
		//used to round two decimal places
		DecimalFormat df = new DecimalFormat("###.##");

		//returns tax rounded two decimal places
		return Double.parseDouble(df.format(returnVal));
	}

	/**
	 * adds the item with the given its identifier index at the end of the cart
	 * 
	 * @param index of the item within the marketItems array
	 * @param cart  shopping cart
	 * @return the number of items present in the cart after the item with
	 *         identifier index is added
	 */
	public static int add(int index, String[] cart, int count) {

		// ensures that the cart is not zero
		if (count == (cart.length)) {
			System.out.println("WARNING: The cart is full. You cannot add any new item.");
			return 0;
		}

		// adds the index from MARKET_ITEMS to the cart
		cart[count] = MARKET_ITEMS[index][0];

		return count + 1;
	}

	/**
	 * Returns how many occurrences of the item with index itemIndex are present in
	 * the shopping cart
	 * 
	 * @param itemIndex identifier of the item to count its occurrences in the cart
	 * @param cart      shopping cart
	 * @param count     number of items present within the cart
	 * @return the number of occurrences of item in the cart
	 */
	public static int occurrencesOf(int itemIndex, String[] cart, int count) {

		//if nothing is in the cart, the method cannot do anything and returns 0
		if (count == 0) {
			return 0;
		}

		// is the name of the item the user wishes to check
		String item = MARKET_ITEMS[itemIndex][0];
		// will count the amount of times that itemIndex is in cart
		int counter = 0;

		// increments counter for every time item is found in cart
		for (int index = 0; index < cart.length; index++) {
			if (cart[index] != null && cart[index].equals(item)) {
				counter++;

			}
		}

		return counter;
	}

	// Removes the first (only one) occurrence of itemToRemove if found and returns
	// the number of
	// items in the cart after remove operation is completed either successfully or
	// not
	public static int remove(String itemToRemove, String[] cart, int count) {

		//if there are no items in the cart, the method prints a warning and returns 0
		if (count == 0) {
			System.out.println("WARNING: " + itemToRemove + " not found in the shopping cart.");
			return 0;
		}

		//gets the index of the first occurrence of itemToRemove within the cart
		int indexOfItem = indexOf(itemToRemove, cart, count);

		// prints a warning if the item is not in the cart
		if (indexOfItem == -1) {
			System.out.println("WARNING: " + itemToRemove + " not found in the shopping cart.");
			return -1;
		}

		//replaces the last item with the index of itemToRemove
		cart[indexOfItem] = cart[count - 1];
		cart[count - 1] = null;
		count--;

		//return the amount of items in the cart
		return count;
	}

	// returns the total value (cost) of the cart without tax in $ (double)
	public static double getSubTotalPrice(String[] cart, int count) {
		double total = 0.0;
		//used to format the final double that is returned
		DecimalFormat df = new DecimalFormat("###.##");
		

		//checks the price of each item and adds them to total
		for (int index = 0; index < count; index++) {
			for (int mktIndex = 0; mktIndex < MARKET_ITEMS.length; mktIndex++) {
				if (cart[index].equals(MARKET_ITEMS[mktIndex][0])) {
					String temp = MARKET_ITEMS[mktIndex][1].substring(1, MARKET_ITEMS[mktIndex][1].length());
					total += Double.parseDouble(temp);
				}
			}
		}

		return Double.parseDouble(df.format(total));
	}

	// prints the Market Catalog (item identifiers, description, and unit prices)
	public static void printMarketCatalog() {
		

		//prints the market catalog
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++\n" + "Item id 	Description 	 Price\n"
				+ "+++++++++++++++++++++++++++++++++++++++++++++\n" + "0		Apple    	 $1.59\n"
				+ "1		Avocado    	 $0.59\n" + "2		Banana    	 $0.49\n" + "3		Beef    	 $3.79\n"
				+ "4		Blueberry    	 $6.89\n" + "5		Broccoli    	 $1.79\n"
				+ "6		Butter    	 $4.59\n" + "7		Carrot    	 $1.19\n" + "8		Cereal    	 $3.69\n"
				+ "9		Cheese    	 $3.49\n" + "10		Chicken    	 $5.09\n" + "11		Chocolate    	 $3.19\n"
				+ "12		Cookie    	 $9.5\n" + "13		Cucumber    	 $0.79\n" + "14		Eggs    	 $3.09\n"
				+ "15		Grape    	 $2.29\n" + "16		Ice Cream    	 $5.39\n" + "17		Milk    	 $2.09\n"
				+ "18		Mushroom    	 $1.79\n" + "19		Onion    	 $0.79\n" + "20		Pepper    	 $1.99\n"
				+ "21		Pizza    	 $11.5\n" + "22		Potato    	 $0.69\n" + "23		Spinach    	 $3.09\n"
				+ "24		Tomato    	 $1.79\n" + "+++++++++++++++++++++++++++++++++++++++++++++");

	}

	// Displays the cart content (items separated by commas)
	public static void displayCartContent(String[] cart, int count) {
		// the final string that will present the items in cart
		String returnVal = "";

		// adds the values of cart into returnVal, separated by ", "
		for (int i = 0; i < count; i++) {
			returnVal += cart[i] + ", ";
		}

		// final result
		System.out.println("Cart Content: " + returnVal);

	}

	public static void main(String[] args) {
		System.out.println("=============   Welcome to the Shopping Cart App   =============\n");

		//used to check the user input
		Scanner scnr = new Scanner(System.in);
		//oversize array for the cart
		String[] cart = new String[100];
		//string used to check what the user wants to do
		String command = "";
		//amount of item in the array
		int count = 0;
		//tax rate
		double tax = 0.05;
		//used to round two decimal places
		DecimalFormat df = new DecimalFormat("###.##");

		do {
			System.out.print("\nCOMMAND MENU:\n [P] print the market catalog\n"
					+ " [A <index>] add one occurrence of an item to the cart given its identifier\n"
					+ " [C] checkout\n" + " [D] display the cart content\n"
					+ " [O <index>] number of occurrences of an item in the cart given its identifier\n"
					+ " [R <index>] remove one occurrence of an item from the cart given its identifier\n"
					+ " [Q]uit the application\n\nENTER COMMAND: ");

			command = scnr.nextLine();

			//the user types 'p' to view the market catalog
			if (command.substring(0, 1).toLowerCase().equals("p")) {
				printMarketCatalog();
			}

			//adds an item to the cart
			else if (command.substring(0, 1).toLowerCase().equals("a")) {

				//gets the index of the item that the user wishes to add to the cart
				int temp = Integer.parseInt(command.substring(2, command.length()));
				//calls the add method
				add(temp, cart, count);
				//increment the count
				count++;
			}

			else if (command.substring(0, 1).toLowerCase().equals("c")) {

				//prints the number of items by calling the count variable
				//prints the sub total by calling getSubSotalPrice()
				//prints the taxby multiplying getSubTotalPrice and tax
				//prints the total by adding the total tax and subtotal
				System.out.println("#items: " + count + " Subtotal: $" + getSubTotalPrice(cart, count) + " Tax: $"
						+ Double.parseDouble(df.format(getSubTotalPrice(cart, count) * tax)) + " TOTAL: $"
						+ getTotal(getSubTotalPrice(cart, count), getTax(tax, getSubTotalPrice(cart, count))));

			} else if (command.substring(0, 1).toLowerCase().equals("d")) {
				//calls displayCartContent to see the items in the cart
				displayCartContent(cart, count);

			} else if (command.substring(0, 1).toLowerCase().equals("o")) {
				//gets the index of the item that the user wishes to remove
				int temp = Integer.parseInt(command.substring(2, command.length()).trim());
				
				//prints the occurrences of and item by calling the occurrencesOf() method
				System.out.println("The number of occurrences of " + MARKET_ITEMS[temp][0] + " (id #" + temp + ") is: "
						+ occurrencesOf(temp, cart, count));

			} else if (command.substring(0, 1).toLowerCase().equals("r")) {
				
				//gets the index of the item that the user wants to remove
				String temp = MARKET_ITEMS[Integer.parseInt(command.substring(2, command.length()).trim())][0];
				
				//assigns the new amount of items in the cart after calling the remove() method
				int tmp = remove(temp, cart, count);
				//remove returns -1 if nothing is removed
				if (tmp == -1) {
					
				}else {
					//if something is removed, count is decremented
					count--;
				}

			}

			//loop the operates while the user does not input 'q'
		} while (!command.toLowerCase().equals("q"));
		System.out.println("=============  Thank you for using this App!!!!!  =============");

	}

}
