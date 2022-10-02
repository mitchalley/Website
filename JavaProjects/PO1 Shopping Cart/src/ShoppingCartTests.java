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


public class ShoppingCartTests {

	/**
	 * Checks whether the total number of items within the cart is incremented after
	 * adding one item
	 * 
	 * @return true if the test passes without problems, false otherwise
	 */
	public static boolean testCountIncrementedAfterAddingOnlyOneItem() {
		boolean testPassed = true; // boolean local variable evaluated to true if this test passed,
									// false otherwise
		String[] cart = new String[20]; // shopping cart
		int count = 0; // number of items present in the cart (initially the cart is empty)

		// Add an item to the cart
		count = ShoppingCart.add(3, cart, count); // add an item of index 3 to the cart
		// Check that count was incremented
		if (count != 1) {
			System.out.println("Problem detected: After adding only one item to the cart, "
					+ "the cart count should be incremented. But, it was not the case.");
			testPassed = false;
		}
		return testPassed;
	}

	/**
	 * Checks whether add and OccurrencesOf return the correct output when only one
	 * item is added to the cart
	 * 
	 * @return true if test passed without problems, false otherwise
	 */
	public static boolean testAddAndOccurrencesOfForOnlyOneItem() {
		boolean testPassed = true; // evaluated to true if test passed without problems, false otherwise
		// define the shopping cart as an oversize array of elements of type String
		// we can set an arbitrary capacity for the cart - for instance 10
		String[] cart = new String[10]; // shopping cart
		int count = 0; // number of items present in the cart (initially the cart is empty)

		// check that OccurrencesOf returns 0 when called with an empty cart
		if (ShoppingCart.occurrencesOf(10, cart, count) != 0) {
			System.out.println("Problem detected: Tried calling OccurrencesOf() method when the cart is "
					+ "empty. The result should be 0. But, it was not.");
			testPassed = false;
		}

		// add one item to the cart
		count = ShoppingCart.add(0, cart, count); // add an item of index 0 to the cart

		// check that OccurrencesOf("Apples", cart, count) returns 1 after adding the
		// item with key 0
		if (ShoppingCart.occurrencesOf(0, cart, count) != 1) {
			System.out.println("Problem detected: After adding only one item with key 0 to the cart, "
					+ "OccurrencesOf to count how many of that item the cart contains should return 1. "
					+ "But, it was not the case.");
			testPassed = false;
		}

		return testPassed;
	}

	// Checks that items can be added more than one time and are found
	public static boolean testAddOccurrencesOfDuplicateItems() {
		boolean passed = false; // asserts the validity of the tested method

		// oversize array for the cart
		String[] cart = new String[10];
		// amount of items in the array
		int count = 0;

		// adding the same item to the array
		for (int i = 0; i < 5; i++) {
			count = ShoppingCart.add(5, cart, i);
		}

		if (ShoppingCart.occurrencesOf(5, cart, count) == 5) {
			passed = true;
		}
		return passed;

	}

	// Checks that the correct output is returned when the user tries to add too
	// much items to the cart
	// exceeding its capacity
	public static boolean testAddingTooMuchItems() {
		boolean passed = false;
		int count = 3;
		String[] cart = new String[3];
		// checks that the return value is 0, which means that the method did not do as the user requested
		if (ShoppingCart.add(2, cart, count) == 0) {
			passed = true;
		}
		return passed;
	}

	// Checks that when only one attempt to remove an item present in the cart is made, only one occurrence of 
	// that item is removed from the cart
	public static boolean testRemoveOnlyOneOccurrenceOfItem() {

		boolean passed = false; // verifies whether or not the method is valid
		String[] cart = new String[10]; // over size array cart
		int itemsInCart = 0;
		// populates the cart
		for (int index = 0; index < 4; index++) {
			ShoppingCart.add(5, cart, index);
			itemsInCart++;
		}

		if (ShoppingCart.remove("Broccoli", cart, 4) == 0 && ((itemsInCart - 1) == 3))
			passed = true;

		return passed;
	}

	// Checks that remove returns false when the user tries to remove an item not present within the cart
	public static boolean testRemoveItemNotFoundInCart() {
		
		boolean passed = false;
		String[] cart = new String[10];
		
		//populates the cart
		for (int index = 0; index < 4; index++) {
			ShoppingCart.add(5, cart, index);
		}
		
		if (ShoppingCart.remove("Apple", cart, 4) == -1) {
			passed = true;
		}
			
		
		return passed;

	}
	
	
	public static boolean testGetSubTotalPrice() {
		
		boolean passed = false;
		String[] cart = {"Apple", "Broccoli", "Apple", "Grape", "Onion", "Onion", "Grape", "Grape", "Onion"};
		
		
		if (ShoppingCart.getSubTotalPrice(cart, 9) == 14.21) {
			passed = true;
		}
		
		return passed;
	}
	
	public static boolean testRemoveTwoOccurencesOfItem() {
		boolean passed = false;
		
		String[] cart = {ShoppingCart.MARKET_ITEMS[0][0], ShoppingCart.MARKET_ITEMS[7][0],ShoppingCart.MARKET_ITEMS[0][0],ShoppingCart.MARKET_ITEMS[9][0]};
		ShoppingCart.remove("Apple", cart, cart.length);
		
		if (ShoppingCart.occurrencesOf(0, cart, cart.length) == 1) {
			for(int i = 0; i < cart.length; i++) {
				System.out.println(cart[i]);
			}
			passed = true;
		}
		
		return passed;
	}

	/**
	 * main method used to call the unit tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(
				"testCountIncrementedAfterAddingOnlyOneItem(): " + testCountIncrementedAfterAddingOnlyOneItem());
		System.out.println("testAddAndOccurrencesOfForOnlyOneItem(): " + testAddAndOccurrencesOfForOnlyOneItem());
		System.out.println("testAddOccurrencesOfDuplicateItems(): " + testAddOccurrencesOfDuplicateItems());
		System.out.println("testAddingTooMuchItems(): " + testAddingTooMuchItems());
		System.out.println("testRemoveOnlyOneOccurrenceOfItem(): " + testRemoveOnlyOneOccurrenceOfItem());
		System.out.println("testRemoveItemNotFoundInCart(): " + testRemoveItemNotFoundInCart());
		System.out.println("testRemoveTwoOccurencesOfItem(): " + testRemoveTwoOccurencesOfItem());
	}

}
