package sim.mediator;

import services.identificationservice.IdService;
import sim.buyerside.Buyer;
import sim.sellerside.Seller;

public class Transaction extends Market {
    String Id;
    Boolean success;

    public Transaction(Seller seller, Buyer buyer) {
        IdService IdService = new IdService();
        this.Id=IdService.createId();

        if (buyer.Bid > seller.Ask){
            buyer.lastRoundSuccess = true;
            seller.lastRoundSuccess = true;
            success = true;}
        else{
            buyer.lastRoundSuccess = false;
            seller.lastRoundSuccess = false;
            success = false;
        }
    }
}
