package simulation.agents;

import java.util.ArrayList;
import java.util.List;

/**
 * A container for managing a group of Seller agents.
 * Responsible for initializing sellers and updating their ask prices.
 */
public class Sellers {

    public final List<Seller> allSellers = new ArrayList<>();

    /**
     * Constructs a group of sellers with initial ask values based on a given average.
     *
     * @param count The number of sellers to create
     * @param avgAsk The average ask value for initializing seller prices
     */
    public Sellers(int count, double avgAsk) {
        for (int i = 0; i < count; i++) {
            String id = String.valueOf(i);
            allSellers.add(new Seller(id, avgAsk));
        }
    }

    /**
     * Updates ask prices for all active sellers based on their last round performance.
     */
    public void updateAsks() {
        for (Seller seller : allSellers) {
            if (seller.getStatus()) {
                seller.updateAsk();
            }
        }
    }

    /**
     * Counts the number of sellers that are still active in the market.
     *
     * @return Number of active sellers
     */
    public int getNumActives() {
        int count = 0;
        for (Seller seller : allSellers) {
            if (seller.getStatus()) {
                count++;
            }
        }
        return count;
    }

    /**
     * @return List of all sellers
     */
    public List<Seller> getAllSellers() {
        return allSellers;
    }
}
