package identification.creator;

public class IdService {
    //ArrayList<String> IdCollection= new ArrayList<String>();
    private static int position = 0;

    public IdService() {

    }
    public String createId() {
        //int position = IdCollection.size();
        String Id = String.format("%04d",position);
        position++;
        //IdCollection.add(Id);
        return Id;
    }
}
