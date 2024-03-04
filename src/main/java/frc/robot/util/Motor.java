package frc.robot.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

public class Motor {
    /* Motor CAN ID */
    public final int id;
    public TalonFX motor;

    /* Encoder CAN ID */
    public final int eID;
    private TalonSRX encoder;

    private boolean stop;
    /* Zero position of the absolute encoder. */
    private double origin;
    /* Absolute destination position we want. */
    private double dest;

    public Motor(int id, int eID) {
        this.id = id;
        this.eID = eID;
    }

    /* Converts the desired absolute position to the current relative position. */
    private double rel(double abs) {
        // https://v6.docs.ctr-electronics.com/en/2023-v6/docs/migration/migration-guide/closed-loop-guide.html
        var r = this.motor.getPosition().getValue();
        var a = this.encoder.getSelectedSensorPosition();
        return r + (abs - a) / 2048;
    }

    public Motor init() {
        this.motor = new TalonFX(this.id);
        this.encoder = new TalonSRX(this.eID);
        return this;
    }

    public Motor set_pid(PID.Profile cfg) {
        this.motor.getConfigurator().apply(PID.Slot0Configs(cfg));
        return this;
    }

    public Motor set_ori(double origin) {
        this.origin = origin;
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
        var req = new PositionDutyCycle(this.rel(this.dest));
        this.motor.setControl(req);
        return this;
    }

    public Motor reset() {
        this.go(this.origin);
        return this;
    }
}
