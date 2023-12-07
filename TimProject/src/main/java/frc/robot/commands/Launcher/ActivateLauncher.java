// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Launcher;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Launcher;
 

public class ActivateLauncher extends CommandBase {
  /** Creates a new Laucher. */
  final Launcher frisbee_flipper = RobotContainer.RC().launcher;
  public ActivateLauncher(){
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //make sure FF is retracted is false
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //new InstantCommand(() -> {
      System.out.println("reached");
      frisbee_flipper.fire();

      frisbee_flipper.speed(0.5);
  
    if(frisbee_flipper.get_speed() == 0.5){
       frisbee_flipper.fire();
      }

  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
  return (frisbee_flipper.getFlipperStatus());
      }
}
