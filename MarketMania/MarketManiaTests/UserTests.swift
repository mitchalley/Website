//
//  UserTests.swift
//  MarketManiaTests
//
//  Created by Connor Hanson on 3/27/21.
//

@testable import MarketMania
import XCTest
import Firebase

class UserTests: XCTestCase {
    
    var uid: String = ""
    var user: MarketMania.User?
    var ref: DatabaseReference!
    var exps: [XCTestExpectation] = []

    override func setUpWithError() throws {
        let exp = expectation(description: "login")
        self.ref = Database.database().reference()
        
        Auth.auth().signIn(withEmail: "usertests@test.com", password: "test1234") { authResult, error in
            if let error = error {
                assertionFailure("Failure to login: \(error)")
            }
            
            if (Auth.auth().currentUser?.uid != nil) {
                self.uid = Auth.auth().currentUser?.uid ?? "error"
                print("UUID: ", self.uid)
                self.ref = Database.database().reference().child("Users").child(self.uid)
                
                // empty user then reset cash balance
                self.ref.setValue("", withCompletionBlock: { _,_ in
                    self.ref.child("cashBalance").setValue(50000.0, withCompletionBlock: { (error, dbref) in
                        if let error = error {
                            assertionFailure("Error setting up test user: \(error)")
                        }
                        
                        // now fetch user
                        exp.fulfill()
                        
                        
                        // now fetch user
                        print("SHIT")
                    })
                    
                    // set something 
                })
                
            } else {
                assertionFailure("Failure to login")
            }
        }
        
        waitForExpectations(timeout: 5)
        
        let fetchExp = XCTestExpectation(description: "fetch")
        
        // do something with user
        fetchUser {
           // print("GCD:", globalCurrentUser)
            fetchExp.fulfill()
            self.exps.append(XCTestExpectation(description: "updateGCU"))
        }
        
        wait(for: [fetchExp], timeout: 5)
        
        //print("GCD:", globalCurrentUser)
        
        self.user = User(uid: self.uid, dictionary: ["": ""]) // user with empty dict
        
    }

    override func tearDownWithError() throws {
        // TODO
    }

    func testUserBuyOneStock() throws {
        let exp = expectation(description: "buy stock")
        user?.buyStock(symbol: "AAPL", numShares: 2, completion: { error, updatedBalance in
            if let error = error {
                assertionFailure("Failed to buy singular stock: \(error)")
            }
            
            XCTAssertNotEqual(updatedBalance, 0.0)
            exp.fulfill()
        })
        waitForExpectations(timeout: 5)
        
        // check that user data is correctly stored in database
        let dbexp = expectation(description: "check stock in db")
        
        self.ref.child("Portfolio").child("AAPL").getData { (error, snapshot) in
            if let error = error {
                assertionFailure("Error getting portfolio data: \(error)")
            } else if snapshot.exists() {
                // check that they have 2 shares of apple
                let dict = snapshot.value as? NSDictionary // portfolio is key, stock symbols are values
                let symbols = dict?["Portfolio"] as? NSDictionary // stock symbols are keys
                let stockInfo = symbols?["AAPL"] as? NSDictionary // can get data from here
                
                //dump(dict, name: "Snapshot", indent: 5, maxDepth: 100, maxItems: 100)

                guard ((stockInfo?["avgPrice"] as! Float) != -1.0) else {
                    dump(dict, name: "Snapshot", indent: 5, maxDepth: 100, maxItems: 100)
                    assertionFailure("No average price")
                    return
                }

                XCTAssertEqual(stockInfo?["shares"] as! Float, 2.0)
                dbexp.fulfill()
            } else {
                assertionFailure("Portfolio shares do not exist")
            }
        }
        
        waitForExpectations(timeout: 5)
        
        // check that GCD currently has updated stocks too
        
        
    }
    
