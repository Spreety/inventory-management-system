package Model;

public class InHouse extends Part {

    private int machineId;

    //constructor
     public InHouse() {
        super(); //pulls constructor from superclass
        this.machineId = machineId;
     }
    //constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        setId(id);  //alternate version
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineId(machineId);
    }
    //getters and setters

    public void setMachineId(int machineId) {

        this.machineId = machineId;
    }

    public int getMachineId() {

        return this.machineId;
    }

}
