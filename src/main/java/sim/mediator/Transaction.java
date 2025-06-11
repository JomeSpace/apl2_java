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

        if (buyer.getBid() > seller.getAsk()){
            buyer.setLastRoundSuccess(true);
            seller.setLastRoundSuccess(true);
            success = true;}
        else{
            buyer.setLastRoundSuccess(false);
            seller.setLastRoundSuccess(false);
            success = false;
        }
    }
}
