package sim;

import static random.generator.NormalDistribution.getStdDev;


public class Buyer {
        public String id;
        Double Bid;

        public Buyer(String id, Double Bid){
            this.id = id;
            this.Bid = getStdDev(Bid);
        }

        Double setBid(){

            return this.Bid;
        }
    public void changeBid(Boolean success){
        //update bid based on sell success in last cycle
        if(success == true){
            this.Bid -= this.Bid*0.01;
        }else if(success == false){
            this.Bid += this.Bid*0.01;
        }
    }
}
