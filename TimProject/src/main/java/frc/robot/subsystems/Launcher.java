// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CAN;
import frc.robot.Constants.PIDFController;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

public class Launcher extends SubsystemBase {
  /** Creates a new Launcher. */
  final TalonFX back_motor = new TalonFx(CAN.BACK_MOTOR);
  final TalonFX front_motor = new TalonFx(CAN.FRONT_MOTOR);
  final int x = 25; //values of x should always remain constant. Nothing should really change here.

  final VelocityVoltage velocity = new VelocityVoltage(0,false,0,1,false); //second value should be true if we have talon fx pros, as it increases power.
 
  public Launcher() {   
  }
  
  public void move_motor() {
  front_motor.setControl(velocity.withVelocity(x)); //velocity is used in rotations per second.
  back_motor.setControl(new Follower(front_motor.getDeviceID, false));
  }

  @Override
  public void periodic() {
  }
}

/*things to do: 
* Limit maximum and minimum voltage
* Work on the pid loop information. And learn how to use pid loops
*/
