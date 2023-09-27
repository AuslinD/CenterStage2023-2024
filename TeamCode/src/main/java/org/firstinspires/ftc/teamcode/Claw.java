package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw {
    private Servo M1, M2;

    double down = 0;
    double up = 1;

    double rotate = 0;

    public Claw(OpMode opMode) {
        M1 = opMode.hardwareMap.get(Servo.class, "clawServo");
        M2 = opMode.hardwareMap.get(Servo.class, "rotateServo");

    }
    public void clawDown(){
        M1.setPosition(down);
    }
    public void clawUp(){
        M1.setPosition(up);
    }
    public void clawdegrees(){
        M2.setPosition(rotate);


    }

}
