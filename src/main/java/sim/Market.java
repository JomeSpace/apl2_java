package sim;

import java.util.Random;
import java.util.ArrayList;

public class Market {
    String name;
    public Market(String name) {
        this.name=name;
    }
    public Boolean handleTransaction(Seller seller,Buyer buyer) {
        if (buyer.Bid >= seller.Ask){
            return true;}
        else{
            return false;}
    }
    public Integer findTransaction(ArrayList<Seller> sellers, ArrayList<Buyer> buyers) {

        return null;
    }
}
