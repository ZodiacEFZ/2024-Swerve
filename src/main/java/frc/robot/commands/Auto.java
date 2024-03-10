// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix6.Timestamp;
import com.ctre.phoenix6.Timestamp.TimestampSource;

import edu.wpi.first.wpilibj2.command.Command;
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

  private enum Auto_State {
    Standby, ShootPreload, ShootingStop, TaxiAndIntake, End
  }

  private Auto_State auto_state = Auto_State.Standby;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    auto_state = Auto_State.ShootPreload;
  }

  java.sql.Timestamp ts_init = new java.sql.Timestamp(System.currentTimeMillis());

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (auto_state) {
      case Standby:
        break;
      case ShootPreload:
        shootPreload();
        auto_state = Auto_State.ShootingStop;
        break;
      case ShootingStop:
        if (System.currentTimeMillis() - ts_init.getTime() > 2000) {
          ShootingStop();
          auto_state = Auto_State.TaxiAndIntake;
        }
        break;
      case TaxiAndIntake:
        TaxiAndIntake();
        auto_state = Auto_State.End;
        break;
      case End:
        break;
    }
  }

  private void shootPreload() {
    m_IntakeMotors.begSend();
    m_ShooterMotors.shoot();
  }

  private void ShootingStop() {
    m_ShooterMotors.shootStop();
    m_IntakeMotors.stopIntake();
    m_IntakeMotors.down();
  }

  private void TaxiAndIntake() {
    ts_init = new java.sql.Timestamp(System.currentTimeMillis());
    m_SwerveSubsystem.car_oriented(0, 0, -0.4);
    if (System.currentTimeMillis() - ts_init.getTime() > 1000) {
      m_IntakeMotors.begIntake();
      m_SwerveSubsystem.car_oriented(0.3, 0, 0);
      ts_init = new java.sql.Timestamp(System.currentTimeMillis());
    }
    if (System.currentTimeMillis() - ts_init.getTime() > 3000) {
      m_SwerveSubsystem.car_oriented(0, 0, 0);
    }
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
