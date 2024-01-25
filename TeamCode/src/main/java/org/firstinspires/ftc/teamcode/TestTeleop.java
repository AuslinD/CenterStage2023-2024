package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;


//@TeleOp(name = "test", group = "test")
//public class TestTeleop extends OpMode {
//    float gain = 2;
//    DcMotor rotateLeft, rotateRight, liftLeft, liftRight;
//
//    Robot robot;
//    int treeAngle = 0;
//    @Override
//    public void init() {
//        robot = new Robot(this);
//
//
//
//    }
//
//    @Override
//    public void loop() {
//        /*
//        if(Math.abs(gamepad2.left_stick_y) > 0.1){
//            liftLeft.setPower(1 * gamepad2.left_stick_y);
//            liftRight.setPower(1 * gamepad2.left_stick_y);
//        }
//        else{
//            liftLeft.setPower(0);
//            liftRight.setPower(0);
//        }
//        if(Math.abs(gamepad2.right_stick_y) > 0.1){
//            rotateRight.setPower(.75 * gamepad2.right_stick_y);
//            rotateLeft.setPower(.75 * gamepad2.right_stick_y);
//        }
//        else{
//            rotateRight.setPower(0);
//            rotateLeft.setPower(0);
//        }
//         */
//        /*
//        if(gamepad2.right_trigger > .1){
//            robot.claw.clawUp();
//        }
//        else if(gamepad2.left_trigger > .1){
//            robot.claw.setClawPosition( .355);
//        }
//        else{
//            robot.claw.clawDown();
//
//        }
//
//
//        if(gamepad2.dpad_up){
//            treeAngle += .02;
//        }
//        else if(gamepad2.dpad_down){
//            treeAngle -= .02;
//        }
//
//
//        robot.claw.setClawAngle(treeAngle);
//
//         */
//
//
//        if (gamepad2.dpad_up) {
//            // Only increase the gain by a small amount, since this loop will occur multiple times per second.
//            gain += 0.005;
//        } else if (gamepad2.dpad_down && gain > 1) { // A gain of less than 1 will make the values smaller, which is not helpful.
//            gain -= 0.005;
//        }
//
//        float[] hsvValuesTop = new float[3];
//        float[] hsvValuesBot = new float[3];
//        NormalizedRGBA colorsTop = robot.colorSensorTop.getNormalizedColors();
//        NormalizedRGBA colorsBot = robot.colorSensorBottom.getNormalizedColors();
//        Color.colorToHSV(colorsTop.toColor(), hsvValuesTop);
//        Color.colorToHSV(colorsBot.toColor(), hsvValuesBot);
//
//        telemetry.addLine()
//                .addData("Red", "%.3f", colorsTop.red)
//                .addData("Green", "%.3f", colorsTop.green)
//                .addData("Blue", "%.3f", colorsTop.blue);
//        telemetry.addLine()
//                .addData("Hue", "%.3f", hsvValuesTop[0])
//                .addData("Saturation", "%.3f", hsvValuesTop[1])
//                .addData("Value", "%.3f", hsvValuesTop[2]);
//        telemetry.addData("Alpha", "%.3f", colorsTop.alpha);
//
//        telemetry.addLine()
//                .addData("Red", "%.3f", colorsBot.red)
//                .addData("Green", "%.3f", colorsBot.green)
//                .addData("Blue", "%.3f", colorsBot.blue);
//        telemetry.addLine()
//                .addData("Hue", "%.3f", hsvValuesBot[0])
//                .addData("Saturation", "%.3f", hsvValuesBot[1])
//                .addData("Value", "%.3f", hsvValuesBot[2]);
//        telemetry.addData("Alpha", "%.3f", colorsBot.alpha);
//        telemetry.addData("Gain", gain);
//        telemetry.update();
//
//    }
//}
