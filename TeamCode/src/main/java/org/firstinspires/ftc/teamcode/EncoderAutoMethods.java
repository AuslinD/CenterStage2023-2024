package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class EncoderAutoMethods {
    Robot robot;

    double initHeading;

    LinearOpMode linearOpMode;
    public EncoderAutoMethods(){
        robot = robot;
        linearOpMode = linearOpMode;
    }
    public static double encoderTicksToInches(double ticks) {
        return (1.88976 * 2 * Math.PI * ticks / 384.5);
    }
    public void drive(double distance, double timeout)
    {
        double initPos = robot.drivetrain.bl.getCurrentPosition();
        ElapsedTime runtime = new ElapsedTime();
        int neg = 1;
        while (linearOpMode.opModeIsActive() && runtime.seconds() < timeout && Math.abs(robot.drivetrain.bl.getCurrentPosition() - initPos) < Math.abs(distance)){
            if ((robot.drivetrain.bl.getCurrentPosition() - initPos) < 0){
                neg = -1;
            }
            if (encoderTicksToInches((robot.drivetrain.bl.getCurrentPosition() - initPos)) * (1/8) >= 1){
                robot.drivetrain.fl.setPower(1 * neg);
                robot.drivetrain.bl.setPower(1 * neg);
                robot.drivetrain.fr.setPower(1 * neg);
                robot.drivetrain.br.setPower(1 * neg);

            }
            else {
                robot.drivetrain.fl.setPower(encoderTicksToInches((robot.drivetrain.bl.getCurrentPosition() - initPos)) * (1/8) * neg);
                robot.drivetrain.fl.setPower(encoderTicksToInches((robot.drivetrain.br.getCurrentPosition() - initPos)) * (1/8) * neg);
                robot.drivetrain.fl.setPower(encoderTicksToInches((robot.drivetrain.fr.getCurrentPosition() - initPos)) * (1/8) * neg);
                robot.drivetrain.fl.setPower(encoderTicksToInches((robot.drivetrain.fl.getCurrentPosition() - initPos)) * (1/8) * neg);
            }
        }



    }
    public void turn(double heading, double timeout){
        double initHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        ElapsedTime runtime = new ElapsedTime();
        while (runtime.seconds() < timeout && linearOpMode.opModeIsActive() && Math.abs(robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - initHeading) < heading )

    }
}
