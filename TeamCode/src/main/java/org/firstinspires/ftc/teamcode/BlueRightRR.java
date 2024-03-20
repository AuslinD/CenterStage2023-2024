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
@Autonomous(name = "Blue_Right_RRClose", group = "Autonomous")
public class BlueRightRR extends LinearOpMode{

    int[] liftAngles = new int[]{1600, 800, 450};
    int[] liftPositions = new int[]{1000, 800, 600};
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
        Action angleLeftBoard;
        Action angleCenterBoard;
        Action angleRightBoard;
        Action toStack;
        Action park;
        Action stackToBackboard;


        claw.setClawAngle(.3);

        leftDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, 59.5), Math.toRadians(-90))
                .turnTo(Math.toRadians(120)) //was  120
                .waitSeconds(5)
                .turnTo(Math.toRadians(180))
                //.waitSeconds(4)
                .build();

        centerDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, 59.5), Math.toRadians(-90))
                .waitSeconds(5)
                .turnTo(Math.toRadians(180))
                //.waitSeconds(4)
                .build();


        rightDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, 59.5), Math.toRadians(-90))
                .turnTo(Math.toRadians(65)) //was  120
                .waitSeconds(5)
                .turnTo(Math.toRadians(180))

                //placeholder for future
                //.waitSeconds(4)
                .build();

        toBackBoard = drive.actionBuilder(new Pose2d(-35, 59.5, Math.toRadians(180)))
                .setReversed(true)
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(29, 58), 0)
                //.splineToSplineHeading(new Pose2d(37,56, Math.toRadians(160)), 0)
                //.waitSeconds(3)
                //.turnTo(Math.toRadians(-180))

                //.waitSeconds(4)
                .build();
        angleLeftBoard = drive.actionBuilder(new Pose2d(29, 58, Math.toRadians(180)))
                .setTangent(0)
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(37,56, Math.toRadians(160)), 0)
                .waitSeconds(3)
                .build();
        angleCenterBoard = drive.actionBuilder(new Pose2d(29, 58, Math.toRadians(180)))
                .setTangent(0)
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(37,56, Math.toRadians(150)), 0)
                .waitSeconds(3)
                .build();
        angleRightBoard = drive.actionBuilder(new Pose2d(29, 58, Math.toRadians(180)))
                .setTangent(0)
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(37,56, Math.toRadians(140)), 0)
                .waitSeconds(3)
                .build();

        toStack = drive.actionBuilder(new Pose2d(37, 56, Math.toRadians(-180)))
                .setTangent(Math.toRadians(180))
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(29, 58.5, Math.toRadians(180)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-34, 58.5), Math.toRadians(180))
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

                .splineToSplineHeading(new Pose2d(52, 57, Math.toRadians(180)), Math.toRadians(180))
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
        Action correctBackboardAngle;
        waitForStart();

        if(isStopRequested()) return;
        int index = 0;

        if(pos == OpenCV.BlueCV.SkystonePosition.LEFT){
            correctDelivery = leftDelivery;
            correctBackboardAngle = angleLeftBoard;
            index = 2;
        }
        else if(pos == OpenCV.BlueCV.SkystonePosition.CENTER){
            correctDelivery = centerDelivery;
            correctBackboardAngle = angleCenterBoard;
            index = 1;
        }
        else{
            correctDelivery = rightDelivery;
            correctBackboardAngle = angleRightBoard;
            index = 0;
        }




        Actions.runBlocking(
                new SequentialAction(


                        new ParallelAction(
                                correctDelivery,
                                new SequentialAction(
                                        actions.LiftOut(2000),
                                        new SleepAction(1),
                                        actions.ClawPosition(claw.autoHalf),
                                        actions.LiftIn(),
                                        actions.ClawPosition(claw.autoHalf)
                                )
                        ),
                        toBackBoard,

                        new ParallelAction(//angle to deliver to backdrop
                                correctBackboardAngle,

                                new SequentialAction(
                                        new SleepAction(1.3),
                                        new ParallelAction(
                                                actions.LiftAngle(450),

                                                new SequentialAction(
                                                        new SleepAction(.5),
                                                        actions.LiftOut(liftPositions[index])
                                                )

                                        )
                                )

                        ),
                        new SequentialAction(
                                new InstantAction(() -> claw.clawUp())
                        ),
                        new ParallelAction(// retract lift and angle
                                actions.LiftOut(0),
                                new SequentialAction(
                                        new SleepAction(2),
                                        actions.LiftAngle(0)

                                )

                        ),
                        /*toStack,// add the stack get stuff here?
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





}
