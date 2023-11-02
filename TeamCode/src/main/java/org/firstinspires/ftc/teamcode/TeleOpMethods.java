package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class TeleOpMethods {

    private static Robot robot;
    static OpMode opMode;
    //Manipulator
    static double rn1p, rn2p, up1p, up2p;

    private static double lockHeadingHeading;
    public TeleOpMethods(OpMode opMode)
    {   //manip later
        rn1p = 0;
        rn2p = 0;
        up1p = 0;
        up2p = 0;
        this.robot = new Robot(opMode);
        this.opMode = opMode; 
        robot.imu.resetYaw();

    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        notDriveTrainStuff(gamepad1, gamepad2);
        manipulatorStuff(gamepad1, gamepad2);



    }

    private static void driveTrainStuff(Gamepad gamepad1, Gamepad gamepad2) {
        //Drivetrain Stuff
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        IMU imu = robot.getImu();


        if (gamepad1.dpad_up) {
            robot.resetImu();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        opMode.telemetry.addData("Yaw", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        opMode.telemetry.addData("Pitch", imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.RADIANS));
        opMode.telemetry.addData("Roll", imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.RADIANS));

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;



        if(gamepad1.right_trigger > 0.1){
            frontRightPower *= .5;
            backRightPower *= .5;
            frontLeftPower *= .5;
            backLeftPower *= .5;
        }

        if(!gamepad1.right_bumper) {
            lockHeadingHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        }
        else{
            double mathLockHeadingHeading = lockHeadingHeading + Math.PI;
            double mathBotHeading = botHeading + Math.PI;
            double headingError;
            if(Math.abs(mathBotHeading - mathLockHeadingHeading) < Math.abs(((2 * Math.PI) - mathBotHeading) - mathLockHeadingHeading)){
                headingError = mathBotHeading - mathLockHeadingHeading;
            }
            else{
                headingError = ((2 * Math.PI) - mathBotHeading) - mathLockHeadingHeading;
            }
            double correctionPower = headingError / Math.PI;
            opMode.telemetry.addData("headingError", headingError);
            opMode.telemetry.addData("correctionPower", correctionPower);
            opMode.telemetry.update();

            if(correctionPower > 0.1){

            }
            else if(correctionPower < -.1){

            }




        }



        robot.drivetrain.fl.setPower(frontLeftPower);
        robot.drivetrain.bl.setPower(backLeftPower);
        robot.drivetrain.fr.setPower(frontRightPower);
        robot.drivetrain.br.setPower(backRightPower);

    }

    private static void manipulatorStuff(Gamepad gamepad1, Gamepad gamepad2) {

        //Manipulator and lift stuff
        int multiplier = 30;
        if(Math.abs(gamepad2.left_stick_y) > 0.1 || Math.abs(gamepad2.right_stick_y) > 0.1) {
            up1p += gamepad2.left_stick_y * multiplier;
            rn1p += gamepad2.right_stick_y * multiplier;
            rn2p = rn1p;
            up2p = up1p;
            if (up1p < 0 || up2p < 0)
            {
                up1p = 0;
                up2p = 0;
            }
            robot.lift.setMotorsToGoUpOrDown((int)(up1p));

            robot.lift.rotateRight.setPower(gamepad2.right_stick_y);
            robot.lift.rotateLeft.setPower(gamepad2.right_stick_y);

            //double max = Math.max(Math.max(Math.abs(rn1p) , Math.abs(rn2p)), Math.max((up1p), Math.abs(up2p)));

            /*if (Math.abs(max) > 1) {
                rn1p /= Math.abs(max);
                rn2p /= Math.abs(max);
                up1p /= Math.abs(max);
                up2p /= Math.abs(max);
            }*/
        }
        else{
            robot.lift.rotateRight.setPower(0);
            robot.lift.rotateLeft.setPower(0);
        }

        if(gamepad2.a){
            robot.claw.clawDown();
        }
        if(gamepad2.b){
            robot.claw.clawUp();
        }

        opMode.telemetry.addData("2goal", rn2p);
        opMode.telemetry.addData("goal", rn1p);
        opMode.telemetry.addData("upgoal", up1p);
        opMode.telemetry.addData("up2goal", up2p);
        opMode.telemetry.addData("oursRR", robot.lift.rotateRight.getCurrentPosition());
        opMode.telemetry.addData("oursRL", robot.lift.rotateLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLL", robot.lift.liftLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLR", robot.lift.liftRight.getCurrentPosition());
        opMode.telemetry.update();
    }

    public static void notDriveTrainStuff(Gamepad gamepad1, Gamepad gamepad2){
        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        robot.drivetrain.fl.setPower(frontLeftPower);
        robot.drivetrain.bl.setPower(backLeftPower);
        robot.drivetrain.fr.setPower(frontRightPower);
        robot.drivetrain.br.setPower(backRightPower);

    }


}
