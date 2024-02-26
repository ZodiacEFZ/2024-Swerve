package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class Shooter extends SubsystemBase {
    public Shooter (){
        var velocityPIDConfigs = new Slot0Configs();
        velocityPIDConfigs.kP = 0;
        velocityPIDConfigs.kI = 0;
        velocityPIDConfigs.kD = 0;
        velocityPIDConfigs.kV = 0;
        // var velocityOutputConfigs = new MotorOutputConfigs();
        // velocityOutputConfigs.NeutralMode = NeutralModeValue.Coast;
        //velocityOutputConfigs.Inverted = inverse ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        leftMotor.getConfigurator().apply(velocityPIDConfigs, 0.05);
        rightMotor.getConfigurator().apply(velocityPIDConfigs, 0.05);
        //leftMotor.getConfigurator().apply(velocityOutputConfigs);     
    }
    public TalonFX leftMotor = new TalonFX(Constants.ShooterLMoterID);
    public TalonFX rightMotor = new TalonFX(Constants.ShooterRMoterID);
    @Override
    public void periodic(){
        boolean pressed = RobotContainer.ctrlJoystick.getRawButtonPressed(1);
        if(pressed){
            SmartDashboard.putBoolean("buttonPressed", true);
            testSpeed();
        }else{
            allStop();
        }
    }
    public void testSpeed(){
        leftMotor.set(0.5);
        rightMotor.set(0.5);
    }
    public void allStop(){
        leftMotor.set(0);
        rightMotor.set(0);
    }
}
