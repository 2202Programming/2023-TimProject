// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.Constants;
import frc.robot.Constants.ChassisConfig;
import frc.robot.Constants.SubsystemConfig;
import frc.robot.Constants.WheelOffsets;
import frc.robot.Constants.ChassisInversionSpecs;

public class RobotSpecs {

    public enum RobotNames {
        SwerveBot("SwerveBot"),
        USE_THIS("USE_THIS"),
        ChadBot("ChadBot"),
        UnknownBot("UnknownBot"),
        BotOnBoard("BotOnBoard");

        String name;

        private RobotNames(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    public RobotNames myRobotName;
    private WheelOffsets myWheelOffsets;
    private ChassisConfig myChassisConfig;
    private SubsystemConfig mySubsystemConfig;
    private ChassisInversionSpecs myChassisInversionSpecs;

    public RobotSpecs(String serialNo) {
        // this(System.getenv("serialnum")); //
        
        myRobotName = RobotNames.SwerveBot;
    }

    public RobotSpecs() {
        myRobotName = RobotNames.SwerveBot;

        // if we are simulated, use the competionBot so we have everything
        if (RobotBase.isSimulation()) {
            myRobotName = RobotNames.SwerveBot;
        }
        // setup to handle any swerve both we have
        switch (myRobotName) {
            case SwerveBot:
                myWheelOffsets = Constants.DriveTrain.swerveBotOffsets;
                myChassisConfig = Constants.DriveTrain.swerveBotChassisConfig;
                mySubsystemConfig = Constants.swerveBotSubsystemConfig;
                myChassisInversionSpecs = Constants.swerveBotChassisInversionSpecs;
                break;

            case USE_THIS:
                myWheelOffsets = Constants.DriveTrain.compBotOffsets;
                myChassisConfig = Constants.DriveTrain.compBotChassisConfig;
                mySubsystemConfig = Constants.compBotSubsystemConfig;
                myChassisInversionSpecs = Constants.compBotChassisInversionSpecs;
                break;

            case ChadBot:
                myWheelOffsets = Constants.DriveTrain.chadBotOffsets;
                myChassisConfig = Constants.DriveTrain.chadBotChassisConfig;
                mySubsystemConfig = Constants.chadBotSubsystemConfig;
                myChassisInversionSpecs = Constants.chadBotChassisInversionSpecs;
                break;

            default:
            case UnknownBot:
            case BotOnBoard:
                myWheelOffsets = Constants.DriveTrain.swerveBotOffsets;
                myChassisConfig = Constants.DriveTrain.swerveBotChassisConfig;
                mySubsystemConfig = Constants.swerveBotSubsystemConfig;
                myChassisInversionSpecs = Constants.swerveBotChassisInversionSpecs;
                System.out.println("***Non-driving robot,don't expect too much***");
                break;
        }
        System.out.println("***I am " + myRobotName + " ***");

        /*
         * Can't check Team Alliance yet, STILL CONSTRUCTING ROBOT
         * //getting team Color
         * color = DriverStation.getAlliance();
         * if(color == DriverStation.Alliance.Blue){
         * isBlue = true;
         * System.out.println("***We are on blue alliance***");
         * }
         * else {
         * isBlue = false;
         * System.out.println("***We are on red alliance***");
         */
    }

    public String getRobotNameString() {
        return this.myRobotName.name;
    }

    public WheelOffsets getWheelOffset() {
        return myWheelOffsets;
    }

    public ChassisConfig getChassisConfig() {
        return myChassisConfig;
    }

    public SubsystemConfig getSubsystemConfig() {
        return mySubsystemConfig;
    }

    public ChassisInversionSpecs getChassisInversionSpecs(){
        return myChassisInversionSpecs;
    }

    // takes the roborio serial # and returns the robot name
    public RobotNames getRobotName(String serialNo) {
        RobotNames tempRobotName;

        if (serialNo == null)
            return RobotNames.UnknownBot;

        if (serialNo.compareTo("031b7511") == 0)
            tempRobotName = RobotNames.SwerveBot;
        else if (serialNo.compareTo("03238151") == 0)
            tempRobotName = RobotNames.ChadBot;
        else if (serialNo.compareTo("0312db1a") == 0)
            tempRobotName = RobotNames.BotOnBoard;
        else if (serialNo.compareTo("032381BF") == 0)
            tempRobotName = RobotNames.USE_THIS;
        else
            tempRobotName = RobotNames.UnknownBot;

        System.out.println("***RoboRio SERIAL NUM: " + serialNo);
        System.out.println("***Robot identified as: " + tempRobotName);
        return tempRobotName;
    }
}