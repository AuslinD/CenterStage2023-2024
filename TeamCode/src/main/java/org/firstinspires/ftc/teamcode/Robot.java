package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    Drivetrain drivetrain;
    Lift lift;
    Claw claw;

    Intake intake;

    IMU imu;

    Servo plane;


    public Robot(OpMode opmode){
        drivetrain = new Drivetrain(opmode);
        lift = new Lift(opmode);
        claw = new Claw(opmode);
        intake = new Intake(opmode);

        imu = opmode.hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();

        plane = opmode.hardwareMap.get(Servo.class, "plane");





    }
    public Robot(LinearOpMode linearOpMode){
        drivetrain = new Drivetrain(linearOpMode);
        lift = new Lift(linearOpMode);
        claw = new Claw(linearOpMode);
        intake = new Intake(linearOpMode);

        imu = linearOpMode.hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
        imu.resetYaw();
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


}
