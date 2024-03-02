// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PID;

public class ShooterMotors extends SubsystemBase {
  /** Creates a new ShooterMotors. */
  public ShooterMotors() {
    shooterMotorLeft = new TalonFX(Constants.ShooterLMoterID);
    shooterMotorRight = new TalonFX(Constants.ShooterRMoterID);
    speed = 0;
    var arg = new PID.Profile(0.05, 2, 0);
    var velocityPIDConfigs = PID.Slot0Configs(arg);
    velocityPIDConfigs.kV = 0;
    // var velocityOutputConfigs = new MotorOutputConfigs();
    // velocityOutputConfigs.NeutralMode = NeutralModeValue.Coast;
    // velocityOutputConfigs.Inverted = inverse ? InvertedValue.Clockwise_Positive :
    // InvertedValue.CounterClockwise_Positive;
    shooterMotorLeft.getConfigurator().apply(velocityPIDConfigs, 0.05);
    shooterMotorRight.getConfigurator().apply(velocityPIDConfigs, 0.05);

  }

  private TalonFX shooterMotorLeft;
  private TalonFX shooterMotorRight;

  private double speed;

  private final DutyCycleOut m_leftRequest = new DutyCycleOut(0.0);
  private final DutyCycleOut m_rightRequest = new DutyCycleOut(0.0);

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    shooterMotorLeft.setControl(m_leftRequest.withOutput(speed));
    shooterMotorRight.setControl(m_rightRequest.withOutput(speed * -1));
  }

  public void testBeg() {
    speed = 0.5;
  }

  public void allStop() {
    speed = 0;
  }
}
