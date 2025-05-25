package sim;

import java.util.ArrayList;

public class Buyers {
    ArrayList<Buyer> allBuyers = new ArrayList<Buyer>();
    public Buyers(int n, double bid) {
        for (int i = 0; i < n; i++) {
            String Id = String.valueOf(i);
            allBuyers.add(new Buyer(Id,bid));
        }
    }
    public void updateBids(){
        for (Buyer buyer : allBuyers) {
                buyer.updateBid();
        }
    }
    public Double getAverageBid(){
        Double sum = 0.0;
        for (Buyer buyer : allBuyers) {
            sum+=buyer.Bid;
        }
        return sum/allBuyers.size();
    }
}
