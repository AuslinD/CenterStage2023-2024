package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
@Config
@Autonomous(name = "BLUE_TEST_AUTO_PIXEL", group = "Autonomous")
public class RedLeftRR extends LinearOpMode{

    Lift lift;
    Intake intake;
    Claw claw;

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-35, -62, Math.toRadians(-90)));
        lift = new Lift(this);
        intake = new Intake(this);
        claw = new Claw(this);
        Action trajectoryAction1;
        Action trajectoryAction2;
        Action trajectoryAction3;

        trajectoryAction1 = drive.actionBuilder(drive.pose)
                .lineToY(-12)
                .turnTo(Math.toRadians(180))

                .build();

        waitForStart();

        if(isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryAction1,
                        LiftOut(),
                        LiftIn()

                )
        );
    }


    public class LiftOut implements Action {
        // checks if the lift motor has been powered on
        private boolean initialized = false;

        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                lift.setMotorsToGoUpOrDown(500);
                initialized = true;
            }

            // checks lift's current position
            double pos = lift.liftLeft.getCurrentPosition();
            packet.put("liftPos", pos);
            if (Math.abs(pos - 500) > 5) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun

                return false;
            }

        }
    }
    public Action LiftOut(){
        return new LiftOut();
    }

    public class LiftIn implements Action {
        // checks if the lift motor has been powered on
        private boolean initialized = false;

        // actions are formatted via telemetry packets as below
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // powers on motor, if it is not on
            if (!initialized) {
                lift.setMotorsToGoUpOrDown(0);
                initialized = true;
            }

            // checks lift's current position
            double pos = lift.liftLeft.getCurrentPosition();
            packet.put("liftPos", pos);
            if (Math.abs(pos - 0) > 5) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun

                return false;
            }

        }
    }

    public Action LiftIn(){
        return new LiftIn();
    }


}
