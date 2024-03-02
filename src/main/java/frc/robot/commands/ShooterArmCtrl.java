// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.Util;
import frc.robot.subsystems.ShooterArm;

public class ShooterArmCtrl extends Command {
  private final int testButton = 114;

  private final ShooterArm arm;

  /** Creates a new ShooterArmCtrl. */
  public ShooterArmCtrl(ShooterArm arm) {
    this.arm = Util.require(this, arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    var p = RobotContainer.ctrlJoystick.getRawButton(this.testButton);
    if (p) {
      this.arm.setTestPos();
    } else {
      this.arm.setStill();
    }
    SmartDashboard.putBoolean("TestShooterArmCtrl", p);
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
