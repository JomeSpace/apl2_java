package sim;

import java.util.Collections;
import java.util.Random;
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
            if(randallSellers.get(i).status & randallBuyers.get(i).status) {
                Transaction transaction = new Transaction(randallSellers.get(i), randallBuyers.get(i));
            }
        }
    }
}
