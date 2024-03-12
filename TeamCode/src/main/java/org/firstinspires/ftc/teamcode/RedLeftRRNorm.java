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

@Config
@Autonomous(name = "RedLeftRRClose", group = "Autonomous")
public class RedLeftRRNorm extends LinearOpMode {

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
        Action toStack;
        Action park;
        Action stackToBackboard;


        claw.setClawAngle(.1);

        leftDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, -57), Math.toRadians(90))
                .turnTo(Math.toRadians(-65))
                .waitSeconds(1)
                .turnTo(Math.toRadians(180))
                .build();

        centerDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, -57), Math.toRadians(90))
                .waitSeconds(1)
                .turnTo(Math.toRadians(180))
                .build();


        rightDelivery = drive.actionBuilder(drive.pose)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-35, -57), Math.toRadians(90))
                .turnTo(Math.toRadians(-115))
                .waitSeconds(1)
                .turnTo(Math.toRadians(180))
                .build();

        toBackBoard = drive.actionBuilder(new Pose2d(-35, -57, Math.toRadians(180)))
                .setTangent(Math.toRadians(0))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(29, -57), 0)
                .splineToSplineHeading(new Pose2d(37, -57, Math.toRadians(200)), 0)
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
                .splineToSplineHeading(new Pose2d(48, -57, Math.toRadians(180)), Math.toRadians(180))
                .build();


        claw.clawDown();





        waitForStart();

        if(isStopRequested()) return;


        Action correctDelivery = leftDelivery;

        Actions.runBlocking(
                new SequentialAction(

                        new ParallelAction(
                                correctDelivery,
                                new SequentialAction(
                                        new SleepAction(2),
                                        //actions.LiftOut(700),
                                        actions.ClawPosition(claw.autoHalf),
                                        //actions.LiftIn(),
                                        actions.ClawPosition(claw.autoHalf)
                                )
                        ),


                        new ParallelAction(//angle to deliver to backdrop
                                toBackBoard,

                                new SequentialAction(
                                        new SleepAction(1.5),
                                        new ParallelAction(
                                                actions.LiftAngle(1600)
                                                //actions.LiftOut(3600)
                                        )
                                )

                        ),
                        new SequentialAction(//delivery sequence
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
                        toStack,
                        toBackBoard,
                        /*actions.Macro(),
                        actions.LiftAngle(0),
                        actions.DeliverySequence(),
                        actions.LiftIn(),*/
                        stackToBackboard,
                        park

                )
        );


    }
}
