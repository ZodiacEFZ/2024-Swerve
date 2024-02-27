// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeMotors extends SubsystemBase {
  /** Creates a new IntakeMotors. */
  public IntakeMotors() {
    speed = 0;

    intakeTalonSRX = new TalonSRX(Constants.intakeTalonSRXPort);
    intakeTalonSRX.configFactoryDefault();
    intakeTalonSRX.config_kP(0, 0);
    intakeTalonSRX.config_kI(0, 0);
    intakeTalonSRX.config_kD(0, 0);
    intakeTalonSRX.setInverted(false);
    intakeTalonSRX.setSensorPhase(false);
    intakeTalonSRX.setNeutralMode(NeutralMode.Coast);
  }

  private TalonSRX intakeTalonSRX;
  private double speed;

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    intakeTalonSRX.set(ControlMode.PercentOutput, speed);
  }

  public void begIntake() {
    speed = 0.5;
  }

  public void stopIntake() {
    speed = 0;
  }
}
