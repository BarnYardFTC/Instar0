package org.firstinspires.ftc.teamcode.opmodes.teleop;


import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.photon.PhotonCore;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

import java.util.ArrayList;

public class TeleopTemplate {

    private static ArrayList<String> binds = new ArrayList<>();
    private static final BarnRobot robot = BarnRobot.getInstance();

    public enum SONY_BUTTONS {
        TRIANGLE(GamepadKeys.Button.Y),
        CIRCLE(GamepadKeys.Button.B),
        CROSS(GamepadKeys.Button.A),
        SQUARE(GamepadKeys.Button.X);

        public final GamepadKeys.Button key;

        SONY_BUTTONS(GamepadKeys.Button key) {
            this.key = key;
        }
    }
    public static void apply(OpMode opMode) {
        PhotonCore.enable();
        robot.init(opMode);

        // Default command
        robot.shooter.setDefaultCommand(robot.shooter.operateShooter());
        robot.intake.setDefaultCommand(robot.intake.enableCommand());
        robot.drive.setDefaultCommand(robot.drive.driveFieldOrientedCommand());

        // Binds
        toggleBind(GamepadKeys.Button.B, "Change speed", robot.drive.setSlowModeCommand(),  robot.drive.setFastModeCommand());
        toggleBind(GamepadKeys.Button.Y, "Shooter", robot.shooter.turnOff(), robot.shooter.operateShooter());
        toggleBind(GamepadKeys.Button.X, "Intake", robot.intake.disableCommand(), robot.intake.enableCommand());
    }

    public static void toggleBind(GamepadKeys.Button button, String description, Command command1, Command command2) {
        robot.gamepadEx1.getGamepadButton(button)
                .toggleWhenPressed(
                        command1,
                        command2
                );

        binds.add(getPreferredKey(button) + ": " + description);
    }

    public static void triggerBind(GamepadKeys.Trigger trigger, String description, Command command, Command offCommand) {
        new Trigger(() -> robot.gamepadEx1.getTrigger(trigger) > 0.5)
                .whenActive(
                        command
                )
                .whenInactive(
                        offCommand
                );
        binds.add(trigger.toString() + ": " + description);
    }

    public static void periodic(){
        binds.forEach(robot.telemetry::addLine);
        robot.telemetry.addData("isShooterReady?", robot.shooter.isReady());
        robot.telemetry.addData("ShooterSpeed: ", robot.shooter.getRPM());
        robot.periodic();
    }

    public static void end() {
        binds.clear();
    }

    private static String getPreferredKey(GamepadKeys.Button button) {
        switch (button) {
            case A:
                return SONY_BUTTONS.CROSS.toString();
            case B:
                return SONY_BUTTONS.CIRCLE.toString();
            case X:
                return SONY_BUTTONS.SQUARE.toString();
            case Y:
                return SONY_BUTTONS.TRIANGLE.toString();
            default:
                return button.toString();
        }
    }
}




