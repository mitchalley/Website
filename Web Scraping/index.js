const puppeteer = require('puppeteer')
const fs = require('fs').promises

async function investInRoth (stockToPurchase) {
    const browser = await puppeteer.launch({
        headless: false
    })

    // Creating a delay function to be used throughout the program
    function delay(time) {
        return new Promise(function(resolve) { 
            setTimeout(resolve, time)
        });
     }


    // Checking the price of stockToPurchase
    const cost = await browser.newPage()
    await cost.setViewport({ width: 1200, height: 800 });

    await cost.goto('https://finance.yahoo.com/quote/'+ stockToPurchase +'?p='+ stockToPurchase +'&.tsrc=fin-srch')
    
    await cost.waitForSelector("#quote-header-info > div.My\\(6px\\).Pos\\(r\\).smartphone_Mt\\(6px\\).W\\(100\\%\\) > div.D\\(ib\\).Va\\(m\\).Maw\\(65\\%\\).Ov\\(h\\) > div.D\\(ib\\).Mend\\(20px\\) > fin-streamer.Fw\\(b\\).Fz\\(36px\\).Mb\\(-4px\\).D\\(ib\\)")
    let price = await cost.evaluate(async () => {
        try{
            return await document.querySelector("#quote-header-info > div.My\\(6px\\).Pos\\(r\\).smartphone_Mt\\(6px\\).W\\(100\\%\\) > div.D\\(ib\\).Va\\(m\\).Maw\\(65\\%\\).Ov\\(h\\) > div.D\\(ib\\).Mend\\(20px\\) > fin-streamer.Fw\\(b\\).Fz\\(36px\\).Mb\\(-4px\\).D\\(ib\\)").textContent
        } catch (error){
            return error
        }
    });
    await cost.close()
    price = Number(price)
    const page = await browser.newPage()
    await page.setViewport({ width: 1000, height: 800 });

    // Loading cookies
    // load cookies 
    const cookiesString = await fs.readFile("./cookies.json")
    let cookies = JSON.parse(cookiesString)
    await page.setCookie(...cookies)
    await page.goto("https://www.tdameritrade.com/funding-and-transfers.html")

    // Accessing broker


    username = 
    password = 

    // Go to TD Ameritrade
    await page.waitForSelector("#pushobj > div > div:nth-child(1) > div.par.iparys_inherited > div > div > div > div > header > div > div.main-header-nav-container__top > div.main-header-mobile-login-cta.main-header-mobile-upper-nav > a")
    await page.click("#pushobj > div > div:nth-child(1) > div.par.iparys_inherited > div > div > div > div > header > div > div.main-header-nav-container__top > div.main-header-mobile-login-cta.main-header-mobile-upper-nav > a")

    // Type in username    
    await page.waitForSelector("#username0")
    const input = await page.$("#username0");
    await input.click({ clickCount: 3 })
    await page.type("#username0", username)


    // Type in password
    await page.waitForSelector("#password1")
    const passfield = await page.$("#password1")
    await passfield.click({clickCount: 3})
    await page.type("#password1", password)

    // Click on Remember Me
    await page.waitForSelector("#authform > main > div.row.user_settings > span > label")
    await page.click("#authform > main > div.row.user_settings > span > label")


    // Click on login
    await page.waitForSelector("#accept")
    await page.click("#accept")

    // Two factor authentication: must be done manually
    // Click on continue
    await page.waitForSelector("#accept")
    await page.click("#accept")
    await page.waitForFunction('document.querySelector("#smscode0").value.length == 6')
    // Get code sent to phone


    // Click on Trust this device
    await page.waitForSelector("#authform > main > div.row.user_settings > span > label")
    await page.click("#authform > main > div.row.user_settings > span > label")

    // Click on continue
    await page.waitForSelector("#accept")
    await page.click("#accept")
    await delay(2000)
    
    // If the entered code is wrong
    let checkFail = await page.evaluate(async () => {
        try {return await document.querySelector("#user_message_inline > p").textContent == 'Invalid Code. Try again.';} catch {return false;}
    });
    
    while (checkFail){
        
        // Resend the code
        await page.waitForSelector("#hint_smscode > p > a > span")
        await page.click("#hint_smscode > p > a > span")

         // Click on continue
        await page.waitForSelector("#accept")
        await page.click("#accept")
        await page.waitForFunction('document.querySelector("#smscode0").value.length == 6')


        // Click on Trust this device
        await page.waitForSelector("#authform > main > div.row.user_settings > span > label")
        await page.click("#authform > main > div.row.user_settings > span > label")

        // Click on continue
        await page.waitForSelector("#accept")
        await page.click("#accept")
        await delay(2000)

        checkFail = await page.evaluate(async () => {
            try {return await document.querySelector("#user_message_inline > p").textContent == 'Invalid Code. Try again.';} catch {return false;}
        });
    }

    // Click on trade
    await delay(7000)
    await page.waitForSelector("#navTrade-menu-lbl")
    await page.click("#navTrade-menu-lbl")

    // Click on stocks or ETFs
    await delay(1000)
    await page.waitForSelector("#tradeStocksBuy > a")
    await page.click("#tradeStocksBuy > a")

    // Check available funds
    await page.waitForSelector("#uniqName_33_0 > div.balanceTableContainer > div > div.balanceBarRightBlock > div.balanceBarListCont > table > tbody > tr:nth-child(2) > td:nth-child(4) > span.subtotalsValue.hideBalancesContent")
    let fundsAvail = await page.evaluate(async () => {
        try{
            return await document.querySelector("#uniqName_33_0 > div.balanceTableContainer > div > div.balanceBarRightBlock > div.balanceBarListCont > table > tbody > tr:nth-child(2) > td:nth-child(4) > span.subtotalsValue.hideBalancesContent").innerText.substring(1)
        } catch (error){
            return error
        }
    });
    fundsAvail = Number(fundsAvail)
    totalShares = Math.floor(fundsAvail / price)
    console.log(totalShares)

    // Click on Action dropdown
    await page.waitForSelector("#action > tbody > tr > td.dijitReset.dijitStretch.dijitButtonContents > div.dijitReset.dijitInputField.dijitButtonText > span")
    await page.click("#action > tbody > tr > td.dijitReset.dijitStretch.dijitButtonContents > div.dijitReset.dijitInputField.dijitButtonText > span")


    // setTimeout(function(){ debugger }, 3000) 
    // Click on Buy
    await page.waitForSelector("#dijit_MenuItem_9_text")
    await page.click("#dijit_MenuItem_9_text")

    // Type in the quantity
    await page.waitForSelector("#quantity")
    await page.click("#quantity")
    await page.type("#quantity", String(totalShares))

    // Type in the symbol
    await page.waitForSelector("#symbol")
    await page.click("#symbol")
    await page.type("#symbol", stockToPurchase)

    // Click on order type
    await page.waitForSelector("#orderType > tbody > tr > td.dijitReset.dijitStretch.dijitButtonContents > div.dijitReset.dijitInputField.dijitButtonText > span")
    await page.click("#orderType > tbody > tr > td.dijitReset.dijitStretch.dijitButtonContents > div.dijitReset.dijitInputField.dijitButtonText > span")
    
    // Click on Limit
    await page.waitForSelector("#dijit_MenuItem_12_text")
    await page.click("#dijit_MenuItem_12_text")

    // Enter Price
    await page.waitForSelector("#limitPrice")
    await page.click("#limitPrice")
    await page.type("#limitPrice", String(price))

    // // Enter day as time in force
    // await page.waitForSelector("#timeInForce > tbody > tr > td.dijitReset.dijitRight.dijitButtonNode.dijitArrowButton.dijitDownArrowButton.dijitArrowButtonContainer")
    // await page.click("#timeInForce > tbody > tr > td.dijitReset.dijitRight.dijitButtonNode.dijitArrowButton.dijitDownArrowButton.dijitArrowButtonContainer")

    // // Click on day
    // await page.waitForSelector("#dijit_MenuItem_11_text")
    // await page.click("#dijit_MenuItem_11_text")

    // Click on review order
    await page.waitForSelector("#reviewOrderBtn_label")
    await page.click("#reviewOrderBtn_label")

    // Click on Place Order
    await delay(3000)
    await page.waitForSelector('.dijitReset.dijitInline.dijitButtonNode [tabindex="0"] [id="searchIcon"]')
    await page.click('.dijitReset.dijitInline.dijitButtonNode [tabindex="0"] [id="searchIcon"]')


    delay(5000)
    await page.screenshot({path: 'orderSummary.png'})
    await browser.close()
}


investInRoth('ONEQ')
    