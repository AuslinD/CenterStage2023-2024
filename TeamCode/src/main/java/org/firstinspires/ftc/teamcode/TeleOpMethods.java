package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class TeleOpMethods {

    Macro macro;

    private static Robot robot;
    static OpMode opMode;
    static double initPos;
    static double treeAngle = .47;
    private static boolean ignoreBounds = false;
    boolean[] pixels = new boolean[]{false, false};
    int treeAngleIndex = 0;
    boolean gameOn;
    //Manipulator
    boolean time = false;
    static boolean state [] = {false,false,false,false,false,false,false,false}; // 6 stats/steps = to false at start

    static double rn1p, rn2p, up1p, up2p;
    boolean down = false;
    boolean lockToAngle = false;
    ElapsedTime stateOneTime = new ElapsedTime();
    ElapsedTime planeTimer = new ElapsedTime();
    boolean[] planeLaunch = new boolean[]{false, false};
    ElapsedTime macrooo = new ElapsedTime();
    private static double lockHeadingHeading;

    public TeleOpMethods(OpMode opMode)
    {   //manip later
        rn1p = 0;
        rn2p = 0;
        up1p = 0;
        up2p = 0;
        this.robot = new Robot(opMode);
        this.macro = new Macro (opMode);
        this.opMode = opMode;
        robot.imu.resetYaw();
        initPos = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        robot.plane.setPosition(-1);
        robot.setPlaneClosed();
        treeAngle = .47;
        gameOn = false;
        ignoreBounds = false;
        //down = false;
    }

    public void teleOpControls(Gamepad gamepad1, Gamepad gamepad2)
    {
        notDriveTrainStuff(gamepad1, gamepad2);//for tryouts
        manipulatorStuff(gamepad1, gamepad2);
        intakeStuff(gamepad1, gamepad2);
        clawStuff(gamepad1, gamepad2);
        planeServoControl(gamepad1, gamepad2);
        hang(gamepad1, gamepad2);
//        if(robot.lift.liftLeft.getCurrentPosition() > 1600){
//            robot.lift.rotateRight.setPower(.35);
//            robot.lift.rotateLeft.setPower(.35);

//        }
        telemetry();
    }

    private void misc(){

    }

    private void intakeStuff(Gamepad gamepad1, Gamepad gamepad2) {
        if(gamepad2.a){
            robot.intake.spinTake(0.75);
            //robot.intake.lowerIntake();
        }
        else if(gamepad2.y){
            robot.intake.spinTake(-1);
            //robot.intake.stowIntake();
        }
        else{
            robot.intake.spinTake(0);
        }
        if(gamepad2.x){
            //robot.intake.lowerIntake();
            robot.intake.spinTakeTroll(1);
        }
        else if(gamepad2.b){
            //robot.intake.stowIntake();
        }
        // Rumble if amperage goes up significantly
        double currentThreshold = 2.3; // Adjust this value based on your motor and testing
        double rumbleStrength = 1.0; // Rumble strength (0.0 to 1.0)
        int rumbleDuration = 10; // Rumble duration in milliseconds

        double intakeCurrent = robot.intake.intakeMotor.getCurrent(CurrentUnit.AMPS);

        if (intakeCurrent > currentThreshold) {
            // Trigger rumble with specified strength and duration
            gamepad2.rumble(100, 100, 200);
            gamepad1.rumble(100, 100, 200);
        }
//        if(robot.intake.getHsvValuesTop()[0] > 100){
//            gamepad1.rumble(.75, .75, 10);
//            gamepad2.rumble(.75, .75, 10);
//        }
//        else if(robot.intake.getHsvValuesBot()[0] > 100){
//            gamepad1.rumble(.01, 0, 10);
//            gamepad2.rumble(.01, 0, 10);
//        }
        /* ONE rumble code
        if(robot.intake.getHsvValuesTop()[0] < 100){
            pixels[1] = false;
        }
        else if(!pixels[1] && pixels[0]){
            pixels[1] = true;
            gamepad1.rumble(.75, .75, 500);
        }
        if(robot.intake.getHsvValuesBot()[0] < 100){
            pixels[0] = false;
        }
        else if(!pixels[0]){
            pixels[0] = true;
            gamepad1.rumble(.25, .25, 500);
        }
         */
    }


    public static int[] macro_timing = {0, 1000, 0, 175, 750};

    private void clawStuff(Gamepad gamepad1, Gamepad gamepad2){

        boolean macroOff = !state[1]&&!state[0]&&!state[3]&&!state[2]&&!state[4]&&!state[5]&&!state[6];


        if(gamepad2.right_trigger > .1){
            robot.claw.clawUp();
        }
        else if(gamepad2.left_trigger > .1){
            robot.claw.setClawPosition(.22);
        }
        else{
            if (!Macro.macroYay()){
                robot.claw.clawDown();
            }

        }
        if(gamepad2.dpad_up){
            treeAngle += .02;
        }
        else if(gamepad2.dpad_down){
            treeAngle -= .02;
        }
        else if(gamepad2.dpad_left){
            robot.lift.liftLeft.setTargetPosition(600);
            robot.lift.liftRight.setTargetPosition(600);

            robot.claw.setClawPosition(0.4);

            if(robot.lift.rotateRight.getCurrentPosition() > 1450){
                robot.lift.rotateRight.setPower(-0.2);
            }
            else if(robot.lift.rotateRight.getCurrentPosition() < 1500){
                robot.lift.rotateRight.setPower(0.2);
            }
        }
        else if (gamepad2.dpad_right){// && macrooo.milliseconds() > 800){ //mathew's macro  ngl im disapointed how you spelled my name
            Macro.macro_run(opMode);
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
            ///*
            
            //state[0] = true; //rotate angle
            macrooo.reset();
//            if(!macro.macroYay()){
//                macro.macroAllOff();
//            }
//            time = true;//reset
//            down = false;//reset

        
        


        }
        if (Macro.macroYay() )
        {
            Macro.macro_run(opMode);
        }
//        if(state[0]) // step 1: resets and angles tree
//        {
////            Macro.macro_run(opMode);
////            state[0] = false;
//            robot.lift.liftLeft.setTargetPosition(45); //retracts lift
//            robot.lift.liftRight.setTargetPosition(45);
//            robot.claw.setClawPosition(0); //retracts tree
//            treeAngle = 0.1; //angles tree
//            if (time) {
//                stateOneTime.reset();
//                time = false;
//            }
//            if (stateOneTime.milliseconds() > macro_timing[0]){
//                state[0] = false;
//                state[1] = true;
//            }
//
//            //so time would go here I think?
//        }
//        else if (state[1]) // step 2: gets pixel
//        {
////            opMode.telemetry.addData("time",stateOneTime.milliseconds()); //doesnt work unless comment out other telemetry
////            opMode.telemetry.update();
//            if (!down){
//                robot.claw.clawHalf();
//                //wait im trolling really hard, we should initialize time when we set state[1] to true since that's
//                //how long the robot has actually been doing the state. Rn it resets time everytime
//                //because it initializes(resets) inside the if statement, so time will never be > 1000 milliseconds
//                if (robot.lift.rotateRight.getCurrentPosition() < -60 && robot.lift.rotateRight.getCurrentPosition() > -75) // check to see if the lift is btw the value
//                {
//                    down = true;
//                    stateOneTime.reset();
//                }
//
//                else if(robot.lift.rotateRight.getCurrentPosition() > -60){
//                    robot.lift.rotateRight.setPower(-0.5);
//                }
//                else if(robot.lift.rotateRight.getCurrentPosition() < -70){
//                    robot.lift.rotateRight.setPower(0.5);
//                }
//
////                liftAngleToPos(-68);
//            }
//            else {
//                //robot.intake.spinTake(1);
//                robot.claw.setClawPosition(0.5);
//                treeAngle = 0.180;
//                if (stateOneTime.milliseconds() > macro_timing[1]){
//                    treeAngle = 0;
//                }
//                if (stateOneTime.milliseconds() > macro_timing[2]) {
//                    robot.claw.setClawPosition(0);//retracts tree
//                    treeAngle = 0.1155;
//                }
//                if (stateOneTime.milliseconds() > macro_timing[3]) {
//                    robot.claw.setClawPosition(0.8); //claw goes down and picks pixel up
//                }
//
//                if (stateOneTime.milliseconds() > macro_timing[4]){
//                    //robot.claw.setClawPosition(0.8);
//                    state[1] = false;
//                    state[2] = true;
//                    stateOneTime.reset();
//                }
//
//            }
//        }
//        if(state[2]) // step 3: moves claw back
//        {
//            robot.claw.setClawPosition(0.5);
//            treeAngle = 0.08;
//            state[2] = false;
//            state[3] = true;
//        }
//        if(state[3]) // step 4: rotate up
//        {
//            if (robot.lift.rotateRight.getCurrentPosition() < 600 && robot.lift.rotateRight.getCurrentPosition() > 565) // check to see if the lift is btw the value
//            {
//                state[3] = false;
//                treeAngle = .41;
//
//            } else if (robot.lift.rotateRight.getCurrentPosition() > 600) {
//                robot.lift.rotateRight.setPower(-0.5);
////                robot.lift.rotateLeft.setPower(-0.5);b
//
//            } else if (robot.lift.rotateRight.getCurrentPosition() < 565) {
//                robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
////              robot.lift.rotateLeft.setPower(0.5);
//            }
//        }
        /*if(state[8]) // step 5: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!AAAAAAAAA
        {
            robot.lift.liftRight.setTargetPosition(277);
            robot.lift.liftLeft.setTargetPosition(277);
            state[4] = false;
            state[5] = true;

        }
        if(state[4]) // step 6:AAAAAAAAAAAAAAA
        {
            if (robot.lift.rotateRight.getCurrentPosition() < 569 && robot.lift.rotateRight.getCurrentPosition() > 554) // check to see if the lift is btw the value
            {
                state[4] = false;
                state[5] = false;

            } else if (robot.lift.rotateRight.getCurrentPosition() > 569) {
                robot.lift.rotateRight.setPower(-0.5);
//                robot.lift.rotateLeft.setPower(-0.5);


            } else if (robot.lift.rotateRight.getCurrentPosition() < 554) {
                robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
//                robot.lift.rotateLeft.setPower(0.5);
            }

        }
        if(state[6]) // step 7
        {
            robot.lift.liftLeft.setTargetPosition(-12);
            robot.lift.liftRight.setTargetPosition(-12);
            treeAngle = 0.49;
            if (robot.lift.rotateRight.getCurrentPosition() < 1109 && robot.lift.rotateRight.getCurrentPosition() > 1089 ) // check to see if the lift is btw the value
            {
                state[6] = false;

            } else if (robot.lift.rotateRight.getCurrentPosition() > 1109) {
            robot.lift.rotateRight.setPower(-0.5);
//            robot.lift.rotateLeft.setPower(-0.5);


        } else if (robot.lift.rotateRight.getCurrentPosition() < 1089)
        {
            robot.lift.rotateRight.setPower(0.5); // set power for angle of the list
//            robot.lift.rotateLeft.setPower(0.5);
        }
        //*/
        //}











        if(!Macro.macroYay()) {
            //comment this to make non rel
            double offSet = robot.lift.rotateRight.getCurrentPosition() / 11356.25;// the
            //offSet = treeAngle - offSet > .33 && treeAngle - offSet < .61? offSet : 0;

            //uncomment this to revert to relative
            //double offSet = 0;
            if(robot.lift.liftLeft.getCurrentPosition() > 300 && !lockToAngle){
                lockToAngle = true;
                robot.claw.setClawAngle(.37 - offSet);
            }
            else{
                lockToAngle = false;
            }
            robot.claw.setClawAngle(treeAngle - offSet);


        }
        else {
            //robot.claw.setClawAngle(treeAngle);
        }

    }
    private void planeServoControl(Gamepad gamepad1, Gamepad gamepad2) {

        if (gamepad1.a) {
            robot.setPlaneClosed();
            robot.setPlanePosition(-1);
        } else if (gamepad1.b) {
            planeTimer.reset();
            planeLaunch[0] = true;
            robot.setPlaneOpen();
            robot.setPlaneAngle(.52);
            robot.drivetrain.setALLMotorPower(0);

        }
        if(planeLaunch[0] || planeLaunch[1]){
            robot.drivetrain.setALLMotorPower(0);
        }
        if(planeLaunch[0] && planeTimer.milliseconds() > 500){
            robot.setPlanePosition(0.47);
            planeLaunch[1] = true;
        }
        if(planeLaunch[1] && planeTimer.milliseconds() > 1000){
            robot.setPlaneAngle(0);
            robot.setPlanePosition(-1);
            robot.setPlaneClosed();
            planeLaunch[0] = false;
            planeLaunch[1] = false;
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
//            opMode.telemetry.addData("headingError", headingError);
//            opMode.telemetry.addData("correctionPower", correctionPower);


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
        if(gamepad1.dpad_left) ignoreBounds = true;

        if(gamepad1.dpad_right){
            ignoreBounds = false;
            robot = new Robot(robot.teleOpMode);
        }

        //Manipulator and lift stuff
        int multiplier = 120;
        if(Math.abs(gamepad2.left_stick_y) > 0.1) {
            up1p += -gamepad2.left_stick_y * multiplier;

            up2p = up1p;



            if (up1p < -10 && !ignoreBounds)
            {
                //up1p = 0;
                //up2p = 0;
                up1p = -10;
            }
            else if(up1p > 3600 && !ignoreBounds){
                up1p = 3600;
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
        else{
            up1p = robot.lift.liftLeft.getCurrentPosition();
        }




        if(gamepad2.right_bumper){
            rn1p = .75;
            rn2p = rn1p;
            robot.lift.rotateRight.setPower(rn1p);
            //robot.lift.rotateLeft.setPower(rn2p);
        }
        else if(gamepad2.left_bumper){
            rn1p = -.75;
            rn2p = rn1p;
            robot.lift.rotateRight.setPower(rn1p);
        }
        else{
            if(robot.lift.liftLeft.getCurrentPosition() > 1600){
                robot.lift.rotateRight.setPower(.15);
                //robot.lift.rotateLeft.setPower(.15);

            }
            else {
                robot.lift.rotateRight.setPower(0);
                //.lift.rotateLeft.setPower(0);
            }
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
        //opMode.telemetry.addData("oursRotL", robot.lift.rotateLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLiftL", robot.lift.liftLeft.getCurrentPosition());
        opMode.telemetry.addData("oursLiftR", robot.lift.liftRight.getCurrentPosition());
        opMode.telemetry.addData("Ignore Bounds", ignoreBounds);
        opMode.telemetry.addData("treeAngle", treeAngle);
        opMode.telemetry.addData("br", robot.drivetrain.br.getCurrentPosition());
        //opMode.telemetry.addData("inLeft", robot.intake.intakeAngleLeft.getPosition());
        //opMode.telemetry.addData("inRight", robot.intake.intakeAngleRight.getPosition());
        opMode.telemetry.addData("MACROOOOOOOOOOOOOOOO", (!state[1]&&!state[0]&&!state[3]&&!state[2]&&!state[4]&&!state[5]&&!state[6]));
        opMode.telemetry.addData("potentiometer", robot.lift.potentiometer.getVoltage());
        double intakeCurrent = robot.intake.intakeMotor.getCurrent(CurrentUnit.AMPS);
        opMode.telemetry.addData("Intake Motor Current", intakeCurrent + " Amps");
        opMode.telemetry.addData("WHY ISN'T THIS WORKING return whether it is on", Macro.macroYay());
        opMode.telemetry.addData("5", Macro.macro_state[5]);
        opMode.telemetry.addData("0", Macro.macro_state[0]);
        opMode.telemetry.addData("1", Macro.macro_state[1]);
        opMode.telemetry.addData("2", Macro.macro_state[2]);
        //opMode.telemetry.addData("PLEASE WORK: ABOOLEAN CHECK", Macro.aBoolean);

        opMode.telemetry.update();
    }






}