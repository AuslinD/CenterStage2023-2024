package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Hang {
    CRServo hangL, hangR;
    public Hang(OpMode opMode){
        hangL = opMode.hardwareMap.get(CRServo.class, "hangL");
        hangR = opMode.hardwareMap.get(CRServo.class, "hangR");
        hangL.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public Hang(LinearOpMode opMode){
        hangL = opMode.hardwareMap.get(CRServo.class, "hangL");
        hangR = opMode.hardwareMap.get(CRServo.class, "hangR");
        hangL.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
