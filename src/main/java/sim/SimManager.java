package sim;

import java.util.List;


public class SimManager {
    Sellers sellers;
    Buyers buyers;

    public SimManager(int nSeller, int nBuyer) {
        this.sellers = new Sellers(nSeller,20.0);
        this.buyers = new Buyers(nBuyer,20.0);
    }

    public void runSim() {
        Thread simThread = new Thread(() -> {
            while (true) {
                Market.handleTransactions(sellers.allSellers, buyers.allBuyers);

                this.sellers.updateBids();
                this.buyers.updateBids();

                System.out.println("Sellers:" + sellers.getAverageAsk() + " Buyers:" + buyers.getAverageBid());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        simThread.setDaemon(true);
        simThread.start();
    }


    public List<dto> getBuyerData() {
        return buyers.allBuyers.stream()
                .map(b -> {
                    return new dto(b.getId(), b.getBid(),b.getMaxBid());
                })
                .toList();
    }

    public List<dto> getSellerData() {
        return sellers.allSellers.stream()
                .map(a -> {
                    return new dto(a.getId(), a.getAsk(),a.getAskMin());
                })
                .toList();
    }
}
