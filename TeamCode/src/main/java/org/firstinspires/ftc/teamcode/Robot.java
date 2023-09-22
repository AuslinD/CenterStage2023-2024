package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Robot {
    Drivetrain drivetrain;
    Lift lift;
    public Robot(OpMode opmode){
        drivetrain = new Drivetrain(opmode);
        lift = new Lift(opmode);
    }
    public Robot(LinearOpMode linearOpMode){
        drivetrain = new Drivetrain(linearOpMode);
        lift = new Lift(linearOpMode);

    }


}
