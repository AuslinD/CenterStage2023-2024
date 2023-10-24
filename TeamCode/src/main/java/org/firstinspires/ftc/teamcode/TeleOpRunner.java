package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Teleop", group = "teleop")
public class TeleOpRunner extends OpMode
{
    TeleOpMethods teleOpMethods;
    @Override
    public void init() {

        teleOpMethods = new TeleOpMethods(this);


    }

    @Override
    public void loop() {

        teleOpMethods.teleOpControls(gamepad1, gamepad2);
    }
}
