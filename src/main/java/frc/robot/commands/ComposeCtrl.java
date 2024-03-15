// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.sql.Timestamp;
import java.util.Date;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IntakeMotors;
import frc.robot.subsystems.ShooterMotors;

public class ComposeCtrl extends Command {
    private final ShooterMotors m_ShooterMotors;
    private final IntakeMotors m_IntakeMotors;

  /** Creates a new ComposeCtrl. */
  public ComposeCtrl(ShooterMotors shooterMotors, IntakeMotors intakeMotors) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_IntakeMotors = intakeMotors;
    this.m_ShooterMotors = shooterMotors;
    currentTimestamp = System.currentTimeMillis();
    addRequirements(m_IntakeMotors);
    addRequirements(m_ShooterMotors);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_ShooterMotors.speaker();
  }

  private enum Shooting_Compose_State{
    IntakeMove,
    ArmMove,
    StandBy,
    Shooting,
    Loading,
    ArmUp
  }

  private long currentTimestamp = 0;
  // private Shooting_Compose_State speaker_compose = Shooting_Compose_State.StandBy;
  private Shooting_Compose_State amp_compose = Shooting_Compose_State.StandBy;

  // public void shootSpeaker(){
  //   speaker_compose = Shooting_Compose_State.IntakeMove;
  //   m_IntakeMotors.up();
  // }

  public void shootAmp(){
    amp_compose = Shooting_Compose_State.IntakeMove;
    m_IntakeMotors.up();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      // if(RobotContainer.ctrlJoystick.getRawButton(1)){
      //   shootSpeaker();
      // }
      // if(RobotContainer.ctrlJoystick.getRawButton(6)){
        // shootAmp();
      // }
      // if(speaker_compose == Shooting_Compose_State.IntakeMove){
      //   if(m_IntakeMotors.getPosition() == m_IntakeMotors.upperPos){
      //       m_ShooterMotors.speaker();
      //       speaker_compose = Shooting_Compose_State.ArmMove;
      //   }
      // }
      // if(speaker_compose == Shooting_Compose_State.ArmMove){
      //   if(m_ShooterMotors.getArmPos() == m_ShooterMotors.restPos){
      //     m_IntakeMotors.begSend();
      //     m_ShooterMotors.shoot();
      //     speaker_compose = Shooting_Compose_State.StandBy;
      //   }
      // }

      // if(amp_compose == Shooting_Compose_State.IntakeMove){
      //   if(m_IntakeMotors.getPosition() == m_IntakeMotors.upperPos){
      //       m_ShooterMotors.speaker();
      //       amp_compose = Shooting_Compose_State.ArmMove;
      //   }
      // }
      // if(amp_compose == Shooting_Compose_State.ArmMove){
      //   if(m_ShooterMotors.getArmPos() == m_ShooterMotors.restPos){
      //     // TODO Timestamp
      //     currentTimestamp = System.currentTimeMillis();
      //     amp_compose = Shooting_Compose_State.Loading;
      //     m_IntakeMotors.begSend();
      //     m_ShooterMotors.load();
      //   }
      // }
      // if(amp_compose == Shooting_Compose_State.Loading){
      //   long nowStamp = System.currentTimeMillis();
      //   if(nowStamp - currentTimestamp == 100){ // in ms
      //     m_IntakeMotors.stopIntake();
      //     m_ShooterMotors.loadStop();
      //     amp_compose = Shooting_Compose_State.Shooting;
      //     m_ShooterMotors.amp();
      //   }
      // }
      // if(amp_compose == Shooting_Compose_State.ArmUp){
      //   if(m_ShooterMotors.getArmPos() == m_ShooterMotors.ampPos){ // in ms
      //     amp_compose = Shooting_Compose_State.StandBy;
      //   }
      // }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
