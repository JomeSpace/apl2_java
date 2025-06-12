package simulation;

import dtos.ParamDTO;

/**
 * Validates parameter ranges for simulation input.
 */
public class ParameterRanges {

    public static final int MIN_SELLERS = 1;
    public static final int MAX_SELLERS = 20;
    public static final int MIN_BUYERS = 1;
    public static final int MAX_BUYERS = 20;

    /**
     * Checks if the parameters in configs are within allowed ranges.
     * Throws IllegalArgumentException if a parameter is out of range.
     *
     * @param configs the parameter DTO to validate
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public static void checkParameterRanges(ParamDTO configs) {
        if (configs.numSellers() < MIN_SELLERS || configs.numSellers() > MAX_SELLERS) {
            throw new IllegalArgumentException("Number of sellers must be between " + MIN_SELLERS + " and " + MAX_SELLERS + ".");
        }
        if (configs.numBuyers() < MIN_BUYERS || configs.numBuyers() > MAX_BUYERS) {
            throw new IllegalArgumentException("Number of buyers must be between " + MIN_BUYERS + " and " + MAX_BUYERS + ".");
        }
    }
}
