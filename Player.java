package innovateMonopoly;

import java.util.ArrayList;

public class Player implements Comparable<Player> {
    protected static int HouseMax = 4;
    protected static int HousePerHotel = 4;
    protected boolean incarcelated;
    protected static int HotelsMax = 4;
    private String name;
    private int numCurrentSquare;
    protected static float PassPerDeparture = 1000;
    protected static float PriceFreedom = 200;
    private boolean canPurchase;
    private float balance;
    private static float initialBalance = 7500;

    private ArrayList<TitleProperty> properties;
    private Surprise safeguard;

    protected Player(Player other){
        this.name = other.name;
        this.incarcelated = other.incarcelated;
        this.numCurrentSquare = other.numCurrentSquare;
        this.canPurchase = other.canPurchase;
        this.balance = other.balance;
        this.properties = other.properties;
        this.safeguard = other.safeguard;
    }

    Player(String _name){
        name = _name;
        incarcelated = false;
        numCurrentSquare = 0;
        canPurchase = false;
        balance = initialBalance;
        properties = new ArrayList<TitleProperty>();
        safeguard = null;
    }

    boolean mustBeIncarcerated(){
        if(incarcelated){
            return false;
        }else{
            if(haveSafeConduct()){
                this.losingSafeConduct();

                Journal.getInstance().occurEvent("The player has just been saved from prison by his safe-conduct card and consequently loses it.");

                return false;
            }
            else {
                return true;
            }
        }
    }

    boolean incarcerate(int numSquareJail){
       if(mustBeIncarcerated()){
           moveToSquare(numSquareJail);

           incarcelated = true;

           Journal.getInstance().occurEvent("The player " + name + " has just entered jail");

           return true;
       }
       else{
           return false;
       }
    }

    boolean obtainSafeConduct(Surprise s){
        if(incarcelated){
            return false;
        }
        else {
            safeguard = s;

            return true;
        }
    }

    private void losingSafeConduct(){
        safeguard.used();

        safeguard = null;
    }

    boolean haveSafeConduct(){
        if(safeguard == null){
            return false;
        }
        else{
            return true;
        }
    }

    boolean canBuySquare(){
        if(incarcelated){
            canPurchase = false;
        }
        else{
            canPurchase = true;
        }
        return canPurchase;
    }

    boolean pay(float amonut){
        boolean pay;

        pay = changeBalance(amonut*-1);

        return pay;
    }

    boolean payTax(float amount){
        if(incarcelated){
            return false;
        }
        else{
            boolean payTax;

            payTax  = pay(amount);

            return payTax;
        }
    }

    boolean payRent(float amount){
        if(incarcelated){
            return false;
        }
        else{
            boolean payRent;

            payRent  = pay(amount);

            return payRent;
        }
    }

    boolean receive(float amount){
        if(incarcelated){
            return false;
        }
        else{
            boolean received;

            received = changeBalance(amount);

            return received;
        }
    }

    boolean changeBalance(float amount){
        balance += amount;

        Journal.getInstance().occurEvent("The balance has increased by: " + amount);

        return true;
    }

    boolean moveToSquare(int numSquare){
        if(incarcelated){
            return false;
        }else{
            numCurrentSquare = numSquare;

            canPurchase = false;

            Journal.getInstance().occurEvent("The current square is : " + numSquare + " and the player " + name + " is moving there");

            return true;
        }
    }

    private boolean canSpend(float price){
      if(incarcelated){
          return false;
      }else{
          if(balance >= price){
              return true;
          }else{
              return false;
          }
      }

    }

