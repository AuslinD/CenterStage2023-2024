package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    //CHANGE THIS
    double ANG_DOWN = -0.5;
    double ANG_UP = 0.5;
    private CRServo intakeLeft, intakeRight, transferLeft, transferRight, transferMiddle;
    public DcMotor intakeMotor;

    public Servo intakeAngleLeft, intakeAngleRight;

    public Intake(OpMode opMode){

        //intakeLeft = opMode.hardwareMap.get(CRServo.class, "inL");
        //intakeRight = opMode.hardwareMap.get(CRServo.class, "inR");
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        transferLeft = opMode.hardwareMap.get(CRServo.class, "trL");
        transferRight = opMode.hardwareMap.get(CRServo.class, "trR");
        intakeAngleLeft = opMode.hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = opMode.hardwareMap.get(Servo.class, "angR");
        transferMiddle = opMode.hardwareMap.get(CRServo.class, "transfer");
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        transferRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //transferLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Intake(LinearOpMode opMode){
        //intakeLeft = opMode.hardwareMap.get(CRServo.class, "inL");
        //intakeRight = opMode.hardwareMap.get(CRServo.class, "inR");
        intakeMotor = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        transferLeft = opMode.hardwareMap.get(CRServo.class, "trL");
        transferRight = opMode.hardwareMap.get(CRServo.class, "trR");
        intakeAngleLeft = opMode.hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = opMode.hardwareMap.get(Servo.class, "angR");
        transferMiddle = opMode.hardwareMap.get(CRServo.class, "transfer");

        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        transferRight.setDirection(DcMotorSimple.Direction.REVERSE);
        transferLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void spinTake(double power){
        //intakeLeft.setPower(power);
        //intakeRight.setPower(power);
        intakeMotor.setPower(power * .5);
        transferLeft.setPower(-power);
        transferRight.setPower(power);
        transferMiddle.setPower(-power);
    }

    public void lowerIntake(){
        intakeAngleLeft.setPosition(0);
        intakeAngleRight.setPosition(.552);
    }

    public void stowIntake(){
        intakeAngleLeft.setPosition(-.55);
        intakeAngleRight.setPosition(.9);
    }

}
