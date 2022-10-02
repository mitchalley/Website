//
//  UsersListView.swift
//  MarketMania
//
//  Created by Mitch Alley on 4/5/21.
//

import UIKit

class UsersListView: UICollectionViewCell, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    var numUsers = -1
    var cellUsernames: [String] = []
    var userDict: NSDictionary = [:]
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUpViews()
        
        collectionView2.delegate = self
        collectionView2.dataSource = self
        //getUsernames(completion: 2)
//        getUsernames() { response in
//            DispatchQueue.main.async {
//                self.cellUsernames = response
//                print(self.cellUsernames)
//            }
//        }
        
        getTopUsers(completion: { error, users in
            
            DispatchQueue.main.async {
                self.cellUsernames = users
                self.collectionView2.reloadData()
            }
            
        })
        //
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    
    let userListLabel: UILabel = {
        let label = UILabel()
        label.add(text: "Name", font: UIFont(boldWithSize: 17), textColor: .white)
        label.textAlignment = .center
        return label
    }()
    
    let userListRank: UILabel = {
        let label = UILabel()
        label.add(text: "Rank", font: UIFont(boldWithSize: 17), textColor: .white)
        label.textAlignment = .right
        return label
    }()
    
    //userlist collection view
    let collectionView2: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        layout.scrollDirection = .vertical
        let cv = UICollectionView(frame: .zero, collectionViewLayout: layout)
        cv.backgroundColor = .clear
        cv.translatesAutoresizingMaskIntoConstraints = false
        
        // register cells
        cv.register(usersListCell.self, forCellWithReuseIdentifier: "usersListCell")
        return cv
    }()
    
    func setUpViews() {
        
        getUserCount(completion: { totalusers in
            self.numUsers = totalusers
            DispatchQueue.main.async {
                self.collectionView2.reloadData()
                // set ui code
            }
            
        })
        
        userListRank.accessibilityIdentifier = "userListRank"
        userListLabel.accessibilityIdentifier = "userListLabel"
        collectionView2.accessibilityIdentifier = "collectionView2"
        
        contentView.addSubviews(views: [userListLabel, userListRank,
                                        collectionView2])
        
        
        userListLabel.anchor(contentView.topAnchor, left: contentView.leftAnchor, bottom: collectionView2.topAnchor, right: nil, topConstant: 20, leftConstant: 10, bottomConstant: 5, rightConstant: 20, widthConstant: 0, heightConstant: 0)

        collectionView2.anchor(userListLabel.bottomAnchor, left: contentView.leftAnchor, bottom: contentView.safeAreaLayoutGuide.bottomAnchor, right: contentView.rightAnchor, topConstant: 0, leftConstant: 10, bottomConstant: 0, rightConstant: 10, widthConstant: 0, heightConstant: 0)
        
        userListRank.anchor(contentView.topAnchor, left: nil, bottom: collectionView2.topAnchor, right: contentView.rightAnchor, topConstant: 20, leftConstant: 20, bottomConstant: 5, rightConstant: 10, widthConstant: 0, heightConstant: 0)
    }
    
    func getUsernames(completion: @escaping ([String]) -> Void) -> Void  {
        var ret: [String] = []
        ref.child("Users").getData(completion: { error, snapshot in
            
            if let error = error {
                print("Descriptive error: \(error)")
                return
            }
            
            if snapshot.exists() {
                let userDict = snapshot.value as? NSDictionary // cast to dictionary we can use
                let itemsArray: NSArray?   = userDict?.object(forKey: "username") as? NSArray;
                if let itemsArray = itemsArray
                {
                    for _ in itemsArray
                    {
                        let usernameId: String? = userDict?.object(forKey: "id") as? String
                       if (usernameId != nil)
                       {
                        print(usernameId)
                        ret.append(usernameId!)
                       }
                     }
                    print(ret)
                    completion(ret)
                }

            } else {
                // do error handling
            }
            
        })
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.cellUsernames.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "usersListCell", for: indexPath) as! usersListCell
        cell.tempLabel.text = cellUsernames[indexPath.row]
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.frame.width, height: collectionView.frame.height/5)
    }
    
}

//cells for userlist
class usersListCell: UICollectionViewCell {

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        
        backgroundColor = .systemGray2
        
        self.accessibilityIdentifier = "leaderboard"
        
        setUpViews()
    }
    
    let tempLabel: UILabel = {
        let label = UILabel()
        label.add(text: "testing", font: UIFont(name: "PingFangHK-Regular", size: 15)!, textColor: .black)
        label.textAlignment = .center
        label.textColor = .white
        return label
    }()
    
    func setUpViews() {
        
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 5
        self.backgroundColor = UIColor(hex: "3A3E50")
        
        contentView.addSubview(tempLabel)
        
//        tempLabel.anchor(contentView.topAnchor, left: contentView.leftAnchor, bottom: nil, right: nil, topConstant: 5, leftConstant: 5, bottomConstant: 0, rightConstant: 0, widthConstant: 0, heightConstant: 0)
        tempLabel.anchorCenterSuperview()
        
      
       
       
    }
}
