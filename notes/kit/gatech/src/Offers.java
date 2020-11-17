
import java.util.ArrayList;

public class Offers
{
    //offer variables.
    private String offerType = "";
    private String offerStream = "";
    private String offerEventName = "";
    private int offerEventYear = 0;
    private int offerEventPrice = 0;

    //offers. getters. setters.
    public String getOfferType() { return this.offerType; }
    public void setOfferType(String name) { this.offerType = name; }
    public String getOfferStream() { return this.offerStream; }
    public void setOfferStream(String name) { this.offerStream = name; }
    public String getOfferEventName() { return this.offerEventName; }
    public void setOfferEventName(String name) { this.offerEventName = name; }
    public int getOfferEventYear() { return this.offerEventYear; }
    public void setOfferEventYear(int num) { this.offerEventYear = num; }
    public int getOfferEventPrice() { return this.offerEventPrice; }
    public void setOfferEventPrice(int num) { this.offerEventPrice = num; }

    //default constructor
    public Offers(){}

    public void displayOffers(ArrayList<Offers> offersList)
    {
        if(offersList.size() == 0 || offersList.isEmpty())
        {
            return;
        }
        //display all offers
        for(Offers offer: offersList)
        {
            System.out.print(offer.getOfferStream() + ","
                    + offer.getOfferType() + ","
                    + offer.getOfferEventName() + ","
                    + offer.getOfferEventYear());

            if(offer.getOfferType().contentEquals("ppv"))
            {//append the ppv price
                System.out.print("," + offer.getOfferEventPrice());
            }
            System.out.println();
        }
    }

    //check if the event has been offered
    public boolean checkIfEventOffered(String offerEventName, ArrayList<Offers> offerList)
    {
        boolean exists = false;
        if(offerList.size() > 0)//list contains at least one record
        {
            for(Offers offer: offerList)
            {
                if(offer.getOfferEventName().contentEquals(offerEventName))
                {
                    exists = true;
                    break;
                }
            }
        }
        return exists;
    }

}
