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
        double initPos = robot.lift.rotateLeft.getCurrentPosition();
        ElapsedTime runtime = new ElapsedTime();
        int neg = 1;
        while (linearOpMode.opModeIsActive() && runtime.seconds() < timeout && Math.abs(robot.lift.rotateLeft.getCurrentPosition() - initPos - Math.abs(distance)) < Math.abs(distance)) {
            if (distance - (robot.lift.rotateLeft.getCurrentPosition() - initPos) < 0) {
                neg = -1;
            }
            if (Math.abs(distance - encoderTicksToInches(robot.lift.rotateLeft.getCurrentPosition()) - initPos) * (1.0 / 8) >= 1) {
                robot.drivetrain.fl.setPower(1.0 * neg);
                robot.drivetrain.bl.setPower(1.0 * neg);
                robot.drivetrain.fr.setPower(1.0 * neg);
                robot.drivetrain.br.setPower(1.0 * neg);

            } else {
                robot.drivetrain.fl.setPower(distance - encoderTicksToInches((robot.lift.rotateLeft.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.fr.setPower(distance - encoderTicksToInches((robot.lift.rotateLeft.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.bl.setPower(distance - encoderTicksToInches((robot.lift.rotateLeft.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.br.setPower(distance - encoderTicksToInches((robot.lift.rotateLeft.getCurrentPosition() - initPos)) * (1.0 / 8));
            }
            linearOpMode.telemetry.addData("foreward", encoderTicksToInches(robot.lift.rotateLeft.getCurrentPosition()));
            linearOpMode.telemetry.addData("distance", robot.lift.rotateLeft.getCurrentPosition() - initPos - Math.abs(distance));
            linearOpMode.telemetry.update();
        }

        robot.drivetrain.setALLMotorPower(0);

    }

    public void turn(double heading, double timeout) {
        double initHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        ElapsedTime runtime = new ElapsedTime();
        int neg = 1;
        while (runtime.seconds() < timeout && linearOpMode.opModeIsActive() && Math.abs(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading - heading) < 0.5) {
            if (heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)< 0) {
                neg *= -1;
            }
            if ((Math.abs(heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) >= 1)) {
                if (neg > 0) {//left turn
                    robot.drivetrain.fl.setPower(1.0 * neg);
                    robot.drivetrain.bl.setPower(1.0 * neg);
                    robot.drivetrain.br.setPower(1.0);
                    robot.drivetrain.fr.setPower(1.0);
                } else {
                    robot.drivetrain.fl.setPower(1.0);
                    robot.drivetrain.bl.setPower(1.0);
                    robot.drivetrain.br.setPower(1.0 * neg);
                    robot.drivetrain.fr.setPower(1.0 * neg);
                }
            } else {
                if (neg > 0) {//left turn
                    robot.drivetrain.fl.setPower((heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * neg);
                    robot.drivetrain.bl.setPower((heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * neg);
                    robot.drivetrain.br.setPower((heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5));
                    robot.drivetrain.fr.setPower((heading - robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading) * (1.0 / 5));
                } else {
                    robot.drivetrain.fl.setPower((heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5));
                    robot.drivetrain.bl.setPower((heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5));
                    robot.drivetrain.br.setPower((heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * neg);
                    robot.drivetrain.fr.setPower((heading - (robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading)) * (1.0 / 5) * neg);
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
        while(elapsedTime.milliseconds() < milliseconds && Math.abs((robot.drivetrain.br.getCurrentPosition() - initPos) - ticks) > 2){
            int currentDistance = robot.drivetrain.br.getCurrentPosition() - initPos;
            robot.drivetrain.fl.setPower(.5 * currentDistance - ticks > 0 ? 1: -1);
            robot.drivetrain.bl.setPower(.5 * currentDistance - ticks > 0 ? 1: -1);
            robot.drivetrain.fr.setPower(.5 * currentDistance - ticks > 0 ? 1: -1);
            robot.drivetrain.br.setPower(.5 * currentDistance - ticks > 0 ? 1: -1);
            linearOpMode.telemetry.addData("currentDistance", currentDistance);
            linearOpMode.telemetry.addData("targetDistance", ticks);
            linearOpMode.telemetry.update();
        }
        robot.drivetrain.setALLMotorPower(0);
    }



}