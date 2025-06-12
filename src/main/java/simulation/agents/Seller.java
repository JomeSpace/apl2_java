package simulation.agents;

import static services.randomservice.NormalDistribution.getStdDev;

/**
 * Represents a seller agent in the simulation.
 * Each seller has an ask price and adjusts it depending on market success.
 */
public class Seller {

    private final String id;
    private double ask;
    private final double askMin;
    private Boolean lastRoundSuccess;
    private boolean status;

    /**
     * Creates a new seller with a given ID and average ask price.
     * The initial ask is randomized using a normal distribution.
     *
     * @param id  Unique identifier for the seller
     * @param avgAsk Average initial ask price
     */
    public Seller(String id, double avgAsk) {
        this.id = id;
        this.ask = getStdDev(avgAsk);
        this.askMin = 0.5 * this.ask;
        this.status = true;
    }

    /**
     * Updates the ask price based on the result of the last round.
     * If the seller was not successful, the price decreases;
     * otherwise, it increases slightly.
     * If the ask falls below the minimum threshold, the seller becomes inactive.
     */
    public void updateAsk() {
        // If the last round was unsuccessful or unknown, decrease ask
        if (this.lastRoundSuccess == null || !this.lastRoundSuccess) {
            this.ask -= 1;

            if (this.ask < this.askMin) {
                status = false;
            }
        } else {
            this.ask += 1;
        }

        this.lastRoundSuccess = null; // Reset for next round
    }

    /**
     * @return Seller ID
     */
    public String getId() {
        return id;
    }

    /**
     * @return Current ask price
     */
    public double getAsk() {
        return ask;
    }

    /**
     * @return Minimum ask threshold before the seller drops out
     */
    public double getAskMin() {
        return askMin;
    }

    /**
     * @return true if the seller is still active
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * Sets whether the seller was successful in the last round.
     * @param lastRoundSuccess true if sale was made, false otherwise
     */
    public void setLastRoundSuccess(Boolean lastRoundSuccess) {
        this.lastRoundSuccess = lastRoundSuccess;
    }

    /**
     * @return Whether the seller was successful in the last round
     */
    public Boolean getLastRoundSuccess() {
        return this.lastRoundSuccess;
    }
}
