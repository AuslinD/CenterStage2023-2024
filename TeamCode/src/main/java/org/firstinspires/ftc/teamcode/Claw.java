package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


public class Claw {

    private Servo tree, clawAngle;

    //CHANGE THIS
    double down = .8;

    double half = .22;//matthew was here

    double autoHalf = half +.07;

    double up = 0;

    double rotate1 = 0;
    double rotate2 = 0;

    public Claw(OpMode opMode) {
        clawAngle = opMode.hardwareMap.get(Servo.class, "angClaw");
        tree = opMode.hardwareMap.get(Servo.class, "tree");
    }
    public Claw(LinearOpMode opMode){
        clawAngle = opMode.hardwareMap.get(Servo.class, "angClaw");
        tree = opMode.hardwareMap.get(Servo.class, "tree");
    }
    public void clawDown(){
        tree.setPosition(down);
    }
    public void clawHalf() {
        tree.setPosition(half);
    }
    public void autoClawHalf() {
        tree.setPosition(autoHalf);
    }
    public void clawUp() {
        tree.setPosition(up);
    }
    public void setClawAngle(double position){
        clawAngle.setPosition(position);
    }

    public void setClawPosition(double position){
        tree.setPosition(position);
    }

    public double getClawAngle(){
        return clawAngle.getPosition();
    }

}
