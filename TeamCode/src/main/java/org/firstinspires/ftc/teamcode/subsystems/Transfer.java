package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

public class Transfer extends SubsystemBase {
    private Servo docServo;

    private final double COLLECT_POS = 0.5;
    private final double PASS_POS = 0.9;
    public boolean isDocCollect = true;

    public Transfer(){
        docServo = BarnRobot.getInstance().hardware.transfer;
        docServo.setDirection(Servo.Direction.FORWARD);
    }

    private void setPosition(double pos){
        docServo.setPosition(pos);
    }

    private void setCollect(){
        setPosition(COLLECT_POS);
    }

    private void setPass(){
        setPosition(PASS_POS);
    }

    public Command setCollectCommand(){
        return new InstantCommand(() -> setCollect(), this);
    }

    public Command setPassCommand(){
        return new InstantCommand(() -> setPass(), this);
    }
}
