package org.firstinspires.ftc.teamcode.general;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.robocol.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;
import org.firstinspires.ftc.teamcode.subsystems.Pinpoint;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Dock;


public class BarnRobot {
    private static BarnRobot instance;
    public Telemetry telemetry;
    public GamepadEx gamepadEx1;

    public Hardware hardware;

    public Drivetrain drive;

    public Pinpoint pinpoint;
    public Limelight limelight;
    public Intake intake;
    public Dock dock;
    public Shooter shooter;

    public static boolean isRobotInitialized = false;

    public static synchronized BarnRobot getInstance() {
        if (instance == null) {
            instance = new BarnRobot();
            isRobotInitialized = true;
        }
        return instance;
    }

    public void init(OpMode opMode) {
        hardware = new Hardware(opMode.hardwareMap);
        gamepadEx1 = new GamepadEx(opMode.gamepad1);
        pinpoint = new Pinpoint();
        limelight = new Limelight();
        drive = new Drivetrain(opMode);
        intake = new Intake();
        dock = new Dock();
        shooter = new Shooter();
        telemetry = opMode.telemetry;
    }

    public void periodic() {
        telemetry.update();
        if (drive != null && drive.follower != null) { //TODO: remove when pedro tuned
            Constants.updateFollowerCoefficients(drive.follower);
            drive.follower.update();
        }
    }

    public boolean sticksUsed() {
        return Math.abs(gamepadEx1.getLeftX()) > 0.05 ||
                Math.abs(gamepadEx1.getLeftY()) > 0.05 ||
                Math.abs(gamepadEx1.getRightX()) > 0.05;
    }

    public InstantCommand rumble(){
        return new InstantCommand(() -> gamepad1.rumble(100));
    }
}
