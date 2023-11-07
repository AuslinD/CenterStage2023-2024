package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw {

    private Servo tree, clawAngle;

    double down = -.5;
    double up = .5;

    double rotate1 = 0;
    double rotate2 = 0;

    public Claw(OpMode opMode) {
        clawAngle = opMode.hardwareMap.get(Servo.class, "angClaw");
        tree = opMode.hardwareMap.get(Servo.class, "tree");
    }
    public Claw(LinearOpMode opMode){
        clawAngle = opMode.hardwareMap.get(Servo.class, "angClaw");
        tree = opMode.hardwareMap.get(Servo.class, "tree");
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

}
