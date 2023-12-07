// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;

import com.revrobotics.CANSparkMax;
import frc.robot.Constants.CAN;
import com.revrobotics.CANSparkMaxLowLevel;

public class LaunchAngle extends SubsystemBase {
  private static final double kp = 1.0;
  private static final double ki = 0;
  private static final double kd = 0;
  private static final double k = 6.0/5.0; //define later [in/volt]
  /** Creates a new LaunchAngle. */
  final CANSparkMax angleMotor = new CANSparkMax(CAN.ELEVATION_ACTUATOR, CANSparkMaxLowLevel.MotorType.kBrushed);
  final AnalogInput vPositionSensor = new AnalogInput(0); //[volts]
  final PIDController pid = new PIDController(kp, ki, kd); //change these names later
  private double distance = 0.0; //[in]
  private double distanceCmd = 0.0;
  

  public LaunchAngle() {
  
  }

  @Override
  public void periodic() {
    distance = vPositionSensor.getVoltage()*k;
    double output = pid.calculate(distance, distanceCmd);
    angleMotor.set(output);
    pid.setTolerance(5, 10); //stolen numbers
  }

  public void setPoint(double point){
    distanceCmd = point;
  }

  public boolean isAtPosition(){
    return pid.atSetpoint();
  }
}
