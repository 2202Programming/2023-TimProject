// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.CAN;



public class LaunchAngle extends SubsystemBase {
  CANSparkMax LinearAccuator = new CANSparkMax(CAN.ELEVATION_ACTUATOR, MotorType.kBrushed);

  /** Creates a new LaunchAngle. */
  public LaunchAngle() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
