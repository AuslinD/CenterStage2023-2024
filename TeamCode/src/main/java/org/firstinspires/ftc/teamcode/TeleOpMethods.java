package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class TeleOpMethods {

    private static Robot robot;
    static OpMode opMode;
    static double initPos;
    static double treeAngle = 0;
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
        initPos = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        notDriveTrainStuff(gamepad1, gamepad2);//for tryouts
        manipulatorStuff(gamepad1, gamepad2);
        intakeStuff(gamepad1, gamepad2);
        clawStuff(gamepad1, gamepad2);
        planeServoControl(gamepad1);

        opMode.telemetry.update();


    }

    private void misc(){

    }


    private void intakeStuff(Gamepad gamepad1, Gamepad gamepad2) {
        if(gamepad2.a){
            robot.intake.spinTake(1);
            robot.intake.lowerIntake();
        }
        else if(gamepad2.y){
            robot.intake.spinTake(-1);
            robot.intake.stowIntake();
        }
        else{
            robot.intake.spinTake(0);
        }
        if(gamepad2.x){
            robot.intake.lowerIntake();
        }
        else if(gamepad2.b){
            robot.intake.stowIntake();
        }
    }
    private void clawStuff(Gamepad gamepad1, Gamepad gamepad2){
        if(gamepad2.right_trigger > .1){
            robot.claw.clawUp();
        }
        else if(gamepad2.left_trigger > .1){
            robot.claw.setClawPosition( .55);
        }
        else{
            robot.claw.clawDown();
        }
        if(gamepad2.dpad_up){
            treeAngle += .025;
        }
        else if(gamepad2.dpad_down){
            treeAngle -= .025;
        }
        robot.claw.setClawAngle(treeAngle);
    }
    private void planeServoControl(Gamepad gamepad) {

        if (gamepad.a) {
            robot.setPlanePosition(0.2);
        } else if (gamepad.b) {
            robot.setPlanePosition(0.8);
        }
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

        double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);


        opMode.telemetry.addData("botHeading", Math.toDegrees(botHeading));
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


            if(correctionPower > 0.1){

            }
            else if(correctionPower < -.1){

            }




        }

        if(Math.abs(gamepad2.right_stick_x) > 0.1){
            frontLeftPower += .2 * gamepad2.right_stick_y;
            backLeftPower += .2 * gamepad2.right_stick_y;
            frontRightPower -= .2 * gamepad2.right_stick_y;
            backRightPower -= .2 * gamepad2.right_stick_y;
        }



        robot.drivetrain.fl.setPower(frontLeftPower);
        robot.drivetrain.bl.setPower(backLeftPower);
        robot.drivetrain.fr.setPower(frontRightPower);
        robot.drivetrain.br.setPower(backRightPower);

    }

    private static void manipulatorStuff(Gamepad gamepad1, Gamepad gamepad2) {

        //Manipulator and lift stuff
        int multiplier = 30;
        if(Math.abs(gamepad2.left_stick_y) > 0.1) {
            up1p += -gamepad2.left_stick_y * multiplier;

            up2p = up1p;
            if (up1p < 0 || up2p < 0)
            {
                //up1p = 0;
                //up2p = 0;
            }
            robot.lift.setMotorsToGoUpOrDown((int)(up1p));



            //double max = Math.max(Math.max(Math.abs(rn1p) , Math.abs(rn2p)), Math.max((up1p), Math.abs(up2p)));

            /*if (Math.abs(max) > 1) {
                rn1p /= Math.abs(max);
                rn2p /= Math.abs(max);
                up1p /= Math.abs(max);
                up2p /= Math.abs(max);
            }*/
        }

        if(gamepad2.right_bumper){
            rn1p = .75;
            rn2p = rn1p;
            robot.lift.rotateRight.setPower(rn1p);
            robot.lift.rotateLeft.setPower(rn2p);
        }
        else if(gamepad2.left_bumper){
            rn1p = -.75;
            rn2p = rn1p;
            robot.lift.rotateRight.setPower(rn1p);
            robot.lift.rotateLeft.setPower(rn2p);
        }
        else{
            robot.lift.rotateRight.setPower(0);
            robot.lift.rotateLeft.setPower(0);
        }


        opMode.telemetry.addData("2goal", rn2p);
        opMode.telemetry.addData("goal", rn1p);
        opMode.telemetry.addData("upgoal", up1p);
        opMode.telemetry.addData("up2goal", up2p);
        opMode.telemetry.addData("oursRR", robot.lift.rotateRight.getCurrentPosition());
        opMode.telemetry.addData("oursRL", robot.lift.rotateLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLL", robot.lift.liftLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLR", robot.lift.liftRight.getCurrentPosition());

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

        if(gamepad1.right_trigger > 0.1){
            frontRightPower *= .5;
            backRightPower *= .5;
            frontLeftPower *= .5;
            backLeftPower *= .5;
        }


        robot.drivetrain.fl.setPower(frontLeftPower);
        robot.drivetrain.bl.setPower(backLeftPower);
        robot.drivetrain.fr.setPower(frontRightPower);
        robot.drivetrain.br.setPower(backRightPower);

        if(y == 0 && x ==0 && rx ==0){
            robot.drivetrain.fl.setPower(0);
            robot.drivetrain.bl.setPower(0);
            robot.drivetrain.fr.setPower(0);
            robot.drivetrain.br.setPower(0);
        }

    }




}
