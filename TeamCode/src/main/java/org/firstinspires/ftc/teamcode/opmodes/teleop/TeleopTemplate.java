package org.firstinspires.ftc.teamcode.opmodes.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.photon.PhotonCore;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.util.ArrayList;

public class TeleopTemplate {

    private static ArrayList<String> binds = new ArrayList<>();
    private static final BarnRobot robot = BarnRobot.getInstance();

    public static void apply(OpMode opMode) {
        PhotonCore.enable();
        robot.init(opMode);
        robot.shooter.setDefaultCommand(robot.shooter.operateShooter());
        robot.intake.setDefaultCommand(robot.intake.enableCommand());
        robot.drive.setDefaultCommand(robot.drive.driveFieldOrientedCommand());

        // Binds
        toggleBind(GamepadKeys.Button.B, "Change speed", robot.drive.setSlowModeCommand(),  robot.drive.setFastModeCommand());
        toggleBind(GamepadKeys.Button.A, "Dock", robot.dock.setPassCommand(), robot.dock.setCollectCommand());
        toggleBind(GamepadKeys.Button.Y, "Shooter", robot.shooter.turnOff(), robot.shooter.operateShooter());
        toggleBind(GamepadKeys.Button.X, "Intake", robot.intake.disableCommand(), robot.intake.enableCommand());
    }

    public static void toggleBind(GamepadKeys.Button button, String description, Command command1, Command command2) {
        robot.gamepadEx1.getGamepadButton(button)
                .toggleWhenPressed(
                        command1,
                        command2
                );
        binds.add(button.toString() + ": " + description);
    }

    public static void periodic(){
        binds.forEach(robot.telemetry::addLine);
        robot.periodic();
    }

    public static void end() {
        binds.clear();
    }
}




