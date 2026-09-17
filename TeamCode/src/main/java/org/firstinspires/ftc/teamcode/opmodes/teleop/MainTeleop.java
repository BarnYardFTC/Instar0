package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.pedropathing.geometry.CoordinateSystem;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;

@TeleOp(name = "Main Teleop", group = "main")
public class MainTeleop extends CommandOpMode {
    @Override
    public void initialize() {
        Drivetrain.setPassPose(new Pose(9.06,10.63,Math.toRadians(90)));
        TeleopTemplate.apply(this);
    }

    @Override
    public void run() {
        super.run();
        TeleopTemplate.periodic();
    }

    @Override
    public void end() {
        super.end();
        Drivetrain.setPassPose(BarnRobot.getInstance().drive.follower.getPose());
        TeleopTemplate.end();
    }
}
