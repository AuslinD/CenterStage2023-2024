package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;




public class Lift {
    public DcMotor rotateLeft, rotateRight, liftLeft, liftRight; // rn stands for rotational motor
    double setPower = 0.8;

    public Lift(OpMode opMode){
        rotateLeft = opMode.hardwareMap.get(DcMotorEx.class, "rotleft");
        rotateRight = opMode.hardwareMap.get(DcMotorEx.class, "rotright");
        liftLeft = opMode.hardwareMap.get(DcMotorEx.class, "upleft");
        liftRight = opMode.hardwareMap.get(DcMotorEx.class, "upright");

        rotateLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotateRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotateLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotateRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rotateLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotateRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotateLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        rotateRight.setDirection(DcMotorSimple.Direction.REVERSE);
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        rotateLeft.setTargetPosition(0);
        rotateRight.setTargetPosition(0);
        liftRight.setTargetPosition(0);
        liftLeft.setTargetPosition(0);

        /*
        rotateLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rotateRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        /*
        rotateLeft.setPower(setPower);
        rotateRight.setPower(setPower);
        liftLeft.setPower(setPower);
        liftRight.setPower(setPower);

         */
    }

    public Lift(LinearOpMode linearOpMode){
        rotateLeft = linearOpMode.hardwareMap.get(DcMotorEx.class, "rotleft");
        rotateRight = linearOpMode.hardwareMap.get(DcMotorEx.class, "rotright");
        liftLeft = linearOpMode.hardwareMap.get(DcMotorEx.class, "upleft");
        liftRight = linearOpMode.hardwareMap.get(DcMotorEx.class, "upright");

        rotateLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotateRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotateLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotateRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rotateLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotateRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotateLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        rotateRight.setDirection(DcMotorSimple.Direction.REVERSE);
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);

        rotateLeft.setTargetPosition(0);
        rotateRight.setTargetPosition(0);
        liftRight.setTargetPosition(0);
        liftLeft.setTargetPosition(0);

        /*
        rotateLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rotateRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */

        liftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftLeft.setPower(setPower);
        liftRight.setPower(setPower);
        

    }
    public void setMotorsToRotate(int targetPosition) {
        // Set the target position for each motor
        rotateLeft.setTargetPosition(targetPosition);
        rotateRight.setTargetPosition(targetPosition);
    }

    public void setMotorsToGoUpOrDown(int targetPosition) {


        liftLeft.setTargetPosition(targetPosition);
        liftRight.setTargetPosition(targetPosition);
    }
    public void setAllLiftMotorPower(double power){
        rotateLeft.setPower(power);
        rotateRight.setPower(power);
        liftLeft.setPower(power);
        liftRight.setPower(power);
    }


    public DcMotor[] getMotors(){
        return new DcMotor[] {rotateLeft, rotateRight, liftLeft, liftRight};
    }
}
