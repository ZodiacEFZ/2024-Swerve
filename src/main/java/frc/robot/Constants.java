// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final int Pegion2_ID = 0;
  public static final int LFa = 2, LFv = 3, LFe = 1, LF0 = 3566;
  public static final int RFa = 11, RFv = 10, RFe = 4, RF0 = -6918;
  public static final int RBa = 12, RBv = 8, RBe = 7, RB0 = -3417;
  public static final int LBa = 6, LBv = 5, LBe = 9, LB0 = 8321;

  public static final int ShooterLMoterID = 30;
  public static final int ShooterRMoterID = 29;
  public static final int shooterArmMotorID = 18;
  public static final int intakeTalonSRXPort = 14, intakeFlipSRXPort = 36;
}
