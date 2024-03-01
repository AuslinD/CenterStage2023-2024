package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);


        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, -62, -1.5708))

                .lineToY(-14)
                        .turnTo(-0.785398)
                .turnTo(0)
                        .lineToX(36)
                        .turnTo(Math.toRadians(135))
                        .turnTo(Math.toRadians(180))
                        .lineToX(-58)
                        .lineToX(36)
                        .turnTo(Math.toRadians(135))
                        .turnTo(Math.toRadians(180))
                        .lineToX(50)



                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}