package sim.mediator;

import sim.buyerside.Buyer;
import sim.sellerside.Seller;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

public abstract class Market {
    String name;

    public static void handleTransactions(ArrayList<Seller> allSellers, ArrayList<Buyer> allBuyers) {
        //determine the number of possible transaction
        int iterations = Math.min(allSellers.size(), allBuyers.size());

        //randomised temp arraylist of Buyers
        ArrayList<Buyer> randallBuyers = new ArrayList<>(allBuyers);
        Collections.shuffle(randallBuyers);

        //randomised temp arraylist of Buyers
        ArrayList<Seller> randallSellers = new ArrayList<>(allSellers);
        Collections.shuffle(randallSellers);

        for(int i = 0; i < iterations; i++) {
            if (randallSellers.get(i).status & randallBuyers.get(i).status) {
                Transaction transaction = new Transaction(randallSellers.get(i), randallBuyers.get(i));
            }
        }

            //bring arrays together
            randallBuyers.sort(Comparator.comparing(Buyer::getId));
            randallSellers.sort(Comparator.comparing(Seller::getId));

           for(int j = 0; j < iterations; j++) {
               allBuyers.get(j).lastRoundSuccess = randallBuyers.get(j).lastRoundSuccess;
               allSellers.get(j).lastRoundSuccess = randallSellers.get(j).lastRoundSuccess;
           }
            //todo add critical component
    }
}
