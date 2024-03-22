package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Macro {
    static OpMode opMode;

    public static Timer timer = new Timer();
    //private static ElapsedTime time = new ElapsedTime ();
    static boolean[] macro_state = {false,false,false,false,false,false, false};
    //private static Robot robot;
    private static Claw claw;
    private static Lift lift;
    private static Intake intake;
    
    public static boolean macro_run = false;
    
    public Macro(OpMode opMode) {
        Macro.lift = new Lift(opMode);
        Macro.claw = new Claw(opMode);
        Macro.intake = new Intake(opMode);
        //Macro.robot = robot;
        //lift = lift;
    }
    
    public static double[] macro_timing = {1.4,1.6,2.4,2.6,3.1,.6,2.9};
    /*public static void initMacro(){
        macro_state[5] = true;
    }*/
    public static boolean aBoolean = true;
    /*public Macro(){ //constructor
        aBoolean = true;
    }*/

    public static void macroAllOff(){
        for (int i = 0; i < macro_state.length; i++){
            macro_state[i] = false;
        }
    }
    public static boolean macroYay(){
        for (boolean state : macro_state){
            if (state){
                return true;
            }
        }
        return false;
    }
    


    public static void liftAngleToPos(int desiredPosition) {
        double tolerance = 100;
        ElapsedTime liftAngleToPosTimer = new ElapsedTime();
        liftAngleToPosTimer.reset();
        PID liftPID = new PID(.95, 0.01, 0, desiredPosition);
        double newPower = liftPID.loop(lift.rotateRight.getCurrentPosition(), liftAngleToPosTimer.milliseconds());
        lift.rotateRight.setPower(newPower);
        /*while (!(Math.abs(lift.rotateRight.getCurrentPosition() - desiredPosition) <= tolerance)) {
            int currentPos = lift.rotateRight.getCurrentPosition();
            if (Math.abs(currentPos - desiredPosition) <= tolerance) {
                break;
            }
            if (desiredPosition > currentPos) {
                lift.rotateRight.setPower(0.5);
            } else if (desiredPosition < currentPos) {
                lift.rotateRight.setPower(-0.5);
            }
        }*/
        //lift.rotateRight.setPower(0);
    }


    public static void macro_run(OpMode opmode){
        
        //Sets the starting pos of all the apendeges
        
        //opmode.telemetry.addData("why", aBoolean);
        //opmode.telemetry.update();
        //Start of the actual macro

        if (aBoolean){
            macro_state[5] = true;
            aBoolean = false;
            claw.setClawPosition (0);
            intake.spinTake(.8);
            timer.reset();
            /*if (lift.rotateRight.getCurrentPosition() + 5 > 200 && 200 < lift.rotateRight.getCurrentPosition() - 5) {
                macro_state[5] = true;
                aBoolean = false;
            }*/
        }
        //if (macro_state[7])
        //if (macroYay ()){ //not needed if state
        if (macro_state[5]) {
            //claw.setClawPosition (0);
            intake.spinTake(1);
            claw.setClawAngle (0);
            //liftAngleToPos (1000);
            if(timer.hasElapsed (0.3)) {
                lift.liftLeft.setTargetPosition(0);
                lift.liftRight.setTargetPosition(0);
                intake.spinTake(0);
            }
            //timer.reset ();
            if(!(Math.abs(lift.rotateRight.getCurrentPosition() - 73) < 100) ) {
                liftAngleToPos (73);
            }
            if (timer.hasElapsed (macro_timing[5])) {
                macro_state[5] = false;
                macro_state[0] = true;
            }
            //opmode.telemetry.addData("FIRST STEP", 5);
            //opmode.telemetry.update();
        }
        if (macro_state[0]) { //step 2

            claw.setClawPosition(0.3);
            if (timer.hasElapsed (macro_timing[0])) {
                macro_state[0] = false;
                macro_state[1] = true;
            }
            }
        if (macro_state[1]) { //step 3
            lift.liftLeft.setTargetPosition(70);
            lift.liftRight.setTargetPosition(70);
            if(!(Math.abs(lift.rotateRight.getCurrentPosition() - 100) < 100)) {
                liftAngleToPos (100);
            }
            //liftAngleToPos(100);
            claw.setClawAngle (0.095); //used to be 0.115
            claw.setClawPosition(0);
            if (timer.hasElapsed (macro_timing[1])){
                macro_state[1] = false;
                macro_state[2] = true;
            }
        }
        if (macro_state[2]) { //gets pixel
            if(!(Math.abs(lift.rotateRight.getCurrentPosition() - 60) < 150)) {
                liftAngleToPos (60);
            }
            //liftAngleToPos (60);
            claw.setClawPosition (0.8);
            if ((timer.hasElapsed (macro_timing[2]))) {
                macro_state[2] = false;
                macro_state[3] = true;
            }
        }
        if (macro_state[3]) {
            claw.setClawPosition (0.6);
            claw.setClawAngle (0.09);
            liftAngleToPos (60);
            if(timer.hasElapsed (macro_timing[3])) {
                macro_state[3] = false;
                macro_state[4] = true;
            }
        }
        if (macro_state[4]) {
            claw.setClawAngle(.05);
            if(!(Math.abs(lift.rotateRight.getCurrentPosition() - 800) < 100)) {
                liftAngleToPos (800);
            }
            //liftAngleToPos (800);
            if (timer.hasElapsed (macro_timing[4])) {
                macro_state[4] = false;
                macro_state[6] = true;
                //aBoolean = true;  // Reset aBoolean to allow macro to start again
                //macroAllOff();  // Reset all macro states
            }
        }
        if (macro_state[6]){
            claw.setClawAngle (0.41);
            if (timer.hasElapsed(macro_timing[6])){
                macro_state[6] = false;
                aBoolean = true;
                macroAllOff();  // Reset all macro states
            }
        }
    }
}