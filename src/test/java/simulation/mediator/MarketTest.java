package simulation.mediator;

import org.junit.jupiter.api.Test;
import simulation.agents.Buyer;
import simulation.agents.Seller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {

    @Test
    void handleTransactions_shouldMarkSuccessWhenBuyerBidHigherThanSellerAsk() {
        // Setup sellers and buyers
        Seller seller = new Seller("s1", 10.0);
        Buyer buyer = new Buyer("b1", 15.0);  // Buyer bid > seller ask

        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        List<Buyer> buyers = new ArrayList<>();
        buyers.add(buyer);

        // Initially, lastRoundSuccess should be null
        assertNull(seller.getLastRoundSuccess());
        assertNull(buyer.getLastRoundSuccess());

        // Call the method under test
        Market.handleTransactions(sellers, buyers);

        // Since buyer's bid (15) > seller's ask (10), transaction success expected
        assertTrue(buyer.getLastRoundSuccess(), "Buyer should have success = true");
        assertTrue(seller.getLastRoundSuccess(), "Seller should have success = true");
    }

    @Test
    void handleTransactions_shouldMarkFailWhenBuyerBidLowerOrEqualSellerAsk() {
        Seller seller = new Seller("s1", 20.0);
        Buyer buyer = new Buyer("b1", 15.0);  // Buyer bid < seller ask

        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        List<Buyer> buyers = new ArrayList<>();
        buyers.add(buyer);

        Market.handleTransactions(sellers, buyers);

        assertFalse(buyer.getLastRoundSuccess(), "Buyer should have success = false");
        assertFalse(seller.getLastRoundSuccess(), "Seller should have success = false");
    }
}
