package Park;

public class Car implements CarInterface {
    float dauer;
    float id;
    float preis;
    public Car(float t, float dauer, float preis) {
        id = t;
        this.dauer = dauer;
        this.preis = preis;
    }
    public float getDauer() {
        return dauer;
    }
    public float getId() {
        return id;
    }
    
    public float getPreis() {
    	return preis;
    }
}