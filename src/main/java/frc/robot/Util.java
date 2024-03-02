package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Util {

    public static <T extends SubsystemBase> T require(Command self, T requirement) {
        self.addRequirements(requirement);
        return requirement;
    }
}
