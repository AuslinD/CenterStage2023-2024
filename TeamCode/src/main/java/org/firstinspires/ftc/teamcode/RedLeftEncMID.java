package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name = "redEncoderLeftMID", group = "auto")
public class RedLeftEncMID extends LinearOpMode {
    OpenCvInternalCamera phoneCam;
    OpenCV.RedCV pipeline;
    //matthew was here
    double treeAngleStraight = .51;

    double treeAngleUp = .71;
    double treeAngleDown = .14;

    @Override
    public void runOpMode() throws InterruptedException {
        EncoderAutoMethods movement = new EncoderAutoMethods(this);
        Robot robot = movement.robot;
        //movement.robot.intake.stowIntake();
        movement.robot.claw.setClawAngle(treeAngleUp);
        movement.robot.claw.clawDown();
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */

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
        waitForStart();




        //pos = OpenCV.RedCV.SkystonePosition.RIGHT;

        //movement.liftAnglePosition(100, 4000);
        if(pos == OpenCV.RedCV.SkystonePosition.RIGHT){
            movement.encoderDrive(-850, 9000);
            movement.encoderIMUTurn(-80, 7000);
            movement.encoderDrive(20, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(450);
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            robot.claw.clawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
            movement.encoderIMUTurn(80, 6500);
            movement.encoderDrive(-880, 3250);
            movement.encoderIMUTurn(-85, 6500);
            movement.encoderDrive(-3000, 6500);
            sleep(1000);
            //movement.encoderDrive(1815, 15000);
            //movement.encoderDrive(150, 500);
            //movement.robot.claw.setClawAngle(treeAngleStraight+.1);
            //movement.encoderIMUTurn(170, 10000);
            //movement.encoderDrive(-450, 3000);

        }
        else if(pos == OpenCV.RedCV.SkystonePosition.CENTER){
            movement.encoderDrive(-870, 7500);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            //movement.robot.claw.setClawAngle(treeAngleDown);
            //movement.robot.claw.setClawAngle(treeAngleDown + treeAngleStraight / 5);
            //sleep(2000);
            //movement.encoderIMUTurn(180,15000);
            movement.robot.claw.setClawAngle(treeAngleDown);


            sleep(2000);
            robot.claw.clawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
            //movement.encoderDrive(600, 5000);
            //movement.encoderIMUTurn(-85, 6500);
            //movement.encoderDrive(3000, 6500);
            //sleep(1000);
            //movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
            //movement.encoderIMUTurn(93, 5000);
            //movement.encoderDrive(2500, 12000);
            //movement.encoderIMUTurn(90, 5000);
            //movement.encoderDrive(100, 5000);
            //movement.encoderIMUTurn(-80, 10000);
            //movement.encoderDrive(-1100, 15500);
        }
        else{
            movement.encoderDrive(-955, 6500);
            movement.encoderIMUTurn(80, 200000);
            //movement.encoderDrive(-50, 500);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            //movement.robot.claw.setClawAngle(treeAngleDown);
            movement.robot.claw.setClawAngle(treeAngleDown - 0.05);
            sleep(2000);
            //movement.robot.claw.setClawAngle(treeAngleDown);
            //sleep(2000);
            robot.claw.clawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
            movement.encoderIMUTurn(-80, 8000);
            movement.encoderDrive(-650, 3000);
            movement.encoderIMUTurn(-90, 6500);
            movement.encoderDrive(-3000, 6500);

            sleep(1000);
            //movement.encoderDrive(80, 5000);
            //movement.encoderIMUTurn(-175, 5000);
            //movement.encoderDrive(-300,500);
            //movement.encoderDrive(2515, 15000);

            //movement.encoderIMUTurn(-170, 10000);
            //movement.encoderDrive(-1100, 15500);

        }

        //movement.liftAnglePosition(800, 3500);
        //movement.robot.claw.clawUp();
        //sleep(1000);

        //movement.robot.lift.setMotorsToGoUpOrDown(0); //uncomment when we have park
        //movement.encoderDrive(100, 3000);
        //sleep(2000);
        //movement.robot.claw.clawUp();
        //sleep(1000);
        //movement.robot.claw.setClawAngle(treeAngleStraight);
        //movement.robot.lift.setMotorsToGoUpOrDown(0);
        //sleep(2000);
        //movement.encoderDrive(100, 3000);





    }
}
