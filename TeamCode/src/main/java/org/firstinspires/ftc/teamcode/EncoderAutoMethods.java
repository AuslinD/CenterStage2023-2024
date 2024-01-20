package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class EncoderAutoMethods {
    Robot robot;

    double initHeading;

    LinearOpMode linearOpMode;

    public EncoderAutoMethods(LinearOpMode opMode) {
        linearOpMode = opMode;
        robot = new Robot(linearOpMode);

    }

    public static double encoderTicksToInches(double ticks) {
        return (1.88976 * 2 * Math.PI * ticks / 384.5);
    }

    public void drive(double distance, double timeout) {
        double initPos = robot.drivetrain.br.getCurrentPosition();
        ElapsedTime runtime = new ElapsedTime();
        int neg = 1;
        while (linearOpMode.opModeIsActive() && runtime.seconds() < timeout && Math.abs(encoderTicksToInches(robot.drivetrain.br.getCurrentPosition() - initPos) - distance) > 0.5) {
            if (distance - (robot.drivetrain.br.getCurrentPosition() - initPos) < 0) {
                neg = -1;
            }
            if (Math.abs(encoderTicksToInches(robot.drivetrain.br.getCurrentPosition()) - initPos - distance) * (1.0 / 8) >= 1) {
                robot.drivetrain.fl.setPower(1.0 * neg);
                robot.drivetrain.bl.setPower(1.0 * neg);
                robot.drivetrain.fr.setPower(1.0 * neg);
                robot.drivetrain.br.setPower(1.0 * neg);

            } else {
                robot.drivetrain.fl.setPower(distance - encoderTicksToInches((robot.drivetrain.br.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.fr.setPower(distance - encoderTicksToInches((robot.drivetrain.br.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.bl.setPower(distance - encoderTicksToInches((robot.drivetrain.br.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.br.setPower(distance - encoderTicksToInches((robot.drivetrain.br.getCurrentPosition() - initPos)) * (1.0 / 8));
            }
            linearOpMode.telemetry.addData("forward", encoderTicksToInches(robot.drivetrain.br.getCurrentPosition()));
            linearOpMode.telemetry.addData("distance", robot.drivetrain.br.getCurrentPosition() - initPos - Math.abs(distance));
            linearOpMode.telemetry.update();
        }

        robot.drivetrain.setALLMotorPower(0);

    }

    public void turn(double heading, double timeout) {
        double initHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        ElapsedTime runtime = new ElapsedTime();
        int neg = 1;
        while (runtime.seconds() < timeout && linearOpMode.opModeIsActive() && Math.abs(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading) - Math.abs(heading) < 0.5) {
            if (heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)< 0) {
                neg = -1;
            }
            if ((Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) >= 1)) {
                if (neg > 0) {//left turn
                    robot.drivetrain.fl.setPower(-1.0);
                    robot.drivetrain.bl.setPower(-1.0);
                    robot.drivetrain.br.setPower(1.0);
                    robot.drivetrain.fr.setPower(1.0);
                } else {
                    robot.drivetrain.fl.setPower(1.0);
                    robot.drivetrain.bl.setPower(1.0);
                    robot.drivetrain.br.setPower(-1.0);
                    robot.drivetrain.fr.setPower(-1.0);
                }
            } else {
                if (neg > 0) {//left turn // problem with turning neg when
                    robot.drivetrain.fl.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * -1);
                    robot.drivetrain.bl.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * -1);
                    robot.drivetrain.br.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5));
                    robot.drivetrain.fr.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5));
                } else {
                    robot.drivetrain.fl.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5));
                    robot.drivetrain.bl.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5));
                    robot.drivetrain.br.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * -1);
                    robot.drivetrain.fr.setPower(Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * -1);
                }
            }
            linearOpMode.telemetry.addData("turn",(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES)));
            linearOpMode.telemetry.update();
        }
    }

    public void sideToSide(double side, double timeout) {
        double initPos = robot.drivetrain.fl.getCurrentPosition();
        ElapsedTime runtime = new ElapsedTime();
        int neg = 1;
        while (linearOpMode.opModeIsActive() && runtime.seconds() < timeout && Math.abs(encoderTicksToInches(robot.drivetrain.fl.getCurrentPosition() - initPos) - Math.abs(side)) < 0.5) {
            if (side - (robot.drivetrain.fl.getCurrentPosition() - initPos) < 0)
            {
                neg = -1;
            }
            if (Math.abs(side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)))) * (1.0 / 8) >= 1){
                if (neg > 0) {//left slide
                    robot.drivetrain.fl.setPower(1.0 * neg);
                    robot.drivetrain.bl.setPower(1.0);
                    robot.drivetrain.br.setPower(1.0 * neg);
                    robot.drivetrain.fr.setPower(1.0);
                } else {
                    robot.drivetrain.fl.setPower(1.0);
                    robot.drivetrain.bl.setPower(1.0 * neg);
                    robot.drivetrain.br.setPower(1.0);
                    robot.drivetrain.fr.setPower(1.0 * neg);
                }
            }
            else {
                if (neg > 0) {//left slide
                    robot.drivetrain.fl.setPower(side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg);
                    robot.drivetrain.bl.setPower(side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5));
                    robot.drivetrain.br.setPower(side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg);
                    robot.drivetrain.fr.setPower(side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos) * (1.0 / 5)));
                } else {
                    robot.drivetrain.fl.setPower(side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5)));
                    robot.drivetrain.bl.setPower(side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg));
                    robot.drivetrain.br.setPower(side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5)));
                    robot.drivetrain.fr.setPower(side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg));
                }
            }
            linearOpMode.telemetry.addData("side", encoderTicksToInches(robot.drivetrain.fl.getCurrentPosition()));
            linearOpMode.telemetry.update();
        }
    }

    public void encoderDrive(int ticks, int milliseconds){
        ElapsedTime elapsedTime = new ElapsedTime();
        int initPos = robot.drivetrain.br.getCurrentPosition();
        while(elapsedTime.milliseconds() < milliseconds && !linearOpMode.isStopRequested() && Math.abs((robot.drivetrain.br.getCurrentPosition() - initPos) - ticks) > 2){
            int currentDistance = robot.drivetrain.br.getCurrentPosition() - initPos;
            int mult = currentDistance - ticks < 0 ? 1: -1;
            robot.drivetrain.fl.setPower(.26 * mult);
            robot.drivetrain.bl.setPower(.26 * mult);
            robot.drivetrain.fr.setPower(.26 * mult);
            robot.drivetrain.br.setPower(.26 * mult);
            linearOpMode.telemetry.addData("currentDistance", currentDistance);
            linearOpMode.telemetry.addData("targetDistance", ticks);
            linearOpMode.telemetry.update();
        }
        robot.drivetrain.setALLMotorPower(0);
    }


    public void encoderIMUTurn(double degrees, int milliseconds){
        ElapsedTime elapsedTime = new ElapsedTime();
        double initPos = robot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180;
        double targetPos = initPos + degrees;
        double mult = degrees < 0 ? -1: 1;
        if(targetPos > 360){
            targetPos -= 360;
        }
        else if(targetPos < 0){
            targetPos = 360 + targetPos;
        }

        while(elapsedTime.milliseconds() < milliseconds && !linearOpMode.isStopRequested() && Math.abs((robot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180) - targetPos) > 1){
            linearOpMode.telemetry.addData("curAngle", robot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180);
            linearOpMode.telemetry.addData("target", targetPos);
            linearOpMode.telemetry.addData("if", robot.getImu().getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + 180 - targetPos);
            linearOpMode.telemetry.update();
            robot.drivetrain.bl.setPower(-.21 * mult);
            robot.drivetrain.fl.setPower(-.21 * mult);
            robot.drivetrain.fr.setPower(.21 * mult);
            robot.drivetrain.br.setPower(.21 * mult);
        }
        robot.drivetrain.setALLMotorPower(0);
    }

    public void liftAnglePosition(int ticks, int milliseconds){
        ElapsedTime elapsedTime = new ElapsedTime();
        int initPos = robot.lift.rotateRight.getCurrentPosition();
        while(elapsedTime.milliseconds() < milliseconds && !linearOpMode.isStopRequested() && Math.abs((robot.lift.rotateRight.getCurrentPosition() - initPos) - ticks) > 2){
            int currentDistance = robot.lift.rotateRight.getCurrentPosition() - initPos;
            int mult = currentDistance - ticks < 0 ? 1: -1;
            robot.lift.rotateLeft.setPower(.5 * mult);
            robot.lift.rotateRight.setPower(.5 * mult);
            linearOpMode.telemetry.addData("currentDistance", currentDistance);
            linearOpMode.telemetry.addData("targetDistance", ticks);
            linearOpMode.telemetry.update();
        }
    }

    public void strafe(double power, int milliseconds){
        ElapsedTime elapsedTime = new ElapsedTime();
        while(elapsedTime.milliseconds() < milliseconds){
            robot.drivetrain.fl.setPower(power * .5);
            robot.drivetrain.br.setPower(power * .5);
            robot.drivetrain.fr.setPower(-power * .5);
            robot.drivetrain.bl.setPower(-power * .5);
        }
        robot.drivetrain.setALLMotorPower(0);
    }



}