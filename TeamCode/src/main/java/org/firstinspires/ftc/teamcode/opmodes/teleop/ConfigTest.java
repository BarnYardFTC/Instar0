package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import java.util.ArrayList;

@TeleOp(name = "ConfigTest", group = "test")
public class ConfigTest extends CommandOpMode {
    private static final BarnRobot robot = BarnRobot.getInstance();

    private ArrayList<String> binds = new ArrayList<>();

    @Override
    public void initialize() {
        robot.init(this);
        robot.drive.setDefaultCommand(robot.drive.driveCommand());
        //TeleopTemplate.toggleBind(GamepadKeys.Button.B, "Intake", robot.intake.enableCommand(), robot.intake.disableCommand());
        TeleopTemplate.toggleBind(GamepadKeys.Button.DPAD_DOWN, "Guard up", robot.transfer.setGuardPositionCommand(robot.transfer.getGuardPos()-0.1), robot.transfer.setGuardPositionCommand(robot.transfer.getGuardPos()-0.1));
        TeleopTemplate.toggleBind(GamepadKeys.Button.DPAD_UP, "Guard", robot.transfer.setGuardPositionCommand(robot.transfer.getGuardPos()+0.1), robot.transfer.setGuardPositionCommand(robot.transfer.getGuardPos()+0.1));


    }

    @Override
    public void run() {
        binds.forEach(robot.telemetry::addLine);
        robot.periodic();
        super.run();
        robot.telemetry.addData("Servo pos: ", robot.transfer.guard.getPosition());
    }
}
