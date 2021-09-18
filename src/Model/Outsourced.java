package Model;

public class Outsourced extends Part {

    private String companyName;

    //constructor
     public Outsourced() {
        super(); //pulls constructor from superclass
         companyName = new String();
     }

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {

        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }

    //getters and setters
    public String getCompanyName(){

        return this.companyName;
    }
    public void setCompanyName(String companyName){

        this.companyName = companyName;
    }
}
