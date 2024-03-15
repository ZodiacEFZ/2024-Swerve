// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.Timestamp;
import com.ctre.phoenix6.Timestamp.TimestampSource;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeMotors;
import frc.robot.subsystems.ShooterMotors;
import frc.robot.subsystems.SwerveSubsystem;

public class Auto extends Command {
  /** Creates a new SwerveAuto. */
  public Auto(SwerveSubsystem ss, ShooterMotors sm, IntakeMotors im) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_SwerveSubsystem = ss;
    m_ShooterMotors = sm;
    m_IntakeMotors = im;
    addRequirements(ss, sm, im);
  }

  private final SwerveSubsystem m_SwerveSubsystem;
  private final ShooterMotors m_ShooterMotors;
  private final IntakeMotors m_IntakeMotors;

  private final int direction = 1; // 1 or -1

  private enum Auto_State {
    TurnPreload, ShootPreload, ShootingStop, Interval, TurnBeg, TurnEnd, TaxiAndIntakeBeg, TaxiAndIntakeEnd
  }

  private Auto_State auto_state;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    auto_state = Auto_State.ShootPreload;
    m_IntakeMotors.up();
    ts_init = new java.sql.Timestamp(System.currentTimeMillis());
    for (int i = 1; i <= 4; i++) {
      angleGoal[i] = 0;
      velocityGoal[i] = 0;
    }
  }

  java.sql.Timestamp ts_init;

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("auto_state", auto_state.ordinal());
    switch (auto_state) {
      case TurnPreload:
        m_SwerveSubsystem.car_oriented(0, 0, 0.2 * direction);
        auto_state = Auto_State.ShootPreload;
        break;
      case ShootPreload:
        if (System.currentTimeMillis() - ts_init.getTime() > 2000) {
          m_SwerveSubsystem.car_oriented(0, 0, 0);
          shootPreload();
          auto_state = Auto_State.ShootingStop;
        }
        break;
      case ShootingStop:
        if (System.currentTimeMillis() - ts_init.getTime() > 2000) {
          ShootingStop();
          auto_state = Auto_State.Interval;
        }
        break;
      case Interval:
        if (System.currentTimeMillis() - ts_init.getTime() > 1000) {
          ts_init = new java.sql.Timestamp(System.currentTimeMillis());
          auto_state = Auto_State.TurnBeg;
        }
        break;
      case TurnBeg:
        // m_SwerveSubsystem.car_oriented(0, 0, -0.2);
        if (System.currentTimeMillis() - ts_init.getTime() > 2400) {
          ts_init = new java.sql.Timestamp(System.currentTimeMillis());
          m_SwerveSubsystem.car_oriented(0, 0, -0.2 * direction);
          auto_state = Auto_State.TurnEnd;
        }
        break;
      case TurnEnd:
        if (System.currentTimeMillis() - ts_init.getTime() > 1000) {
          ts_init = new java.sql.Timestamp(System.currentTimeMillis());
          TaxiAndIntakeBeg();
          auto_state = Auto_State.TaxiAndIntakeBeg;
        }
        break;
      case TaxiAndIntakeBeg:
        m_SwerveSubsystem.car_oriented(0, 0.3, 0);
        if (System.currentTimeMillis() - ts_init.getTime() > 3000) {
          m_SwerveSubsystem.car_oriented(0, 0, 0);
          // m_IntakeMotors.stopIntake();
          auto_state = Auto_State.TaxiAndIntakeEnd;
        }
        break;
      case TaxiAndIntakeEnd:
        break;
    }

    angleGoal = m_SwerveSubsystem.get_theta();
    velocityGoal = m_SwerveSubsystem.get_velocity();

    for (int i = 1; i <= 4; i++) {
      angleGoal[i] = (Math.toDegrees(angleGoal[i])) % 360;
      velocityGoal[i] = 18000 * velocityGoal[i];
    }

    // SmartDashboard.putNumberArray("rawAngleGoal", angleGoal);

    RobotContainer.LeftFrontSwerveModule.setStatus(angleGoal[1], velocityGoal[1]);
    RobotContainer.RightFrontSwerveModule.setStatus(angleGoal[2], velocityGoal[2]);
    RobotContainer.RightBackSwerveModule.setStatus(angleGoal[3], velocityGoal[3]);
    RobotContainer.LeftBackSwerveModule.setStatus(angleGoal[4], velocityGoal[4]);
  }

  private double[] angleGoal = new double[8], velocityGoal = new double[8];

  private void shootPreload() {
    m_IntakeMotors.begSend();
    m_ShooterMotors.shootSpeaker();
    ts_init = new java.sql.Timestamp(System.currentTimeMillis());
  }

  private void ShootingStop() {
    m_ShooterMotors.shootStop();
    m_IntakeMotors.stopIntake();
    m_IntakeMotors.down();
    ts_init = new java.sql.Timestamp(System.currentTimeMillis());
  }

  private void TaxiAndIntakeBeg() {
    m_IntakeMotors.begIntake();
    ts_init = new java.sql.Timestamp(System.currentTimeMillis());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
