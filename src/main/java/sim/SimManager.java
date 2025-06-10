package sim;

import dto.collection.dto;

import java.util.List;


public class SimManager {
    Sellers sellers;
    Buyers buyers;
    private Thread simThread;
    private volatile boolean running = false;

    public SimManager(int nSeller, int nBuyer) {
        this.sellers = new Sellers(nSeller,50.0);
        this.buyers = new Buyers(nBuyer,50.0);
    }

    public void startSimThread() {
        if (running) {
            System.out.println("Simulation already running.");
            return;
        }
        running = true;

        simThread = new Thread(() -> {
            try {
                // Your loop: update buyers, sellers, etc.
                while (running && !Thread.currentThread().isInterrupted()) {
                    // 1) perform Simulation
                    Market.handleTransactions(sellers.allSellers, buyers.allBuyers);

                    this.sellers.updateBids();
                    this.buyers.updateBids();

                    System.out.println("Sellers:" + sellers.getAverageAsk() + " Buyers:" + buyers.getAverageBid());

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        break;
                    }

                    // 2) sleep for 100ms (or however long)
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                // Thread was interrupted during sleep or wait
                Thread.currentThread().interrupt();  // Preserve interrupt status
            }
        },"SimThread");
        simThread.setDaemon(true);
        simThread.start();
    }
    public synchronized void stopSimThread() {
        if (!running) {
            System.out.println("Simulation is not running.");
            return;
        }
        running = false;
        if (simThread != null) {
            simThread.interrupt();
        }
        System.out.println("Stop signal sent to simulation.");
    }

    public List<dto> getBuyerData() {
        return buyers.allBuyers.stream()
                .map(b -> {
                    return new dto(b.getId(), b.getBid(),b.getMaxBid(), b.getStatus());
                })
                .toList();
    }

    public List<dto> getSellerData() {
        return sellers.allSellers.stream()
                .map(a -> {
                    return new dto(a.getId(), a.getAsk(),a.getAskMin(),a.getStatus());
                })
                .toList();
    }
}
