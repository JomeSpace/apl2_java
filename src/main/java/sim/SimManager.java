package sim;

import dto.collection.ParamDTO;
import dto.collection.DTO;
import sim.buyerside.Buyers;
import sim.mediator.Market;
import sim.sellerside.Sellers;

import java.util.List;


public class SimManager {
    Sellers sellers;
    Buyers buyers;
    private Thread simThread;
    public volatile boolean running = false;
    public volatile boolean paused = false;

    public SimManager(ParamDTO configs) {
        this.sellers = new Sellers(configs.numSellers(), 20.0);
        this.buyers = new Buyers(configs.numBuyers(), 20.0);
    }

    public void startSimThread() {
        if (running) {
            System.out.println("Simulation already running.");
            return;
        }

        running = true;
        paused = false;

        simThread = new Thread(() -> {
            // Your loop: update buyers, sellers, etc.
            while (running && !Thread.currentThread().isInterrupted()) {
                if (paused) {
                    try {
                        Thread.sleep(100); // Sleep briefly while paused
                        continue;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                // 1) perform Simulation
                Market.handleTransactions(sellers.allSellers, buyers.allBuyers);

                // Update bids for both sellers and buyers
                this.sellers.updateBids();
                this.buyers.updateBids();

                // 2) check if simulation should continue
                if(sellers.getNumActives() == 0 || buyers.getNumActives() == 0) {
                    System.out.println("Simulation ended: No active sellers or buyers.");
                    killSimThread();
                }
                //sleep to simulate time passing
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        },"SimThread");

        simThread.start();
    }
    public synchronized void killSimThread() {
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
    public void pauseThread() {
        if (!running || paused) {
            System.out.println("Simulation is already paused or not running.");
            return;
        }
        paused = true;
        System.out.println("Simulation paused.");
    }

    public void resumeThread() {
        if (!running) {
            System.out.println("Simulation is not running.");
            return;
        }
        if (!paused) {
            System.out.println("Simulation is already running.");
            return;
        }
        paused = false;
        System.out.println("Simulation resumed.");
    }

    public boolean isPaused() {
        return paused;
    }

    public List<DTO> getBuyerData() {
        return buyers.allBuyers.stream()
                .map(b -> {
                    return new DTO(b.getId(), b.getBid(),b.getMaxBid(), b.getStatus());
                })
                .toList();
    }

    public List<DTO> getSellerData() {
        return sellers.allSellers.stream()
                .map(a -> {
                    return new DTO(a.getId(), a.getAsk(),a.getAskMin(),a.getStatus());
                })
                .toList();
    }
}