    boolean sell(int ip){
        if(incarcelated){
            return false;
        }else {
            if(existsProperty(ip)){
                if(properties.get(ip).sell(this)){
                    properties.remove(ip);
                    Journal.getInstance().occurEvent("The sales process has been carried out correctly");
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    boolean hasSomethingToManage(){
        if(properties.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    private boolean canGetOutOfJailPaying(){
        if(balance >= PriceFreedom){
            return true;
        }
        else {
            return false;
        }
    }

    boolean exitJailPaying(){
        if(incarcelated && canGetOutOfJailPaying()){
            pay(PriceFreedom);

            incarcelated = false;

            Journal.getInstance().occurEvent("The player "+ name + " has managed to get out of jail by paying " + PriceFreedom);

            return true;
        }
        else{
            return false;
        }
    }

    boolean exitJailThrowing(){
        if(Dice.getInstance().exitFromJail()){
            incarcelated = false;

            Journal.getInstance().occurEvent("The player " + name + " has managed to get out of prison by means of the dice");

            return true;
        }
        else {
            return false;
        }
    }

    boolean passThroughDeparture(){
        changeBalance(PassPerDeparture);

        Journal.getInstance().occurEvent("The player " + name + " has just received " + PassPerDeparture + " for passing through departure");

        return true;
    }

    public int compareTo (Player other){
        int max = Float.compare(balance, other.balance);

        return max;
    }

    boolean cancelMortgage(int ip){
        boolean result = false;

        if(incarcelated){
            return result;
        }

        if(existsProperty(ip)){
            TitleProperty property;
            property = properties.get(ip);

            float amount;
            amount = property.getFeeCancelMortgage();

            boolean canSpend;
            canSpend = canSpend(amount);

            if(canSpend){
                result = property.cancelMortgage(this);
            }
            if(result){
                Journal.getInstance().occurEvent("The player "+ name + " cancels the mortgage on the property " + ip);
            }
        }
        return result;
    }

    boolean buy(TitleProperty title){
      boolean result = false;

      if(incarcelated){
          return result;
      }

      if(getCanPurchase()){
          float price = title.getPurchasePrice();

          if(canSpend(price)){
              result = title.Buy(this);

              if(result){
                  properties.add(title);
                  Journal.getInstance().occurEvent("The player " + this + " purchase the property " + title.toString());
              }
              canPurchase = false;
          }
      }

    return result;
    }

    boolean buildHouse(int ip){
        boolean result = false;

        if(incarcelated){
            return result;
        }

        if(!incarcelated){
            boolean exist;
            exist = this.existsProperty(ip);
            if(!incarcelated && exist){
                TitleProperty propiedad;
                propiedad = properties.get(ip);

                boolean canBuildHouse;
                canBuildHouse = this.canBuildHouse(propiedad);

                float price;
                price = propiedad.getPriceBuild();

                if(canSpend(price) && propiedad.getNumHouses() < getHouseMax()){
                    canBuildHouse = true;
                }

                if(!incarcelated && exist && canBuildHouse){
                    result = propiedad.buildHouse(this);
                    if(result){
                        Journal.getInstance().occurEvent("The player " + name + " builds house on the property" + ip);
                    }
                }

            }
        }

        return result;
    }

    boolean buildHotel(int ip){
        boolean result = false;

        if(incarcelated){
            return result;
        }

        if(existsProperty(ip)){
            TitleProperty property;
            property = properties.get(ip);

            boolean canBuildHotel = false;
            canBuildHotel = this.canBuildHotel(property);

            float price;
            price = property.getPriceBuild();

            if(canSpend(price)){
                if(property.getNumHotels() < HotelsMax){
                    if(property.getNumHouses() >= getHousePerHotel()){
                        canBuildHotel = true;
                    }
                }
            }

            if(canBuildHotel){
                result = property.buildHotel(this);

                int casasPorHotel;
                casasPorHotel = this.getHousePerHotel();

                property.destroyHouse(casasPorHotel,this);

                Journal.getInstance().occurEvent("The player " + name + " builds hotel in the property " + ip);
            }

        }

        return result;
    }

    boolean mortgage(int ip){
        boolean result = false;

        if(incarcelated){
            return result;
        }

        if(existsProperty(ip)){
            TitleProperty property;
            property = properties.get(ip);

            result = property.mortgage(this);
        }

        if(result){
            Journal.getInstance().occurEvent("The player " + name + " mortgages the property " + ip);
        }
        return result;
    }

    int quantityHousesHotels() {
        int HouseHotels = 0;

        for (int i = 0; i < properties.size(); i++){
            HouseHotels = properties.get(i).quantityHousesHotels();
        }

        return HouseHotels;
    }

    public int getNumCurrentSquare() {
        return numCurrentSquare;
    }


    private int getHouseMax() {
        return HouseMax;
    }

    int getHousePerHotel() {
        return HousePerHotel;
    }

    private int getHotelsMax() {
        return HotelsMax;
    }

    protected String getName() {
        return name;
    }

    int getnumCurrentSquare(){
        return numCurrentSquare;
    }

    private float getPriceFreedom() {
        return PriceFreedom;
    }

    private float getMoneyPassPerDeparture() {
        return PassPerDeparture;
    }

    protected ArrayList<TitleProperty> getProperties() {
        return properties;
    }

    private boolean getCanPurchase(){
        return canPurchase;
    }

    protected float getBalance() {
        return balance;
    }

    boolean inBankrupt(){
        if(balance < 0){
            return true;
        }
        else{
            return false;
        }

    }

    private boolean existsProperty(int ip){
        if(!properties.isEmpty() && properties.get(ip).getProprietor() == this){
            return true;
        }else{
            return false;
        }
    }

    public boolean isIncarcelated(){
        return incarcelated;
    }

    private boolean canBuildHouse(TitleProperty property){
        if(property.getNumHouses() < HouseMax){
            return true;
        }else{
            return false;
        }
    }

    private boolean canBuildHotel(TitleProperty property){
        if(property.getNumHouses() < HotelsMax){
            return true;
        }else{
            return false;
        }
    }
}


