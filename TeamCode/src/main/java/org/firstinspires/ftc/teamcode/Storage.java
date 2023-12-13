package org.firstinspires.ftc.teamcode;

public class Storage {/*



    //4600 for lift


        if(gamepad2.right_bumper){
            rn1p = .75;
            rn2p = rn1p;
            robot.lift.rotateRight.setPower(rn1p);
            robot.lift.rotateLeft.setPower(rn2p);
        }
        else if(gamepad2.left_bumper){
            if(robot.lift.rotateRight.getCurrentPosition() > 0){
                rn1p = -.75;
                rn2p = rn1p;
                robot.lift.rotateRight.setPower(rn1p);
                robot.lift.rotateLeft.setPower(rn2p);
            }
            else if (ignoreBounds){
                rn1p = -.75;
                rn2p = rn1p;
                robot.lift.rotateRight.setPower(rn1p);
                robot.lift.rotateLeft.setPower(rn2p);
            }

        }
        else{
            if(robot.lift.liftLeft.getCurrentPosition() > 1600){
                robot.lift.rotateRight.setPower(.15);
                robot.lift.rotateLeft.setPower(.15);

            }
            else {
                robot.lift.rotateRight.setPower(0);
                robot.lift.rotateLeft.setPower(0);
            }
        }


        boolean macroOff = !state[1]&&!state[0]&&!state[3]&&!state[2]&&!state[4]&&!state[5]&&!state[6];

        if (macroOff){
                robot.claw.clawDown();
        }
         if(macroOff) {
            double offSet = robot.lift.rotateRight.getCurrentPosition() / 11356.25;
            offSet = treeAngle - offSet > .33 && treeAngle - offSet < .61? offSet : 0;
            robot.claw.setClawAngle(treeAngle - offSet);
        }
        else {
            robot.claw.setClawAngle(treeAngle);
        }
    */
}
