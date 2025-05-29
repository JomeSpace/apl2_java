package sim;

import static random.generator.NormalDistribution.getStdDev;

public class Seller {
    public String id;
    Double Ask;
    Boolean lastRoundSuccess;
    Double askMin;
    Boolean status;

    public Seller(String id, Double Ask) {
        this.id = id;
        this.Ask = getStdDev(Ask);
        this.askMin = this.Ask - 20;
        this.status = true;
    }
    public void updateAsk() {
        //update prices based on sell success in last cycle
        if(this.lastRoundSuccess==null || !this.lastRoundSuccess) {
            this.Ask -= 1;
            //determine status
            if(this.Ask < this.askMin) {
                this.status = false;
                System.out.println("Seller: "+this.id+" is out");
            }
        } else {
            this.Ask += 1;
        }

        this.lastRoundSuccess = null;
    }
}
