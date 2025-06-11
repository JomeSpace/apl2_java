package sim.buyerside;

import static services.randomservice.NormalDistribution.getStdDev;


public class Buyer {
        public String id;
        Double Bid;
        Boolean lastRoundSuccess;
        final Double bidMax;
        Boolean status;


    public Buyer(String id, Double Bid){
        this.id = id;
        this.Bid = getStdDev(Bid);
        this.bidMax = 2*this.Bid;
        this.status = true;
    }

    public void updateBid(){
        //update bid based on sell success in last cycle
        if(this.lastRoundSuccess==null || !this.lastRoundSuccess) {
            this.Bid += 2;
            //determine status
            if(this.Bid > bidMax) {
                this.status = false;
                System.out.println("Buyer: "+this.id+" is out");
            }
        } else {
            this.Bid -= 2;
        }

        this.lastRoundSuccess = null;
    }

    public String getId() {
        return id;
    }

    public Double getBid() {
        return Bid;
    }

    public Double getMaxBid(){
        return bidMax;
    }

    public boolean getStatus() { return this.status; }

    public void setLastRoundSuccess(Boolean lastRoundSuccess) {
        this.lastRoundSuccess = lastRoundSuccess;
    }

    public Boolean getlastRoundSuccess() {
        return this.lastRoundSuccess;
    }
}
