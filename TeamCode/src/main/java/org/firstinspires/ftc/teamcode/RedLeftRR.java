package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "RED_LEFT_RR_TESTING", group = "Autonomous")
public class RedLeftRR extends LinearOpMode{

    Lift lift;
    Intake intake;
    Claw claw;
    org.firstinspires.ftc.teamcode.Macro macro;

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-35, -62, Math.toRadians(-90)));
        lift = new Lift(this);
        intake = new Intake(this);
        claw = new Claw(this);
        macro = new org.firstinspires.ftc.teamcode.Macro(this);


        Action leftDelivery;
        Action toBackBoard;
        Action toStack;
        Action park;
        Action rightDelivery;

        claw.setClawAngle(.71);

        leftDelivery = drive.actionBuilder(drive.pose)
                .lineToY(-14)
                .turnTo(Math.toRadians(-135))
                .turnTo(-180)
                .build();


        rightDelivery = drive.actionBuilder(drive.pose)
                .lineToY(-12)
                .turnTo(Math.toRadians(-45))
                .turnTo(Math.toRadians(180))
                .build();
        toBackBoard = drive.actionBuilder(drive.pose)
                .setTangent(Math.toRadians(180))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(29, -14), 180)
                .splineToSplineHeading(new Pose2d(37, -15, Math.toRadians(135)), 180)
                .build();


        toStack = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(29, -14, Math.toRadians(180)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-58, -14), Math.toRadians(180))
                .build();
        /*
        park = drive.actionBuilder(drive.pose)

                .lineToX(50)
                .build();

         */


        Action correctDelivery = rightDelivery;


        waitForStart();

        if(isStopRequested()) return;



        Actions.runBlocking(
                new SequentialAction(
                        correctDelivery,
                        LiftOut(500),
                        ClawPosition(claw.autoHalf),
                        LiftIn(),
                        toBackBoard,
                        LiftOut(900),
                        DeliverySequence(),
                        LiftIn(),
                        toStack/*,
                        toBackBoard,
                        LiftOut(900),
                        Macro(),
                        DeliverySequence(),
                        LiftIn(),
                        park*/

                )
        );
    }


    private class LiftOut implements Action {
        // checks if the lift motor has been powered on
        int position;

        public void setPosition(int position) {
            this.position = position;
        }

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
            if (Math.abs(pos - position) > 5) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun

                return false;
            }

        }
    }
    private Action LiftOut(int pos){
        LiftOut action = new LiftOut();
        action.setPosition(pos);
        return action;
    }

    private class LiftIn implements Action {
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
            if (Math.abs(pos - 0) > 4) {
                // true causes the action to rerun
                return true;
            } else {
                // false stops action rerun

                return false;
            }

        }
    }

    private Action LiftIn(){
        return new LiftIn();
    }

    private class DeliverySequence implements Action{
        private boolean initialized = false;
        ElapsedTime elapsedTime = new ElapsedTime();

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!initialized){
                elapsedTime.reset();
            }
            if(elapsedTime.milliseconds() < 200) {
                claw.setClawAngle(.51);
                claw.clawHalf();
            }
            else if(elapsedTime.milliseconds() < 500){
                lift.setMotorsToGoUpOrDown(lift.liftLeft.getCurrentPosition() + 10);
                claw.clawUp();
            }

            return !(elapsedTime.milliseconds() > 600);
        }
    }
    private Action DeliverySequence() {
        return new DeliverySequence();
    }

    private class ClawPosition implements Action{

        double position;


        public void setPosition(double position) {
            this.position = position;
        }


        private boolean initialized = false;
        ElapsedTime elapsedTime = new ElapsedTime();

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!initialized){
                elapsedTime.reset();
            }
            claw.setClawPosition(position);

            return !(elapsedTime.milliseconds() < 600);
        }
    }

    private Action ClawPosition(double pos){
        ClawPosition action = new ClawPosition();
        action.setPosition(pos);
        return action;
    }

    private class Macro implements Action{

        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            org.firstinspires.ftc.teamcode.Macro.macro_run(RedLeftRR.this);
            return org.firstinspires.ftc.teamcode.Macro.macroYay();
        }
    }
    private Action Macro(){
        return new Macro();
    }


}
