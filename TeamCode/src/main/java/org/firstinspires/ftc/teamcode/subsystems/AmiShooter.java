package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.general.Hardware;

public class AmiShooter {
    private DcMotorEx rightMotor;
    private DcMotorEx leftMotor;
    private double encoderCPM = 28;
    private double gearRatio = 13.7;
    private double kV , kS , kP;
    public void init(Hardware hwmap){
        rightMotor = hwmap.shooterMotorRight;
        leftMotor = hwmap.shooterMotorLeft;
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
    }

    public void setMotorPower(double power){
        rightMotor.setPower(power);
        leftMotor.setPower(power);
    }

    public double getTicksPerSec(){
        return leftMotor.getVelocity();
    }

    public double getRPM(){
        return (getTicksPerSec() * 60.0) / encoderCPM;
    }

}
