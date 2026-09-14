package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

@TeleOp(name = "Limelight Test", group = "test")
public class LimelightTest extends CommandOpMode {
    BarnRobot robot;
    @Override
    public void initialize() {
        robot = BarnRobot.getInstance();
        TeleopTemplate.apply(this);
        robot.limelight.start();
        TeleopTemplate.toggleBind(GamepadKeys.Button.A, "Update Angle Difference", robot.drive.updateLimelightDifferenceCommand(), robot.drive.clearTargetAngleDifferenceCommand());
    }

    @Override
    public void run() {
        super.run();
        TeleopTemplate.periodic();
        robot.limelight.periodic();
        robot.telemetry.addData("ll sees? ", robot.limelight.hasValidTarget());
        robot.telemetry.addData("tx", robot.limelight.getTx());
        robot.telemetry.addData("ty", robot.drive.getDifference());
    }

    @Override
    public void end() {
        super.end();
        TeleopTemplate.end();
    }
}
