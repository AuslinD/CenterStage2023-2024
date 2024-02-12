package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Macro {
    static OpMode opMode;
    
    public static Timer timer = new Timer();
    
    private static Robot robot;
    private static Lift lift;
    
    public static boolean macro_run = false;
    
    public Macro(Robot robot) {
        Macro.robot = robot;
        lift = robot.lift;
    }
    
    public static double[] macro_timing = {0, 0.5, 1, 1.5, 2};
    
    public static void liftAngleToPos(int desiredPosition) {
        double tolerance = 100;
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
    
    public static void macro_run(OpMode opmode) {
        timer.reset();
        
        robot.claw.setClawPosition(0);
        robot.claw.setClawAngle(0.1);
        liftAngleToPos(1000);
        
        macro_run = true;
        
        Thread macroThread = new Thread(() -> {
            while (macro_run) {
                liftAngleToPos(73);
                waitUntilTimeElapsed(macro_timing[0]);
                
                robot.claw.setClawPosition(0.3);
                waitUntilTimeElapsed(macro_timing[1]);
                
                robot.lift.liftLeft.setTargetPosition(85);
                robot.lift.liftRight.setTargetPosition(85);
                liftAngleToPos(100);
                robot.claw.setClawAngle(0.115);
                robot.claw.setClawPosition(0.00);
                waitUntilTimeElapsed(macro_timing[2]);
                
                liftAngleToPos(60);
                robot.claw.setClawPosition(0.8);
                waitUntilTimeElapsed(macro_timing[3]);
                
                robot.claw.setClawPosition(0.5);
                robot.claw.setClawAngle(0.1);
                waitUntilTimeElapsed(macro_timing[4]);
                
                liftAngleToPos(800);
                robot.claw.setClawAngle(0.41);
                macro_run = false;
            }
        });
        
        macroThread.start();
    }
    
    private static void waitUntilTimeElapsed(double targetTime) {
        while (true) {
            if (!(timer.hasElapsed (targetTime))) break;
        }
    }
}