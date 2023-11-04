package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous(name = "RedRightEnc", group = "Encoder Auto")
public class RedRightEnc extends LinearOpMode {
    EncoderAutoMethods movement;
    Robot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        movement = new EncoderAutoMethods();
        movement.drive(5, 5);
        movement.turn(90)

    }
}
