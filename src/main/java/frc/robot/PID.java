package frc.robot;

import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix6.configs.Slot0Configs;

public final class PID {
    public static Slot0Configs Slot0Configs(Profile p) {
        var cfg = new Slot0Configs();
        cfg.kP = p.kP;
        cfg.kI = p.kI;
        cfg.kD = p.kD;
        return cfg;
    }

    public static Slot0Configs Slot0Configs(double kP, double kI, double kD) {
        return Slot0Configs(new Profile(kP, kI, kD));
    }

    public static SlotConfiguration SlotConfiguration(Profile p) {
        var cfg = new SlotConfiguration();
        cfg.kP = p.kP;
        cfg.kI = p.kI;
        cfg.kD = p.kD;
        return cfg;
    }

    public static SlotConfiguration SlotConfiguration(double kP, double kI, double kD) {
        return SlotConfiguration(new Profile(kP, kI, kD));
    }

    public static class Profile {
        public final double kP;
        public final double kI;
        public final double kD;

        public Profile(double kP, double kI, double kD) {
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
        }
    }
}

/*
 * <del>
 * 做不到带泛型的本地静态接口的外部分发……
 * 恕我直言Java就是**史**
 * 建议看看隔壁Scala和Rust的trait……不要再**史上雕花**用继承来做interface了！！！
 * </del>
 * 表达一下愤怒
 */