// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.PID;

public class SwerveModule extends SubsystemBase {
  /** Creates a new SwerveModule. */
  public SwerveModule(int number, int aID, int vID, int eID, int zeroPos, boolean inverse) {
    this.number = number;
    this.zeroPos = zeroPos;

    angle = 0;

    // velocityMotor.getConfigurator().apply(new TalonFXConfiguration());
    var arg_velocity = new PID.Profile(0.36, 0, 0);
    var velocityPIDConfigs = PID.Slot0Configs(arg_velocity);
    velocityPIDConfigs.kV = 0;
    var velocityOutputConfigs = new MotorOutputConfigs();
    velocityOutputConfigs.NeutralMode = NeutralModeValue.Coast;
    velocityOutputConfigs.Inverted = inverse ? InvertedValue.Clockwise_Positive
        : InvertedValue.CounterClockwise_Positive;
    velocityMotor = new TalonFX(vID);
    velocityMotor.getConfigurator().apply(velocityPIDConfigs, 0.05);
    velocityMotor.getConfigurator().apply(velocityOutputConfigs);

    // angleMotor.getConfigurator().apply(new TalonFXConfiguration());
    var arg_angle = new PID.Profile(19.22, 0, 0);
    var anglePIDConfigs = PID.Slot0Configs(arg_angle);
    anglePIDConfigs.kV = 0;
    var angleOutputConfigs = new MotorOutputConfigs();
    angleOutputConfigs.NeutralMode = NeutralModeValue.Coast;
    angleOutputConfigs.Inverted = InvertedValue.Clockwise_Positive;
    // var EncoderConfigs = new TalonFXConfiguration();
    // EncoderConfigs.Feedback.FeedbackRemoteSensorID = eID;
    // EncoderConfigs.Feedback.FeedbackSensorSource =
    // FeedbackSensorSourceValue.RemoteCANcoder;
    angleMotor = new TalonFX(aID);
    angleMotor.getConfigurator().apply(anglePIDConfigs, 0.05);
    angleMotor.getConfigurator().apply(angleOutputConfigs);
    // angleMotor.getConfigurator().apply(EncoderConfigs);

    magEncoder = new WPI_TalonSRX(eID);
  }

  private TalonFX velocityMotor;
  private TalonFX angleMotor;
  private WPI_TalonSRX magEncoder;

  private final VelocityVoltage m_velocity = new VelocityVoltage(0).withSlot(0);
  private final PositionVoltage m_angle = new PositionVoltage(0).withSlot(0);

  private double angle;
  private double zeroPos;
  private double kV;
  private int number;
  private double position;

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    position = magEncoder.getSelectedSensorPosition();
    angle = (((position - zeroPos) / 4096 * 360) % 360 + 360) % 360;

    SmartDashboard.putNumber("Swerve Module" + number + " Angle", angle);
    SmartDashboard.putNumber("Swerve Module" + number + " rawAnglePos", position);
    SmartDashboard.putNumber("Swerve Module" + number + " Velocity", velocityMotor.getVelocity().getValueAsDouble());
  }

  public void setStatus(double angleGoal, double velocityGoal) {
    double deltaTheta = 0;
    double rawDeltaTheta = angleGoal - angle;

    if (Math.abs(rawDeltaTheta) <= 90) {
      deltaTheta = rawDeltaTheta;
      kV = 1;
    }

    if (rawDeltaTheta > 90 && rawDeltaTheta < 270) {
      deltaTheta = rawDeltaTheta - 180;
      kV = -1;
    }

    if (rawDeltaTheta >= 270 && rawDeltaTheta <= 360) {
      deltaTheta = rawDeltaTheta - 360;
      kV = 1;
    }

    if (rawDeltaTheta < -90 && rawDeltaTheta > -270) {
      deltaTheta = rawDeltaTheta + 180;
      kV = -1;
    }

    if (rawDeltaTheta <= -270 && rawDeltaTheta >= -360) {
      deltaTheta = rawDeltaTheta + 360;
      kV = 1;
    }

    double positionGoal = deltaTheta / 360 * 4096 + position;

    // if (Math.abs(deltaTheta) > 0.5) {
    // angleMotor.setControl(m_angle.withVelocity(0.1 * kV * (positionGoal -
    // position)));
    // }

    angleMotor.set(Math.min((positionGoal - position) * 0.001, 0.3));

    // angleMotor.setControl(m_angle.withPosition((positionGoal - position) * 10 /
    // 2048));
    // angleMotor.setControl(m_angle.withPosition(zeroPos * 10 / 2048));

    SmartDashboard.putNumber("posErr" + number, (positionGoal - position) * 10 / 2048);
    SmartDashboard.putNumber("posAngleFX" + number, angleMotor.getRotorPosition().getValueAsDouble());
    SmartDashboard.putNumber("deltaTheta" + number, deltaTheta);
    SmartDashboard.putNumber("AngleGoal" + number, angleGoal);
    SmartDashboard.putNumber("PositionGoal" + number, positionGoal);

    velocityMotor.setControl(m_velocity.withVelocity(kV * velocityGoal * 10 * 0.3 / 2048));
  }

  public void setStill() {
    angleMotor.set(0);
    velocityMotor.set(0);
  }
}
