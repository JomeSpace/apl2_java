package sim;

import static random.generator.NormalDistribution.getStdDev;


public class Buyer {
        public String id;
        Double Bid;
        Boolean lastRoundSuccess;
        Double bidMax;
        Boolean status;


    public Buyer(String id, Double Bid){
        this.id = id;
        this.Bid = getStdDev(Bid);
        this.bidMax = this.Bid + 20;
        this.status = true;

    }

    public void updateBid(){
        //update bid based on sell success in last cycle
        if(this.lastRoundSuccess==null || !this.lastRoundSuccess) {
            this.Bid += 1;
            //determine status
            if(this.Bid > bidMax) {
                this.status = false;
                System.out.println("Buyer: "+this.id+" is out");
            }
        } else {
            this.Bid -= 1;
        }

        this.lastRoundSuccess = null;
    }
}
