package innovateMonopoly;

public class TitleProperty {
    private float rentalBase;
    private static float mortgageInteresFactor = 1.1f;
    private float factorRevaluation;
    private float mortgageBase;
    private boolean mortgage;
    private String name;
    private int numHouses;
    private int numHotels;
    private float purchasePrice;
    private float priceBuild;

    Player proprietor;

    TitleProperty(String _name, float _rentalBase, float _factorRevaluation, float _mortgageBase, float _purchasePrice, float _priceBuild){
        name = _name;
        rentalBase = _rentalBase;
        factorRevaluation = _factorRevaluation;
        mortgageBase = _mortgageBase;
        purchasePrice = _purchasePrice;
        priceBuild = _priceBuild;
        mortgage = false;
        numHouses = 0;
        numHotels = 0;
        proprietor = null;
    }

    private float getRentPrice(){
        float RentalPrice;

        if(mortgage || ownerIncarcerated() ){
            RentalPrice = 0;
            return RentalPrice;
        }
        else{
            RentalPrice = rentalBase *(1+(numHouses *0.5f)+(numHotels *2.5f));
            return RentalPrice;
        }

    }

    float getFeeCancelMortgage(){
        return mortgageBase *(1+(numHouses *0.5f)+(numHotels *2.5f)) * mortgageInteresFactor;
    }

    boolean cancelMortgage(Player player){
        if(mortgage && isThisOwner(player)){
            proprietor.pay(getFeeCancelMortgage());
            mortgage = false;

            return true;
        }
        else{
            return false;
        }
    }

    boolean mortgage(Player player){
        if(!mortgage && isThisOwner(player)){
            proprietor.receive(getAmountMortgage());
            mortgage = true;

            return true;
        }
        else {
            return false;
        }
    }

    void transferRent(Player player){
        float rent = getRentPrice();

         if(haveProprietor() && !isThisOwner(player)){
             player.payRent(rent);
             proprietor.receive(rent);
         }
    }

    private boolean ownerIncarcerated(){
        if(proprietor.incarcelated){
            return true;
        }
        else{
            return false;
        }
    }

    int quantityHousesHotels(){
        return numHouses + numHotels;
    }

    boolean destroyHouse(int n, Player player){
        if(isThisOwner(player) && numHouses >= n){
            numHouses -= n;

            return true;
        }
        else {
            return false;
        }
    }

    private float getPriceSell(){
        float sellPrice;

        sellPrice = purchasePrice +(numHouses +5* numHotels)* priceBuild * factorRevaluation;

        return sellPrice;
    }

    boolean buildHouse(Player player){
        boolean buyHouse = false;
        if(isThisOwner(player)){
            proprietor.pay(priceBuild);
            numHouses++;
            buyHouse = true;
        }
        return buyHouse;
    }

    boolean buildHotel(Player player){
        boolean buyHotel = false;

        if(isThisOwner(player)){
            proprietor.pay(priceBuild);
            numHotels++;
            buyHotel = true;
        }

        return buyHotel;
    }

    boolean Buy(Player player){
        boolean buy;

        if(haveProprietor()){
            buy = false;
            return buy;
        }else{
            proprietor = player;
            proprietor.pay(purchasePrice);
            buy = true;
            return buy;
        }
    }

    private boolean isThisOwner(Player player){
        if(player == proprietor){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean getMortgage(){
        return mortgage;
    }

    private float getAmountMortgage(){
        return rentalBase *(1+(numHouses *0.5f)+(numHotels *2.5f));
    }

    String getName() {
        return name;
    }

    int getNumHouses() {
        return numHouses;
    }

    int getNumHotels() {
        return numHotels;
    }

    float getPurchasePrice() {
        return purchasePrice;
    }

    float getPriceBuild() {
        return priceBuild;
    }

    Player getProprietor() {
        return proprietor;
    }

    boolean haveProprietor(){
        if(proprietor != null){
            return true;
        }
        else{
            return false;
        }
    }

    boolean sell(Player player){
        if(isThisOwner(player) && !mortgage){
            player.receive(getPriceSell());
            proprietor =null;
            numHotels =0;
            numHouses =0;
            return true;
        }
        else {
            return false;
        }
    }

    void updateOwnerForConversion(Player player){
        proprietor = player;
    }

}

