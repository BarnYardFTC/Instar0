package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
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

    private Double targetAngleDiff = null;

    private static Pose passPose = new Pose(0,0,Math.toRadians(90));

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

     //PEDRO PATHING DRIVE SECTION
    private void driveFollower() {
        GamepadEx gp = BarnRobot.getInstance().gamepadEx1;
        double x = gp.getLeftY() * speedModifier;
        double y = -gp.getLeftX() * speedModifier;
        double stickTurn = -gp.getRightX() * speedModifier * 0.7;

        Limelight limelight = BarnRobot.getInstance().limelight;
        double turn = stickTurn;

        if (limelight != null && limelight.hasValidTarget()) {
            double tx = limelight.getTx();
            targetAngleDiff = -Math.toRadians(tx);
            if (Math.abs(stickTurn) < 0.1 && Math.abs(tx) > 0.5) {
                turn = Math.max(-0.25, Math.min(0.25, -tx * 0.012));
            }
        } else {
            targetAngleDiff = null;
        }

        if (!follower.getTeleopDrive() && (BarnRobot.getInstance().sticksUsed() || targetAngleDiff != null)) {
            follower.startTeleopDrive(true);
        }

        try {
            follower.setTeleOpDrive(x, y, turn, false);
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set teleop", e);
        }
    }

    private void driveFieldOriented() {
        GamepadEx gp = BarnRobot.getInstance().gamepadEx1;
        double x = gp.getLeftY() * speedModifier;
        double y = -gp.getLeftX() * speedModifier;
        double stickTurn = -gp.getRightX() * speedModifier * 0.7;

        Limelight limelight = BarnRobot.getInstance().limelight;
        double turn = stickTurn;

        if (limelight != null && limelight.hasValidTarget()) {
            double tx = limelight.getTx();
            targetAngleDiff = -Math.toRadians(tx);
            if (Math.abs(stickTurn) < 0.1 && Math.abs(tx) > 0.5) {
                turn = Math.max(-0.25, Math.min(0.25, -tx * 0.012));
            }
        } else {
            targetAngleDiff = null;
        }

        if (!follower.getTeleopDrive() && (BarnRobot.getInstance().sticksUsed() || targetAngleDiff != null)) {
            follower.startTeleopDrive(true);
        }

        try {
            follower.setTeleOpDrive(x, y, turn, false);
        } catch (Exception e) {
            BarnRobot.getInstance().telemetry.addData("failed to set field oriented", e);
        }
    }

    public static void setPassPose(Pose pose){
        passPose = pose;
    }

    public static Pose getPassPose() {
        return passPose;
    }

    public Command goToCommand(Pose pose) {
        return new FollowPathCommand(
                follower,
                follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), pose))
                        .setLinearHeadingInterpolation(follower.getHeading(), pose.getHeading())
                        .build()
        );
    }

    public RunCommand driveFollowerCommand() {
        return new RunCommand(this::driveFollower, this);
    }

    public RunCommand driveFieldOrientedCommand() {
        return new RunCommand(this::driveFieldOriented, this);
    }

    public Command setTargetAngleDifferenceCommand(double angleDiff) {
        return new InstantCommand(() -> targetAngleDiff = angleDiff, this);
    }

    public Command clearTargetAngleDifferenceCommand() {
        return new InstantCommand(() -> targetAngleDiff = null, this);
    }

    public Command updateLimelightDifferenceCommand() {
        return new RunCommand(() -> {
            Limelight limelight = BarnRobot.getInstance().limelight;
            if (limelight != null && limelight.hasValidTarget()) {
                targetAngleDiff = -Math.toRadians(limelight.getTx());
            } else {
                targetAngleDiff = null;
            }
        }, this);
    }

    public double getDifference(){
        return targetAngleDiff != null ? targetAngleDiff : 0.0;
    }

    public Command setSlowModeCommand() {
        return new InstantCommand(() -> speedModifier = SLOW_SPEED);
    }

    public Command setFastModeCommand() {
        return new InstantCommand(() -> speedModifier = FAST_SPEED);
    }
}
