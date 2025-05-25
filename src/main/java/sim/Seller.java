package sim;

import static random.generator.NormalDistribution.getStdDev;

public class Seller {
    public String id;
    Double Ask;
    Boolean stock;
    public Seller(String id, Double Ask) {
        this.id = id;
        this.Ask = getStdDev(Ask);
    }
    public void changeAsk(Boolean success){
        //update prices based on sell success in last cycle
        if(success==true){
            Ask += Ask *0.01;
        }else if(success==false){
            Ask -= Ask *0.01;
        }
    }
}
