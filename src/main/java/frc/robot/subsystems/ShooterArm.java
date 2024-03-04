// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.HashMap;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.PID;
import frc.robot.util.Motor;

public class ShooterArm extends SubsystemBase {

  private final Motor left = new Motor(114, 514);
  private final Motor right = new Motor(1919, 810);

  private class Pos {
    public double L;
    public double R;

    public Pos(double L, double R) {
      this.L = L;
      this.R = R;
    }
  }

  private final HashMap<String, Pos> pos = new HashMap<String, Pos>();

  /** Creates a new ShooterArm. */
  public ShooterArm() {
    this.left.init().setPID(new PID.Profile(0, 0, 0));
    this.right.init().setPID(new PID.Profile(0, 0, 0));
    this.regPos("test", new Pos(114, 514))
        .regPos("another-test", new Pos(514, 114));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    this.left.run();
    this.right.run();
  }

  public ShooterArm stop() {
    this.left.stop();
    this.right.stop();
    return this;
  }

  public ShooterArm go(String pos) {
    var got = this.pos.get(pos);
    if (got != null) {
      this.left.go(got.L);
      this.right.go(got.R);
    } else {
      this.stop();
    }
    return this;
  }

  public ShooterArm regPos(String name, Pos pos) {
    this.pos.put(name, pos);
    return this;
  }

}
