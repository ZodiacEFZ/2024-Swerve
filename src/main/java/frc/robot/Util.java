package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Util {

    public static <T extends SubsystemBase> T require(Command self, T requirement) {
        self.addRequirements(requirement);
        return requirement;
    }

    public static class Motor {
        /* Motor CAN ID */
        public final int id;
        public TalonFX motor;
        public PID.Profile pid;

        /* Encoder CAN ID */
        public final int eID;
        private TalonSRX encoder;

        private boolean stop;
        private double dest;

        public Motor(int id, int eID) {
            this.id = id;
            this.eID = eID;
        }

        private static double conv(double ori) {
            // https://v6.docs.ctr-electronics.com/en/2023-v6/docs/migration/migration-guide/closed-loop-guide.html
            return ori / 2048;
        }

        public Motor init() {
            this.motor = new TalonFX(this.id);
            this.encoder = new TalonSRX(this.eID);
            return this;
        }

        public Motor setPID(PID.Profile cfg) {
            this.pid = cfg;
            return this;
        }

        public Motor stop() {
            this.stop = true;
            this.motor.set(0);
            return this;
        }

        public Motor go(double dest) {
            this.stop = false;
            this.dest = dest;
            return this;
        }

        public Motor run() {
            if (this.stop) {
                this.motor.set(0);
                return this;
            }
            var curr = this.encoder.getSelectedSensorPosition();
            var req = new PositionDutyCycle(conv(this.dest));
            this.motor.setControl(req);
            return this;
        }
    }
}
