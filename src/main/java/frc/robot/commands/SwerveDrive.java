// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.SwerveSubsystem;

public class SwerveDrive extends Command {
  /** Creates a new SwerveDrive. */
  public SwerveDrive(SwerveSubsystem ss) {
    // Use addRequirements() here to declare subsystem dependencies.
    swerveSubsystem = ss;
    addRequirements(ss);
  }

  private final SwerveSubsystem swerveSubsystem;
  private boolean field_oriented = false, flag = false;
  private double targetAngle = 0, deadZone = 0.1, kv = 1;
  private double[] angleGoal = new double[8], velocityGoal = new double[8];

  private double x_value, y_value, rot_value;
  private boolean aimmingState = false;
  // private int aimmingOrien = 0;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    field_oriented = false;
  }

  public void stop_all() {
    RobotContainer.LeftBackSwerveModule.setStill();
    RobotContainer.LeftFrontSwerveModule.setStill();
    RobotContainer.RightBackSwerveModule.setStill();
    RobotContainer.RightFrontSwerveModule.setStill();

    for (int i = 1; i <= 4; i++) {
      angleGoal[i] = 0;
      velocityGoal[i] = 0;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // SmartDashboard.putNumber("angle", swerveSubsystem.get_field_angle());
    x_value = RobotContainer.driveJoystick.getRawAxis(0);
    y_value = -RobotContainer.driveJoystick.getRawAxis(1);
    rot_value = -RobotContainer.driveJoystick.getRawAxis(4);

    if (RobotContainer.driveJoystick.getRawButtonPressed(1)) {
      if (field_oriented) {
        // Current state is true so turn off
        field_oriented = false;
        RobotContainer.driveRumble();
      } else {
        // Current state is false so turn on
        field_oriented = true;
        RobotContainer.driveRumble();
      }
    }
    // field_oriented=false;
    if (!flag) {
      targetAngle = swerveSubsystem.get_field_angle();
    }
    SmartDashboard.putBoolean("field_oriented", field_oriented);
    SmartDashboard.putNumber("targetangle", targetAngle);
    if (Math.abs(x_value) < deadZone)
      x_value = 0;
    if (Math.abs(y_value) < deadZone)
      y_value = 0;
    if (Math.abs(rot_value) < deadZone)
      rot_value = 0;
    if (Math.abs(x_value) < deadZone && Math.abs(y_value) < deadZone && Math.abs(rot_value) < deadZone) {
      stop_all();
      flag = false;
    } else {
      if (Math.abs(rot_value) < deadZone)
        flag = true;
      else
        flag = false;
      if (field_oriented) {
        swerveSubsystem.field_oriented(x_value, y_value, rot_value,
            Math.toRadians(swerveSubsystem.get_field_angle()));
      } else {
        swerveSubsystem.car_oriented(x_value, y_value, rot_value);
      }
      angleGoal = swerveSubsystem.get_theta();
      velocityGoal = swerveSubsystem.get_velocity();

      for (int i = 1; i <= 4; i++) {
        angleGoal[i] = (Math.toDegrees(angleGoal[i])) % 360;
        velocityGoal[i] = 18000 * velocityGoal[i] + 2000;
      }

      SmartDashboard.putNumberArray("rawAngleGoal", angleGoal);

      RobotContainer.LeftFrontSwerveModule.setStatus(angleGoal[1], velocityGoal[1]);
      RobotContainer.RightFrontSwerveModule.setStatus(angleGoal[2], velocityGoal[2]);
      RobotContainer.RightBackSwerveModule.setStatus(angleGoal[3], velocityGoal[3]);
      RobotContainer.LeftBackSwerveModule.setStatus(angleGoal[4], velocityGoal[4]*0.86);
    }

    if (RobotContainer.driveJoystick.getRawButtonPressed(2)) {
      RobotContainer.LeftBackSwerveModule.alter_velo();
      RobotContainer.LeftFrontSwerveModule.alter_velo();
      RobotContainer.RightBackSwerveModule.alter_velo();
      RobotContainer.RightFrontSwerveModule.alter_velo();
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

  private double max_db(double a, double b) {
    if (a > b) {
      return a;
    } else {
      return b;
    }
  }
}
