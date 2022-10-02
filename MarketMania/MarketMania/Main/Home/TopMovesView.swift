//
//  TopMovesView.swift
//  MarketMania
//
//  Created by Atessa Amjadi on 3/24/21.
//

import UIKit

class TopMovesView: UICollectionViewCell, UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    
    var winners: [Stock] = []
    var homeVC: HomeVC?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        setUpViews()
        
        collectionView1.delegate = self
        collectionView1.dataSource = self
        
        // run async function that reloads view once data is fetched
        getWinners { response in
            // UI updates are only allowed in main queue
            DispatchQueue.main.async {
                //print("winners", response)
                self.winners = response
                self.collectionView1.reloadData()
            }
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    let topMovesLabel: UILabel = {
        let label = UILabel()
        label.add(text: "Today's Winners", font: UIFont(boldWithSize: 17), textColor: .white)
        label.textAlignment = .center
        return label
    }()
    
    //top moves collection view
    let collectionView1: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        layout.scrollDirection = .horizontal
        let cv = UICollectionView(frame: .zero, collectionViewLayout: layout)
        cv.backgroundColor = .clear
        cv.translatesAutoresizingMaskIntoConstraints = false
        
        // register cells
        cv.register(MoverCell.self, forCellWithReuseIdentifier: "mover")
        return cv
    }()
    
    func setUpViews() {
        
        contentView.addSubviews(views: [topMovesLabel, collectionView1])
        
        topMovesLabel.anchor(contentView.topAnchor, left: contentView.leftAnchor, bottom: collectionView1.topAnchor, right: nil, topConstant: 0, leftConstant: 10, bottomConstant: 5, rightConstant: 0, widthConstant: 0, heightConstant: 0)

        //make the collection view only the height of one cell
        collectionView1.heightAnchor.constraint(equalTo: collectionView1.widthAnchor, multiplier: 0.4).isActive = true

        collectionView1.anchor(topMovesLabel.bottomAnchor, left: contentView.leftAnchor, bottom: nil, right: contentView.rightAnchor, topConstant: 0, leftConstant: 10, bottomConstant: 0, rightConstant: 0, widthConstant: 0, heightConstant: 0)
        
    }
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        if (winners.count != 0){
            return winners.count
        }
        
        return 6
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        //**** Commented out for now to see UI
        //let stock = winners[indexPath.row]
        
//        cell.tickerLabel.text = stock.symbol
//        cell.nameLabel.text = stock.companyName
//        cell.moveLabel.text = String((stock.changePercent ?? 0.0))
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "mover", for: indexPath) as! MoverCell
        
        cell.accessibilityIdentifier = "mover"
              
        if (winners.count != 0) {
            let stock = winners[indexPath.row]

            cell.tickerLabel.text = "\(stock.symbol!)"
            cell.nameLabel.text = stock.companyName
            cell.moveLabel.text = String((stock.changePercent ?? 0.0)) + "%"
        }

        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return CGSize(width: collectionView.frame.width/2.5, height: collectionView.frame.width/2.5)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let stockDetailVC = StockDetailVC()
        stockDetailVC.stock = winners[indexPath.row]
//        homeVC?.navigationController?.pushViewController(stockDetailVC, animated: true)
        homeVC?.navigationController?.present(stockDetailVC, animated: true, completion: nil)
    }
    
}


class MoverCell: UICollectionViewCell {
    
    let tickerLabel: UILabel = {
        let label = UILabel()
        label.add(text: "Placeholder", font: UIFont(boldWithSize: 18), textColor: .white)
        label.textAlignment = .left
        return label
    }()
    
    let nameLabel: UILabel = {
        let label = UILabel()
        label.add(text: "Subtext placeholder", font: UIFont(regularWithSize: 11), textColor: .white)
        label.textAlignment = .left
        label.numberOfLines = 2
        return label
    }()
    
    let moveLabel: UILabel = {
        let label = UILabel()
        label.add(text: "Move %", font: UIFont(regularWithSize: 30), textColor: UIColor(hex: "7ABE60"))
        label.textAlignment = .center
        return label
    }()
    
    override init(frame: CGRect) {
        super.init(frame: .zero)
        
        self.backgroundColor = .cell_background
        self.layer.masksToBounds = true
        self.layer.cornerRadius = 5
        
        setUpViews()
        
//        let stack: UIStackView = setUpViews()
//        self.contentView.addSubview(stack)
//
//        NSLayoutConstraint.activate([
//            stack.topAnchor.constraint(equalTo: self.contentView.topAnchor),
//            stack.leadingAnchor.constraint(equalTo: self.contentView.leadingAnchor),
//            stack.bottomAnchor.constraint(equalTo: self.contentView.bottomAnchor),
//            stack.trailingAnchor.constraint(equalTo: self.contentView.trailingAnchor)
//        ])
//
//        self.contentView.layer.cornerRadius = 5
    }
    
    func setUpViews() {
        
        contentView.addSubviews(views: [tickerLabel, nameLabel, moveLabel])
        
        tickerLabel.anchor(contentView.topAnchor, left: contentView.leftAnchor, bottom: nil, right: contentView.rightAnchor, topConstant: 20, leftConstant: 10, bottomConstant: 0, rightConstant: 0, widthConstant: 0, heightConstant: 0)
        
        nameLabel.anchor(tickerLabel.bottomAnchor, left: contentView.leftAnchor, bottom: nil, right: contentView.rightAnchor, topConstant: 5, leftConstant: 10, bottomConstant: 0, rightConstant: 0, widthConstant: 0, heightConstant: 0)
        
        moveLabel.anchor(nil, left: contentView.leftAnchor, bottom: contentView.bottomAnchor, right: contentView.rightAnchor, topConstant: 0, leftConstant: 0, bottomConstant: 20, rightConstant: 0, widthConstant: 0, heightConstant: 0)
        
        
//        let stack = UIStackView()
//
//        stack.axis = .vertical
//        stack.translatesAutoresizingMaskIntoConstraints = false
//
//        stack.addArrangedSubview(tickerLabel)
//        stack.addArrangedSubview(nameLabel)
//        stack.addArrangedSubview(moveLabel)
//
//        return stack
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
