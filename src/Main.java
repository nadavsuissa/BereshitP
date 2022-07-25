import PID_CON.PID;

public class Main {
    public static void main(String[] args) {
        System.out.println("Simulation Landing:");
        PID pid = new PID();
        pid.setSetPoint(0.00045);
        pid.setP(0.0000004);
        pid.setI(0.00000155);
        pid.setD(1);
        Simulation s1 = new Simulation(24.8,932,181 * 1000,58.3,30000,0,1,0,350, Bereshit_101.WEIGHT_EMP + 121,pid);
        s1.run();



       /* public PID pid2 = new PID();
        pid2.setSetPoint(0.00045);
        pid2.setP(0.0000004);
        pid2.setI(0.00000155);
        pid2.setD(dt);
        Simulation s2 = new Simulation(24.8,932,181 * 1000,58.3,30000,0,1,0,350,WEIGHT_EMP + fuel,pid2);
        s2.run();
*/

    }
}
