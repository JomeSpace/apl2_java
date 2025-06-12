package dtos;

/**
 * Data Transfer Object used to exchange Simulation parameter between UI and the json services to be inputted in the simulation.
 * @param numSellers
 * @param numBuyers
 */
public record ParamDTO(Integer numSellers, Integer numBuyers) {
    /**
     * @return overview of the simulation parameters as a string.
     */
    @Override
    public String toString() {
        return "Simulation Parameters: " +
                "Sellers = " + numSellers + ", " +
                "Buyers = " + numBuyers;
    }
}
