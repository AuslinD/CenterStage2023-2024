package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {

    //CHANGE THIS
    double ANG_DOWN = -0.5;
    double ANG_UP = 0.5;
    private CRServo intakeLeft, intakeRight, transferLeft, transferRight;

    private Servo intakeAngleLeft, intakeAngleRight;

    public Intake(OpMode opMode){
        intakeLeft = opMode.hardwareMap.get(CRServo.class, "inL");
        intakeRight = opMode.hardwareMap.get(CRServo.class, "inR");
        transferLeft = opMode.hardwareMap.get(CRServo.class, "trL");
        transferRight = opMode.hardwareMap.get(CRServo.class, "trR");
        intakeAngleLeft = opMode.hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = opMode.hardwareMap.get(Servo.class, "angR");
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        transferRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public Intake(LinearOpMode opMode){
        intakeLeft = opMode.hardwareMap.get(CRServo.class, "inL");
        intakeRight = opMode.hardwareMap.get(CRServo.class, "inR");
        transferLeft = opMode.hardwareMap.get(CRServo.class, "trL");
        transferRight = opMode.hardwareMap.get(CRServo.class, "trR");
        intakeAngleLeft = opMode.hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = opMode.hardwareMap.get(Servo.class, "angR");
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        transferRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void spinTake(double power){
        intakeLeft.setPower(power);
        intakeRight.setPower(power);
        transferLeft.setPower(power);
        transferRight.setPower(power);
    }

    public void lowerIntake(){
        intakeAngleLeft.setPosition(.99);
        intakeAngleRight.setPosition(.372);
    }

    public void stowIntake(){
        intakeAngleLeft.setPosition(.7);
        intakeAngleRight.setPosition(.7);
    }

}
