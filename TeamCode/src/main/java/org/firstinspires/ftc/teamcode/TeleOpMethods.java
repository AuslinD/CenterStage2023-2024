package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class TeleOpMethods {
    Robot robot;
    //Manipulator

    public TeleOpMethods(OpMode opMode)
    {   //manip later
        this.robot = new Robot(opMode);
    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        double FLP = 0;
        double FRP = 0;
        double BRP = 0;
        double BLP = 0;
        double rn1p = 0;
        double rn2p = 0;
        double up1p = 0;
        double up2p = 0;

        if(Math.abs(gamepad1.left_stick_y) > 0.1 || Math.abs(gamepad1.left_stick_x) > 0.1 || Math.abs(gamepad1.right_stick_x) > 0.1) {
            FLP += gamepad1.left_stick_y;
            FRP += gamepad1.left_stick_y;
            BRP += gamepad1.left_stick_y;
            BLP += gamepad1.left_stick_y;

            FLP += gamepad1.left_stick_x;
            FRP += gamepad1.left_stick_x;
            BRP += gamepad1.left_stick_x;
            BLP += gamepad1.left_stick_x;

            FLP += gamepad1.right_stick_x;
            FRP += gamepad1.right_stick_x;
            BRP += gamepad1.right_stick_x;
            BLP += gamepad1.right_stick_x;

            double max = Math.max(Math.max(Math.abs(FLP) , Math.abs(FRP)), Math.max((BRP), Math.abs(BLP)));

            if (Math.abs(max) > 1) {
                FRP /= Math.abs(max);
                FLP /= Math.abs(max);
                BRP /= Math.abs(max);
                BLP /= Math.abs(max);
            }
            if(Math.abs(gamepad1.right_trigger) > 0.1) {

                robot.drivetrain.getMotors()[0].setPower(FLP * .4);
                robot.drivetrain.getMotors()[1].setPower(FRP * .4);
                robot.drivetrain.getMotors()[2].setPower(BLP * .4);
                robot.drivetrain.getMotors()[3].setPower(BRP * .4);
            }
            else {
                robot.drivetrain.getMotors()[0].setPower(FLP);
                robot.drivetrain.getMotors()[1].setPower(FRP);
                robot.drivetrain.getMotors()[2].setPower(BLP);
                robot.drivetrain.getMotors()[3].setPower(BRP);
            }

        }


        else {
            robot.drivetrain.getMotors()[0].setPower(0);
            robot.drivetrain.getMotors()[1].setPower(0);
            robot.drivetrain.getMotors()[2].setPower(0);
            robot.drivetrain.getMotors()[3].setPower(0);
        }
        if(Math.abs(gamepad2.left_stick_y) > 0.1 || Math.abs(gamepad2.left_stick_x) > 0.1 || Math.abs(gamepad2.right_stick_x) > 0.1) {
            rn1p += gamepad2.left_stick_y;
            rn2p += gamepad2.left_stick_y;
            up1p += gamepad2.left_stick_y;
            up2p += gamepad2.left_stick_y;

            rn1p += gamepad2.left_stick_x;
            rn2p += gamepad2.left_stick_x;
            up1p += gamepad2.left_stick_x;
            up2p += gamepad2.left_stick_x;

            rn1p += gamepad2.right_stick_x;
            rn2p += gamepad2.right_stick_x;
            up1p += gamepad2.right_stick_x;
            up2p += gamepad2.right_stick_x;

            double max = Math.max(Math.max(Math.abs(rn1p) , Math.abs(rn2p)), Math.max((up1p), Math.abs(up2p)));

            if (Math.abs(max) > 1) {
                rn1p /= Math.abs(max);
                rn2p /= Math.abs(max);
                up1p /= Math.abs(max);
                up2p /= Math.abs(max);
            }
            if(Math.abs(gamepad2.right_trigger) > 0.1) {

                robot.drivetrain.getMotors()[0].setPower(rn1p * .4);
                robot.drivetrain.getMotors()[1].setPower(rn2p * .4);
                robot.drivetrain.getMotors()[2].setPower(up1p * .4);
                robot.drivetrain.getMotors()[3].setPower(up2p * .4);
            }
            else {
                robot.drivetrain.getMotors()[0].setPower(rn1p);
                robot.drivetrain.getMotors()[1].setPower(rn2p);
                robot.drivetrain.getMotors()[2].setPower(up1p);
                robot.drivetrain.getMotors()[3].setPower(up2p);
            }

        }



    }


}
