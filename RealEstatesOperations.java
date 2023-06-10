package innovateMonopoly;

public class RealEstatesOperations {
    private int numProperty;
    private PropertyManagment managment;

    public RealEstatesOperations(PropertyManagment gest, int ip){
        numProperty = ip;
        managment = gest;
    }

    public PropertyManagment getManagment(){
        return managment;
    }

    public int getNumProperty(){
        return numProperty;
    }
}
