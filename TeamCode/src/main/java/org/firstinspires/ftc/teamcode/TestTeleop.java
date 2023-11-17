package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "test", group = "test")
public class TestTeleop extends OpMode {
    DcMotor rotateLeft, rotateRight, liftLeft, liftRight;
    Robot robot;
    @Override
    public void init() {
        robot = new Robot(this);
        /*
        rotateLeft = hardwareMap.get(DcMotorEx.class, "rotleft");
        rotateRight = hardwareMap.get(DcMotorEx.class, "rotright");
        liftLeft = hardwareMap.get(DcMotorEx.class, "upleft");
        liftRight = hardwareMap.get(DcMotorEx.class, "upright");

        rotateLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotateRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotateLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rotateRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rotateLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotateRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rotateLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        rotateRight.setDirection(DcMotorSimple.Direction.REVERSE);
        liftLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        liftRight.setDirection(DcMotorSimple.Direction.REVERSE);
*/
        /*
        rotateLeft.setTargetPosition(0);
        rotateRight.setTargetPosition(0);
        liftRight.setTargetPosition(0);
        liftLeft.setTargetPosition(0);

         */



    }

    @Override
    public void loop() {
        /*
        if(Math.abs(gamepad2.left_stick_y) > 0.1){
            liftLeft.setPower(1 * gamepad2.left_stick_y);
            liftRight.setPower(1 * gamepad2.left_stick_y);
        }
        else{
            liftLeft.setPower(0);
            liftRight.setPower(0);
        }
        if(Math.abs(gamepad2.right_stick_y) > 0.1){
            rotateRight.setPower(.75 * gamepad2.right_stick_y);
            rotateLeft.setPower(.75 * gamepad2.right_stick_y);
        }
        else{
            rotateRight.setPower(0);
            rotateLeft.setPower(0);
        }

         */

        telemetry.addData("left up per rot", liftLeft.getCurrentPosition() / (rotateLeft.getCurrentPosition()+.000001));
        telemetry.addData("right up per rot", liftRight.getCurrentPosition() / (rotateRight.getCurrentPosition()+.000001));
        telemetry.addData("left up per rot", liftLeft.getCurrentPosition() / (rotateRight.getCurrentPosition()+0.000001));
        telemetry.addData("parallel odom", robot.drivetrain.bl.getCurrentPosition());
        telemetry.update();


    }
}
