package sim;

import static random.generator.NormalDistribution.getStdDev;


public class Buyer {
        public String id;
        Double Bid;
        Boolean lastRoundSuccess;

        public Buyer(String id, Double Bid){
            this.id = id;
            this.Bid = getStdDev(Bid);
        }

    public void updateBid(){
        //update bid based on sell success in last cycle
        if(this.lastRoundSuccess==null) {
            this.lastRoundSuccess = false;
        }
        if(this.lastRoundSuccess == true){
            this.Bid -= 1;
        }else if(lastRoundSuccess == false){
            this.Bid += 1;
        }
        this.lastRoundSuccess = null;
    }
}
