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
  public Launcher() {
    final TalonFX back_motor = new TalonFx(CAN.BACK_MOTOR);
    final TalonFX front_motor = new TalonFx(CAN.FRONT_MOTOR);

  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
