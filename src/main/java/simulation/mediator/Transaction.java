package simulation.mediator;

import simulation.agents.Buyer;
import simulation.agents.Seller;

/**
 * Represents a transaction attempt between a buyer and a seller.
 * Determines whether the transaction is successful based on the bid and ask values.
 */
public class Transaction extends Market {

    private final boolean success;

    /**
     * Constructs a Transaction and evaluates whether the buyer's bid is sufficient
     * to meet the seller's ask.
     *
     * @param seller The seller participating in the transaction
     * @param buyer  The buyer participating in the transaction
     */
    public Transaction(Seller seller, Buyer buyer) {
        if (buyer.getBid() > seller.getAsk()) {
            buyer.setLastRoundSuccess(true);
            seller.setLastRoundSuccess(true);
            this.success = true;
        } else {
            buyer.setLastRoundSuccess(false);
            seller.setLastRoundSuccess(false);
            this.success = false;
        }
    }

    /**
     * @return true if the transaction was successful; false otherwise
     */
    public boolean isSuccess() {
        return success;
    }
}
