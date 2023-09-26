package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Robot {
    Drivetrain drivetrain;
    Lift lift;
    Claw claw;
    public Robot(OpMode opmode){
        drivetrain = new Drivetrain(opmode);
        lift = new Lift(opmode);
        claw = new Claw();
    }
    public Robot(LinearOpMode linearOpMode){
        drivetrain = new Drivetrain(linearOpMode);
        lift = new Lift(linearOpMode);
        claw = new Claw();
    }


}
