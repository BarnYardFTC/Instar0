package org.firstinspires.ftc.teamcode.general;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PredictiveBrakingCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Configurable
public class Constants {

    public static double translational_kP = 0;
    public static double translational_kI = 0;
    public static double translational_kD = 0;
    public static double translational_F = 0;

    public static double translationalSecondary_kP = 0;
    public static double translationalSecondary_kI = 0;
    public static double translationalSecondary_kD = 0;
    public static double translationalSecondary_F = 0;

    public static double heading_kP = 0;
    public static double heading_kI = 0;
    public static double heading_kD = 0;
    public static double heading_F = 0;

    public static double headingSecondary_kP = 0;
    public static double headingSecondary_kI = 0;
    public static double headingSecondary_kD = 0;
    public static double headingSecondary_F = 0;

    public static double drive_kP = 0;
    public static double drive_kI = 0;
    public static double drive_kD = 0;
    public static double drive_F = 0;
    public static double drive_filter = 0;

    public static double driveSecondary_kP = 0;
    public static double driveSecondary_kI = 0;
    public static double driveSecondary_kD = 0;
    public static double driveSecondary_F = 0;
    public static double driveSecondary_filter = 0;

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(10.12)
            .forwardZeroPowerAcceleration(-37.525758122052025)
            .lateralZeroPowerAcceleration(-36.684408297836285)

            .useSecondaryTranslationalPIDF(true)
            .useSecondaryHeadingPIDF(true)
            .useSecondaryDrivePIDF(true)

            .translationalPIDFCoefficients(new PIDFCoefficients(translational_kP, translational_kI, translational_kD, translational_F))
            .secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(translationalSecondary_kP, translationalSecondary_kI, translationalSecondary_kD, translationalSecondary_F))
            .headingPIDFCoefficients(new PIDFCoefficients(heading_kP, heading_kI, heading_kD, heading_F))
            .secondaryHeadingPIDFCoefficients(new PIDFCoefficients(headingSecondary_kP, headingSecondary_kI, headingSecondary_kD, headingSecondary_F))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(drive_kP, drive_kI, drive_kD, drive_F, drive_filter))
            .secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(driveSecondary_kP, driveSecondary_kI, driveSecondary_kD, driveSecondary_F, driveSecondary_filter))

            .predictiveBrakingCoefficients(new PredictiveBrakingCoefficients(0.3, 0.057999299877891146, 0.0025011065684205627));

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        Follower follower = new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .pinpointLocalizer(localizerConstants)
                .build();
        updateFollowerCoefficients(follower);
        return follower;
    }

    public static void updateFollowerCoefficients(Follower follower) {
        if (follower == null) return;
        follower.setTranslationalPIDFCoefficients(new PIDFCoefficients(translational_kP, translational_kI, translational_kD, translational_F));
        follower.setSecondaryTranslationalPIDFCoefficients(new PIDFCoefficients(translationalSecondary_kP, translationalSecondary_kI, translationalSecondary_kD, translationalSecondary_F));
        follower.setHeadingPIDFCoefficients(new PIDFCoefficients(heading_kP, heading_kI, heading_kD, heading_F));
        follower.setSecondaryHeadingPIDFCoefficients(new PIDFCoefficients(headingSecondary_kP, headingSecondary_kI, headingSecondary_kD, headingSecondary_F));
        follower.setDrivePIDFCoefficients(new FilteredPIDFCoefficients(drive_kP, drive_kI, drive_kD, drive_F, drive_filter));
        follower.setSecondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(driveSecondary_kP, driveSecondary_kI, driveSecondary_kD, driveSecondary_F, driveSecondary_filter));
    }

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName(Hardware.CONFIG.DRIVETRAIN_RF.key)
            .rightRearMotorName(Hardware.CONFIG.DRIVETRAIN_RB.key)
            .leftRearMotorName(Hardware.CONFIG.DRIVETRAIN_LB.key)
            .leftFrontMotorName(Hardware.CONFIG.DRIVETRAIN_LF.key)
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(77.01525830847073)
            .yVelocity(25.394159179972853);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-20.4/2.54)
            .strafePodX(18.1/2.54)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);
}
