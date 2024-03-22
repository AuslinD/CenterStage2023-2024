package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.function.Function;

@Config
@Autonomous(name = "RedLeftRRClose", group = "Autonomous")
public class RedLeftRRNorm extends LinearOpMode {

    int[] liftAngles = new int[]{850, 900, 900};
    int[] liftPositions = new int[]{2300, 2250, 2000};

    OpenCvInternalCamera phoneCam;
    OpenCV.RedCV pipeline;

    Lift lift;
    Intake intake;
    Claw claw;
    org.firstinspires.ftc.teamcode.Macro macro;

    ElapsedTime totalElapsedTime;

    org.firstinspires.ftc.teamcode.ActionList actions;

    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(-35, -62, Math.toRadians(-90)));
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


        claw.setClawAngle(.5);

        leftDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, -57), Math.toRadians(90))
                .turnTo(Math.toRadians(-65))
                .waitSeconds(5)
                .turnTo(Math.toRadians(180))
                .build();

        centerDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, -57), Math.toRadians(90))
                .waitSeconds(5)
                .turnTo(Math.toRadians(180))
                .build();


        rightDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, -57), Math.toRadians(90))
                .turnTo(Math.toRadians(-115))
                .waitSeconds(5)
                .turnTo(Math.toRadians(180))
                .build();

        toBackBoard = drive.actionBuilder(new Pose2d(-35, -57, Math.toRadians(180)))
                .setTangent(Math.toRadians(0))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(29, -57), 0)
                //.splineToSplineHeading(new Pose2d(37, -57, Math.toRadians(200)), 0)
                .build();
        //angling to backboard
        angleRightBoard = drive.actionBuilder(new Pose2d(29, -57, Math.toRadians(180)))
                .setReversed(true)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(37, -57, Math.toRadians(200)), 0)
                .waitSeconds(3)
                .build();
        angleCenterBoard = drive.actionBuilder(new Pose2d(29, -57, Math.toRadians(180)))
                .setReversed(true)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(37, -57, Math.toRadians(212)), 0)
                .waitSeconds(3)
                .build();
        angleLeftBoard = drive.actionBuilder(new Pose2d(29, -57, Math.toRadians(180)))
                .setReversed(true)
                .setTangent(0)
                .splineToSplineHeading(new Pose2d(37, -57, Math.toRadians(222)), 0)
                .waitSeconds(3)
                .build();


        toStack = drive.actionBuilder(new Pose2d(37, -57, Math.toRadians(200)))
                .setTangent(Math.toRadians(180))
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(29, -58, Math.toRadians(180)), Math.toRadians(180))
                //.waitSeconds(0)
                .splineToConstantHeading(new Vector2d(-34, -58), Math.toRadians(180))
                .setReversed(false)
                .splineTo(new Vector2d(-57, -35), Math.toRadians(180))//at stack rn
                .build();
        stackToBackboard = drive.actionBuilder(new Pose2d(-56, -35, Math.toRadians(180)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-34, -58), Math.toRadians(0))
                .waitSeconds(0)
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(29, -58), 0)
                .splineToSplineHeading(new Pose2d(37, -57, Math.toRadians(200)), 0)
                .build();

        park = drive.actionBuilder(new Pose2d(37, -57, Math.toRadians(200)))
                .setTangent(Math.toRadians(-180))
                .splineToSplineHeading(new Pose2d(52, -62, Math.toRadians(180)), Math.toRadians(180))
                .build();


        claw.clawDown();




        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new OpenCV.RedCV();
        phoneCam.setPipeline(pipeline);

        // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
        // out when the RC activity is in portrait. We do our actual image processing assuming
        // landscape orientation, though.
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                phoneCam.startStreaming(320,240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode)
            {
                telemetry.addLine("No camera");
            }
        });
        OpenCV.RedCV.SkystonePosition pos = null;
        while (!isStarted() && !isStopRequested())
        {
            telemetry.addData("Analysis", pipeline.getAnalysis());
            telemetry.update();
            pos = pipeline.getAnalysis();
            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }

        Action correctDelivery;
        waitForStart();

        if(isStopRequested()) return;
        int index = 0;
        Action correctBackboardAngle;

        if(pos == OpenCV.RedCV.SkystonePosition.LEFT){
            correctDelivery = leftDelivery;
            correctBackboardAngle = angleLeftBoard;
            index = 0;
        }
        else if(pos == OpenCV.RedCV.SkystonePosition.RIGHT) {
            correctDelivery = rightDelivery;
            correctBackboardAngle = angleRightBoard;
            index = 2;
        }
        else{
            correctDelivery = centerDelivery;
            correctBackboardAngle = angleCenterBoard;
            index = 1;
        }




        Actions.runBlocking(
                new SequentialAction(

                        new ParallelAction(
                                correctDelivery,
                                new SequentialAction(
                                        //deliver spike
                                        new SleepAction(2),
                                        actions.LiftOut(index != 1? 2000 : 2200),
                                        new InstantAction(() -> claw.setClawAngle(.11)),
                                        actions.ClawPosition(claw.autoHalf),
                                        new SleepAction(1),
                                        new ParallelAction(
                                                actions.LiftIn(),
                                                new InstantAction(() -> claw.setClawAngle(.50))
                                        ),

                                        new InstantAction(() -> claw.setClawAngle(.50))
                                )
                        ),
                        toBackBoard,

                        new ParallelAction(//angle to deliver to backdrop
                                correctBackboardAngle,

                                new SequentialAction(
                                        new SleepAction(1.5),
                                        new ParallelAction(
                                                actions.LiftAngle(liftAngles[index]),
                                                actions.LiftOut(liftPositions[index])
                                        ),
                                        actions.LiftAngle(liftAngles[index])
                                )

                        ),
                        new SequentialAction(//delivery sequence
                                new InstantAction(() -> claw.setClawAngle(.50 - lift.rotateRight.getCurrentPosition() / 11356.25)),
                                new InstantAction(() -> claw.clawUp()),
                                new SleepAction(1)
                        ),
                        new ParallelAction(// retract lift and angle
                                actions.LiftOut(0),
                                new SequentialAction(
                                        new SleepAction(1),
                                        actions.LiftAngle(0)
                                )

                        ),
                        //correctBackboardAngle,
                        /*toStack,
                        toBackBoard,
                        actions.Macro(),
                        actions.LiftAngle(0),
                        actions.LiftIn(),
                        stackToBackboard,*/
                        park

                )
        );


    }
}
