// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterMotors extends SubsystemBase {
  /** Creates a new ShooterMotors. */
  public ShooterMotors() {
    shooterMotorLeft = new TalonFX(Constants.ShooterLMoterID);
    shooterMotorRight = new TalonFX(Constants.ShooterRMoterID);
    shooterArmMotor = new TalonFX(Constants.shooterArmMotorID);
    speed = 0;

    var velocityPIDConfigs = new Slot0Configs();
    velocityPIDConfigs.kP = 0.05;
    velocityPIDConfigs.kI = 2;
    velocityPIDConfigs.kD = 0;
    velocityPIDConfigs.kV = 0;
    // var velocityOutputConfigs = new MotorOutputConfigs();
    // velocityOutputConfigs.NeutralMode = NeutralModeValue.Coast;
    // velocityOutputConfigs.Inverted = inverse ? InvertedValue.Clockwise_Positive :
    // InvertedValue.CounterClockwise_Positive;
    shooterMotorLeft.getConfigurator().apply(velocityPIDConfigs, 0.05);
    shooterMotorRight.getConfigurator().apply(velocityPIDConfigs, 0.05);

    var positionPIDConfigs = new Slot0Configs();
    positionPIDConfigs.kP = 0.2;
    positionPIDConfigs.kI = 0.005;
    positionPIDConfigs.kD = 0.03;
    positionPIDConfigs.kV = 0;
    shooterArmMotor.getConfigurator().apply(positionPIDConfigs, 0.05);

    // restPos = shooterArmMotor.getPosition().getValue() + 0.2; ampPos = restPos - 2;
    m_angleRequest = new DutyCycleOut(0);
  }

  private TalonFX shooterMotorLeft;
  private TalonFX shooterMotorRight;
  private TalonFX shooterArmMotor;

  private double speed;

  private final VelocityDutyCycle m_leftRequest = new VelocityDutyCycle(0.0);
  private final VelocityDutyCycle m_rightRequest = new VelocityDutyCycle(0.0);

  private final DutyCycleOut m_angleRequest;
  private double restPos, ampPos, nowArmPos;

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    shooterMotorLeft.setControl(m_leftRequest.withVelocity(speed * -1));
    shooterMotorRight.setControl(m_rightRequest.withVelocity(speed));

    // shooterArmMotor.setControl(m_angleRequest.withPosition(nowArmPos));
    shooterArmMotor.setControl(m_angleRequest.withOutput(0));

  }

  public double getArmPos() {
    return shooterArmMotor.getPosition().getValueAsDouble();
  }

  public void testBeg() {
    speed = 70;
  }

  public void allStop() {
    speed = 0;
  }

  public void speaker() {
    nowArmPos = restPos;
  }

  public void amp() {
    nowArmPos = ampPos;
  }
}
