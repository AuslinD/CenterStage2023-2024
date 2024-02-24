package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name = "redEncoderRight", group = "auto")
public class RedRight extends LinearOpMode {
    OpenCvInternalCamera phoneCam;
    OpenCV.RedCV pipeline;

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

        
        //matthew was here

        //pos = OpenCV.RedCV.SkystonePosition.RIGHT;

        //movement.liftAnglePosition(100, 4000);
        if(pos == OpenCV.RedCV.SkystonePosition.RIGHT){
            movement.PIDdriveCorrection(-980, 15500);
            movement.PIDTurn(90, 5000);
            movement.PIDdriveCorrection(800, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(450);
            movement.robot.claw.setClawAngle(treeAngleDown);
            movement.robot.claw.setClawAngle(treeAngleDown + treeAngleStraight / 5);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            movement.robot.claw.autoClawHalf();
            sleep(2000);
            movement.encoderDrive(50, 500);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            movement.PIDTurn(178.5, 10000);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            movement.PIDdriveCorrection(-745, 3000);

        }
        else if(pos == OpenCV.RedCV.SkystonePosition.CENTER){
            movement.robot.lift.setMotorsToGoUpOrDown(500);

            movement.robot.claw.setClawAngle(treeAngleDown);
            movement.robot.claw.setClawAngle(treeAngleDown + treeAngleStraight / 5);
            movement.robot.claw.setClawAngle(treeAngleDown);
            movement.PIDdriveCorrection(-875, 5000);



            sleep(2000);
            movement.robot.claw.autoClawHalf();
            sleep(2000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.encoderDrive(100, 5000);
            movement.PIDTurn(-85.5, 10000);
            movement.PIDdriveCorrection(-1275, 5500);
        }
        else{
            movement.PIDdriveCorrection(-980, 5500);
            movement.PIDTurn(90, 10000);
            //movement.encoderDrive(-50, 5000);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            movement.robot.claw.setClawAngle(treeAngleDown);
            //movement.robot.claw.setClawAngle(treeAngleDown + treeAngleStraight / 5);
            movement.robot.claw.setClawAngle(treeAngleDown);
            sleep(2000);
            movement.robot.claw.autoClawHalf();
            sleep(2000);
            movement.encoderDrive(100, 5000);
            movement.robot.claw.setClawAngle(treeAngleStraight);
            movement.robot.lift.setMotorsToGoUpOrDown(0);
            movement.PIDTurn(-165, 13000);
            movement.robot.lift.setMotorsToGoUpOrDown(500);
            movement.PIDdriveCorrection(-1200, 5500);
        }

        //movement.liftAnglePosition(800, 3500);
        //sleep(2000);
        movement.robot.claw.clawUp();
        sleep(2500);

        movement.robot.lift.setMotorsToGoUpOrDown(0);
        movement.PIDdriveCorrection(100, 1500);
        movement.strafe(.5, 5000);





    }
}
