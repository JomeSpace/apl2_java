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
            return false;
        }
    }
    public Integer findTransaction(ArrayList<Seller> sellers, ArrayList<Buyer> buyers) {

        return null;
    }
    public static void main(String[] args) {
        Market market = new Market("market");
        Seller sell1 = new Seller("0",10.0);
        Buyer buy1 = new Buyer("0",12.0);
        System.out.println(market.handleTransaction(sell1, buy1));
    }
}
