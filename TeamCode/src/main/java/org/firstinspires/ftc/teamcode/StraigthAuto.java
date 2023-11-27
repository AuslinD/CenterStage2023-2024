package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "straight", group = "test")
public class StraigthAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this);
        robot.intake.stowIntake();
        robot.plane.setPosition(.47);//hello austin my pookie wookie bear <3
        waitForStart();
        EncoderAutoMethods autoMethods = new EncoderAutoMethods(this);
        autoMethods.drive(200, 5);
        sleep(30000);
        robot.drivetrain.fl.setPower(.5);
        robot.drivetrain.bl.setPower(.5);
        robot.drivetrain.fr.setPower(.5);
        robot.drivetrain.br.setPower(.5);
        /*sleep(20000);
        robot.drivetrain.fl.setPower(-.4);
        robot.drivetrain.bl.setPower(-.4);
        robot.drivetrain.fr.setPower(-.4);
        robot.drivetrain.br.setPower(-.4);
        sleep(1750);
        robot.drivetrain.fl.setPower(0);
        robot.drivetrain.bl.setPower(0);
        robot.drivetrain.fr.setPower(0);
        robot.drivetrain.br.setPower(0);
        robot.intake.spinTake(-1);
        robot.intake.lowerIntake();*/
        sleep(1000);
        robot.intake.spinTake(-1);
        robot.intake.spinTake(-1);
        sleep(5000);
        robot.intake.stowIntake();
        robot.drivetrain.setALLMotorPower(-.3);
        sleep(600);
        robot.drivetrain.setALLMotorPower(0);


    }
}
