package bot.den.orange.subsystems;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import bot.den.orange.Constants;

public class Intake implements BaseSubsystem {
    private final Telemetry telemetry;
    private DcMotor intakeMotor = null;
    private CRServo leftServo = null;
    private CRServo rightServo = null;
    private double intakePower = Constants.Intake.intakePower;
    private double intakeServoPower = Constants.Intake.intakeServoPower;

    public Intake(Telemetry telemetry){
        this.telemetry=telemetry;
    }

    public void init(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.Robot.ConfigNames.intake);
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        intakeMotor.setZeroPowerBehavior(BRAKE);
        leftServo = hardwareMap.get(CRServo.class, Constants.Robot.ConfigNames.leftIntakeServo);
        leftServo.setDirection(CRServo.Direction.REVERSE);
        rightServo = hardwareMap.get(CRServo.class, Constants.Robot.ConfigNames.rightIntakeServo);
        rightServo.setDirection(CRServo.Direction.FORWARD);
    }

    public void showTelemetry(){
        telemetry.addData("Intake", "Power (%.2f)", intakeMotor.getPower());
        telemetry.addData("intakeServos", "Power L(%.2f) R(%.2f)", leftServo.getPower(), rightServo.getPower());
    }

    public void intake(){
        intakeMotor.setPower(intakePower);
        leftServo.setPower(intakeServoPower);
        rightServo.setPower(intakeServoPower);
    }
    public void outtake(){
        intakeMotor.setPower(Constants.Intake.outtakePower);
        leftServo.setPower(Constants.Intake.outtakeServoPower);
        rightServo.setPower(Constants.Intake.outtakeServoPower);
    }

    public void stopIntake(){
        intakeMotor.setPower(Constants.Intake.stopPower);
        leftServo.setPower(Constants.Intake.stopServoPower);
        rightServo.setPower(Constants.Intake.stopServoPower);
    }

    public void changeIntakePower(double change){
        intakePower += change;
    }
    public void changeIntakeServoPower(double change){
        intakeServoPower += change;
    }
}

