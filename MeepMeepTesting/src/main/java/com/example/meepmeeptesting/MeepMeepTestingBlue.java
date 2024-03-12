package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTestingBlue {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, 62, -1.5708))

                .splineToConstantHeading(new Vector2d(-35,55), Math.toRadians(-90))
                .turnTo(Math.toRadians(60))
                .splineToConstantHeading(new Vector2d(-35,59), Math.toRadians(-90))
                .turnTo(Math.toRadians(0))
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(29, 59), 0)
                .splineToSplineHeading(new Pose2d(37,56, Math.toRadians(130)), 0)
                .turnTo(Math.toRadians(-180))
                .splineToConstantHeading(new Vector2d(-35,59), Math.toRadians(-180))
                .splineToSplineHeading(new Pose2d(-57,35, Math.toRadians(180)),Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-34, 59), Math.toRadians(0))
                .setTangent(0)
                .splineToConstantHeading(new Vector2d(29, 59), 0)
                .splineToSplineHeading(new Pose2d(37, 57, Math.toRadians(150)), 0)
                .splineToSplineHeading(new Pose2d(48, 57, Math.toRadians(180)), Math.toRadians(180))

                //.splineToConstantHeading(new Vector2d(-35,59), Math.toRadians(-180))

                /*.splineToConstantHeading(new Vector2d(29, -14), 0)
                .setTangent(180)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(29, -14), 0)
                .splineToSplineHeading(new Pose2d(37, -15, Math.toRadians(135)), 0)// end of backdrop section
                .splineToSplineHeading(new Pose2d(29, -14, Math.toRadians(180)), Math.toRadians(180))
                .splineToConstantHeading(new Vector2d(-58, -14), Math.toRadians(180))
                 */




                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}