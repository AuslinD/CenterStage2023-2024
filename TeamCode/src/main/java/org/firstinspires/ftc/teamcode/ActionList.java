package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.C;

public class ActionList {
    Lift lift;
    Intake intake;
    Claw claw;
    org.firstinspires.ftc.teamcode.Macro macro;
    LinearOpMode opMode;

    ElapsedTime totalElapsedTime;

    public ActionList(LinearOpMode opMode){
        this.opMode = opMode;
        lift = new Lift(opMode);
        intake = new Intake(opMode);
        claw = new Claw(opMode);
        macro = new org.firstinspires.ftc.teamcode.Macro(opMode);
        totalElapsedTime = new ElapsedTime();
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
                //TODO: check this is actually working
                lift.setMotorsToGoUpOrDown(position);
                initialized = true;
            }

            if(lift.liftLeft.getCurrentPosition() > 1600){
                lift.rotateRight.setPower(.15);
                //robot.lift.rotateLeft.setPower(.15);

            }
            else {
                lift.rotateRight.setPower(0);
                //.lift.rotateLeft.setPower(0);
            }

            // checks lift's current position
            double pos = lift.liftRight.getCurrentPosition();
            packet.put("liftPos", pos);
            if (Math.abs(pos - position) > 2) {
                //wtf
                return true;
            } else {
                //figurethisout later

                lift.rotateRight.setPower(0);
                return false;
            }

        }
    }
    public Action LiftOut(int pos){
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
                //TODO: this should go back in but it doesnnt
                lift.setMotorsToGoUpOrDown(0);
                initialized = true;
            }

            // checks lift's current position
            double pos = lift.liftRight.getCurrentPosition();
            packet.put("liftPos", pos);
            if (Math.abs(0 - pos) > 2) {
                // figure out later
                lift.setMotorsToGoUpOrDown(0);
                return true;
            } else {
                // figure out later

                return false;
            }

        }
    }

    public Action LiftIn(){
        return new LiftIn();
    }

    private class DeliverySequence implements Action{
        private boolean initialized = false;
        ElapsedTime elapsedTime = new ElapsedTime();

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!initialized){

                elapsedTime.reset();
                initialized = true;
            }
            if(elapsedTime.milliseconds() < 200) {
                claw.setClawAngle(.51);
                claw.clawHalf();
            }
            else if(elapsedTime.milliseconds() < 600){
                lift.setMotorsToGoUpOrDown(lift.liftLeft.getCurrentPosition() + 10);
                claw.clawUp();
            }

            return false;
        }
    }
    public Action DeliverySequence() {
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
                initialized = true;
            }
            claw.setClawPosition(position);



            return (elapsedTime.milliseconds() < 600);
        }
    }

    public Action ClawPosition(double pos){
        ClawPosition action = new ClawPosition();
        action.setPosition(pos);
        return action;
    }

    private class Macro implements Action{

        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            org.firstinspires.ftc.teamcode.Macro.macro_run(opMode);
            return org.firstinspires.ftc.teamcode.Macro.macroYay();
        }
    }
    public Action Macro(){
        return new Macro();
    }


    private class LiftAngle implements Action{
        ElapsedTime elapsedTime;
        PID pid;
        int position;

        public void setPosition(int position) {
            this.position = position;
        }

        private boolean initialized = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if(!initialized){
                elapsedTime = new ElapsedTime();
                elapsedTime.reset();
                pid = new PID(.95, 0.01, 0, position);
                initialized = true;
            }
            double multiplier = 1;
            //double power = pid.loop(position, elapsedTime.milliseconds());
            if(lift.rotateRight.getCurrentPosition() < position){
                multiplier = 1;
            }
            else{
                multiplier = -1;
            }

            lift.rotateRight.setPower(.5 * multiplier);

            if(Math.abs(lift.rotateRight.getCurrentPosition() - position) > 100){
                lift.rotateRight.setPower(.5 * multiplier);
                //lift.rotateRight.setPower(.2);
                return true;
            }
            else{
                lift.rotateRight.setPower(0);
                return false;
            }

        }
    }
    public Action LiftAngle(int pos){
        LiftAngle action = new LiftAngle();
        action.setPosition(pos);
        return action;
    }
}
