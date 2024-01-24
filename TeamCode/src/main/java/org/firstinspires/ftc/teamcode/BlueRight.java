package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name = "blueEncoderRight", group = "auto")
public class BlueRight extends LinearOpMode {
    OpenCvInternalCamera phoneCam;
    OpenCV.BlueCV pipeline;

    double treeAngleStraight = .51;

    double treeAngleUp = .71;
    double treeAngleDown = .21;

    @Override
    public void runOpMode() throws InterruptedException {
        EncoderAutoMethods movement = new EncoderAutoMethods(this);
        Robot robot = new Robot(this);
        robot.intake.stowIntake();
        robot.claw.setClawAngle(treeAngleUp);
        robot.claw.clawDown();
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */

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
        waitForStart();
        if(pos == OpenCV.BlueCV.SkystonePosition.RIGHT){
            movement.encoderDrive(-930, 6500);
            movement.encoderIMUTurn(-90, 200000);
            movement.encoderDrive(100, 2000);
            robot.lift.setMotorsToGoUpOrDown(500);
            robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            robot.claw.clawHalf();
            sleep(2000);
            //movement.encoderDrive(200, 2000);
            //movement.robot.claw.setClawAngle(treeAngleStraight+.1);
            //movement.encoderIMUTurn(-180, 10000);
            //movement.encoderDrive(-2290, 15500);

        }
        else if(pos == OpenCV.BlueCV.SkystonePosition.CENTER){
            movement.encoderDrive(-875, 5000);
            robot.lift.setMotorsToGoUpOrDown(500);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            movement.robot.claw.clawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight+.1);
            //movement.encoderDrive(100, 5000);
            //movement.encoderIMUTurn(90, 10000);
            //movement.encoderDrive(-2290, 15500);
        }
        else{
            movement.encoderDrive(-870, 15500);
            movement.encoderIMUTurn(-90, 5000);
            movement.encoderDrive(870, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(450);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            movement.robot.claw.clawHalf();
            //sleep(2000);
            //movement.encoderDrive(100,1000);
            //movement.encoderDrive(50, 500);
            //movement.robot.claw.setClawAngle(treeAngleStraight+.1);
            //movement.encoderIMUTurn(205, 10000);
            //movement.encoderDrive(-2150, 3000);

        }
        sleep(2000);
        //movement.robot.claw.clawUp();
        sleep(1000);

        movement.robot.lift.setMotorsToGoUpOrDown(0);
        sleep(2000);
        //movement.encoderDrive(100, 3000);
    }
}
