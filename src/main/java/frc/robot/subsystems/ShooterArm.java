// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterArm extends SubsystemBase {
  private final int LMotorID = 114;
  private final int RMotorID = 514;

  private final TalonSRX LMotor;
  private final TalonSRX RMotor;

  private final double testLPos = 114;
  private final double testRPos = 514;

  /** Creates a new ShooterArm. */
  public ShooterArm() {
    this.LMotor = new TalonSRX(this.LMotorID);
    this.RMotor = new TalonSRX(this.RMotorID);
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

  public ShooterArm setPos(double posL, double posR) {
    this.LMotor.set(ControlMode.Position, posL);
    this.RMotor.set(ControlMode.Position, posR);
    return this;
  }

  public ShooterArm setTestPos() {
    return this.setPos(this.testLPos, this.testRPos);
  }

}
