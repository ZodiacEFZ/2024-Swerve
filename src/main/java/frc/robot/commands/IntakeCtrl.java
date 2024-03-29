// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeMotors;

public class IntakeCtrl extends Command {
  private final IntakeMotors m_IntakeMotors;
  /** Creates a new IntakeCtrl. */
  public IntakeCtrl(IntakeMotors intakeMotors) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_IntakeMotors = intakeMotors;
    addRequirements(intakeMotors);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    nowStatus = false;
    m_IntakeMotors.down();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (RobotContainer.ctrlJoystick.getRawButton(2)) {
      m_IntakeMotors.begIntake();
    }
    if (RobotContainer.ctrlJoystick.getRawButton(1) || RobotContainer.ctrlJoystick.getPOV() == 90) {
      m_IntakeMotors.begSend();
    }
    if (!RobotContainer.ctrlJoystick.getRawButton(2) && !RobotContainer.ctrlJoystick.getRawButton(1) && RobotContainer.ctrlJoystick.getPOV() == -1) {
      m_IntakeMotors.stopIntake();
    }
    SmartDashboard.putBoolean("IntakeButtonPressed", RobotContainer.ctrlJoystick.getRawButton(2));
    if (RobotContainer.ctrlJoystick.getRawButtonPressed(3)) {
      m_IntakeMotors.up();
      nowStatus = true;
    }
    if (RobotContainer.ctrlJoystick.getRawButtonPressed(4)) {
      m_IntakeMotors.down();
      nowStatus = false;
    }
    SmartDashboard.putBoolean("IntakeStatus", nowStatus);
  }

  private boolean nowStatus; //false is down & true is up

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
