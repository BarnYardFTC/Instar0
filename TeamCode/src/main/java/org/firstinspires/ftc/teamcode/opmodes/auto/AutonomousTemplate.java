package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class AutonomousTemplate {
    final static double FIELD_SIZE = 141.5;

    enum Alliance {
        RED,
        BLUE
    }

    static final Pose START_POSE = new Pose(8.83106169296987,132.80850968436155, Math.toRadians(90));
    static final Pose COLLECT_POSE = new Pose(8.83106169296987, 15.812051649928243, Math.toRadians(90));
    static final Pose SHOOT_POSE = new Pose(47.291965566714495,108.44404591104735,Math.toRadians(-90));

    static PathChain goCollect, goShoot;

    static Pose transform(Pose pose, Alliance alliance) {
        if (alliance == Alliance.BLUE) {
            return pose;
        }

        return new Pose(
                FIELD_SIZE - pose.getX(),
                FIELD_SIZE - pose.getY(),
                pose.getHeading() + Math.PI
        );
    }

    static void buildPathChains(Follower follower, Alliance alliance) {
        Pose start = transform(START_POSE, alliance);
        Pose collect = transform(COLLECT_POSE, alliance);
        Pose shoot = transform(SHOOT_POSE, alliance);

        goCollect = follower.pathBuilder()
                .addPath(new BezierLine(start, collect))
                .setLinearHeadingInterpolation(start.getHeading(), collect.getHeading())
                .build();

        goShoot = follower.pathBuilder()
                .addPath(new BezierLine(collect, shoot))
                .setLinearHeadingInterpolation(collect.getHeading(), shoot.getHeading())
                .build();
    }
}
