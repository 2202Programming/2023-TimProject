// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.util.ModuleInversionSpecs;
import frc.robot.util.PIDFController;
/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final double DT = 0.02;
  public static final double FTperM = 3.28084;
  public static final double MperFT = 1.0 / FTperM;
// RoboRio and CAN ports

public static final class CAN {
  // FRC DEFAULTS
  public static final int PDP = 1; // for rev
  public static final int PCM1 = 2; // for rev
  public static final int DT_FL_DRIVE = 20;
  public static final int DT_FL_ANGLE = 21;
  public static final int DT_BL_DRIVE = 22;
  public static final int DT_BL_ANGLE = 23;
  public static final int DT_BR_DRIVE = 24;
  public static final int DT_BR_ANGLE = 25;
  public static final int DT_FR_DRIVE = 26;
  public static final int DT_FR_ANGLE = 27;

  //TODO MrL - copy code from 2023 for Swerve CAN assignments.
  //Noah - Should be done


}

public static final class SubsystemConfig {
  public final boolean HAS_DRIVETRAIN;
  public SubsystemConfig(boolean HAS_DRIVETRAIN){
    this.HAS_DRIVETRAIN = HAS_DRIVETRAIN;
  }
}

// PWM assignments on the Rio
public static final class PWM {
  // public static final int INTAKE = 0;
}
public static final class WheelOffsets {
  public final double CC_FL_OFFSET;
  public final double CC_BL_OFFSET;
  public final double CC_FR_OFFSET;
  public final double CC_BR_OFFSET;

  public WheelOffsets(double FL, double BL, double FR, double BR) {
    this.CC_FL_OFFSET = FL;
    this.CC_BL_OFFSET = BL;
    this.CC_FR_OFFSET = FR;
    this.CC_BR_OFFSET = BR;
  }
}
public static final class ChassisConfig {

  // Kinematics model - wheel offsets from center of robot (0, 0)
  // Left Front given below, symmetry used for others
  public final double XwheelOffset; // meters, half of X wheelbase
  public final double YwheelOffset; // meters, half of Y wheelbase

  public final double wheelCorrectionFactor; // percent
  public final double wheelDiameter; // meters
  public final double kSteeringGR; // [mo-turns to 1 angle wheel turn]
  public final double kDriveGR; // [mo-turn to 1 drive wheel turn]

  public ChassisConfig(double XwheelOffset, double YwheelOffset, double wheelCorrectionFactor, double wheelDiameter,
      double kSteeringGR,
      double kDriveGR) {
    this.XwheelOffset = XwheelOffset;
    this.YwheelOffset = YwheelOffset;
    this.wheelCorrectionFactor = wheelCorrectionFactor;
    this.wheelDiameter = wheelDiameter * wheelCorrectionFactor;
    this.kSteeringGR = kSteeringGR;
    this.kDriveGR = kDriveGR;
  }
  
}
public static final class ChassisInversionSpecs{
  public ModuleInversionSpecs FR;
  public ModuleInversionSpecs FL;
  public ModuleInversionSpecs BR;
  public ModuleInversionSpecs BL;

  public ChassisInversionSpecs(ModuleInversionSpecs FR, ModuleInversionSpecs FL, ModuleInversionSpecs BR, ModuleInversionSpecs BL){
      this.FR = FR;
      this.FL = FL;
      this.BR = BR;
      this.BL = BL;
  }
}

public static final class DriveTrain {
  public static final WheelOffsets swerveBotOffsets = new WheelOffsets(-98.942, 91.33, -177.035, -28.215);
    public static final ChassisConfig swerveBotChassisConfig = new ChassisConfig(10.5 / 12, 10.5 / 12, 0.995,
        99.5 / 1000.0, 12.8, 8.14); 
        public static final PIDFController drivePIDF = new PIDFController(0.09 * FTperM, 0.0, 0.0, 0.08076 * FTperM);
        public static final PIDFController anglePIDF = new PIDFController(0.01, 0.0, 0.0, 0.0);
}
public static final SubsystemConfig swerveBotSubsystemConfig = new SubsystemConfig(true);
public static final ChassisInversionSpecs swerveBotChassisInversionSpecs = new ChassisInversionSpecs(
  new ModuleInversionSpecs(true,false,false), //FR
  new ModuleInversionSpecs(false,false,false), //FL
  new ModuleInversionSpecs(true,false,false), //BR
  new ModuleInversionSpecs(false,false,false)); //BL
// Digital IO on the RIO
public static final class DigitalIO {
  // public static final int IntakeLightGate = 0;
  // public static final int ClawLightgate = 1;
}

public static final class AnalogIn {
  // public static final int MAGAZINE_ANGLE = 0;
}

// PWM assignments on the Rio
public static final class PCM1 {
  // static final int <name> = 0;

}

// if we use a second PCM
public static final class PCM2 {
}


  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
