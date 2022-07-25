import PID_CON.PID;

public class Main {
    public static void main(String[] args) {
        Simulation s1 = new Simulation(1,2,3,4,5,6,7,8,9,10,new PID());
        s1.run();
    }
}
