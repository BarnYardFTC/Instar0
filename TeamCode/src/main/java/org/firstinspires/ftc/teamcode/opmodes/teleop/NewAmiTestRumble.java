package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;


public class NewAmiTestRumble extends OpMode {
    double endGameStart;
    boolean isEndGame;
    public void init(){

    }
    public void start(){
        endGameStart = getRuntime() + 60;
    }
    @Override
    public void loop() {

        if(endGameStart >= getRuntime() && !isEndGame);
            gamepad1.rumbleBlips(3);
            isEndGame = true;
    }
}
