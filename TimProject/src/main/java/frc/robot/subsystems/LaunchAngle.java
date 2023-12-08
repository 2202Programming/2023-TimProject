// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AnalogIn;
import frc.robot.Constants.CAN;

public class LaunchAngle extends SubsystemBase {
  private static final double kp = 1.0;
  private static final double ki = 0;
  private static final double kd = 0;
  private static final double kINperVolt = 6.0 / 5.0; // define later [in/volt]

  CANSparkMax angleMotor = new CANSparkMax(CAN.ELEVATION_ACTUATOR, MotorType.kBrushed);
  final AnalogInput vPositionSensor = new AnalogInput(AnalogIn.LINEAR_ACTUATOR); // [volts]
  final PIDController pid = new PIDController(kp, ki, kd); // change these names later
  private double meas_distance = 0.0; // [in]
  private double distanceCmd = 0.0;

  public LaunchAngle() {
    // read our power on position
    meas_distance = vPositionSensor.getVoltage() * kINperVolt;
    
    //set the pid's tolerance once at construction
    pid.setTolerance(0.2 /* [in] */, 0.1/* [in/sec] */); // best guess
  }

  @Override
  public void periodic() {
    //below will be done in the robot-loop
    // measure
    meas_distance = vPositionSensor.getVoltage() * kINperVolt;

    // calulate
    double output = pid.calculate(meas_distance, distanceCmd);

    // output
    angleMotor.set(output);
  }

  public void setPoint(double point) {
    distanceCmd = point;
  }

  public boolean isAtPosition() {
    return pid.atSetpoint();
  }
}
