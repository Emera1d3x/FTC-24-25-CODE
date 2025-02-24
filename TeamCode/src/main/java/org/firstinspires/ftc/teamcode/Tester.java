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
    DcMotor motorArm;
    DcMotor motorLBWheel;
    DcMotor motorRBWheel;
    CRServo servoWheel;
    // JOYSTICK VARS
    float leftStickX;
    float leftStickY;
    // REVERSE CONTROL VARS
    int reverseControl = -1;
    ElapsedTime toggleTimer = new ElapsedTime();
    // SERVO GRABBER VARS
    boolean in = false;
    boolean out = false;
    ElapsedTime grabTimer = new ElapsedTime();
    int curPosition = 0;

    public void initializeStuff(){
        motorArm = hardwareMap.get(DcMotor.class, "motorArm"); // Pin #2
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLBWheel = hardwareMap.get(DcMotor.class, "motorLB"); // Pin #0
        motorRBWheel = hardwareMap.get(DcMotor.class, "motorRB"); // Pin #1
        servoWheel = hardwareMap.get(CRServo.class, "servoWheel"); // Pin #0
        toggleTimer.reset();
        grabTimer.reset();
    }

    @Override
    public void init() {
        initializeStuff();
        telemetry.addData("Launch Test:", "Successful");
        telemetry.addData("System Version", "Final");
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("Arm Position:", String.valueOf(motorArm.getCurrentPosition()) + " " + curPosition);
        telemetry.addData("Controller One", gamepad1.toString());
        telemetry.addData("Controller Two", gamepad2.toString());
        String thing = in+" "+out;
        telemetry.addData("IN OUT:", thing);
        // DEFAULTS
        motorArm.setPower(0);
        motorLBWheel.setPower(0.0);
        motorRBWheel.setPower(0.0);
        // STICK VALUES
        leftStickX = gamepad1.left_stick_x; // Right (+), Left (-)
        leftStickY = -gamepad1.left_stick_y*reverseControl; // Up (+), Down (-)
        // EXPONENTIAL ACCELERATION
        // MOVEMENT CONTROL
        if (leftStickX<=0){
            motorRBWheel.setPower(leftStickY+Math.abs(leftStickX));
            motorLBWheel.setPower(-leftStickY);
            if (gamepad1.y && toggleTimer.seconds() > 4 && false){reverseControl*=-1;toggleTimer.reset();}
        } else if (leftStickX>0){
            motorRBWheel.setPower(leftStickY);
            motorLBWheel.setPower(-leftStickY-leftStickX);
            if (gamepad1.y && toggleTimer.seconds() > 4 && false){reverseControl*=-1;toggleTimer.reset();}
        }
        if (gamepad1.dpad_up){
            motorRBWheel.setPower(0.17*reverseControl);
            motorLBWheel.setPower(-0.17*reverseControl);
        } else if (gamepad1.dpad_down){
            motorRBWheel.setPower(-0.17*reverseControl);
            motorLBWheel.setPower(0.17*reverseControl);
        } else if (gamepad1.dpad_left){
            motorLBWheel.setPower(-0.17*reverseControl);
        } else if (gamepad1.dpad_right){
            motorRBWheel.setPower(0.17*reverseControl);
        }
        // ARM CONTROL
        if (gamepad2.right_trigger>0){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(1);
            curPosition = motorArm.getCurrentPosition();
        } else if (gamepad2.left_trigger>0){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(-1);
            curPosition = motorArm.getCurrentPosition();
        } else if (gamepad2.right_bumper) {
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(0.31);
            curPosition = motorArm.getCurrentPosition();
        } else if (gamepad2.left_bumper){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(-0.31);
            curPosition = motorArm.getCurrentPosition();
        } else if (gamepad1.right_trigger>0){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(1);
            curPosition = motorArm.getCurrentPosition();
        }  else if (gamepad1.left_trigger>0){
            motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorArm.setPower(-0.5);
            curPosition = motorArm.getCurrentPosition();
        }
        // CLAW CONTROL
        if (in){servoWheel.setPower(0.2);} else if (out){servoWheel.setPower(-0.2);}
        if (gamepad2.b && grabTimer.milliseconds() > 200){
            if (in){in = false; out = false; grabTimer.reset();servoWheel.setPower(0);}
            else {in = true; out = false; grabTimer.reset();}
        } else if (gamepad2.a && grabTimer.milliseconds() > 200){
            if (out){in = false; out = false; grabTimer.reset();servoWheel.setPower(0);}
            else {in = false; out = true; grabTimer.reset();}
        }
    }
}