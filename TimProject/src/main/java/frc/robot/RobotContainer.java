// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Launcher.ActivateLauncher;
import frc.robot.commands.Launcher.DeActivateLauncher;
import frc.robot.commands.swerve.FieldCentricDrive;
import frc.robot.commands.swerve.RobotCentricDrive;
import frc.robot.subsystems.LaunchAngle;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.PhotonVision;
import frc.robot.subsystems.Sensors_Subsystem;
import frc.robot.subsystems.SwerveDriveTrain;
import frc.robot.subsystems.hid.HID_Xbox_Subsystem;
import frc.robot.util.RobotSpecs;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  static RobotContainer rc;
  
  // The robot's subsystems and commands are defined here...
  public final RobotSpecs robotSpecs;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public static RobotContainer RC() {
    return rc;
  }

  enum Bindings {
    Real,
    // below are testing modes, add as needed
    Testing
  }

  // subsystems
  public final PhotonVision photonVision;
  public final Sensors_Subsystem sensors;
  public final SwerveDriveTrain drivetrain;
  public final Launcher launcher;
  public final LaunchAngle launcherAngle;

  public final HID_Xbox_Subsystem dc; // short for driver controls

  public RobotContainer() {
    RobotContainer.rc = this; // for singleton accesor
    robotSpecs = new RobotSpecs();

    //Driver controls subsystem 
    dc = new HID_Xbox_Subsystem(0.3, 0.9, 0.05);
    photonVision = null;

    //this switch isn't really needed unless code base will run on different robots with diff configs like we
    // did during 2023 early season.
    switch (robotSpecs.myRobotName) {      
      //we expect swerveBot - nothing else
      case SwerveBot:
        // ordered by desired run order of periodic() calls
        sensors = new Sensors_Subsystem();
        drivetrain = new SwerveDriveTrain(); 
        launcher = new Launcher();
        launcherAngle = new LaunchAngle();
        break;
      
      case USE_THIS:
      case ChadBot:
      case BotOnBoard: // fall through
      case UnknownBot: // fall through
      default:
        launcher = null;
        launcherAngle = null;
        sensors = null;
        drivetrain = null;
        break;
    }
   
    if (drivetrain != null)
      drivetrain.setDefaultCommand(new FieldCentricDrive(drivetrain));
    configureBindings(Bindings.Real);    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings(Bindings bindings) {
    // some short hand to simplify bindings
    //var driver = dc.Driver();
    //var oper = dc.Operator();
    switch (bindings) {
      case Real:
      default:
        driverIndividualBindings();
        operatorIndividualBindings();
    }
  }

  private void driverIndividualBindings() {
    CommandXboxController driver = dc.Driver();
    driver.rightTrigger().onTrue(new ActivateLauncher());
    driver.x().onTrue(new DeActivateLauncher());
    // Triggers / shoulder Buttons
    driver.leftTrigger().whileTrue(new RobotCentricDrive(drivetrain, dc));
  }

  private void operatorIndividualBindings() {
    //CommandXboxController operator = dc.Operator();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;  // Autos.exampleAuto(m_exampleSubsystem);
  }
}
