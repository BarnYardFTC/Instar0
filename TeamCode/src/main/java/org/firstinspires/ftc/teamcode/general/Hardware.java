package org.firstinspires.ftc.teamcode.general;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {
    private HardwareMap hwMap = hardwareMap;

    public enum CONFIG {
        DRIVETRAIN_LB("leftBackDrivetrain"),
        DRIVETRAIN_LF("leftFrontDrivetrain"),
        DRIVETRAIN_RB("rightBackDrivetrain"),
        DRIVETRAIN_RF("rightFrontDrivetrain"),
        PINPOINT("pinpoint"),
        LIMELIGHT("limelight"),
        INTAKE("intakeMotor"),
        DOCK("docServo"),
        GUARD("guardServo"),
        SHOOTER_RIGHT("shooterMotorRight"),
        SHOOTER_LEFT("shooterMotorLeft");

        public final String key;

        CONFIG(String key) {
            this.key = key;
        }
    };
    public Limelight3A limelight;

    public GoBildaPinpointDriver pinpoint;


    public DcMotor leftFrontDrivetrain;
    public DcMotor rightFrontDrivetrain;
    public DcMotor leftBackDrivetrain;
    public DcMotor rightBackDrivetrain;
    public DcMotorEx shooterMotorRight;
    public DcMotorEx shooterMotorLeft;

    public DcMotor intake;
    public Servo dock;
    public Servo guard;

    public Hardware(HardwareMap hwMap, boolean debug) {
        this.hwMap = hwMap;
        if (debug){
            debugInit();
        }
        else {
            initMotors();
            initServos();
            initSensors();
        }
    }

    public void debugInit() {
        //Motors
        try {
            leftFrontDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_LF.key);
        } catch (Exception e) {
            opMode.telemetry.addData("leftFrontDrivetrain failed", e);
        }

        try {
            rightFrontDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_RF.key);
        } catch (Exception e) {
            opMode.telemetry.addData("rightFrontDrivetrain failed", e);
        }

        try {
            leftBackDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_LB.key);
        } catch (Exception e){
            opMode.telemetry.addData("leftBackDrivetrain failed", e);
        }

        try {
        rightBackDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_RB.key);
        } catch (Exception e){
            opMode.telemetry.addData("rightBackDrivetrain failed", e);
        }

        try {
            shooterMotorRight = hwMap.get(DcMotorEx.class, CONFIG.SHOOTER_RIGHT.key);
        } catch (Exception e){
            opMode.telemetry.addData("shooterMotorRight failed", e);
        }

        try {
            shooterMotorLeft = hwMap.get(DcMotorEx.class, CONFIG.SHOOTER_LEFT.key);
        } catch (Exception e) {
            opMode.telemetry.addData("shooterMotorLeft failed", e);
        }

        try {
            intake = hwMap.get(DcMotor.class, CONFIG.INTAKE.key);
        } catch (Exception e) {
            opMode.telemetry.addData("intake failed", e);
        }

        //Servos
        try {
            dock = hwMap.get(Servo.class, CONFIG.DOCK.key);
        } catch (Exception e){
            opMode.telemetry.addData("dock failed", e);
        }

        try {
        guard = hwMap.get(Servo.class, CONFIG.GUARD.key);
        } catch (Exception e){
            opMode.telemetry.addData("guard failed", e);
        }

        //sensors
        try {
        pinpoint = hwMap.get(GoBildaPinpointDriver.class, CONFIG.PINPOINT.key);
        } catch (Exception e){
            opMode.telemetry.addData("pinpoint failed", e);
        }

        try {
            limelight = hwMap.get(Limelight3A.class, CONFIG.LIMELIGHT.key);
        } catch (Exception e){
            opMode.telemetry.addData("limelight failed", e);
        }
    }

    private void initMotors() {
        leftFrontDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_LF.key);
        rightFrontDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_RF.key);
        leftBackDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_LB.key);
        rightBackDrivetrain = hwMap.get(DcMotor.class, CONFIG.DRIVETRAIN_RB.key);

        shooterMotorRight = hwMap.get(DcMotorEx.class, CONFIG.SHOOTER_RIGHT.key);
        shooterMotorLeft = hwMap.get(DcMotorEx.class, CONFIG.SHOOTER_LEFT.key);

        intake = hwMap.get(DcMotor.class, CONFIG.INTAKE.key);
    }

    private void initServos(){
        dock = hwMap.get(Servo.class, CONFIG.DOCK.key);
        guard = hwMap.get(Servo.class, CONFIG.GUARD.key);
    }

    private void initSensors(){
        pinpoint = hwMap.get(GoBildaPinpointDriver.class, CONFIG.PINPOINT.key);
        limelight = hwMap.get(Limelight3A.class, CONFIG.LIMELIGHT.key);
    }
}