package random.generator;
import java.util.concurrent.ThreadLocalRandom;

public class NormalDistribution {

    /**
     * @param mean
     * @param stdDev
     * @return
     */
    public static double getStdDev(double mean, double stdDev) {
        // Generate a standard normal (mean=0, stddev=1)
        double standardNormal = ThreadLocalRandom.current().nextGaussian();
        // Scale and shift to get desired mean and stddev
        return mean + stdDev * standardNormal;
    }
    public static double getStdDev(double mean) {
        // Generate a standard normal (mean=0, stddev=1)
        double standardNormal = ThreadLocalRandom.current().nextGaussian();
        // Scale and shift to get desired mean and stddev
        return mean + 1 * standardNormal;
    }

    /**
     * just testing
     */
    public static void main(String[] args) {
        double mean = 10.0;
        double stdDev = 2.0;

            double value = getStdDev(mean, stdDev);
            System.out.println(value);
    }
}
