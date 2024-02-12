package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
@Config
public class Macro {
    static OpMode opMode;
    public static Timer timer = new Timer();
    private static Robot robot;
    private static Claw outtake;
    private static Lift lift;
    public static boolean macro_run = false;
    public interface MacroCompletionListener {
        void onMacroCompleted();
    }
    public Macro(Robot robot) {
        Macro.robot = robot;
        lift = robot.lift;
    }
    public static double[] macro_timing = {0, 0.5, 1, 1.5, 2};
    public static void liftAngleToPos(int desiredPosition, MacroCompletionListener listener) {
        ElapsedTime liftAngleToPosTimer = new ElapsedTime();
        liftAngleToPosTimer.reset();
        double tolerance = 100;
        while (!(Math.abs(lift.rotateRight.getCurrentPosition() - desiredPosition) <= tolerance)) {
            int currentPos = lift.rotateRight.getCurrentPosition();
            if (Math.abs(currentPos - desiredPosition) <= tolerance) {
                break;
            }
            if (desiredPosition > currentPos) {
                lift.rotateRight.setPower(0.5);
            } else if (desiredPosition < currentPos) {
                lift.rotateRight.setPower(-0.5);
            }
        }
        lift.rotateRight.setPower(0);
        listener.onMacroCompleted();
    }
    public static void macro_run(OpMode opmode) {
        timer.reset();
        robot.claw.setClawPosition(0);
        robot.claw.setClawAngle(0.1);
        liftAngleToPos(1000, new MacroCompletionListener() {
            @Override
            public void onMacroCompleted() {
                startMacro();
            }
        });
    }
    private static void startMacro() {
        liftAngleToPos(73, new MacroCompletionListener() {
            @Override
            public void onMacroCompleted() {
                robot.claw.setClawPosition(0.3);
                timer.reset();
                liftAngleToPos(100, new MacroCompletionListener() {
                    @Override
                    public void onMacroCompleted() {
                        robot.lift.liftLeft.setTargetPosition(85);
                        robot.lift.liftRight.setTargetPosition(85);
                        liftAngleToPos(100, new MacroCompletionListener() {
                            @Override
                            public void onMacroCompleted() {
                                robot.claw.setClawAngle(0.115);
                                robot.claw.setClawPosition(0.00);
                                timer.reset();
                                liftAngleToPos(60, new MacroCompletionListener() {
                                    @Override
                                    public void onMacroCompleted() {
                                        robot.claw.setClawPosition(0.8);
                                        timer.reset();
                                        liftAngleToPos(800, new MacroCompletionListener() {
                                            @Override
                                            public void onMacroCompleted() {
                                                robot.claw.setClawAngle(0.41);
                                                macro_run = false;
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}