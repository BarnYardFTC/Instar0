package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Hardware;

import java.util.ArrayList;

@TeleOp(name = "ConfigTest", group = "test")
public class ConfigTest extends CommandOpMode {
    @Override
    public void initialize() {
        Hardware hardware = new Hardware(this.hardwareMap, true);
    }

    @Override
    public void run() {
    }
}
