



public class Vehicle {
    public enum Type { NORMAL, AMBULANCE, FIRE_TRUCK }
    
    private String id;
    private Type type;
    
    public Vehicle(String id, Type type) {
        this.id = id;
        this.type = type;
    }
    
    public String getId() {
        return id;
    }
    
    public Type getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return id + " (" + type + ")";
    }
}
