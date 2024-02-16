package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Macro {
    static OpMode opMode;

    public static Timer timer = new Timer();
    //private static ElapsedTime time = new ElapsedTime ();
    static boolean[] macro_state = {false,false,false,false,false,false};
    private static Robot robot;
    private static Claw outtake;
    private static Lift lift;
    
    public static boolean macro_run = false;
    
    public Macro(Robot robot) {
        Macro.robot = robot;
        lift = robot.lift;
    }
    
    public static double[] macro_timing = {1,1.5,2,2.5,3,0.5};
    public static void initMacro(){
        macro_state[5] = true;
    }
    public static boolean aBoolean = true;
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

        while (!(Math.abs(lift.rotateRight.getCurrentPosition() - desiredPosition) <= tolerance)) {
            int currentPos = lift.rotateRight.getCurrentPosition();
            if (Math.abs(currentPos - desiredPosition) <= tolerance) {
                break;
            }
            if (desiredPosition > currentPos) {
                lift.rotateRight.setPower(0.5);
            } else if (desiredPosition < currentPos) {
                lift.rotateRight.setPower(-0.5);
            }
        }
        lift.rotateRight.setPower(0);
    }


    public static void macro_run(OpMode opmode){
        
        //Sets the starting pos of all the apendeges
        
        //opmode.telemetry.addData("why", aBoolean);
        //opmode.telemetry.update();
        //Start of the actual macro
        if (aBoolean){
            macro_state[5] = true;
            aBoolean = false;
            /*if (lift.rotateRight.getCurrentPosition() + 5 > 200 && 200 < lift.rotateRight.getCurrentPosition() - 5) {
                macro_state[5] = true;
                aBoolean = false;
            }*/
        }
        //if (macroYay ()){ //not needed if state
            if (macro_state[5]) {
                robot.claw.setClawPosition (0);
                robot.claw.setClawAngle (0.1);
                //liftAngleToPos (1000);
                robot.lift.liftLeft.setTargetPosition (0);
                robot.lift.liftRight.setTargetPosition (0);
                timer.reset ();
                liftAngleToPos (73);
                if (timer.hasElapsed (macro_timing[5])) {
                    macro_state[5] = false;
                    macro_state[0] = true;
                }
                //opmode.telemetry.addData("FIRST STEP", 5);
                //opmode.telemetry.update();
            }
            if (timer.hasElapsed (macro_timing[0]) && macro_state[0]) {
                robot.claw.setClawPosition (0.3);

                macro_state[0] = false;
                macro_state[1] = true;
            }
            if (macro_state[1]) {
                robot.lift.liftLeft.setTargetPosition(85);
                robot.lift.liftRight.setTargetPosition(85);
                //liftAngleToPos(100);
                robot.claw.setClawAngle (0.115);
                robot.claw.setClawPosition(0.00);
                if (timer.hasElapsed (macro_timing[1])){
                    macro_state[1] = false;
                    macro_state[2] = true;
                }
            }
            if (macro_state[2]) {
                //liftAngleToPos (60);
                robot.claw.setClawPosition (0.8);
                if ((timer.hasElapsed (macro_timing[2]))) {
                    macro_state[2] = false;
                    macro_state[3] = true;
                }
            }
            if (macro_state[3]) {
                robot.claw.setClawPosition (0.5);
                robot.claw.setClawAngle (0.1);
                if(timer.hasElapsed (macro_timing[3])) {
                    macro_state[3] = false;
                    macro_state[4] = true;
                }
            }
            if (macro_state[4]) {
                //liftAngleToPos (800);
                robot.claw.setClawAngle (0.41);
                if (timer.hasElapsed (macro_timing[4])) {
                    macro_state[4] = false;
                }
            }
            aBoolean = true;
        //}
    }
}