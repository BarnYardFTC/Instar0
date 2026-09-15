package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.opmodes.auto.BlueTemplate.*;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.general.Constants;

@Autonomous(name = "Blue 8", group = "Blue Testing")
public class Blue8 extends CommandOpMode {
    BarnRobot robot;
    Follower follower;

    @Override
    public void initialize() {
        robot = BarnRobot.getInstance();
        robot.init(this);
        follower = Constants.createFollower(hardwareMap);
        BlueTemplate.buildPathChains(follower);
        follower.setStartingPose(START_POSE);
        robot.shooter.operateShooter();
        robot.intake.enableCommand();
        schedule(autoRoutine());
    }

    @Override
    public void run() {
        robot.periodic();
        follower.update();
        super.run();
    }

    @Override
    public void end() {
        super.end();
        robot.drive.setPassPose(robot.drive.follower.get);
    }

    private Command autoRoutine() {
        return new SequentialCommandGroup(
                new FollowPathCommand(follower, goCollect),
                new FollowPathCommand(follower, goShoot)
        );
    }
}
