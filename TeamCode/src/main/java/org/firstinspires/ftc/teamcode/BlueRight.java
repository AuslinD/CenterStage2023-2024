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
    double treeAngleDown = .11;

    @Override
    public void runOpMode() throws InterruptedException {
        EncoderAutoMethods movement = new EncoderAutoMethods(this);
        Robot robot = new Robot(this);
        //robot.intake.stowIntake();
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
        waitForStart();
        if(pos == OpenCV.BlueCV.SkystonePosition.RIGHT){
            movement.encoderDrive(-800, 6500);
            movement.encoderIMUTurn(-60, 3000);
            robot.lift.setMotorsToGoUpOrDown(300);
            robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            robot.claw.autoClawHalf();
            sleep(2000);
            movement.encoderDrive(40, 2000);
            movement.encoderIMUTurn(60, 8000);
            movement.encoderDrive(700, 5000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.encoderIMUTurn(-88, 6500);
            movement.encoderDrive(3000, 6500);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            sleep(2000);

        }
        else if(pos == OpenCV.BlueCV.SkystonePosition.CENTER){
            robot.lift.setMotorsToGoUpOrDown(300);
            movement.robot.claw.setClawAngle(treeAngleDown);
            movement.encoderDrive(-875, 5000);

            sleep(2000);
            movement.robot.claw.autoClawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight+.1);
            movement.encoderDrive(875, 5000);
            movement.encoderIMUTurn(-90, 6500);
            movement.encoderDrive(3000, 6500);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            sleep(1000);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
        }
        else{
            movement.encoderDrive(-700, 10000);
            movement.encoderIMUTurn(70,5000);
            movement.robot.lift.setMotorsToGoUpOrDown(350);
            movement.robot.claw.setClawAngle(treeAngleDown + 0.05);
            sleep(1000);
            movement.robot.claw.autoClawHalf();
            movement.encoderIMUTurn(-70, 8000);
            movement.encoderDrive(700, 3500);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.encoderIMUTurn(-90, 6500);
            movement.encoderDrive(3000, 6500);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.robot.lift.setMotorsToGoUpOrDown(0);


            //matthew was here
        }
        //sleep(2000);
        //movement.robot.claw.clawUp();
        //movement.robot.claw.setClawAngle(treeAngleStraight);
        //sleep(3000);

        movement.robot.lift.setMotorsToGoUpOrDown(0);
        sleep(2000);
        //movement.encoderDrive(100, 3000);
    }
}
