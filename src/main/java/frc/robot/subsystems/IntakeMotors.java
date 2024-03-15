// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeMotors extends SubsystemBase {
  /** Creates a new IntakeMotors. */
  public IntakeMotors() {
    speed = 0;
    upperPos = 3950;
    lowerPos = 2520;
    nowPos = lowerPos;

    intakeTalonSRX = new TalonSRX(Constants.intakeTalonSRXPort);
    intakeTalonSRX.configFactoryDefault();
    intakeTalonSRX.config_kP(0, 0);
    intakeTalonSRX.config_kI(0, 0);
    intakeTalonSRX.config_kD(0, 0);
    intakeTalonSRX.setInverted(false);
    intakeTalonSRX.setSensorPhase(false);
    intakeTalonSRX.setNeutralMode(NeutralMode.Coast);

    intakeFlipSRX = new TalonSRX(Constants.intakeFlipSRXPort);
    intakeFlipSRX.config_kP(0, 1);
    intakeFlipSRX.config_kI(0, 0.0001);
    intakeFlipSRX.config_kD(0, 0.1);
    intakeFlipSRX.setInverted(false);
    intakeFlipSRX.setSensorPhase(false);
    intakeFlipSRX.setNeutralMode(NeutralMode.Coast);
    intakeFlipSRX.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
  }

  private TalonSRX intakeTalonSRX;
  private TalonSRX intakeFlipSRX;
  private double speed;
  private double nowPos;
  public double upperPos, lowerPos;

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    intakeTalonSRX.set(ControlMode.PercentOutput, speed);
    intakeFlipSRX.set(ControlMode.Position, nowPos);
    SmartDashboard.putNumber("intakeFlipSRX_Pos", intakeFlipSRX.getSelectedSensorPosition());
  }

  public double getPosition(){
    return intakeFlipSRX.getSelectedSensorPosition();
  }

  public void begIntake() {
    speed = -0.8;
  }

  public void stopIntake() {
    speed = 0;
  }

  public void begSend() {
    speed = 0.4;
  }

  public void up() {
    nowPos = upperPos;
  }

  public void down() {
    nowPos = lowerPos;
  }
}
