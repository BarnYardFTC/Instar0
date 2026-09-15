package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.general.BarnRobot;

public class Dock extends SubsystemBase {
    private Servo servo;

    private final double COLLECT_POS = 1;
    private final double PASS_POS = 0.3;
    public boolean isDocCollect = true;

    public Dock(){
        servo = BarnRobot.getInstance().hardware.transfer;
        servo.setDirection(Servo.Direction.FORWARD);
    }

    private void setPosition(double pos){
        servo.setPosition(pos);
    }

    private void setCollect(){
        setPosition(COLLECT_POS);
    }

    private void setPass(){
        setPosition(PASS_POS);
    }

    public double getPos(){
        return servo.getPosition();
    }

    public Command setCollectCommand(){
        return new InstantCommand(() -> setCollect(), this);
    }

    public Command setPassCommand(){
        return new InstantCommand(() -> setPass(), this);
    }

//    public ConditionalCommand setPassNShootCommand(){
//        return new ConditionalCommand(
//                setPassCommand(),
//                BarnRobot.getInstance().rumb(),
//                () -> BarnRobot.getInstance().shooter.isReady()
//        );
//    }

    public Command setPositionCommand(double pos) {
        return new InstantCommand(() -> setPosition(pos));
    }
}
