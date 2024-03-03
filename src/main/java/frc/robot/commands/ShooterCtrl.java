// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterMotors;

public class ShooterCtrl extends Command {
  private final ShooterMotors m_ShooterMotors;
  /** Creates a new ShooterCtrl. */
  public ShooterCtrl(ShooterMotors shooterMotors) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_ShooterMotors = shooterMotors;
    addRequirements(shooterMotors);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (RobotContainer.ctrlJoystick.getRawButton(1)) {
      m_ShooterMotors.testBeg();
    }
    if (RobotContainer.ctrlJoystick.getRawButtonReleased(1)) {
      m_ShooterMotors.allStop();
    }
    SmartDashboard.putBoolean("ShooterButtonPressed", RobotContainer.ctrlJoystick.getRawButton(1));
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
