package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Config
@Autonomous(name = "Blue_Right_RR_TESTING", group = "Autonomous")
public class BlueRightRR extends LinearOpMode{

    int[] liftAngles = new int[]{1600, 800, 350};
    OpenCvInternalCamera phoneCam;
    OpenCV.BlueCV pipeline;

    Lift lift;
    Intake intake;
    Claw claw;
    org.firstinspires.ftc.teamcode.Macro macro;
    ElapsedTime totalElapsedTime;
    org.firstinspires.ftc.teamcode.ActionList actions;

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-35, 62, +1.5708));
        lift = new Lift(this);
        intake = new Intake(this);
        claw = new Claw(this);
        macro = new org.firstinspires.ftc.teamcode.Macro(this);
        totalElapsedTime = new ElapsedTime();
        actions = new org.firstinspires.ftc.teamcode.ActionList(this);



        Action leftDelivery;
        Action centerDelivery;
        Action rightDelivery;
        Action toBackBoard;
        Action toStack;
        Action park;
        Action stackToBackboard;


        claw.setClawAngle(.3);

        leftDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35,55), Math.toRadians(-90))
                .waitSeconds(2)
                .turnTo(Math.toRadians(120)) //was 60
                .splineToConstantHeading(new Vector2d(-35,59), Math.toRadians(-90))
                .turnTo(Math.toRadians(180))
                //.waitSeconds(4)
                .build();

        centerDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35,48), Math.toRadians(-90))
                .waitSeconds(2)
                .turnTo(Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(-35,59), Math.toRadians(-90))
                .turnTo(Math.toRadians(180))
                //.waitSeconds(4)
                .build();


        rightDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, 59), Math.toRadians(-90))
                .turnTo(Math.toRadians(60)) //was  120
                .waitSeconds(4)
                .turnTo(Math.toRadians(180))

                //placeholder for future
                //.waitSeconds(4)
                .build();

        toBackBoard = drive.actionBuilder(new Pose2d(-35, 57, Math.toRadians(180)))
                .setReversed(true)
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(29, 57), 0)
                .splineToSplineHeading(new Pose2d(37,56, Math.toRadians(145)), 0)
                .turnTo(Math.toRadians(-180))

                //.waitSeconds(4)
                .build();


        toStack = drive.actionBuilder(new Pose2d(37, 56, Math.toRadians(-180)))
                .setTangent(Math.toRadians(180))
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(29, 57.5, Math.toRadians(180)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-34, 57.5), Math.toRadians(180))
                .setReversed(false)
                .splineTo(new Vector2d(-57, 35), Math.toRadians(180))//at stack rn

                //.waitSeconds(4)
                .build();
        
        stackToBackboard = drive.actionBuilder(new Pose2d(-57, 35, Math.toRadians(180)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-34, 57), Math.toRadians(0))
                .waitSeconds(1)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(29, 57), 0)
                .splineToSplineHeading(new Pose2d(37, 56, Math.toRadians(150)), 0)
                .build();

        park = drive.actionBuilder(new Pose2d(37,56, Math.toRadians(155)))

                .splineToSplineHeading(new Pose2d(48, 57, Math.toRadians(180)), Math.toRadians(180))
                .build();

        claw.clawDown();



        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new OpenCV.BlueCV();
        phoneCam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }
            //matthew was here
            @Override
            public void onError(int errorCode) {
                telemetry.addLine("No camera");
            }
        });
        OpenCV.BlueCV.SkystonePosition pos = null;
        while (!isStarted() && !isStopRequested()) {
            telemetry.addData("Analysis", pipeline.getAnalysis());
            telemetry.update();
            pos = pipeline.getAnalysis();
            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }

        Action correctDelivery = leftDelivery;
        waitForStart();

        if(isStopRequested()) return;
        int index = 0;

        if(pos == OpenCV.BlueCV.SkystonePosition.LEFT){
            correctDelivery = leftDelivery;
            index = 2;
        }
        else if(pos == OpenCV.BlueCV.SkystonePosition.CENTER){
            correctDelivery = centerDelivery;
            index = 1;
        }
        else{
            correctDelivery = rightDelivery;
            index = 0;
        }




        Actions.runBlocking(
                new SequentialAction(


                        new ParallelAction(
                                correctDelivery,
                                new SequentialAction(
                                        new SleepAction(5),
                                        //LiftOut(700),
                                        ClawPosition(claw.autoHalf),
                                        //LiftIn(),
                                        ClawPosition(claw.autoHalf)
                                )
                        ),

                        new ParallelAction(//angle to deliver to backdrop
                                toBackBoard,

                                new SequentialAction(
                                        new SleepAction(1.5),
                                        new ParallelAction(
                                                actions.LiftAngle(liftAngles[index])
                                                //actions.LiftOut(3600)
                                        )
                                )

                        ),
                        new SequentialAction(
                                new InstantAction(() -> claw.clawHalf()),

                                new SleepAction(.5),
                                //new InstantAction(() -> lift.setMotorsToGoUpOrDown(1000)),
                                new InstantAction(() -> claw.clawUp())
                        ),
                        new ParallelAction(// retract lift and angle
                                //actions.LiftOut(0),
                                new SequentialAction(
                                        new SleepAction(2),
                                        actions.LiftAngle(0)

                                )

                        ),
                        toStack,// add the stack get stuff here?
                        toBackBoard,
                        stackToBackboard,
                        /*new ParallelAction( //testing stuff here

                                //LiftOut(0),
                                new SequentialAction(

                                        new SleepAction(2),
                                        new ParallelAction(
                                                actions.LiftAngle(liftAngles[index])
                                                //actions.LiftOut(3600)
                                        )


                                )

                        ),*/
                        //toStack,
                        //toBackBoard,
                        //LiftOut(900),
                        //Macro(),
                        //DeliverySequence(),
                        //LiftIn()
                        //stackToBackboard,
                        park

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
                //TODO: check this is actually working
                lift.setMotorsToGoUpOrDown(position);
                initialized = true;
            }

            // checks lift's current position
            double pos = lift.liftRight.getCurrentPosition();
            packet.put("liftPos", pos);
            if (Math.abs(pos - position) > 2) {
                //wtf
                return true;
            } else {
                //figurethisout later

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
                initialized = true;
            }
            claw.setClawPosition(position);



            return (elapsedTime.milliseconds() < 600);
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
            org.firstinspires.ftc.teamcode.Macro.macro_run(BlueRightRR.this);
            return org.firstinspires.ftc.teamcode.Macro.macroYay();
        }
    }
    private Action Macro(){
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
    private Action LiftAngle(int pos){
        LiftAngle action = new LiftAngle();
        action.setPosition(pos);
        return action;
    }


}
