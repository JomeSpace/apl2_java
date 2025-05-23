package sim;

import java.util.Random;

import static java.lang.Thread.sleep;

public class pocMain {


    public static void main(String[] args) throws InterruptedException {
        int nSeller = 5;  // Number of Seller instances
        int nBuyer = 5;  // Number of Buyer instances

        Seller[] sellers = new Seller[nSeller];
        Buyer[] buyers = new Buyer[nBuyer];

        for (int i = 0; i < nSeller; i++) {
            int id = i;
            sellers[i] = new Seller("seller"+id,100.0);
        }
        for (int i = 0; i < nBuyer; i++) {
            int id = i;
            buyers[i] = new Buyer("buyer"+id,10.0);
        }
        Market base = new Market("market");
        while(true) {
            for (int i = 0; i < nSeller; i++) {
                Random rand = new Random();

                //random temp array

                Boolean outcome = base.handleTransaction(sellers[indexSeller],buyers[indexBuyer]);

                System.out.println(outcome+sellers[indexSeller].Ask.toString()+" "+buyers[indexBuyer].Bid.toString()+" "+sellers[indexSeller].Ask.toString());

                sellers[indexSeller].changeAsk(outcome);
                buyers[indexBuyer].changeBid(outcome);
            }
            sleep(1000);
        }

    }
}
