package org.firstinspires.ftc.teamcode.opmodes.teleop;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.general.BarnRobot;
import org.firstinspires.ftc.teamcode.subsystems.AmiShooter;

@TeleOp(name = "kSTuner")
public class kSTuner extends CommandOpMode {

    private final BarnRobot robot = BarnRobot.getInstance();
    private final AmiShooter amiShooter = new AmiShooter();
    public double kS = 0;
    double[] increments = {0.000001, 0.00001,0.0001, 0.001, 0.01, 0.1};
    int incrementIdx = 5;


    @Override
    public void initialize() {
        robot.init(this);
        amiShooter.init(robot.hardware);
        // Do not call TeleopTemplate.apply(this) here so robot.shooter default command doesn't conflict with kS tuning
    }
    @Override
    public void run() {
        super.run();
        if(gamepad1.dpadRightWasPressed() && incrementIdx < 5){
            incrementIdx++;
        }else if (gamepad1.dpadLeftWasPressed() && incrementIdx > 0){
            incrementIdx--;
        }

        double currentStep = increments[incrementIdx];
        // change kS
        if (gamepad1.dpadUpWasPressed()) {
            kS += currentStep;
        }
        if (gamepad1.dpadDownWasPressed()) {
            kS -= currentStep;
        }

        amiShooter.setMotorPower(kS);
        telemetry.addData("step", "%.6f", currentStep);
        telemetry.addData("kS", "%.6f", kS);
        telemetry.addData("RPM", amiShooter.getRPM());
        telemetry.addData("ticks per sec", amiShooter.getTicksPerSec());
        TeleopTemplate.periodic();

    }
    @Override
    public void end() {
        super.end();
        TeleopTemplate.end();

    }
}
