package simulation.agents;

import static services.randomservice.NormalDistribution.getStdDev;

/**
 * Represents a buyer agent in the market simulation.
 * The buyer places bids and adjusts them based on success in past transactions.
 */
public class Buyer {

    private final String id;
    private Double bid;
    private final Double bidMax;
    private Boolean lastRoundSuccess; // null means no transaction info yet
    private Boolean status;

    /**
     * Constructs a Buyer with a randomized initial bid based on the given base value.
     *
     * @param id      the unique identifier of the buyer
     * @param baseBid the average bid value to be randomized with standard deviation
     */
    public Buyer(String id, Double baseBid) {
        this.id = id;
        this.bid = getStdDev(baseBid);        // apply random variability
        this.bidMax = 2 * this.bid;           // max allowed bid (threshold)
        this.status = true;                   // active when created
        this.lastRoundSuccess = null;        // no transactions done yet
    }

    /**
     * Updates the buyer's bid depending on the success of the last transaction round.
     * <ul>
     *     <li>If last round was unsuccessful or unknown (null), increase the bid by 1.</li>
     *     <li>If bid exceeds max allowed bid, deactivate the buyer.</li>
     *     <li>If last round was successful, decrease the bid by 1.</li>
     * </ul>
     * After updating, resets {@code lastRoundSuccess} to {@code null} for the next round.
     */
    public void updateBid() {
        if (this.lastRoundSuccess == null || !this.lastRoundSuccess) {
            this.bid += 1;
            if (this.bid > this.bidMax) {
                this.status = false; // buyer is no longer active
            }
        } else {
            this.bid -= 1;
        }
        this.lastRoundSuccess = null; // reset for next round
    }

    // --- Getters and setters ---

    public String getId() {
        return id;
    }

    public Double getBid() {
        return bid;
    }

    public Double getMaxBid() {
        return bidMax;
    }

    public boolean getStatus() {
        return status;
    }

    /**
     * Returns the success status of the last transaction round.
     * Can be {@code null} if no transaction info is available yet.
     *
     * @return {@code Boolean.TRUE} if last round succeeded, {@code Boolean.FALSE} if failed,
     *         or {@code null} if unknown
     */
    public Boolean getLastRoundSuccess() {
        return lastRoundSuccess;
    }

    public void setLastRoundSuccess(Boolean lastRoundSuccess) {
        this.lastRoundSuccess = lastRoundSuccess;
    }
}
