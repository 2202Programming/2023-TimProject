// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CAN;
import frc.robot.Constants.PCM1;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

public class Launcher extends SubsystemBase {
  int frisbee_flipper_timer = 0;
  double commanded_speed = 0;
  private boolean is_open = false;
  /** Creates a new Launcher. */
  DoubleSolenoid frisbee_flipper = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, PCM1.FRISBEE_FLIPPER_FWD , PCM1.FRISBEE_FLIPPER_BACK);
  final TalonFX back_motor = new TalonFX(CAN.BACK_LAUNCH_MOTOR);
  final TalonFX front_motor = new TalonFX(CAN.FRONT_LAUNCH_MOTOR);
  TalonFXConfiguration configs = new TalonFXConfiguration();
  final VelocityVoltage velocity = new VelocityVoltage(0,false,0,1,false); //second value should be true if we have talon fx pros, as it increases power.


  public Launcher() {   
    //stolen numbers yay
    configs.Slot0.kP = 0.11; // An error of 1 rotation per second results in 2V output
    configs.Slot0.kI = 0.5; // An error of 1 rotation per second increases output by 0.5V every second
    configs.Slot0.kD = 0.0001; // A change of 1 rotation per second squared results in 0.01 volts output
    configs.Slot0.kV = 0.12; // Falcon 500 is a 500kV motor, 500rpm per V = 8.333 rps per V, 1/8.33 = 0.12 volts / Rotation per second
    // Peak output of 8 volts
    configs.Voltage.PeakForwardVoltage = 8;
    configs.Voltage.PeakReverseVoltage = -8;
    back_motor.setControl(new Follower(front_motor.getDeviceID(), false));
  }
  //[rot/sec]
  public void speed(double speed) {
  commanded_speed = speed;
  front_motor.setControl(velocity.withVelocity(speed)); //velocity is used in rotations per second.
  }
  //[rot/sec]
public double get_speed(){
  double average = (back_motor.get() + front_motor.get()) / 2.0;
  return average;
}
public boolean readyToFire(){
  double speed = Math.abs(get_speed() - commanded_speed);
  if(frisbee_flipper.get() == Value.kReverse && speed / commanded_speed < 0.1){
    return true;
  }
return false;
}
public void fire(){
  if(frisbee_flipper_timer >0){
    return ;
  }
  frisbee_flipper_timer = 5;
  frisbee_flipper.set(Value.kForward);
  is_open = true;
}
public void retract(){
  frisbee_flipper.set(Value.kReverse);
  is_open = false;
}
public boolean getFlipperStatus(){
  return is_open;
}
  @Override
  public void periodic() {
    if(frisbee_flipper_timer > 0){
    frisbee_flipper_timer -= 1;
  }
    if(frisbee_flipper_timer == 0){
      frisbee_flipper.set(Value.kReverse);
    }
  }
}

/*things to do: 
* Limit maximum and minimum voltage
* Work on the pid loop information. And learn how to use pid loops
*/
