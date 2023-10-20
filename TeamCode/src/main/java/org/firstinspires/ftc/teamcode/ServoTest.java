package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ServoTest", group = "test")
public class ServoTest extends LinearOpMode {
    CRServo intakeLeft, intakeRight;


    @Override
    public void runOpMode() throws InterruptedException {
        intakeLeft = hardwareMap.get(CRServo.class, "inL");
        intakeRight = hardwareMap.get(CRServo.class, "inR");

        waitForStart();

        intakeLeft.setPower(.5);
        intakeRight.setPower(.5);
    }
}
