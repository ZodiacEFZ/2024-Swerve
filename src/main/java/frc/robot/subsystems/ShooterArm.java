// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PID;

public class ShooterArm extends SubsystemBase {
  private final int LMotorID = 114;
  private final int RMotorID = 514;

  private final TalonSRX LMotor;
  private final TalonSRX RMotor;
  private final PID.Profile LMotorCfg = new PID.Profile(0, 0, 0);;
  private final PID.Profile RMotorCfg = new PID.Profile(0, 0, 0);;

  private final double testLPos = 114;
  private final double testRPos = 514;

  private class Pos {
    public double L;
    public double R;

    public Pos(double L, double R) {
      this.L = L;
      this.R = R;
    }
  }

  private final HashMap<String, Pos> pos = new HashMap<String, Pos>();

  /** Creates a new ShooterArm. */
  public ShooterArm() {
    this.LMotor = new TalonSRX(this.LMotorID);
    this.RMotor = new TalonSRX(this.RMotorID);
    this.LMotor.configureSlot(PID.SlotConfiguration(this.LMotorCfg));
    this.RMotor.configureSlot(PID.SlotConfiguration(this.RMotorCfg));
    this.pos.put("test", new Pos(114, 514));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public ShooterArm setStill() {
    this.LMotor.set(ControlMode.PercentOutput, 0);
    this.RMotor.set(ControlMode.PercentOutput, 0);
    return this;
  }

  private ShooterArm setPos(double posL, double posR) {
    this.LMotor.set(ControlMode.Position, posL);
    this.RMotor.set(ControlMode.Position, posR);
    return this;
  }

  public ShooterArm setPos(String pos) {
    var got = this.pos.get(pos);
    if (got != null) {
      this.LMotor.set(ControlMode.Position, got.L);
      this.RMotor.set(ControlMode.Position, got.R);
    } else {
      this.setStill();
    }
    return this;
  }

  public ShooterArm setTestPos() {
    return this.setPos("test");
  }

}
