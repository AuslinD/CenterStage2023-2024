package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    
    public static double[] macro_timing = {0, 0.5, 1, 1.5, 2};
    public static int currentStep = 0;
    public static boolean stepCompleted = true;
    
    public static void liftAngleToPos(int desiredPosition) {
        double tolerance = 100;
        
        int currentPos = lift.rotateRight.getCurrentPosition();
        if (Math.abs(currentPos - desiredPosition) <= tolerance) {
            stepCompleted = true;
            return;
        }
        
        if (desiredPosition > currentPos) {
            lift.rotateRight.setPower(0.5);
        } else if (desiredPosition < currentPos) {
            lift.rotateRight.setPower(-0.5);
        }
    }
    
    public static void macro_run(OpMode opmode) {
        timer.reset();
        
        //Sets the starting pos of all the appendages
        robot.claw.setClawPosition(0);
        robot.claw.setClawAngle(0.1);
        lift.rotateRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.rotateRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.lift.liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.lift.liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.lift.liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        robot.lift.liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        // Start of the actual macro
        if (!macro_run) {
            macro_run = true;
            currentStep = 0;
            stepCompleted = true;
        }
        
        if (stepCompleted) {
            switch (currentStep) {
                case 0:
                    liftAngleToPos(73);
                    if (stepCompleted) {
                        currentStep++;
                        timer.reset();
                    }
                    break;
                case 1:
                    if (timer.hasElapsed(macro_timing[0])) {
                        robot.claw.setClawPosition(0.3);
                        currentStep++;
                        timer.reset();
                    }
                    break;
                case 2:
                    liftAngleToPos(100);
                    if (stepCompleted) {
                        robot.lift.liftLeft.setTargetPosition(85);
                        robot.lift.liftRight.setTargetPosition(85);
                        liftAngleToPos(100);
                        robot.claw.setClawAngle(0.115);
                        robot.claw.setClawPosition(0.00);
                        currentStep++;
                        timer.reset();
                    }
                    break;
                case 3:
                    liftAngleToPos(60);
                    if (stepCompleted) {
                        robot.claw.setClawPosition(0.8);
                        currentStep++;
                        timer.reset();
                    }
                    break;
                case 4:
                    liftAngleToPos(800);
                    if (stepCompleted) {
                        robot.claw.setClawAngle(0.41);
                        macro_run = false;
                    }
                    break;
                default:
                    macro_run = false;
                    break;
            }
        }
    }
}