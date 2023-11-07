package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    double ANG_DOWN = -.5;
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
    }

    public Intake(LinearOpMode opMode){
        intakeLeft = opMode.hardwareMap.get(CRServo.class, "inL");
        intakeRight = opMode.hardwareMap.get(CRServo.class, "inR");
        transferLeft = opMode.hardwareMap.get(CRServo.class, "trL");
        transferRight = opMode.hardwareMap.get(CRServo.class, "trR");
        intakeAngleLeft = opMode.hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = opMode.hardwareMap.get(Servo.class, "angR");
        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void spinTake(double power){
        intakeLeft.setPower(power);
        intakeRight.setPower(power);
        transferLeft.setPower(power);
        transferRight.setPower(power);
    }

    public void lowerIntake(){
        intakeAngleLeft.setPosition(ANG_DOWN);
        intakeAngleRight.setPosition(ANG_DOWN);
    }

}
