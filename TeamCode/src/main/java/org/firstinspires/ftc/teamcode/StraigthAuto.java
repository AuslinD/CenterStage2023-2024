package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "straight", group = "test")
public class StraigthAuto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(this);
        waitForStart();
        robot.drivetrain.fl.setPower(-1);
        robot.drivetrain.bl.setPower(-1);
        robot.drivetrain.fr.setPower(-1);
        robot.drivetrain.br.setPower(-1);
        sleep(15000);
        robot.drivetrain.fl.setPower(0);
        robot.drivetrain.bl.setPower(0);
        robot.drivetrain.fr.setPower(0);
        robot.drivetrain.br.setPower(0);
    }
}
