//
//  SignUpVC.swift
//  BadgerBytes
//
//  Created by Mitch Alley on 3/16/21.
//
import UIKit
import Firebase

class ModifyAccountVC:  UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout {
    
    weak var cv: UICollectionView! // this pages collection view
    var cellExpanded: Bool!
    
    override func loadView() {
        super.loadView()
        
        cellExpanded = false
        let cv = UICollectionView(frame: .zero, collectionViewLayout: UICollectionViewFlowLayout())
        cv.translatesAutoresizingMaskIntoConstraints = false
        self.view.addSubview(cv)
        
        NSLayoutConstraint.activate([
                   cv.topAnchor.constraint(equalTo: self.view.topAnchor),
                   cv.bottomAnchor.constraint(equalTo: self.view.bottomAnchor),
                   cv.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
                   cv.trailingAnchor.constraint(equalTo: self.view.trailingAnchor),
               ])
        self.cv = cv
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.cv.backgroundColor = .main_background
        self.cv.dataSource = self
        self.cv.delegate = self

        self.cv.register(SimpleTextInputCell.self, forCellWithReuseIdentifier: "InputCell")
        self.cv.register(SimpleTextCell.self, forCellWithReuseIdentifier: "TextCell")

    }



// everything is public to match public status of protocols they are following
    public func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }

    public func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 5
    }

    public func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let placeHolders = ["First Name", "Last Name", "Username", "Email"]
        
        if (indexPath.row == 4) {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "TextCell", for: indexPath) as! SimpleTextCell
            cell.textLabel.text = "Confirm"
            cell.textLabel.textColor = .main_background
            cell.textLabel.textAlignment = .center
            cell.textLabel.leadingAnchor.constraint(equalTo: cell.leadingAnchor, constant: -10).isActive = true
            cell.contentView.backgroundColor = .primary_purple
            
            
            return cell
        } else {
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "InputCell", for: indexPath) as! SimpleTextInputCell
            
            cell.textInput.attributedPlaceholder = NSAttributedString(string: placeHolders[indexPath.row], attributes: [.foregroundColor: UIColor.subtitle_label])
            
            return cell
        }
        
        //cell.textLabel.text = String(indexPath.row + 1)
    }


    public func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if (indexPath.row == 4) {
            saveItems()
            
            // reset UI
            let tabBarVC = UIApplication.shared.keyWindow?.rootViewController as! TabBarVC
            tabBarVC.setUpViewControllers()
            self.view.endEditing(true)
            self.view.window?.rootViewController?.dismiss(animated: true, completion: nil)
        }
    }
    
    func saveItems() {
        var firstName = (self.cv.cellForItem(at: [0,0]) as! SimpleTextInputCell).textInput.text
        if (firstName == ""){firstName = globalCurrentUser?.firstName}
        var lastName = (self.cv.cellForItem(at: [0,1]) as! SimpleTextInputCell).textInput.text
        if (lastName == ""){lastName = globalCurrentUser?.lastName}
        var username = (self.cv.cellForItem(at: [0,2]) as! SimpleTextInputCell).textInput.text
        if (username == ""){username = globalCurrentUser?.username}
        var email = (self.cv.cellForItem(at: [0,3]) as! SimpleTextInputCell).textInput.text
        if (email == ""){email = globalCurrentUser?.email}
        
        guard let currentUserID = Auth.auth().currentUser?.uid else {return}
        let values = ["firstName": firstName, "lastName": lastName, "email": email, "username" :username]
        

        Database.database().reference().child("Users").child(currentUserID).updateChildValues(values)
        
        if(email != globalCurrentUser?.email){
            Auth.auth().currentUser?.updateEmail(to: email!, completion: { (err) in
                if let err = err {
                    print("Database info error: " + err.localizedDescription)
                }
                
                print("Successfully updated email")
            })
        }
    }


    public func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {

        return CGSize(width: collectionView.bounds.size.width - 16, height: 50)
    }
    public func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 8
    }

    public func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 0
    }

    public func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        insetForSectionAt section: Int) -> UIEdgeInsets {
        return UIEdgeInsets.init(top: 8, left: 8, bottom: 8, right: 8)
    }
}
