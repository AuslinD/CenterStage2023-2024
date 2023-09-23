package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;




public class Lift {
    private DcMotor rn1,rn2,up1 ,up2 ; // rn stands for rotational motor
    public Lift(OpMode opMode){
        rn1 = opMode.hardwareMap.get(DcMotorEx.class, "rn1");
        rn2 = opMode.hardwareMap.get(DcMotorEx.class, "rn2");
        up1 = opMode.hardwareMap.get(DcMotorEx.class, "up1");
        up2 = opMode.hardwareMap.get(DcMotorEx.class, "up2");

        rn1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rn2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        up1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        up2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rn1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rn2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        up1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        up2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rn1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rn2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        up1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        up2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rn1.setDirection(DcMotorSimple.Direction.FORWARD);
        rn2.setDirection(DcMotorSimple.Direction.FORWARD);
        up1.setDirection(DcMotorSimple.Direction.FORWARD);
        up2.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public Lift(LinearOpMode linearOpMode){
        rn1 = linearOpMode.hardwareMap.get(DcMotorEx.class, "rn1");
        rn2 = linearOpMode.hardwareMap.get(DcMotorEx.class, "rn2");
        up1 = linearOpMode.hardwareMap.get(DcMotorEx.class, "up1");
        up2 = linearOpMode.hardwareMap.get(DcMotorEx.class, "up2");

        rn1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rn2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        up1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        up2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rn1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rn2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        up1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        up2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rn1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rn2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        up1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        up2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rn1.setDirection(DcMotorSimple.Direction.FORWARD);
        rn2.setDirection(DcMotorSimple.Direction.FORWARD);
        up1.setDirection(DcMotorSimple.Direction.FORWARD);
        up2.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setAllLiftMotorPower(double power){
        rn1.setPower(power);
        rn2.setPower(power);
        up1.setPower(power);
        up2.setPower(power);
    }


    public DcMotor[] getMotors(){
        return new DcMotor[] {rn1, rn2, up1, up2};
    }
}
