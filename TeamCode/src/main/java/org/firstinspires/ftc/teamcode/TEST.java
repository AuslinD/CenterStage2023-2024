package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name = "TEST", group = "auto")
public class TEST extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        EncoderAutoMethods movement = new EncoderAutoMethods(this);
        Robot robot = movement.robot;
        while (!(robot.lift.rotateRight.getCurrentPosition() < 600 && robot.lift.rotateRight.getCurrentPosition() > 565))// check to see if the lift is btw the value
        {
            if (robot.lift.rotateRight.getCurrentPosition() > 600) {
                robot.lift.rotateRight.setPower(-0.5);
//                robot.lift.rotateLeft.setPower(-0.5);b
            } else if (robot.lift.rotateRight.getCurrentPosition() < 565) {
                robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
//              robot.lift.rotateLeft.setPower(0.5);
            }
        }
    }

}