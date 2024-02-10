package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Macro {
    static OpMode opMode;

    public static Timer timer = new Timer();

    private static Robot robot;
    private static Claw outtake;
    private static Lift lift;
    
    public static boolean macro_run = false;
    
    public Macro(Robot robot) {
        Macro.robot = robot;
        lift = robot.lift;
    }
    
    public static int[] macro_timing = {1,2,3,4,5};


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
        timer.reset();
        
        //Sets the starting pos of all the apendeges
        robot.claw.setClawPosition(0);
        robot.claw.setClawAngle(0.1);
        liftAngleToPos (1000);
        robot.lift.liftLeft.setTargetPosition(0);
        robot.lift.liftRight.setTargetPosition(0);

        //Start of the actual macro
        if (lift.rotateRight.getCurrentPosition() + 5 > 200 && 200 < lift.rotateRight.getCurrentPosition() - 5) {
            macro_run = true;
        }
        while (macro_run){
            liftAngleToPos (75);
            
            if (timer.hasElapsed (macro_timing[0])) {
                
                robot.claw.setClawPosition (0.3);
            }
            if (timer.hasElapsed (macro_timing[1])) {
                robot.lift.liftLeft.setTargetPosition(80);
                robot.lift.liftRight.setTargetPosition(80);
                robot.claw.setClawAngle (0.115);
                robot.claw.setClawPosition(0.00);
            }
            if (timer.hasElapsed (macro_timing[2])) {
                liftAngleToPos (60);
                robot.claw.setClawPosition (0.8);
            }
            if (timer.hasElapsed (macro_timing[3])) {
                robot.claw.setClawPosition (0.5);
                robot.claw.setClawAngle (0.08);
            }
            if (timer.hasElapsed (macro_timing[4])) {
                liftAngleToPos (1000);
                robot.claw.setClawAngle (0.41);
                macro_run = false;
            }
        }
    }
}