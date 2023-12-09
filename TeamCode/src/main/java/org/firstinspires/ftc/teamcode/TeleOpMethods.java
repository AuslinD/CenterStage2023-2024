package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


public class TeleOpMethods {

    private static Robot robot;
    static OpMode opMode;
    static double initPos;
    static double treeAngle = .47;
    int treeAngleIndex = 0;
    boolean gameOn = false;
    //Manipulator
    boolean state [] = {false,false,false,false,false,false,false,false,false,false,false,false,false}; // 6 stats/steps = to false at start
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
        robot.plane.setPosition(.47);
        treeAngle = .47;
        gameOn = false;
    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        notDriveTrainStuff(gamepad1, gamepad2);//for tryouts
        manipulatorStuff(gamepad1, gamepad2);
        intakeStuff(gamepad1, gamepad2);
        clawStuff(gamepad1, gamepad2);
        planeServoControl(gamepad1, gamepad2);
        hang(gamepad1, gamepad2);
        if(robot.lift.liftLeft.getCurrentPosition() > 1600){
            robot.lift.rotateRight.setPower(.35);
            robot.lift.rotateLeft.setPower(.35);

        }
        telemetry();


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
            treeAngle += .02;
        }
        else if(gamepad2.dpad_down){
            treeAngle -= .02;
        }
        if (gamepad2.dpad_right){ //mathew's macro
            /*ElapsedTime runtime = new ElapsedTime();
            if(runtime.milliseconds() > 500){
                runtime.reset();
                double[] change = {0.1499, 0.2699, 0.49, 0.55, 0.82};
                treeAngle = change[treeAngleIndex];
                treeAngleIndex++;
                if(treeAngleIndex > 4){
                    treeAngleIndex = 0;
                }
            }*/

            state[0] = true; //rotate angle



        }
        if(state[0]) // step 1
        {
            robot.lift.liftLeft.setTargetPosition(0); //retracts lift??
            robot.lift.liftRight.setTargetPosition(0);
            robot.claw.clawUp(); //retracts tree
            treeAngle = 0.1499; //angles tree
            state[0] = false;
            state[1] = true;
        }
        else if (state[1]) // step 2
        {
            treeAngle = 0.18999;
            if (robot.lift.rotateRight.getCurrentPosition() < -1020 && robot.lift.rotateRight.getCurrentPosition() > -1040) // check to see if the lift is btw the value
            {
                state[1] = false;
                state[2] = true;
            }
            else if(robot.lift.rotateRight.getCurrentPosition() > -1020){
                robot.lift.rotateRight.setPower(-0.5);
                robot.lift.rotateLeft.setPower(-0.5);

            }
            else if(robot.lift.rotateRight.getCurrentPosition() < -1040){
                robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
                robot.lift.rotateLeft.setPower(0.5);
            }

        }
        if(state[2]) // step 3
        {
            treeAngle = 0.10999;
            state[2] = false;
        }
        if(state[3]) // step 4
        {
            if (robot.lift.rotateRight.getCurrentPosition() < -130 && robot.lift.rotateRight.getCurrentPosition() > -145) // check to see if the lift is btw the value
            {
                state[3] = false;
                state[4] = true;

            } else if (robot.lift.rotateRight.getCurrentPosition() > -130) {
                robot.lift.rotateRight.setPower(-0.5);
                robot.lift.rotateLeft.setPower(-0.5);


            } else if (robot.lift.rotateRight.getCurrentPosition() < -145) {
                robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
                robot.lift.rotateLeft.setPower(0.5);
            }
        }
        if(state[4]) // step 5
        {
            robot.lift.liftRight.setTargetPosition(277);
            robot.lift.liftLeft.setTargetPosition(277);
            state[4] = false;
            state[5] = true;

        }
        if(state[5]) // step 6
        {
            if (robot.lift.rotateRight.getCurrentPosition() < -130 && robot.lift.rotateRight.getCurrentPosition() > -145) // check to see if the lift is btw the value
            {
                state[5] = false;
                state[6] = true;

            } else if (robot.lift.rotateRight.getCurrentPosition() > -130) {
                robot.lift.rotateRight.setPower(-0.5);
                robot.lift.rotateLeft.setPower(-0.5);


            } else if (robot.lift.rotateRight.getCurrentPosition() < -145) {
                robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
                robot.lift.rotateLeft.setPower(0.5);
            }

        }
        if(state[6]) // step 7
        {
            robot.lift.liftLeft.setTargetPosition(-12);
            robot.lift.liftRight.setTargetPosition(-12);
            treeAngle = 0.49;
            if (robot.lift.rotateRight.getCurrentPosition() < 135 && robot.lift.rotateRight.getCurrentPosition() > 115 ) // check to see if the lift is btw the value
            {
                state[6] = false;

            } else if (robot.lift.rotateRight.getCurrentPosition() > 135) {
            robot.lift.rotateRight.setPower(-0.5);
            robot.lift.rotateLeft.setPower(-0.5);


        } else if (robot.lift.rotateRight.getCurrentPosition() < 115)
        {
            robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
            robot.lift.rotateLeft.setPower(0.5);
        }

        }











        if(treeAngle > .9){
            //treeAngle = .7;
            treeAngle = .9;
        }
        else if (treeAngle < .01){
            //treeAngle = .3;
            treeAngle = .01;
        }



        robot.claw.setClawAngle(treeAngle);
    }
    private void planeServoControl(Gamepad gamepad1, Gamepad gamepad2) {

        if (gamepad1.a || gamepad2.a) {
            robot.setPlanePosition(0.47);
        } else if (gamepad1.b) {
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
        int multiplier = 35;
        if(Math.abs(gamepad2.left_stick_y) > 0.1) {
            up1p += -gamepad2.left_stick_y * multiplier;

            up2p = up1p;
            if (up1p < -10)
            {
                //up1p = 0;
                //up2p = 0;
                up1p = -10;
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

        if(gamepad1.right_trigger > 0.1){
            robot.drivetrain.fl.setPower(.5 * frontLeftPower);
            robot.drivetrain.bl.setPower(.5 * backLeftPower);
            robot.drivetrain.fr.setPower(.5 * frontRightPower);
            robot.drivetrain.br.setPower(.5 * backRightPower);
        }



        if(y == 0 && x ==0 && rx ==0){
            robot.drivetrain.fl.setPower(0);
            robot.drivetrain.bl.setPower(0);
            robot.drivetrain.fr.setPower(0);
            robot.drivetrain.br.setPower(0);
        }

    }

    private static void hang(Gamepad gamepad1, Gamepad gamepad2){
        if(gamepad1.dpad_down){
            robot.hang.hangL.setPower(1);
            robot.hang.hangR.setPower(1);
        }
        else if(gamepad1.dpad_up){
            robot.hang.hangL.setPower(-1);
            robot.hang.hangR.setPower(-1);
        }
        else{
            robot.hang.hangL.setPower(0);
            robot.hang.hangR.setPower(0);
        }

    }

    private static void telemetry(){
        double botHeading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        opMode.telemetry.addData("botHeading", Math.toDegrees(botHeading));
        opMode.telemetry.addData("Yaw", robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));
        opMode.telemetry.addData("Pitch", robot.imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.RADIANS));
        opMode.telemetry.addData("Roll", robot.imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.RADIANS));
        opMode.telemetry.addData("2goal", rn2p);
        opMode.telemetry.addData("goal", rn1p);
        opMode.telemetry.addData("upgoal", up1p);
        opMode.telemetry.addData("up2goal", up2p);
        opMode.telemetry.addData("oursRotR", robot.lift.rotateRight.getCurrentPosition());
        opMode.telemetry.addData("oursRotL", robot.lift.rotateLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLiftL", robot.lift.liftLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLiftR", robot.lift.liftRight.getCurrentPosition());
        opMode.telemetry.addData("treeAngle", treeAngle);
        opMode.telemetry.addData("br", robot.drivetrain.br.getCurrentPosition());
    }






}
