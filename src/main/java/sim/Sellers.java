package sim;

import java.util.ArrayList;

public class Sellers extends pocMain {
    ArrayList<Seller> allSellers = new ArrayList<Seller>();
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
    public Double getAverageAsk(){
        Double sum = 0.0;
        for (Seller seller : allSellers) {
            sum+=seller.Ask;
        }
        return sum/allSellers.size();
    }
}
