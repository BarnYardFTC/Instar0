package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class BlueTemplate {
    static final Pose START_POSE = new Pose(0,0,0);
    static final Pose SHOOT_POSE = new Pose(1,1,1);

    static PathChain goShoot;

    static void buildPathChains(Follower follower) {
        goShoot = follower.pathBuilder()
                .addPath(new BezierLine(START_POSE, SHOOT_POSE))
                .setLinearHeadingInterpolation(START_POSE.getHeading(), SHOOT_POSE.getHeading())
                .build();
    }
}
