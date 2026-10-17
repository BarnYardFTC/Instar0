package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;

@Autonomous(name = "Ami Auto 4+4", group = "Autonomous")
public class AmiAutonomous4Plus4 extends CommandOpMode {
    
    BarnRobot robot;
    private Follower follower;

    // Define Poses (using Math.toRadians for heading)
    private final Pose start = new Pose(8.2086, 15.9652, Math.toRadians(90));
    private final Pose shootpos1 = new Pose(57.8742, 17.2583, Math.toRadians(180));
    private final Pose collectpos = new Pose(12.2781, 10.4603, Math.toRadians(180));
    private final Pose shoottocollectcontrol = new Pose(29.7525, 8.7559, Math.toRadians(0));
    private final Pose shootpos2 = new Pose(57.6424, 114.0911, Math.toRadians(90));
    private final Pose collecttoshootcontrol = new Pose(20.9007, 122.505, Math.toRadians(0));

    @Override
    public void initialize() {
        robot = BarnRobot.getInstance();
        robot.init(this);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(start);

        // Schedule subsystems to run in the background
        schedule(robot.shooter.operateShooter());
        schedule(robot.intake.enableCommand());

        // Schedule the main path sequence
        schedule(autoRoutine());
    }



    @Override
    public void run() {
        robot.periodic();
        follower.update();
        super.run();
        
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    public Command autoRoutine() {
        return new SequentialCommandGroup(
                new FollowPathCommand(follower, path1()),
                new WaitCommand(3000), // Wait 3 second for the shooter
                new FollowPathCommand(follower, path2()),
                new FollowPathCommand(follower, path3()),
                new WaitCommand(3000)  // Wait 3 second for the shooter
        );
    }

    public PathChain path1() {
        return follower.pathBuilder()
                .addPath(new BezierLine(start, shootpos1))
                .setConstantHeadingInterpolation(shootpos1.getHeading())
                .build();
    }

    public PathChain path2() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(shootpos1, shoottocollectcontrol, collectpos))
                .setConstantHeadingInterpolation(collectpos.getHeading())
                .build();
    }

    public PathChain path3() {
        return follower.pathBuilder()
                .addPath(new BezierCurve(collectpos, collecttoshootcontrol, shootpos2))
                .setConstantHeadingInterpolation(shootpos2.getHeading())
                .build();
    }
}
