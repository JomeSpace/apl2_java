package sim.sellerside;

import java.util.ArrayList;

public class Sellers {
    public ArrayList<Seller> allSellers = new ArrayList<Seller>();
    public Sellers(int n,Double Bid) {
        for (int i = 0; i < n; i++) {
            String Id = String.valueOf(i);
            allSellers.add( new Seller(Id,Bid));
        }
    }
    public void updateBids(){
        for (Seller seller : allSellers) {
                if(seller.status) {
                    seller.updateAsk();
                }
        }
    }
    public Double getAverageAsk() {
        Double sum = 0.0;
        int count = 0;
        for (Seller seller : allSellers) {
            if(seller.status) {
                sum+=seller.Ask;
                count++;
            }
        }
        return sum/count;
    }
    public int getNumActives() {
        int count = 0;
        for (Seller seller : allSellers) {
            if(seller.status) {
                count++;
            }
        }
        return count;
    }
}
