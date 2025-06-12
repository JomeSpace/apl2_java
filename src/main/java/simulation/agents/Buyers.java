package simulation.agents;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of Buyer agents participating in the simulation.
 * Provides functionality to manage and evaluate buyers as a group.
 */
public class Buyers {

    public final List<Buyer> allBuyers = new ArrayList<>();

    /**
     * Creates a list of buyers with randomized bids based on the given average bid.
     *
     * @param numberOfBuyers the number of buyers to create
     * @param averageBid     the base bid used to generate each buyer's initial bid
     */
    public Buyers(int numberOfBuyers, double averageBid) {
        for (int i = 0; i < numberOfBuyers; i++) {
            String id = String.valueOf(i);
            allBuyers.add(new Buyer(id, averageBid));
        }
    }

    /**
     * Updates the bids for all active buyers.
     * Inactive buyers are ignored.
     */
    public void updateBids() {
        for (Buyer buyer : allBuyers) {
            if (buyer.getStatus()) {
                buyer.updateBid();
            }
        }
    }

    /**
     * Returns the number of currently active buyers.
     *
     * @return the number of buyers whose status is active
     */
    public int getNumActives() {
        int count = 0;
        for (Buyer buyer : allBuyers) {
            if (buyer.getStatus()) {
                count++;
            }
        }
        return count;
    }
}
