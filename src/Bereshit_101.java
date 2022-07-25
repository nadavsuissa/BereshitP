import PID_CON.PID;

/**
 * This class represents the basic flight controller of the Bereshit space craft.
 *
 * @author ben-moshe
 */
public class Bereshit_101 {

    // Static constants of Bereshit
    public static final double WEIGHT_EMP = 165; // kg
    public static final double WEIGHT_FULE = 420; // kg
    public static final double WEIGHT_FULL = WEIGHT_EMP + WEIGHT_FULE; // kg
    // https://davidson.weizmann.ac.il/online/askexpert/%D7%90%D7%99%D7%9A-%D7%9E%D7%98%D7%99%D7%A1%D7%99%D7%9D-%D7%97%D7%9C%D7%9C%D7%99%D7%AA-%D7%9C%D7%99%D7%A8%D7%97
    public static final double MAIN_ENG_F = 430; // N
    public static final double SECOND_ENG_F = 25; // N
    public static final double MAIN_BURN = 0.15; //liter per sec, 12 liter per m'
    public static final double SECOND_BURN = 0.009; //liter per sec 0.6 liter per m'
    public static final double ALL_BURN = MAIN_BURN + 8 * SECOND_BURN;


    public double vs;
    public double hs;
    public double dist;
    public double ang; // zero is vertical (as in landing)
    public double alt; // 2:25:40 (as in the simulation) // https://www.youtube.com/watch?v=JJ0VfRL9AMs
    public double time;
    public double dt; // sec
    public double acc; // Acceleration rate (m/s^2)
    public double fuel; //
    public double weight;
    public PID pid = new PID();

    public double accMax(double weight) {
        return acc(weight, true, 8);
    }

    public double acc(double weight, boolean main, int seconds) {
        double ans = 0;
        if (main) {
            ans += MAIN_ENG_F;
        }
        ans += seconds * SECOND_ENG_F;
        ans /= weight;
        return ans;
    }

    public Bereshit_101(double vs, double hs, double dist, double ang, double alt, double time, double dt, double acc, double fuel, double weight, PID pid) {
        this.vs = vs;
        this.hs = hs;
        this.dist = dist;
        this.ang = ang;
        this.alt = alt;
        this.time = time;
        this.dt = dt;
        this.acc = acc;
        this.fuel = fuel;
        this.weight = weight;
        this.pid = pid;
    }

    public Bereshit_101() { }

    /**
     * Init all the parameters for the simulation
     */
    public void init() {
        // starting point:
        vs = 24.8;
        hs = 932;
        dist = 181 * 1000;
        ang = 58.3; // zero is vertical (as in landing)
        alt = 30000; // meters
        time = 0;
        dt = 1; // sec
        acc = 0; // Acceleration rate (m/s^2)
        fuel = 350;
        weight = WEIGHT_EMP + fuel;
        pid.setSetPoint(0.00045);
        pid.setP(0.0000004);
        pid.setI(0.00000155);
        pid.setD(dt);
    }
}
