package sim;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public abstract class Market {
    String name;

    public static void handleTransactions(ArrayList<Seller> allSellers, ArrayList<Buyer> allBuyers) {
        int iterations;
        if(allSellers.size() < allBuyers.size()) {
            iterations = allSellers.size();
        }else {
            iterations = allBuyers.size();
        }

        //randomised temp arraylist of Buyers
        ArrayList<Buyer> randallBuyers = allBuyers;
        Collections.shuffle(randallBuyers);

        for(int i = 0; i < iterations; i++) {
            Transaction transaction = new Transaction(allSellers.get(i), randallBuyers.get(i));
        }
    }
}
