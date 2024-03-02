package frc.robot;

import com.ctre.phoenix6.configs.Slot0Configs;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Util {
    public static Slot0Configs withPID(double kP, double kI, double kD) {
        var cfg = new Slot0Configs();
        cfg.kP = kP;
        cfg.kI = kI;
        cfg.kD = kD;
        return cfg;
    }

    public static <T extends SubsystemBase> T require(Command self, T requirement) {
        self.addRequirements(requirement);
        return requirement;
    }
}
