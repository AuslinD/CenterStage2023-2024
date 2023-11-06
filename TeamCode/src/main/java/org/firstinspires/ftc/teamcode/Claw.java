package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw {

    double ANG_DOWN = -.5;
    private CRServo intakeLeft, intakeRight, transferLeft, transferRight;
    private Servo tree, clawAngle, intakeAngleLeft, intakeAngleRight;

    double down = 0;
    double up = 1;

    double rotate1 = 0;
    double rotate2 = 0;

    public Claw(OpMode opMode) {
        intakeLeft = opMode.hardwareMap.get(CRServo.class, "inL");
        intakeRight = opMode.hardwareMap.get(CRServo.class, "inR");
        transferLeft = opMode.hardwareMap.get(CRServo.class, "trL");
        transferRight = opMode.hardwareMap.get(CRServo.class, "trR");
        intakeAngleLeft = opMode.hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = opMode.hardwareMap.get(Servo.class, "angR");
        clawAngle = opMode.hardwareMap.get(Servo.class, "angClaw");
        tree = opMode.hardwareMap.get(Servo.class, "tree");

        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        transferLeft.setDirection(DcMotorSimple.Direction.REVERSE);


    }
    public void clawDown(){
        tree.setPosition(down);
    }
    public void clawUp(){
        tree.setPosition(up);
    }
    public void setClawDegree(){
        clawAngle.setPosition(rotate1);
    }

    public void lowerIntake(){
        intakeAngleLeft.setPosition(ANG_DOWN);
        intakeAngleRight.setPosition(ANG_DOWN);
    }

    public void spinTake(double power){
        intakeLeft.setPower(power);
        intakeRight.setPower(power);
        transferLeft.setPower(power);
        transferRight.setPower(power);
    }



}
