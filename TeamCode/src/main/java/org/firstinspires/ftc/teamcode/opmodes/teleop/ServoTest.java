package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp(name = "Servo Test", group = "test")
public class ServoTest extends CommandOpMode {
    private Servo goyServo;
    private GamepadEx gamepadEx;

    @Override
    public void initialize() {
        goyServo = hardwareMap.get(Servo.class, "goyServo");
        gamepadEx = new GamepadEx(new Gamepad());

        gamepadEx.getGamepadButton(GamepadKeys.Button.Y).toggleWhenPressed(one(), zero());
    }
    @Override
    public void run() {
        super.run();
        this.telemetry.addData("servo pos: ", goyServo.getPosition());
    }
    Command zero() {
        return new InstantCommand(() -> goyServo.setPosition(0));
    }

    Command one() {
        return new InstantCommand(() -> goyServo.setPosition(1));
    }
}
