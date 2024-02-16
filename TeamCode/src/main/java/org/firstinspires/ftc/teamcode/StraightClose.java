package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "straightImmediate", group = "auto")
public class StraightClose extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        Robot robot = new Robot(this);
        //robot.intake.stowIntake();
        robot.claw.setClawAngle(.71);
        robot.claw.clawDown();
        robot.plane.setPosition(.47);//hello austin my pookie wookie bear <3
        waitForStart();

        robot.drivetrain.fl.setPower(.25);
        robot.drivetrain.bl.setPower(.25);
        robot.drivetrain.fr.setPower(.25);
        robot.drivetrain.br.setPower(.25);
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
        sleep(5400);

        robot.drivetrain.setALLMotorPower(0);



    }

}
