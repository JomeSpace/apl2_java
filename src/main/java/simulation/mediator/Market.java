package simulation.mediator;

import simulation.agents.Buyer;
import simulation.agents.Seller;

import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for a market that handles transactions
 * between buyers and sellers.
 */
public abstract class Market {

    /**
     * Handles one round of transactions between buyers and sellers.
     * Buyers and sellers are randomly shuffled, and each pair is
     * matched for a transaction.
     * After processing, the transaction result (success or failure)
     * is transferred back to the original agents.
     *
     * @param allSellers List of all sellers in the market
     * @param allBuyers  List of all buyers in the market
     */
    public static void handleTransactions(List<Seller> allSellers, List<Buyer> allBuyers) {
        int iterations = Math.min(allSellers.size(), allBuyers.size());

        // Create shuffled copies of buyers and sellers
        ArrayList<Buyer> shuffledBuyers = new ArrayList<>(allBuyers);
        Collections.shuffle(shuffledBuyers);

        ArrayList<Seller> shuffledSellers = new ArrayList<>(allSellers);
        Collections.shuffle(shuffledSellers);

        // Process transactions between shuffled buyers and sellers
        for (int i = 0; i < iterations; i++) {
            Buyer buyer = shuffledBuyers.get(i);
            Seller seller = shuffledSellers.get(i);

            if (buyer.getStatus() && seller.getStatus()) {
                new Transaction(seller, buyer);
            }
        }

        // Restore the order of buyers and sellers by ID to update original lists
        shuffledBuyers.sort(Comparator.comparing(Buyer::getId));
        shuffledSellers.sort(Comparator.comparing(Seller::getId));

        for (int j = 0; j < iterations; j++) {
            allBuyers.get(j).setLastRoundSuccess(shuffledBuyers.get(j).getLastRoundSuccess());
            allSellers.get(j).setLastRoundSuccess(shuffledSellers.get(j).getLastRoundSuccess());
        }
    }
}
