

public class TrafficLight {
    public enum Signal { RED, YELLOW, GREEN }
    
    private Signal currentSignal = Signal.RED;
    
    public Signal getSignal() {
        return currentSignal;
    }
    
    public void setSignal(Signal signal) {
        this.currentSignal = signal;
    }
    
    public void nextSignal() {
        switch (currentSignal) {
            case RED -> currentSignal = Signal.GREEN;
            case GREEN -> currentSignal = Signal.YELLOW;
            case YELLOW -> currentSignal = Signal.RED;
        }
    }
}
