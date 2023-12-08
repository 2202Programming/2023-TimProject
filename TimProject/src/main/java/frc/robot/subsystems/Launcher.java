// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CAN;
import frc.robot.Constants.PCM1;
import frc.robot.util.PIDFController;

public class Launcher extends SubsystemBase {
  int frisbee_flipper_timer = 0;
  double commanded_speed = 0;
  private boolean is_open = false;

  static final int kTimeoutMs = 30;  // for sending config to Talons
  static final int kSlot = 0;         //we are just using one slot

  /** Creates a new Launcher. */
  final DoubleSolenoid frisbee_flipper = new DoubleSolenoid(
      PneumaticsModuleType.CTREPCM, 
      PCM1.FRISBEE_FLIPPER_FWD,
      PCM1.FRISBEE_FLIPPER_BACK);

  final WPI_TalonFX back_motor = new WPI_TalonFX(CAN.BACK_LAUNCH_MOTOR);
  final WPI_TalonFX front_motor = new WPI_TalonFX(CAN.FRONT_LAUNCH_MOTOR);
  TalonFXConfiguration configs = new TalonFXConfiguration();

  //Put pid values for Talon here, it has method to copy down to hardware
  PIDFController talonPid = new PIDFController(.11, 0.0, 0.0, 0.0);
// Mr. L I switched to use Phoenix5, and PIDFController to hold constants
    //configs.slot0.kP = 0.11; // An error of 1 rotation per second results in 2V output
    //configs.slot0.kI = 0; // An error of 1 rotation per second increases output by 0.5V every second
    //configs.slot0.kD = 0; // A change of 1 rotation per second squared results in 0.01 volts output
    //configs.slot0.kF = 0.12; // Falcon 500 is a 500kV motor, 500rpm per V = 8.333 rps per V, 1/8.33 = 0.12
                             // volts / Rotation per second


  public Launcher() {
    // CTRE recommends this...        
    front_motor.configFactoryDefault();                             
    back_motor.configFactoryDefault();
    
    // set the closed loop pid values 
    talonPid.copyTo(front_motor, kSlot);
    talonPid.copyTo(back_motor, kSlot);

    front_motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, kSlot, kTimeoutMs);
    back_motor.set(TalonFXControlMode.Follower, front_motor.getDeviceID());

  }

  // speed [rpm]
  public void speed(double speed)
  {
    commanded_speed = speed;  // [rpm]
    double vel_unitsPer100ms = speed * 2048.0 / 600.0;   //[sensor counts/100ms] 
    //just set front_motor, back should follow
    front_motor.set(TalonFXControlMode.Velocity, vel_unitsPer100ms);
  }

  // [rot/sec]
  public double get_speed() {
    //Mr. L - motor.get() returns the current commanded speed, not the acutal speed
    // you need to setup the sensor on talonFX to get measured speed.
    
    //double average = (back_motor.get() + front_motor.get()) / 2.0;    [counts/100ms]
    double average = (back_motor.getSelectedSensorVelocity() + front_motor.getSelectedSensorVelocity() )/2.0;
    //convert counts/100ms to RPM
    average = (600.0* average)/2048.0;
    return average;    // TODO verify this is right, should be in RPM
  }

  public boolean readyToFire() {
    double speed_error = Math.abs(get_speed() - commanded_speed);

    // you can also look at the closed loop error
    //double motor_speed_cl_err = front_motor.getClosedLoopError(kSlot)*600.0/2048.0; //[100ms counts]*[scale]= [rpm]

    if (frisbee_flipper.get() == Value.kReverse && speed_error / commanded_speed < 0.1) {
      return true;
    }
    return false;
  }

  public void fire() {
    if (frisbee_flipper_timer > 0) {
      return;
    }
    frisbee_flipper_timer = 5;
    frisbee_flipper.set(Value.kForward);
    is_open = true;
  }

  public void retract() {
    frisbee_flipper.set(Value.kReverse);
    is_open = false;
  }

  public boolean getFlipperStatus() {
    return is_open;
  }

  @Override
  public void periodic() {
    if (frisbee_flipper_timer > 0) {
      frisbee_flipper_timer -= 1;
    }
    if (frisbee_flipper_timer == 0) {
      frisbee_flipper.set(Value.kReverse);
    }
  }
}

/*
 * things to do:
 * Limit maximum and minimum voltage
 * Work on the pid loop information. And learn how to use pid loops
 */
