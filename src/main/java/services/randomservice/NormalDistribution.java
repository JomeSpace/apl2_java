package services.randomservice;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class to generate normally distributed random numbers.
 */
public class NormalDistribution {

    /**
     * Generates a normally distributed random number with given mean and standard deviation.
     *
     * @param mean   the mean (average) value of the distribution
     * @param stdDev the standard deviation of the distribution
     * @return a random number from the normal distribution with specified mean and stdDev
     */
    public static double getStdDev(double mean, double stdDev) {
        double standardNormal = ThreadLocalRandom.current().nextGaussian();
        return mean + stdDev * standardNormal;
    }

    /**
     * Generates a normally distributed random number with given mean and a fixed standard deviation of 2.
     *
     * @param mean the mean (average) value of the distribution
     * @return a random number from the normal distribution with specified mean and stdDev = 2
     */
    public static double getStdDev(double mean) {
        double standardNormal = ThreadLocalRandom.current().nextGaussian();
        return mean + 2 * standardNormal;
    }
}
