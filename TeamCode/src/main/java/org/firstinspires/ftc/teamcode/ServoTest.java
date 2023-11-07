package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ServoTest", group = "test")
public class ServoTest extends LinearOpMode {
    double up = .5;
    double down = -.5;
    CRServo intakeLeft, intakeRight, transferLeft, transferRight;

    Servo intakeAngleLeft, intakeAngleRight, clawAngle, tree;

    @Override
    public void runOpMode() throws InterruptedException {
        intakeLeft = hardwareMap.get(CRServo.class, "inL");
        intakeRight = hardwareMap.get(CRServo.class, "inR");
        transferLeft = hardwareMap.get(CRServo.class, "trL");
        transferRight = hardwareMap.get(CRServo.class, "trR");
        intakeAngleLeft = hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = hardwareMap.get(Servo.class, "angR");
        clawAngle = hardwareMap.get(Servo.class, "angClaw");
        tree = hardwareMap.get(Servo.class, "tree");

        intakeLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        transferLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        transferRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        intakeLeft.setPower(.5);
        intakeRight.setPower(.5);
        transferRight.setPower(.5);
        transferLeft.setPower(.5);
        sleep(10000);

        while(!isStopRequested()){
            intakeAngleLeft.setPosition(up);
            intakeAngleRight.setPosition(up);
            clawAngle.setPosition(up);
            tree.setPosition(up);
            telemetry.addLine("up!");
            telemetry.update();
            sleep(2000);
            intakeAngleLeft.setPosition(down);
            intakeAngleRight.setPosition(down);
            clawAngle.setPosition(down);
            tree.setPosition(down);
            telemetry.addLine("down!");
            telemetry.update();
            sleep(2000);
        }




    }
}
