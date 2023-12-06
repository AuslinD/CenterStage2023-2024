package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "redEncoderActual?", group = "auto")
public class RedRight extends LinearOpMode {
    OpenCvInternalCamera phoneCam;
    OpenCV.RedCV pipeline;

    @Override
    public void runOpMode() throws InterruptedException {
        EncoderAutoMethods movement = new EncoderAutoMethods(this);
        movement.robot.intake.stowIntake();
        movement.robot.claw.setClawAngle(.9);
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
            movement.encoderDrive(-990, 11500);
            movement.encoderIMUTurn(85, 5000);
            movement.encoderDrive(775, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(350);
            movement.robot.claw.setClawAngle(.35);
            movement.robot.claw.setClawPosition(.55);
            movement.encoderIMUTurn(170, 10000);
        }
        else if(pos == OpenCV.RedCV.SkystonePosition.CENTER){
            movement.encoderDrive(-775, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(350);
            movement.robot.claw.setClawAngle(.35);
            movement.robot.claw.setClawPosition(.55);
        }
        else{
            movement.encoderDrive(-930, 6500);
            movement.encoderIMUTurn(90, 200000);
            movement.robot.lift.setMotorsToGoUpOrDown(350);
            movement.robot.claw.setClawAngle(.35);
            movement.robot.claw.setClawPosition(.55);
            
        }

        sleep(203034);
        movement.encoderDrive(-800, 5000);


    }
}
