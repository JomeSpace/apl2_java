package simulation;

import dtos.ParamDTO;
import dtos.AgentDTO;
import simulation.agents.Buyers;
import simulation.agents.Sellers;
import simulation.mediator.Market;

import java.util.List;

/**
 * "Logikschicht" here happens all the data processing and simulation logic.
 * SimManager controls the lifecycle and data flow of the market simulation.
 * It manages a background thread for continuous execution, allows pausing/resuming,
 * and supplies simulation data for the UI.
 */
public class SimManager {
    private final Sellers sellers;
    private final Buyers buyers;
    private Thread simThread;

    public volatile boolean running = false;
    public volatile boolean paused = false;

    /**
     * Initializes the simulation with parameters from the DTO.
     * @param configs Parameter configuration (e.g., from JSON)
     */
    public SimManager(ParamDTO configs) {
        this.sellers = new Sellers(configs.numSellers(), 20.0);
        this.buyers = new Buyers(configs.numBuyers(), 20.0);
    }

    /**
     * Starts the simulation loop in a separate thread.
     * Handles buyer/seller interactions and lifecycle.
     */
    public void startSimThread() {
        if (running) {
            System.out.println("Simulation already running.");
            return;
        }

        running = true;
        paused = false;

        simThread = new Thread(() -> {
            while (running && !Thread.currentThread().isInterrupted()) {
                try {
                    // Pause logic
                    if (paused) {
                        Thread.sleep(100);
                        continue;
                    }

                    // 1. Simulate interactions
                    Market.handleTransactions(sellers.allSellers, buyers.allBuyers);

                    // 2. Update agent states
                    sellers.updateAsks();
                    buyers.updateBids();

                    // 3. Check end condition
                    if (sellers.getNumActives() == 0 || buyers.getNumActives() == 0) {
                        System.out.println("Simulation ended: No active sellers or buyers.");
                        killSimThread();
                        break;
                    }

                    // 4. Simulate time progression
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    System.out.println("Simulation thread interrupted.");
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            running = false;
            System.out.println("Simulation thread has exited.");
        }, "SimThread");

        simThread.start();
    }

    /**
     * Stops the simulation thread and marks it as not running.
     */
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

    /**
     * Pauses the simulation loop.
     */
    public void pauseThread() {
        if (!running || paused) {
            System.out.println("Simulation is already paused or not running.");
            return;
        }

        paused = true;
        System.out.println("Simulation paused.");
    }

    /**
     * Resumes the simulation if previously paused.
     */
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

    /**
     * @return true if the simulation is currently paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Provides UI-ready data for buyer visualization.
     * @return List of DTOs representing buyers
     */
    public List<AgentDTO> getBuyerData() {
        return buyers.allBuyers.stream()
                .map(b -> new AgentDTO(b.getId(), b.getBid(), b.getMaxBid(), b.getStatus()))
                .toList();
    }

    /**
     * Provides UI-ready data for seller visualization.
     * @return List of DTOs representing sellers
     */
    public List<AgentDTO> getSellerData() {
        return sellers.allSellers.stream()
                .map(s -> new AgentDTO(s.getId(), s.getAsk(), s.getAskMin(), s.getStatus()))
                .toList();
    }
}
