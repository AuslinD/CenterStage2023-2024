package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Macro {
    private static Timer timer;
    private static Claw outtake;
    private static Lift lift;

    public static void liftAngleToPos(int desiredPosition){

        double kp = 1.00, ki = 1.00, kd = 1.00;
        boolean setPointReached = false;
        double tolerance = 5;

        if (lift.rotateRight.getCurrentPosition() + tolerance > desiredPosition && desiredPosition < lift.rotateRight.getCurrentPosition() - tolerance){
            setPointReached = true;
        }

        double integralSum = 0;
        double lastError = 0;

        ElapsedTime liftAngleToPosTimer = new ElapsedTime();

        while (!setPointReached) {
            if (lift.rotateRight.getCurrentPosition() + tolerance > desiredPosition && desiredPosition < lift.rotateRight.getCurrentPosition() - tolerance){
                setPointReached = true;
            }
            int encoderPosition = lift.rotateRight.getCurrentPosition();
            int error = desiredPosition - encoderPosition;
            double derivative = (error - lastError) / liftAngleToPosTimer.seconds();
            integralSum = integralSum + (error * liftAngleToPosTimer.seconds());
            double out = (kp * error) + (ki * integralSum) + (kd * derivative);
            lift.rotateRight.setPower(out);
            lastError = error;
            liftAngleToPosTimer.reset();
        }
    }

    public static void macro_run(){
        timer.reset();

        //Sets the starting pos of all the apendeges
        outtake.setClawPosition(0);
        outtake.setClawAngle(0.1);
        lift.rotateRight.setTargetPosition(100);
        lift.liftLeft.setTargetPosition(45);
        lift.liftRight.setTargetPosition(45);

        //Start of the actual macro
        if (lift.rotateRight.getCurrentPosition() + 5 > 200 && 200 < lift.rotateRight.getCurrentPosition() - 5){
                liftAngleToPos(60);

            if(timer.hasElapsed(0.1)){
                outtake.clawHalf();
            }
            if(timer.hasElapsed(0.2)){
                outtake.setClawAngle(0.1155);
            }
            if(timer.hasElapsed(0.3)){
                outtake.setClawPosition(0.8);
            }
            if(timer.hasElapsed(0.4)){
                outtake.setClawPosition(0.5);
                outtake.setClawAngle(0.08);
            }
            if(timer.hasElapsed(0.5)){
                liftAngleToPos(580);
                outtake.setClawAngle(0.41);

            }
        }
    }
}
