package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BlueTemplate {
    static final Pose START_POSE = new Pose(8.83106169296987,132.80850968436155, Math.toRadians(90));
    static final Pose COLLECT_POSE = new Pose(8.83106169296987, 15.812051649928243, Math.toRadians(90));
    static final Pose SHOOT_POSE = new Pose(47.291965566714495,108.44404591104735,Math.toRadians(-90));

    static PathChain goCollect, goShoot;

    static void buildPathChains(Follower follower) {
        goCollect = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, COLLECT_POSE))
                .setLinearHeadingInterpolation(START_POSE.getHeading(), COLLECT_POSE.getHeading())
                .build();

        goShoot = follower.pathBuilder()
                .addPath(new BezierLine(COLLECT_POSE, SHOOT_POSE))
                .setLinearHeadingInterpolation(COLLECT_POSE.getHeading(), SHOOT_POSE.getHeading())
                .build();
    }
}
