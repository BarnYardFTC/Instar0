package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.math.MathFunctions;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;

public class Drivetrain extends SubsystemBase {
    private final DcMotor leftFront;
    private final DcMotor rightFront;
    private final DcMotor leftBack;
    private final DcMotor rightBack;

    public Follower follower;

    private double speedModifier;
    private final double SLOW_SPEED = 0.3;
    private final double FAST_SPEED = 1.0;

    public Drivetrain(OpMode opMode) {
        speedModifier = FAST_SPEED;
        leftFront = BarnRobot.getInstance().hardware.leftFrontDrivetrain;
        rightFront = BarnRobot.getInstance().hardware.rightFrontDrivetrain;
        leftBack = BarnRobot.getInstance().hardware.leftBackDrivetrain;
        rightBack = BarnRobot.getInstance().hardware.rightBackDrivetrain;

        initMotor(DcMotorSimple.Direction.REVERSE, leftFront);
        initMotor(DcMotorSimple.Direction.FORWARD, rightFront);
        initMotor(DcMotorSimple.Direction.REVERSE, leftBack);
        initMotor(DcMotorSimple.Direction.FORWARD, rightBack);

        follower = Constants.createFollower(opMode.hardwareMap);
    }

    private void initMotor(DcMotorSimple.Direction direction, DcMotor motor) {
        motor.setDirection(direction);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void drive() {
        GamepadEx gamepadEx = BarnRobot.getInstance().gamepadEx1;
        double lf_pw = gamepadEx.getLeftY() - gamepadEx.getLeftX() + gamepadEx.getRightX();
        double rf_pw = gamepadEx.getLeftY() + gamepadEx.getLeftX() - gamepadEx.getRightX();
        double lb_pw = gamepadEx.getLeftY() + gamepadEx.getLeftX() + gamepadEx.getRightX();
        double rb_pw = gamepadEx.getLeftY() - gamepadEx.getLeftX() - gamepadEx.getRightX();
        leftFront.setPower(lf_pw * speedModifier);
        rightFront.setPower(rf_pw * speedModifier);
        leftBack.setPower(lb_pw * speedModifier);
        rightBack.setPower(rb_pw * speedModifier);
    }

    public RunCommand driveCommand() {
        return new RunCommand(this::drive, this);
    }

    private void driveFieldOriented() {
        GamepadEx gp = BarnRobot.getInstance().gamepadEx1;
        double x = gp.getLeftY() * speedModifier;
        double y = -gp.getLeftX() * speedModifier;
        double turn = -gp.getRightX() * speedModifier * 0.7;

        if (!follower.getTeleopDrive() && BarnRobot.getInstance().sticksUsed()) {
            follower.startTeleopDrive(true);
        }

        try {
            follower.setTeleOpDrive(x, y, turn, false);
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set field oriented teleop", e);
        }
    }

    public RunCommand driveFieldOrientedCommand() {
        return new RunCommand(this::driveFieldOriented, this);
    }

    public Command setSlowModeCommand() {
        return new InstantCommand(() -> speedModifier = SLOW_SPEED, this);
    }

    public Command setFastModeCommand() {
        return new InstantCommand(() -> speedModifier = FAST_SPEED, this);
    }
}