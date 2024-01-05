package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "ServoTest", group = "test")
public class ServoTest extends LinearOpMode {
    double up = .5;
    double down = -.5;
    CRServo intakeLeft, intakeRight, transferLeft, transferRight;

    Servo intakeAngleLeft, intakeAngleRight, clawAngle, tree;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this);
        intakeAngleLeft = hardwareMap.get(Servo.class, "angL");
        intakeAngleRight = hardwareMap.get(Servo.class, "angR");
        robot.imu.resetYaw();
        double initPos = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        robot.plane.setPosition(.47);



        waitForStart();

        //hangL.setPower(-1);
        //hangR.setPower(1);
        /*
        intakeLeft.setPower(.5);
        intakeRight.setPower(.5);
        transferRight.setPower(.5);
        transferLeft.setPower(.5);

         */
        sleep(1000);

        while(!isStopRequested()){
            /*
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



             */
            intakeAngleLeft.setPosition(.075);
            intakeAngleRight.setPosition(-1);
            sleep(5000);
            intakeAngleRight.setPosition(.552);
            intakeAngleLeft.setPosition(0);
            sleep(5000);
        }




    }
}
