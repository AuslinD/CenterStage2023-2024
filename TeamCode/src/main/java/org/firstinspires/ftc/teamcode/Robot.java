package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;

public class Robot {
    Drivetrain drivetrain;
    Lift lift;
    Claw claw;

    Intake intake;

    Hang hang;

    IMU imu;

    Servo plane;
    Servo planeGate;
    Servo planeAngle;
    OpMode teleOpMode;




    public Robot(OpMode opmode){
        teleOpMode = opmode;
        drivetrain = new Drivetrain(opmode);
        lift = new Lift(opmode);
        claw = new Claw(opmode);
        intake = new Intake(opmode);
        hang = new Hang(opmode);

        imu = opmode.hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
        planeGate = opmode.hardwareMap.get(Servo.class, "planeGate");
        plane = opmode.hardwareMap.get(Servo.class, "plane");
        planeAngle = opmode.hardwareMap.get(Servo.class, "planeAngle");
        planeAngle.setPosition(0);


    }
    public Robot(LinearOpMode linearOpMode){
        drivetrain = new Drivetrain(linearOpMode);
        lift = new Lift(linearOpMode);
        claw = new Claw(linearOpMode);
        intake = new Intake(linearOpMode);
        hang = new Hang(linearOpMode);

        imu = linearOpMode.hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();

        plane = linearOpMode.hardwareMap.get(Servo.class, "plane");
    }

    public IMU getImu(){
        return imu;
    }
    public void resetImu(){

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
    }
    public void setPlanePosition(double position)
    {
        plane.setPosition(position);
    }
    public void setPlaneClosed(){
        planeGate.setPosition(1);
    }
    public void setPlaneOpen(){
        planeGate.setPosition(.5);
    }

    public void setPlaneAngle(double position){planeAngle.setPosition(position);}

}