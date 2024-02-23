package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name = "blueEncoderLeft", group = "auto")
public class BlueLeft extends LinearOpMode {
    OpenCvInternalCamera phoneCam;
    OpenCV.BlueCV pipeline;

    double treeAngleStraight = .51;

    double treeAngleUp = .71;
    double treeAngleDown = .13;

    @Override
    public void runOpMode() throws InterruptedException {
        EncoderAutoMethods movement = new EncoderAutoMethods(this);
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
        pipeline = new OpenCV.BlueCV();
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
        OpenCV.BlueCV.SkystonePosition pos = null;
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
        if(pos == OpenCV.BlueCV.SkystonePosition.RIGHT){
            movement.PIDdriveCorrection(-980, 6500);
            movement.PIDTurn(-90, 200000);
            movement.encoderDrive(25, 2000);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            movement.robot.claw.autoClawHalf();
            sleep(2000);
            movement.encoderDrive(200, 2000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.PIDTurn(168.5, 10000);
            movement.PIDdriveCorrection(-1180, 9500);

        }
        else if(pos == OpenCV.BlueCV.SkystonePosition.CENTER){
            movement.PIDdriveCorrection(-875, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            movement.robot.claw.autoClawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight+.1);
            movement.robot.claw.setClawAngle(treeAngleDown + treeAngleStraight / 5);
            sleep(100);
            movement.robot.claw.setClawAngle(treeAngleDown);
            movement.encoderDrive(100, 5000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.PIDTurn(90, 10000);
            movement.PIDdriveCorrection(-1300, 15500);
        }
        else{
            movement.PIDdriveCorrection(-980, 10500);
            movement.PIDTurn(-90, 200000);
            movement.PIDdriveCorrection(725, 4000);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            movement.robot.claw.autoClawHalf();
            sleep(2000);
            movement.encoderDrive(100,1000);
            //movement.encoderDrive(50, 500);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.PIDTurn(-168, 10000);
            movement.PIDdriveCorrection(-520, 3000);

        }

        //movement.liftAnglePosition(800, 3500);
        movement.robot.claw.clawUp();
        sleep(1000);

        movement.robot.lift.setMotorsToGoUpOrDown(0);
        movement.encoderDrive(100, 3000);
            if(pos == OpenCV.BlueCV.SkystonePosition.LEFT)
                movement.PIDTurn(-50, 2000);
        movement.strafe(-.5, 5000);
            if(pos == OpenCV.BlueCV.SkystonePosition.LEFT)
                movement.encoderDrive(-300, 2000);





    }
}
