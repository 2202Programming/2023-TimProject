package frc.robot.util;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.Constants;
import frc.robot.Constants.ChassisConfig;
import frc.robot.Constants.SubsystemConfig;
import frc.robot.Constants.WheelOffsets;
import frc.robot.Constants.ChassisInversionSpecs;

public class RobotSpecs {
    private WheelOffsets myWheelOffsets;
    private ChassisConfig myChassisConfig;
    private SubsystemConfig mySubsystemConfig;
    private ChassisInversionSpecs myChassisInversionSpecs;
    public RobotSpecs() {
    myWheelOffsets = Constants.DriveTrain.swerveBotOffsets;
    myChassisConfig = Constants.DriveTrain.swerveBotChassisConfig;
    mySubsystemConfig = Constants.swerveBotSubsystemConfig;
    myChassisInversionSpecs = Constants.swerveBotChassisInversionSpecs;
    }
    
}
