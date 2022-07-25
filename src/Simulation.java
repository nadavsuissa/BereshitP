import PID_CON.PID;


public class Simulation {
    Bereshit_101 bereshit = new Bereshit_101();

    Simulation(double vs, double hs, double dist, double ang, double alt, double time,
               double dt, double acc, double fuel, double weight, PID pid) {
        bereshit.init(vs,hs,dist,ang,alt,time,dt,acc,fuel,weight,pid);
    }

    public void run() {

        String title = String.format("%10s %10s %10s %10s %10s %10s %10s %10s ",
                "time", "vs", "hs", "dist", "alt", "ang", "weight", "acc");
        System.out.println(title);
        double NN = 0.7; // rate[0,1]

        // ***** main simulation loop ******
        while (bereshit.alt > 0) {
            if (bereshit.time % 10 == 0 || bereshit.alt < 100) {
                String log = String.format("%10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f %10.4f ",
                        bereshit.time,
                        bereshit.vs,
                        bereshit.hs,
                        bereshit.dist,
                        bereshit.alt,
                        bereshit.ang,
                        bereshit.weight,
                        bereshit.acc,
                        bereshit.fuel);
                System.out.println(log);
            }

            // over 2 km above the ground
            if (bereshit.alt > 2000) {
                // maintain a vertical speed of [20-25] m/s
                if (bereshit.vs > 25) {
                    NN += 0.003 * bereshit.dt;
                } // more power for braking
                if (bereshit.vs < 20) {
                    NN -= 0.003 * bereshit.dt;
                } // less power for braking
            }
            // lower than 2 km - horizontal speed should be close to zero
            else {
                if (bereshit.ang > 3) {
                    bereshit.ang -= 3;
                } // rotate to vertical position.
                else {
                    bereshit.ang = 0;
                }
                double pid_output = bereshit.pid.getOutput(bereshit.time, bereshit.alt);
                if (bereshit.hs < 2) {
                    bereshit.hs = 0;
                }
                if (pid_output > 10) {
                    NN = 0;
                }
                if (pid_output < 0) {
                    NN = 0.5;
                }
                if (bereshit.alt < 110) { // close to the ground!
                    NN = 1;
                    if (bereshit.vs < 5) {
                        NN = 0.7;
                    }
                }
            }
            if (bereshit.alt < 5) {
                NN = 0.4;
            }

            // main computations
            double ang_rad = Math.toRadians(bereshit.ang);
            double h_acc = Math.sin(ang_rad) * bereshit.acc;
            double v_acc = Math.cos(ang_rad) * bereshit.acc;
            double vacc = Moon.getAcc(bereshit.hs);
            bereshit.time += bereshit.dt;
            double dw = bereshit.dt * Bereshit_101.ALL_BURN * NN;
            // check if there is enough fuel
            if (bereshit.fuel > 0) {
                bereshit.fuel -= dw;
                bereshit.weight = Bereshit_101.WEIGHT_EMP + bereshit.fuel;
                bereshit.acc = NN * bereshit.accMax(bereshit.weight);
            }
            else { // ran out of fuel
                bereshit.acc = 0;
            }

            v_acc -= vacc;
            if (bereshit.hs > 0) {
                bereshit.hs -= h_acc * bereshit.dt;
            }
            bereshit.dist -= bereshit.hs * bereshit.dt;
            bereshit.vs -= v_acc * bereshit.dt;
            bereshit.alt -= bereshit.dt * bereshit.vs;
        }
    }
}
