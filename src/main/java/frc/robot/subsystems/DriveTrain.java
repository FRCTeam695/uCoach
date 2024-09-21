package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.CANSparkMax;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkLowLevel.MotorType;

public class DriveTrain extends SubsystemBase {

    private CANSparkMax leftlead;
    private CANSparkMax leftfollow;

    private CANSparkMax rightlead;
    private CANSparkMax rightfollow;

    private DifferentialDrive drivetrain;

    public DriveTrain()
    {

        leftlead = new CANSparkMax(10, MotorType.kBrushless);
        leftfollow = new CANSparkMax(12, MotorType.kBrushless);

        rightlead = new CANSparkMax(11, MotorType.kBrushless);
        rightfollow = new CANSparkMax(13, MotorType.kBrushless);

        leftlead.restoreFactoryDefaults();
        leftfollow.restoreFactoryDefaults();
        leftlead.setInverted(true);

        rightlead.restoreFactoryDefaults();
        rightfollow.restoreFactoryDefaults();
        rightlead.setInverted(false);

        leftfollow.follow(leftlead);
        rightfollow.follow(rightlead);

        drivetrain = new DifferentialDrive(leftlead, rightlead);

    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
    }

    public Command drive(DoubleSupplier left, DoubleSupplier right)
    {
        return new FunctionalCommand(

        // ** INIT
        () -> {
        },
      
        // ** EXECUTE
        ()-> {
            drivetrain.tankDrive(left.getAsDouble(), right.getAsDouble());
        },
      
        // ** ON INTERRUPTED
        interrupted-> {},
      
        // ** END CONDITION
        ()-> false,

        // ** REQUIREMENTS
        this);

    }

}
