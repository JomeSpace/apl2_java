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
    public void updateBids() {
        for (Buyer buyer : allBuyers) {
                if(buyer.status) {
                    buyer.updateBid();
                }
        }
    }
    public Double getAverageBid() {
        Double sum = 0.0;
        int count = 0;
        for (Buyer buyer : allBuyers) {
            if(buyer.status) {
                sum+=buyer.Bid;
                count++;
            }
        }
        return sum/count;
    }
    public void printBuyers() {
        for (Buyer buyer : allBuyers) {
            System.out.println(buyer.id+" "+buyer.Bid+" "+buyer.status);
        }
    }
}
