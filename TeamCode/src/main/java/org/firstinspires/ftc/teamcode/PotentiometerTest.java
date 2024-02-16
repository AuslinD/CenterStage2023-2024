package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "poten", group = "test")
public class PotentiometerTest extends LinearOpMode {
    OpenCvInternalCamera phoneCam;
    OpenCV.RedCV pipeline;
    //matthew was here
    double treeAngleStraight = .51;

    double treeAngleUp = .71;
    double treeAngleDown = .14;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        EncoderAutoMethods movement = new EncoderAutoMethods(this);
        Robot robot = movement.robot;
        //movement.robot.intake.stowIntake();
        movement.robot.claw.setClawAngle(treeAngleUp);
        movement.robot.claw.clawDown();
        movement.holdLiftAngle(2, 8000);



    }
}
