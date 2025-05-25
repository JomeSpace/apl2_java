package sim;

import static random.generator.NormalDistribution.getStdDev;

public class Seller {
    public String id;
    Double Ask;
    Boolean lastRoundSuccess;
    public Seller(String id, Double Ask) {
        this.id = id;
        this.Ask = getStdDev(Ask);
    }
    public void updateAsk(){
        //update prices based on sell success in last cycle
        if(this.lastRoundSuccess==null) {
            this.lastRoundSuccess = false;
        }
        if(this.lastRoundSuccess==true){
            this.Ask += 1;
        }else if(this.lastRoundSuccess==false){
            this.Ask -= 1;
        }
        this.lastRoundSuccess = null;
    }
}
