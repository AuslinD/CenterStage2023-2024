package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "test", group = "test")
public class TestTeleop extends OpMode {
    DcMotor rotateLeft, rotateRight, liftLeft, liftRight;

    Robot robot;
    int treeAngle = 0;
    @Override
    public void init() {
        robot = new Robot(this);



    }

    @Override
    public void loop() {
        /*
        if(Math.abs(gamepad2.left_stick_y) > 0.1){
            liftLeft.setPower(1 * gamepad2.left_stick_y);
            liftRight.setPower(1 * gamepad2.left_stick_y);
        }
        else{
            liftLeft.setPower(0);
            liftRight.setPower(0);
        }
        if(Math.abs(gamepad2.right_stick_y) > 0.1){
            rotateRight.setPower(.75 * gamepad2.right_stick_y);
            rotateLeft.setPower(.75 * gamepad2.right_stick_y);
        }
        else{
            rotateRight.setPower(0);
            rotateLeft.setPower(0);
        }
         */
        if(gamepad2.right_trigger > .1){
            robot.claw.clawUp();
        }
        else if(gamepad2.left_trigger > .1){
            robot.claw.setClawPosition( .355);
        }
        else{
            robot.claw.clawDown();

        }


        if(gamepad2.dpad_up){
            treeAngle += .02;
        }
        else if(gamepad2.dpad_down){
            treeAngle -= .02;
        }


        robot.claw.setClawAngle(treeAngle);


        /*
        telemetry.addData("left up per rot", liftLeft.getCurrentPosition() / (rotateLeft.getCurrentPosition()+.000001));
        telemetry.addData("right up per rot", liftRight.getCurrentPosition() / (rotateRight.getCurrentPosition()+.000001));
        telemetry.addData("left up per rot", liftLeft.getCurrentPosition() / (rotateRight.getCurrentPosition()+0.000001));
        telemetry.addData("parallel odom", robot.drivetrain.bl.getCurrentPosition());
        telemetry.update();

         */


    }
}
