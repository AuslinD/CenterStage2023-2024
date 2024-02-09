package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "redWIP", group = "auto")
public class RedLeftDelivery extends LinearOpMode {
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
        movement.robot.intake.stowIntake();
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







        if(pos == OpenCV.RedCV.SkystonePosition.RIGHT){
            movement.encoderDrive(-900, 10500);
            movement.encoderIMUTurn(-80, 5000);
            movement.encoderDrive(85, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(450);

            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            robot.claw.clawHalf();
            sleep(2000);
            //first delivery
            movement.robot.claw.setClawAngle(treeAngleStraight);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
            movement.encoderIMUTurn(80, 6500);
            movement.encoderDrive(880, 3250);
            movement.encoderIMUTurn(-85, 6500);
            movement.encoderDrive(-3000, 6500);
            sleep(1000);


        }
        else if(pos == OpenCV.RedCV.SkystonePosition.CENTER){
            movement.encoderDrive(-895, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            movement.robot.claw.setClawAngle(treeAngleDown);


            sleep(2000);
            //first delivery
            robot.claw.clawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
            movement.encoderDrive(900, 5000);
            movement.encoderIMUTurn(-85, 6500);
            movement.encoderDrive(-3000, 6500);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
        }
        else{
            movement.encoderDrive(-955, 6500);
            movement.encoderIMUTurn(80, 200000);

            movement.robot.lift.setMotorsToGoUpOrDown(500);

            movement.robot.claw.setClawAngle(treeAngleDown + treeAngleStraight / 5);
            sleep(1000);
            //first delivery
            robot.claw.clawHalf();
            sleep(1000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);
            movement.encoderIMUTurn(-80, 8000);
            movement.encoderDrive(850, 3000);
            movement.encoderIMUTurn(-85, 6500);
            movement.encoderDrive(-3000, 6500);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);


        }






    }
}
