package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Macro.macro_run;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.w8wjb.ftc.AdafruitNeoDriver;
import android.graphics.Color;

@TeleOp(name = "Teleop", group = "teleop")
public class TeleOpRunner extends OpMode
{
    private static final int NUM_PIXELS = 24;
    AdafruitNeoDriver neopixels;

    TeleOpMethods teleOpMethods;
    Macro macro;
    @Override
    public void init() {
        neopixels = hardwareMap.get(AdafruitNeoDriver.class, "neopixels");
        neopixels.setNumberOfPixels(NUM_PIXELS);

        neopixels.fill(Color.rgb(255, 255, 255));
        neopixels.show();

        teleOpMethods = new TeleOpMethods(this);
//        Macro macro = new macro_run(this);

    }

    @Override
    public void loop() {

        teleOpMethods.teleOpControls(gamepad1, gamepad2);
    }
}
