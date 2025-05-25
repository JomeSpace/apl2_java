package sim;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;


import static java.lang.Thread.sleep;

public class pocMain {


    public static void main(String[] args) throws InterruptedException {
        int nSeller = 3;  // Number of Seller instances
        int nBuyer = 15;  // Number of Buyer instances

        //parameters
        Double Elastisity=0.1;

        ArrayList<Seller> sellers = new ArrayList<>();
        ArrayList<Buyer> buyers = new ArrayList<>();

        for (int i = 0; i < nSeller; i++) {
            int id = i;
            sellers.add(i, new Seller("seller" + id, 50.0));
        }
        for (int i = 0; i < nBuyer; i++) {
            int id = i;
            buyers.add(i, new Buyer("buyer" + id, 50.0));
        }
        Market base = new Market("market");
        while(true) {
            for (int i = 0; i < 15; i++) {
                Random rand = new Random();

                //random temp array
                ArrayList<Buyer> randomBuyers = new ArrayList<Buyer>(buyers);

                Collections.shuffle(randomBuyers);//shuffle the array to be ordered randomly

                Boolean outcome;
                if(nBuyer < i+1 | nSeller < i+1) {
                    outcome = false;
                }else{
                    outcome = base.handleTransaction(sellers.get(i), randomBuyers.get(i));
                    System.out.println("Sellerid"+sellers.get(i).id+" randomBuyerId:"+randomBuyers.get(i).id);
                }
                if(buyers.size() >= i+1){
                    buyers.get(i).changeBid(outcome);
                    System.out.println(buyers.get(i).id+"called");
                }
                if(sellers.size() >= i+1){
                    System.out.println(outcome);
                    sellers.get(i).changeAsk(outcome);
                }


            }
            for(Buyer buyer : buyers){
                System.out.println("Buyer:"+buyer.id+" "+buyer.Bid.toString());
            }
            System.out.println("\n");
            for(Seller seller : sellers){
                System.out.println("Seller:"+seller.id+" "+seller.Ask.toString());
            }
            System.out.println("\n"+"\n");
            sleep(100);
        }

    }
}
