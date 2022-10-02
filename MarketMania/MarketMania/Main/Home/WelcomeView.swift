//
//  WelcomeView.swift
//  MarketMania
//
//  Created by Atessa Amjadi on 3/24/21.
//

import UIKit


class WelcomeView: UICollectionViewCell {
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(frame: CGRect) {
        super.init(frame: frame)
        
        //backgroundColor = .menu_white
        
        setUpViews()
    }
    
    let welcomeLabel: UILabel = {
        let label = UILabel()
        label.add(text: "Welcome back ", font: UIFont(boldWithSize: 33), textColor: .white)
        label.textAlignment = .left
        label.numberOfLines = 1
        return label
    }()
    
    let userNameLabel: UILabel = {
        let label = UILabel()
        label.add(text: (globalCurrentUser?.firstName ?? ""), font: UIFont(name: "Verdana-BoldItalic", size: 25)!, textColor: .white)
        label.textAlignment = .left
        label.numberOfLines = 1
        return label
    }()
    
    let tempSarcasticLabel: UILabel = {
        let label = UILabel()
        label.add(text: "Ready to lose more $ ?", font: UIFont(regularWithSize: 20), textColor: UIColor(hex: "686B75"))
        label.textAlignment = .center
        return label
    }()
    
    func setUpViews() {
        
        contentView.addSubviews(views: [welcomeLabel, userNameLabel, tempSarcasticLabel])
        
        welcomeLabel.anchor(contentView.safeAreaLayoutGuide.topAnchor, left: contentView.leftAnchor, bottom: nil, right: contentView.rightAnchor, topConstant: 35, leftConstant: 20, bottomConstant: 0, rightConstant: 0, widthConstant: 0, heightConstant: 0)
        
//        userNameLabel.anchor(welcomeLabel.bottomAnchor, left: contentView.leftAnchor, bottom: tempSarcasticLabel.topAnchor, right: nil, topConstant: 0, leftConstant: 20, bottomConstant: 25, rightConstant: 0, widthConstant: 0, heightConstant: 0)
        
        tempSarcasticLabel.anchor(welcomeLabel.bottomAnchor, left: welcomeLabel.leftAnchor, bottom: nil, right: nil, topConstant: 5, leftConstant: 0, bottomConstant: 0, rightConstant: 0, widthConstant: 0, heightConstant: 0)
       
    }
    
}
