package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.seattlesolvers.solverslib.command.CommandOpMode;

@TeleOp(name = "RumbletestTelep", group = "main")
public class RumbleTest extends CommandOpMode {
    // this is the setting up of gamepad rumble
    Gamepad.RumbleEffect.Builder rumble = new Gamepad.RumbleEffect.Builder();

    @Override
    public void initialize() {
        TeleopTemplate.apply(this);
        // this is the setting up the rumble
        rumble.addStep(0.5,0.5,2000);
        //this is running the rumble
        gamepad1.runRumbleEffect(rumble.build());
    }




}



