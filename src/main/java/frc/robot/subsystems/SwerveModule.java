// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase {
  /** Creates a new SwerveModule. */
  public SwerveModule(int number, int aID, int vID, int eID, int zeroPos, boolean v_inverse, boolean a_inverse) {
    this.number = number;
    this.zeroPos = zeroPos;

    angle = 0;

    // velocityMotor.getConfigurator().apply(new TalonFXConfiguration());
    var velocityPIDConfigs = new Slot0Configs();
    velocityPIDConfigs.kP = 0.36;
    velocityPIDConfigs.kI = 0;
    velocityPIDConfigs.kD = 0;
    velocityPIDConfigs.kV = 0;
    var velocityOutputConfigs = new MotorOutputConfigs();
    velocityOutputConfigs.NeutralMode = NeutralModeValue.Coast;
    velocityOutputConfigs.Inverted = v_inverse ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
    velocityMotor = new TalonFX(vID);
    velocityMotor.getConfigurator().apply(velocityPIDConfigs, 0.05);
    velocityMotor.getConfigurator().apply(velocityOutputConfigs);

    // angleMotor.getConfigurator().apply(new TalonFXConfiguration());
    var anglePIDConfigs = new Slot0Configs();
    anglePIDConfigs.kP = 11.45;
    anglePIDConfigs.kI = 0;
    anglePIDConfigs.kD = 0;
    anglePIDConfigs.kV = 0;
    var angleOutputConfigs = new MotorOutputConfigs();
    angleOutputConfigs.NeutralMode = NeutralModeValue.Coast;
    angleOutputConfigs.Inverted = a_inverse ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
    // var EncoderConfigs = new TalonFXConfiguration();
    // EncoderConfigs.Feedback.FeedbackRemoteSensorID = eID;
    // EncoderConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
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
  private boolean alter = false;

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
      // angleMotor.setControl(m_angle.withVelocity(0.1 * kV * (positionGoal - position)));
    // }

    angleMotor.set(Math.min((positionGoal - position) * 0.001, 0.5));

    // angleMotor.setControl(m_angle.withPosition((positionGoal - position) * 10 / 2048));
    // angleMotor.setControl(m_angle.withPosition(zeroPos * 10 / 2048));

    SmartDashboard.putNumber("posErr" + number, (positionGoal - position) * 10 / 2048);
    SmartDashboard.putNumber("posAngleFX" + number, angleMotor.getRotorPosition().getValueAsDouble());
    SmartDashboard.putNumber("deltaTheta" + number, deltaTheta);
    SmartDashboard.putNumber("AngleGoal" + number, angleGoal);
    SmartDashboard.putNumber("PositionGoal" + number, positionGoal);

    velocityMotor.setControl(m_velocity.withVelocity(kV * velocityGoal * 10 * (alter ? 0.3 : 1) / 2048));
  }

  public void setStill() {
    angleMotor.set(0);
    velocityMotor.set(0);
  }

  public void alter_velo() {
    alter = !alter;
  }
}

