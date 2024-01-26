package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.w8wjb.ftc.AdafruitNeoDriver;


import org.firstinspires.ftc.teamcode.Robot;


@Autonomous(name = "HELPMEEEE")
public class HELPMEEEE extends OpMode {

    ElapsedTime stateOneTime = new ElapsedTime();
    private static final int NUM_PIXELS = 60;

    float gain = 2;

    AdafruitNeoDriver neopixels;
    NormalizedColorSensor colorSensorTop;

    @Override
    public void init() {
        //setup for neopixels
        neopixels = hardwareMap.get(AdafruitNeoDriver.class, "neopixels");
        neopixels.setNumberOfPixels(NUM_PIXELS);

        //setup for colour sensor
        colorSensorTop = hardwareMap.get(NormalizedColorSensor.class, "colortop");

        //sets neopixels white for initialization
        int neopixelColor = Color.rgb(255, 255, 255);
        neopixels.fill(neopixelColor);
        neopixels.show();
    }

    @Override
    public void loop() {
        wait(500);
        neopixels.fill("#0000ff");
        neopixels.show();

        if (gamepad2.dpad_up) {
            gain += 0.005;
        } else if (gamepad2.dpad_down && gain > 1) {
            gain -= 0.005;
        }

        NormalizedRGBA colorsTop = colorSensorTop.getNormalizedColors();

        int setColorRed = (int) (colorsTop.red * 2550);
        int setColorGreen = (int) (colorsTop.green * 2550);
        int setColorBlue = (int) (colorsTop.blue * 2550);

        if (setColorRed > 255){setColorRed = 255;}
        if (setColorGreen > 255){setColorGreen = 255;}
        if (setColorBlue > 255){setColorBlue = 255;}

        int neopixelColor = Color.rgb(setColorRed, setColorGreen, setColorBlue);

        neopixels.fill(neopixelColor);
        neopixels.show();

        telemetry.addLine()
                .addData("R", setColorRed)
                .addData("G", setColorBlue)
                .addData("B", setColorBlue);

        telemetry.addData("Gain", gain);
        telemetry.update();
    }
}