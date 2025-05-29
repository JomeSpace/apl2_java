package sim;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;


import static java.lang.Thread.sleep;

public class pocMain {

    public static void main(String[] args) throws InterruptedException {
        int nSeller = 5;  // Number of Seller instances
        int nBuyer = 10;  // Number of Buyer instances

        Sellers sellers = new Sellers(nSeller,20.0);
        Buyers buyers = new Buyers(nBuyer,20.0);

        while(true) {
            Market.handleTransactions(sellers.allSellers, buyers.allBuyers);
            sellers.updateBids();
            buyers.updateBids();

            System.out.println("Sellers:"+sellers.getAverageAsk()+" Buyers:"+buyers.getAverageBid());

            sleep(100);

        }
    }

}
