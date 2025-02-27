// FINAL CODE

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CyberLyons")
public class Tester extends OpMode {
    // MATERIALS
    DcMotor motorTest;

    public void initializeStuff(){
        motorTest = hardwareMap.get(DcMotor.class, "motorTest"); // Pin #0
        motorTest.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void init() {
        initializeStuff();
        telemetry.addData("Launch Test:", "Successful");
        telemetry.addData("System Version", "TESTING AUTONOMOUS");
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("Motor Test Position:", String.valueOf(motorTest.getCurrentPosition()));
        motorTest.setPower(0);
        if (gamepad1.a){
            motorTest.setPower(0.5);
        } else if (gamepad1.b) {
            motorTest.setPower(-0.5);
        }
    }
}