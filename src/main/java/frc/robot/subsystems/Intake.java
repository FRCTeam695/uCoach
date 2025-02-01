package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

public class Intake extends SubsystemBase
{
    public SparkMax liftmotor;
    public SparkMax rollermotor;
    public SparkMaxConfig configLo;
    public SparkMaxConfig configHi;
    public SparkMaxConfig configRollerLow;
    public SparkMaxConfig configRollerHigh;
  
    // creates a new intake
    public Intake()
    {
        liftmotor = new SparkMax(53, SparkLowLevel.MotorType.kBrushless);
        rollermotor = new SparkMax(54, SparkLowLevel.MotorType.kBrushless);
        configLo = new SparkMaxConfig();
        configHi = new SparkMaxConfig();
        configRollerLow = new SparkMaxConfig();
        configRollerHigh = new SparkMaxConfig();
    
        configLo
            .smartCurrentLimit(10)
            .idleMode(IdleMode.kBrake);

        configRollerLow
            .smartCurrentLimit(20)
            .idleMode(IdleMode.kBrake);

        configRollerHigh
            .smartCurrentLimit(40)
            .idleMode(IdleMode.kBrake);
    
        liftmotor.configure(configLo, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rollermotor.configure(configRollerLow, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        liftmotor.getEncoder().setPosition(0);
    }

    public Command runLift(DoubleSupplier speed)
    {
        return runEnd(
            () -> {
              liftmotor.set(speed.getAsDouble() / 5);
              //System.out.println(speed.getAsDouble());
              System.out.println(liftmotor.getEncoder().getPosition());
            },
            () -> {
                liftmotor.set(0);               
            }
            );
    }
    
    public Command runRoller(DoubleSupplier speed)
    {
        return run(
            () -> {
                rollermotor.configure(configRollerLow, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                rollermotor.set(speed.getAsDouble() * .3);
                System.out.println(rollermotor.getEncoder().getVelocity());
            }
            );
    }

    public Command releaseAlgae()
    {
        return runEnd(
            () -> {
                rollermotor.configure(configRollerHigh, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                liftmotor.set(-0.25);
                rollermotor.set(-.7);
            },
            () -> {
                rollermotor.set(0); 
                liftmotor.set(0);              
            }
            );
    }
    
}

// old pid junk:
    // public CANSparkFlex motor;
    // public RelativeEncoder encoder;
    // public SparkPIDController pid;

    // double kP = 0.00005;
    // double kI = 0.000001;
    // double kD = 0.002;
    // double kFF = 0.0001;

    // int dir = 0;

    // double kMinOutput = -1;
    // double kMaxOutput = 1;

    // double setPoint;

    // public pidcontrol() {
    //     motor = new CANSparkFlex(49, CANSparkLowLevel.MotorType.kBrushless);
    //     encoder = motor.getEncoder();

    //     motor.restoreFactoryDefaults();
    //     encoder.setPosition(0);

    //     pid = motor.getPIDController();

    //     pid.setP(kP);
    //     pid.setD(kD);
    //     pid.setI(kI);
    //     pid.setFF(kFF);

    //     pid.setOutputRange(kMinOutput, kMaxOutput);

    //     SmartDashboard.putNumber("kP", kP);
    //     SmartDashboard.putNumber("kD", kD);
    //     setPoint = 2000;
    // }

    // public Command pidcontrolCommand() {
    //     return new FunctionalCommand(
    //         () -> {
    //             kP = SmartDashboard.getNumber("kP", kP);
    //             // kD = SmartDashboard.getNumber("kD", kD);
    //             // kI = SmartDashboard.getNumber("kI", kI);
    //             // kFF = SmartDashboard.getNumber("kFF", kFF);
    //             pid.setP(kP);
    //              pid.setD(kD);
    //              pid.setI(kI);
    //             // pid.setFF(kFF);
    //         },

    //         () -> {

    //             pid.setReference(setPoint, CANSparkFlex.ControlType.kVelocity);
    //             SmartDashboard.putNumber("Velocity",encoder.getVelocity());
    //             // SmartDashboard. put some  numbers on here that tell ur velocity so u can grpah ok good luck sigmalpha gamma
    //         },

    //         interrupted -> {
    //             pid.setReference(0, CANSparkFlex.ControlType.kVelocity);
    //         },

    //         () -> false,

    //     this);
   // }
