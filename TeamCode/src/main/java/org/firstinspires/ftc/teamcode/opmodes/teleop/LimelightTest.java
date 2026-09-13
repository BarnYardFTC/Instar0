package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

@TeleOp(name = "Limelight Test", group = "test")
public class LimelightTest extends CommandOpMode {
    @Override
    public void initialize() {
        BarnRobot robot = BarnRobot.getInstance();
        TeleopTemplate.apply(this);
        robot.limelight.start();
        TeleopTemplate.toggleBind(GamepadKeys.Button.A, "Update Angle Difference", robot.drive.updateLimelightDifferenceCommand(), robot.drive.clearTargetAngleDifferenceCommand());
    }

    @Override
    public void run() {
        super.run();
        TeleopTemplate.periodic();
        BarnRobot.getInstance().limelight.periodic();
    }

    @Override
    public void end() {
        super.end();
        TeleopTemplate.end();
    }
}