    func testUserBuySeveralStocks() throws {
        var AAPLexp = expectation(description: "buyAAPL")
        var AMCexp = expectation(description: "buyAMC")
        
        user?.buyStock(symbol: "AAPL", numShares: 4, completion: { error, updatedBalance in
            if let error = error {
                assertionFailure("Error buying Apple stock: \(error)")
            }
            
            XCTAssertNotEqual(updatedBalance, -1.0)
            AAPLexp.fulfill()
        })
        
        // assume that the first purchase went through
        user?.buyStock(symbol: "AMC", numShares: 10, completion: { error, updatedBalance in
            if let error = error {
                assertionFailure("Error buying AMC stock: \(error)")
            }
            
            XCTAssertNotEqual(updatedBalance, -1.0)
            AMCexp.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        
        // check db to see that AMC and AAPL are in it
        var dbexp = expectation(description: "check stock in db")
        var avgAMC: Float?
        var avgAAPL: Float?
        
        self.ref.getData { (error, snapshot) in
            if let error = error {
                assertionFailure("Error getting portfolio data: \(error)")
            } else if snapshot.exists() {
                let dict = snapshot.value as? NSDictionary // portfolio is key, stock symbols are values
                let symbols = dict?["Portfolio"] as? NSDictionary // stock symbols are keys
                let AAPLInfo = symbols?["AAPL"] as? NSDictionary // can get data from here
                let AMCInfo = symbols?["AMC"] as? NSDictionary
                
                guard dict != nil && symbols != nil else {
                    dump(dict, name: "Dictionary", indent: 5, maxDepth: 100, maxItems: 100)
                    assertionFailure("One of the parent dicts is nil")
                    return
                }
                
                guard AAPLInfo != nil else {
                    assertionFailure("AAPL dict is nil")
                    dump(dict, name: "Apple", indent: 5, maxDepth: 100, maxItems: 100)
                    return
                }
                
                guard AMCInfo != nil else {
                    assertionFailure("AMC dict is nil")
                    dump(dict, name: "AMC", indent: 5, maxDepth: 100, maxItems: 100)
                    return
                }
                
                // check AAPL
                XCTAssertNotNil(avgAAPL = AAPLInfo?["avgPrice"] as? Float, "No average price for APPL")
                XCTAssertEqual(AAPLInfo?["shares"] as? Float, 4)
                // check AMC
                XCTAssertNotNil(avgAMC = AMCInfo?["avgPrice"] as? Float, "No average price for AMC")
                XCTAssertEqual(AMCInfo?["shares"] as? Float, 10)

                dbexp.fulfill()
            } else {
                assertionFailure("Portfolio shares do not exist")
            }
        }
        
        waitForExpectations(timeout: 5)
        
        AAPLexp = expectation(description: "APPL pt2")
        AMCexp = expectation(description: "AMC pt2")
        // add new shares to apple and AMC
        user?.buyStock(symbol: "AAPL", numShares: 10, completion: { error, updatedBalance in
            if let error = error {
                assertionFailure("Failed to buy APPL stock: \(error)")
            }
            
            XCTAssertNotEqual(updatedBalance, -1.0)
            AAPLexp.fulfill()
        })
        
        user?.buyStock(symbol: "AMC", numShares: 10, completion: { error, updatedBalance in
            if let error = error {
                assertionFailure("Failed to buy AMC stock: \(error)")
            }
            
            XCTAssertNotEqual(updatedBalance, -1.0)
            AMCexp.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        
        dbexp = expectation(description: "db2")
        // check in db that all values are correct
        self.ref.getData { (error, snapshot) in
            if let error = error {
                assertionFailure("Error getting portfolio data: \(error)")
            } else if snapshot.exists() {
                let dict = snapshot.value as? NSDictionary // portfolio is key, stock symbols are values
                let symbols = dict?["Portfolio"] as? NSDictionary // stock symbols are keys
                let AAPLInfo = symbols?["AAPL"] as? NSDictionary // can get data from here
                let AMCInfo = symbols?["AMC"] as? NSDictionary
                
                guard dict != nil && symbols != nil else {
                    dump(dict, name: "Dictionary", indent: 5, maxDepth: 100, maxItems: 100)
                    assertionFailure("One of the parent dicts is nil")
                    return
                }
                
                guard AAPLInfo != nil else {
                    assertionFailure("AAPL dict is nil")
                    dump(dict, name: "Apple", indent: 5, maxDepth: 100, maxItems: 100)
                    return
                }
                
                guard AMCInfo != nil else {
                    assertionFailure("AMC dict is nil")
                    dump(dict, name: "AMC", indent: 5, maxDepth: 100, maxItems: 100)
                    return
                }
                
                // TODO: fix average price
                
                // check AAPL
                XCTAssertNotNil(AAPLInfo?["avgPrice"] as? Float, "No average price for APPL")
                XCTAssertEqual(avgAAPL, AAPLInfo?["avgPrice"] as? Float)
                XCTAssertEqual(AAPLInfo?["shares"] as? Float, 14)

                // check AMC
                XCTAssertNotNil(AMCInfo?["avgPrice"] as? Float, "No average price for AMC")
                XCTAssertEqual(avgAMC, AMCInfo?["avgPrice"] as? Float)
                XCTAssertEqual(AMCInfo?["shares"] as? Float, 20)

                dbexp.fulfill()
            } else {
                assertionFailure("Portfolio shares do not exist")
            }
        }
        
        waitForExpectations(timeout: 5)
    }
    
    func testUserBuyInsufficientCash() throws {
        let exp = expectation(description: "exp")
        
        user?.buyStock(symbol: "GOOG", numShares: 500000, completion: { error, updatedBalance in
            XCTAssertNotNil(error, "Shoudl have thrown error, invalid funds but have: \(updatedBalance)")
            XCTAssertEqual(-1.0, updatedBalance)
            exp.fulfill()
        })
        
        waitForExpectations(timeout: 5)
    }
    
    func testUserBuyAndSellStocks() throws {
        XCTFail()
    }
    
    func testUserGetPortfolioValue() throws {
        XCTFail()
    }
    
    func testUserGetCashBalance() throws {
        XCTFail()
    }
    
    func testFetchUser() throws {
        XCTFail()
    }
    
    func testUserUpdateCashBalance() throws {
        let exp = expectation(description: "cash")
        
        user?.updateCashBalance(delta: -20.0, completion: { error, updatedBalance in
            if let error = error {
                assertionFailure("Error updating user cash balance: \(error)")
            }
            
            XCTAssertEqual(updatedBalance, 49980.0)
            exp.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        
        // check that user has updated cash in account
        //wait(for: [exps[0]], timeout: 12)
        //XCTAssertEqual(globalCurrentUser?.cashBalance, 49980.0)
    }
    
    func testUserUpdatePortfolioValue() throws {
        let exp = expectation(description: "portfolio value")
        
        user?.updatePortfolioValue(completion: {error, newVal in
            if let error = error {
                assertionFailure("Error updating user cash balance: \(error)")
            }
            
           // XCTAssertEqual(globalCurrentUser?.portfolioValue, newVal)
            exp.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        
    }
    
    // prereq: buy stocks works
    func testUserGetPortfolio() throws {
        // TODO: test empty portfolio
        
        let initialExp = expectation(description: "empty")
        user?.getPortfolio(completion: { error, portfolioStocks in
            
            if let error = error {
                XCTFail("Error getting empty portfolio: \(error)")
            }
            
            else if portfolioStocks.count != 0 {
                dump(portfolioStocks)
                XCTFail("Should have received empty portfolio, but didn't")
            }
            
            else {
                initialExp.fulfill()
            }
        })
        
        waitForExpectations(timeout: 5)
        
        let expAAPL = expectation(description: "AAPL")
        let expMO = expectation(description: "MO")
        let expGME = expectation(description: "GME")
        
        user?.buyStock(symbol: "AAPL", numShares: 75, completion: { error, updatedBalance in
            // basic checks
            if let error = error {
                assertionFailure("Error buying AAPL stock: \(error)")
            }
            
            if updatedBalance == 0.0 {
                assertionFailure("No money spent in AAPL transaction")
            }
            // assume its in db
            expAAPL.fulfill()
        })
        
        user?.buyStock(symbol: "MO", numShares: 50, completion: { error, updatedBalance in
            // basic checks
            if let error = error {
                assertionFailure("Error buying MO stock: \(error)")
            }
            
            if updatedBalance == 0.0 {
                assertionFailure("No money spent in MO transaction")
            }
            // assume its in db
            expMO.fulfill()
        })
        
        user?.buyStock(symbol: "GME", numShares: 25, completion: { error, updatedBalance in
            // basic checks
            if let error = error {
                assertionFailure("Error buying GME stock: \(error)")
            }
            
            if updatedBalance == 0.0 {
                assertionFailure("No money spent in GME transaction")
            }
            // assume its in db
            expGME.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        
        // test getting
        let expGET = expectation(description: "Getting")
        
        user?.getPortfolio(completion: { error, portfolioStocks in
            if let error = error {
                assertionFailure("Failed to get portfolio stocks: \(error)")
            }
            
            guard portfolioStocks.count == 3 else {
                dump(portfolioStocks, name: "Portfolio", indent: 0, maxDepth: 10, maxItems: 10)
                assertionFailure("Portfolio should contain exactly 3 stocks")
                return
            }
            
            var boolArray: [Bool] = [false, false, false] // AAPL, MO, GME
            
            for stock in portfolioStocks {
                if stock.symbol == "AAPL" {
                    XCTAssertEqual(stock.shares, 75)
                    XCTAssertNotEqual(stock.avgPrice, 0)
                    boolArray[0] = true
                }
                
                else if stock.symbol == "MO" {
                    XCTAssertEqual(stock.shares, 50)
                    XCTAssertNotEqual(stock.avgPrice, 0)
                    boolArray[1] = true
                }
                
                else if stock.symbol == "GME" {
                    XCTAssertEqual(stock.shares, 25)
                    XCTAssertNotEqual(stock.avgPrice, 0)
                    boolArray[2] = true
                }
                
                else {
                    assertionFailure("Unknown symbol <\(stock.symbol!) returned")
                }
            }
            
            for b in boolArray {
                XCTAssert(b)
            }
            
            expGET.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        
    }
    
    func testUserSellStock() throws {
        
        let expSellNone = expectation(description: "sellNone")
        user?.sellStock(symbol: "AAPL", numShares: 12, completion: { error, updatedBalance in
            XCTAssertNotNil(error, "No error thrown when trying to oversell portfolio")
            XCTAssertEqual(updatedBalance, -1.0, "Balance should not change, is \(updatedBalance)")
            
            expSellNone.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        
        let expBuy = expectation(description: "buy")
        
        user?.buyStock(symbol: "AAPL", numShares: 12, completion: { error, updatedBalance in
            if let error = error {
                XCTFail("Error: \(error)")
            }
            expBuy.fulfill()
        })
        
        waitForExpectations(timeout: 5)
        let expOverSell = expectation(description: "overselling by one share")
        
        user?.sellStock(symbol: "AAPL", numShares: 13, completion: { error, updatedBalance in
            XCTAssertNotNil(error, "No error thrown when trying to oversell portfolio")
            XCTAssertEqual(updatedBalance, -1.0, "Balance should not change, is \(updatedBalance)")
            
            expOverSell.fulfill()
        })
        
        waitForExpectations(timeout: 5)
    }
    
    //
    // MARK: WATCHLIST TESTS
    //
    
    func testAddToWatchList() throws {
        //user?.addToWatchList(stock: <#T##Stock#>)
    }
    
    func testGetWatchList() throws {
        
    }
    
    func testRemoveFromWatchList() throws {
        
    }

    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
