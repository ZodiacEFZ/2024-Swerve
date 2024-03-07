// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ShooterCtrl;
import frc.robot.commands.SwerveDrive;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterMotors;
import frc.robot.subsystems.SwerveModule;
import frc.robot.subsystems.SwerveSubsystem;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  public static SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  public static SwerveModule LeftFrontSwerveModule = new SwerveModule(1, Constants.LFa, Constants.LFv, Constants.LFe,
      Constants.LF0, true, true); // (number, angleMotorPort, velocityMotorPort, zeroPosition)
  public static SwerveModule RightFrontSwerveModule = new SwerveModule(2, Constants.RFa, Constants.RFv, Constants.RFe,
      Constants.RF0, false, false);
  public static SwerveModule RightBackSwerveModule = new SwerveModule(3, Constants.RBa, Constants.RBv, Constants.RBe,
      Constants.RB0, false, true);
  public static SwerveModule LeftBackSwerveModule = new SwerveModule(4, Constants.LBa, Constants.LBv, Constants.LBe,
      Constants.LB0, false, true);
  public static SwerveDrive swerveDrive = new SwerveDrive(swerveSubsystem);

  private static ShooterMotors m_ShooterMotors = new ShooterMotors();
  private static ShooterCtrl m_ShooterCtrl = new ShooterCtrl(m_ShooterMotors);

  // Replace with CommandPS4Controller or CommandJoystick if needed
  // private final CommandXboxController m_driverController =
  // new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public static Joystick driveJoystick = new Joystick(0);
  public static Joystick ctrlJoystick = new Joystick(1);

  public static void driveRumble() {
    driveJoystick.setRumble(RumbleType.kLeftRumble, 1);
    driveJoystick.setRumble(RumbleType.kRightRumble, 1);
    Timer.delay(0.1);
    driveJoystick.setRumble(RumbleType.kLeftRumble, 0);
    driveJoystick.setRumble(RumbleType.kRightRumble, 0);
    Timer.delay(0.1);
  }

  public static void ctrlRumble() {
    ctrlJoystick.setRumble(RumbleType.kLeftRumble, 1);
    ctrlJoystick.setRumble(RumbleType.kRightRumble, 1);
    Timer.delay(0.1);
    ctrlJoystick.setRumble(RumbleType.kLeftRumble, 0);
    ctrlJoystick.setRumble(RumbleType.kRightRumble, 0);
    Timer.delay(0.1);
  }

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    // new Trigger(m_exampleSubsystem::exampleCondition)
    // .onTrue(new ExampleCommand(m_exampleSubsystem));

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.
    // m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }

  public static Command getTeleopShooterCommand() {
    return m_ShooterCtrl;
  }
}
