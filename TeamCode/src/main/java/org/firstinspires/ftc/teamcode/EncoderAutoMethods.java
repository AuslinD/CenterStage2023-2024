package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class EncoderAutoMethods {
    Robot robot;

    double initHeading;

    LinearOpMode linearOpMode;

    public EncoderAutoMethods() {
        robot = robot;
        linearOpMode = linearOpMode;
    }

    public static double encoderTicksToInches(double ticks) {
        return (1.88976 * 2 * Math.PI * ticks / 384.5);
    }

    public void drive(double distance, double timeout) {
        double initPos = robot.drivetrain.bl.getCurrentPosition();
        ElapsedTime runtime = new ElapsedTime();
        int neg = 1;
        while (linearOpMode.opModeIsActive() && runtime.seconds() < timeout && Math.abs(robot.drivetrain.bl.getCurrentPosition() - initPos - Math.abs(distance)) < Math.abs(distance)) {
            if (distance - (robot.drivetrain.bl.getCurrentPosition() - initPos) < 0) {
                neg = -1;
            }
            if (Math.abs(distance - encoderTicksToInches(robot.drivetrain.bl.getCurrentPosition()) - initPos) * (1.0 / 8) >= 1) {
                robot.drivetrain.fl.setPower(1.0 * neg);
                robot.drivetrain.bl.setPower(1.0 * neg);
                robot.drivetrain.fr.setPower(1.0 * neg);
                robot.drivetrain.br.setPower(1.0 * neg);

            } else {
                robot.drivetrain.fl.setPower(distance - encoderTicksToInches((robot.drivetrain.bl.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.fr.setPower(distance - encoderTicksToInches((robot.drivetrain.br.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.bl.setPower(distance - encoderTicksToInches((robot.drivetrain.fr.getCurrentPosition() - initPos)) * (1.0 / 8));
                robot.drivetrain.br.setPower(distance - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 8));
            }
        }
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
        }
    }

        public void sideToSide(double side, double timeout) {
            double initPos = robot.drivetrain.fl.getCurrentPosition();
            ElapsedTime runtime = new ElapsedTime();
            int neg = 1;
            while ((linearOpMode.opModeIsActive() && runtime.seconds() < timeout && (Math.abs(robot.drivetrain.fl.getCurrentPosition()) - initPos - Math.abs(side)) < Math.abs(side)))
            {
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
                        robot.drivetrain.fl.setPower((side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg);
                        robot.drivetrain.bl.setPower((side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5));
                        robot.drivetrain.br.setPower((side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg);
                        robot.drivetrain.fr.setPower((side - encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos) * (1.0 / 5));
                    } else {
                        robot.drivetrain.fl.setPower((side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5));
                        robot.drivetrain.bl.setPower((side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg);
                        robot.drivetrain.br.setPower((side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5));
                        robot.drivetrain.fr.setPower((side - (encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1.0 / 5) * neg);
                    }
    }
}
