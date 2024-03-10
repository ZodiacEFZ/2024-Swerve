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

public class SwerveAuto extends Command {
  /** Creates a new SwerveAuto. */
  public SwerveAuto(SwerveSubsystem ss, ShooterMotors sm, IntakeMotors im) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_SwerveSubsystem = ss;
    m_ShooterMotors = sm;
    m_IntakeMotors = im;
    addRequirements(ss, sm, im);
  }

  private final SwerveSubsystem m_SwerveSubsystem;
  private final ShooterMotors m_ShooterMotors;
  private final IntakeMotors m_IntakeMotors;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_IntakeMotors.up();
    m_ShooterMotors.speaker();
  }

  java.sql.Timestamp ts_init = new java.sql.Timestamp(System.currentTimeMillis());

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_IntakeMotors.begSend();
    m_ShooterMotors.shoot();
    if (System.currentTimeMillis() - ts_init.getTime() > 2000) {
      m_IntakeMotors.stopIntake();
      m_ShooterMotors.shootStop();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
