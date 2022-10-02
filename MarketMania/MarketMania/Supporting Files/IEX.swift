//
//  IEX_Data.swift
//  MarketMania
//
//  Created by Connor Hanson on 3/20/21.
//
import Foundation

// TODO: store in environment variable
// not urgent since this is the public sandbox key.
// Do not put any other key in this variable
let tpk: String = "Tpk_03bc85510ff64107800ae53bbfafa504" // test public key
let pk: String = ""
let tok: String = tpk

let isTest = false // set to true if you want to use real data, false to use sandbox data

let testURL: String = "https://sandbox.iexapis.com/stable"
let productionURL: String = ""
let baseURL: String = testURL

/**
 * Pass as array of symbols. Case does not matter. AAPL == aApl == aapl
 *
 * @return: Array of stock objects stock objects with all their fields filled out
 */
func getStocks(symbols: [String], completion: @escaping ([Stock]) -> Void) -> Void {
    // check if one or many
    if (symbols.count >= 1) {
//        let urlString: String = baseURL
//        // get single
//    } else if (symbols.count > 1) {
        var urlString: String = baseURL + "/stock/market/batch?symbols="
        for stock in symbols {
            if stock != symbols.first {
                urlString += ","
            }
            urlString += stock
        }
        urlString += "&types=quote&token=" + tok
        getBatchOfStocks(urlString: urlString, completion: completion)
        // get many
    } else {
        // error
    }
}

/**
 * Pass in the search string, i.e. whatever sequence of letters user believes a stock symbol contains
 *
 * @return: Array of symbols up to top 10 matches, results are sorted for relevancy by API
 */
func searchStocks(searchString: String, completion: @escaping ([SearchStock]) -> Void) -> Void {
    print("searchStocks:", searchString)
    
    if (searchString.count >= 1) {
        let urlString: String = baseURL + "/search/" +  searchString + "?token=" + tok
        
        print(urlString)
        
        var ret: [SearchStock] = []
        
        let session = URLSession.shared
        let url = URL(string: urlString)!
        let req = URLRequest(url: url)
        
        let task = session.dataTask(with: req as URLRequest, completionHandler: {
            data, response, error in
            
            guard error == nil else {
                print(error!.localizedDescription)
                return
            }
            
            guard let data = data else {
                print("searchStocks - invalid data")
                return
            }
            
            do {
                // TODO: find way to limit response size to allow for quicker page loading
    //            print("DATA: ", data)
      //          print("RESP: ", response)
                let decoder = JSONDecoder()
                ret = try decoder.decode([SearchStock].self, from: data)
                completion(ret) // this passes the value set in ret ([Stock]) to the callback arg ([Stock])
            } catch let error {
                print("Error decoding JSON: " + error.localizedDescription)
            }
        })
        task.resume()
    }
    else {
        // error
    }
}

func getWinners(completion: @escaping ([Stock]) -> Void) -> Void {
    let urlString: String = baseURL + "/stock/market/list/gainers?token=" + tok
    return getListOfStocks(urlString: urlString, completion: completion)
}

func getLosers(completion: @escaping ([Stock]) -> Void) -> Void {
    let urlString: String = baseURL + "/stock/market/list/losers?token=" + tok
    return getListOfStocks(urlString: urlString, completion: completion)
}

func getMostActive(completion: @escaping ([Stock]) -> Void) -> Void {
    let urlString: String = baseURL + "/stock/market/list/mostactive?token=" + tok
    return getListOfStocks(urlString: urlString, completion: completion)
}

func getHighestVolume(completion: @escaping ([Stock]) -> Void) -> Void {
    let urlString: String = baseURL + "/stock/market/list/iexvolume?token=" + tok
    return getListOfStocks(urlString: urlString, completion: completion)
}

/**
 Valid Types: sector, tag, list
 */
func getCollection(type: String, collectionName: String, completion: @escaping ([Stock]) -> Void) -> Void {
    guard (type == "sector" || type == "tag" || type == "list") else {
        return
    }
    
    let urlEncodedCollection = collectionName.replacingOccurrences(of: " ", with: "%20")
    
    let urlString: String = baseURL + "/stock/market/collection/" + type + "?collectionName=" +
        urlEncodedCollection + "&token=" + tok
    return getListOfStocks(urlString: urlString, completion: completion)
}

/**
 Returns a list  of less than or equal to 10 stocks through the completion handler's arg (@escaping ([Stock]))
 */
private func getListOfStocks(urlString: String, completion: @escaping ([Stock]) -> Void) -> Void {
    var ret: [Stock] = []
    
    let session = URLSession.shared
    let url = URL(string: urlString)!
    let req = URLRequest(url: url)
    
    let task = session.dataTask(with: req as URLRequest, completionHandler: {
        data, response, error in
        
        guard error == nil else {
            print(error!.localizedDescription)
            return
        }
        
        guard let data = data else {
            return
        }
        
        do {
            // TODO: find way to limit response size to allow for quicker page loading
//            print("DATA: ", data)
  //          print("RESP: ", response)
            let decoder = JSONDecoder()
            ret = try decoder.decode([Stock].self, from: data)
            completion(ret) // this passes the value set in ret ([Stock]) to the callback arg ([Stock])
        } catch let error {
            print("Error decoding JSON: " + error.localizedDescription)
        }
    })
    task.resume()
}

private func getBatchOfStocks(urlString: String, completion: @escaping ([Stock]) -> Void) -> Void {
    
    let session = URLSession.shared
    guard let url = URL(string: urlString) else {
        print("url error")
        return
    }
    let req = URLRequest(url: url)
    
    let task = session.dataTask(with: req as URLRequest, completionHandler: {
        data, response, error in
        
        guard error == nil else {
            print(error!.localizedDescription)
            return
        }
        
        guard let data = data else {
            return
        }
        
        do {

            let decoder = JSONDecoder()
            var ret: [Stock] = []
            
            // structure of batch -> array:[["quote": Stock], ...]
            let batch: [[String: Stock]] = (try decoder.decode(StockBatch.self, from: data)).array
//            print("BATCH:", batch)
            
            for dictionary in batch {
                ret.append(dictionary["quote"]!)
            }
            completion(ret)

        } catch let error {
            print("Error decoding JSON Batch: " + error.localizedDescription)
        }
    })
    task.resume()

    
}

