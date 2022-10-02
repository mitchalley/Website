//
//  LoginUITests.swift
//  MarketManiaUITests
//
//  Created by Connor Hanson on 4/7/21.
//

import XCTest

class LoginUITests: XCTestCase {

    var app: XCUIApplication!
    // https://developer.apple.com/documentation/xctest/xcuiapplication -> class documentation
    // https://developer.apple.com/documentation/xctest/xcuielement -> actions which app can use
    
    // https://www.hackingwithswift.com/articles/148/xcode-ui-testing-cheat-sheet -> good reference
    

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.

        // In UI tests it is usually best to stop immediately when a failure occurs.
        continueAfterFailure = false

        // In UI tests it’s important to set the initial state - such as interface orientation - required for your tests before they run. The setUp method is a good place to do this.
        app = XCUIApplication()
        app.launchArguments.append("--uitesting")
        app.launchArguments.append("isUITestingLogin")
        
        // UI tests must launch the application that they test.
        app.launch()
        
        // launch is cold start, use activate when entering app from home screen, etc
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testLoginViewExists() throws {
        
        // Use this to print the current state of the application and to get a log view of all the elements contained within the current view
        
//        print("STATE:", app.state)
//        print("DEBUG:", app.debugDescription)
        
        // check that all labels exist
        XCTAssert(app.staticTexts["titleLabel"].exists)

        // check that all inputviews/textfields exist
        XCTAssert(app.otherElements["emailInputView"].exists)
        XCTAssert(app.otherElements["passwordInputView"].exists)
        
        // check that buttons exist
        XCTAssert(app.buttons["signInButton"].exists)
        XCTAssert(app.buttons["signUpButton"].exists)
        
        // check that containers exist
        XCTAssert(app.otherElements["containerView"].exists)
        XCTAssert(app.otherElements["inputBackgroundView"].exists)
        
        // background img
        XCTAssert(app.images["backgroundImageView"].exists)

        // also check furthur within the elements that each of their elements are displayed/work correctly
        
        let emailInputView = app.otherElements["emailInputView"]
        let emailText = emailInputView.textFields.element(boundBy: 0)
        let emailUnderLine = emailInputView.otherElements.element(boundBy: 0)
        XCTAssert(emailText.exists)
        XCTAssert(emailText.placeholderValue == "Email")
        XCTAssert(emailUnderLine.exists)
        
        let passwordInputView = app.otherElements["passwordInputView"]
        let passwordText = passwordInputView.secureTextFields.element(boundBy: 0)
        let passwordUnderLine = passwordInputView.otherElements.element(boundBy: 0)
        XCTAssert(passwordText.exists)
        XCTAssert(passwordText.placeholderValue == "Password")
        XCTAssert(passwordUnderLine.exists)
    }


    func testLaunchPerformance() throws {
        if #available(macOS 10.15, iOS 13.0, tvOS 13.0, *) {
            // This measures how long it takes to launch your application.
            measure(metrics: [XCTApplicationLaunchMetric()]) {
                XCUIApplication().launch()
            }
        }
    }
}
