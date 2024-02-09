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
    
//    public static double kp = 1.00, ki = 1.00, kd = 1.00;
    public static int[] macro_timing = {1,2,3,4,5};


    public static void liftAngleToPos(int desiredPosition) {
        double tolerance = 100;
        ElapsedTime liftAngleToPosTimer = new ElapsedTime();
        liftAngleToPosTimer.reset();
        double timeout = 5.0; // 5 seconds timeout for safety

        while (liftAngleToPosTimer.seconds() < timeout) {
            int currentPos = lift.rotateRight.getCurrentPosition();
            if (Math.abs(currentPos - desiredPosition) <= tolerance) {
                break; // Desired position reached within tolerance
            }

            if (desiredPosition > currentPos) {
                lift.rotateRight.setPower(0.2); // Move up
            } else if (desiredPosition < currentPos) {
                lift.rotateRight.setPower(-0.2); // Move down
            }
        }

        lift.rotateRight.setPower(0); // Stop the motor after exiting the loop
    }


    public static void macro_run(OpMode opmode){
        //Macro Vars for FTC dashboard
        
        timer.reset();
        lift = new Lift(opmode);
        outtake = new Claw(opmode);
        
        //Sets the starting pos of all the apendeges
//        outtake.setClawPosition(0);
//        outtake.setClawAngle(0.1);
        liftAngleToPos (1000);
//        lift.liftLeft.setTargetPosition(45);
//        lift.liftRight.setTargetPosition(45);

        //Start of the actual macro
        /*if (lift.rotateRight.getCurrentPosition() + 5 > 200 && 200 < lift.rotateRight.getCurrentPosition() - 5){
            liftAngleToPos(60);

            if(timer.hasElapsed(macro_timing[0])){
                outtake.setClawPosition (0.3);
            }
            if(timer.hasElapsed(macro_timing[1])){
                outtake.setClawAngle(0.1155);
            }
            if(timer.hasElapsed(macro_timing[2])){
                outtake.setClawPosition(0.8);
            }
            if(timer.hasElapsed(macro_timing[3])){
                outtake.setClawPosition(0.5);
                outtake.setClawAngle(0.08);
            }
            if(timer.hasElapsed(macro_timing[4])){
                liftAngleToPos(580);
                outtake.setClawAngle(0.41);
            }
        }
        */
    }
}