// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

import frc.robot.subsystems.Intake;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */



public class RobotContainer
{
  private final Intake m_intake = new Intake();
  private final CommandJoystick m_joystick = new CommandJoystick(0);
  private final DoubleSupplier liftSpeed = () -> m_joystick.getRawAxis(1);
  private final DoubleSupplier rollerSpeed = () -> m_joystick.getRawAxis(3);
  
  public RobotContainer()
  {
    configureBindings();
  }

  private void configureBindings()
  {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    //new Trigger(m_exampleSubsystem::exampleCondition);

    m_joystick.button(1).whileTrue(m_intake.runLift(liftSpeed));
    m_joystick.button(11).onTrue(m_intake.runRoller(rollerSpeed));
    m_joystick.button(12).whileTrue(m_intake.releaseAlgae());

    // m_driverController.b().onTrue(m_exampleSubsystem.LEDColors());
    // m_exampleSubsystem.setDefaultCommand(m_exampleSubsystem.servoTurn());
    // m_exampleSubsystem.setDefaultCommand(m_exampleSubsystem.motorTurn(leftYAxis));
    // m_exampleSubsystem.setDefaultCommand(m_exampleSubsystem.TankDrive(rightYAxis,leftYAxis));
   // pidcontroller.setDefaultCommand(pidcontroller.pidcontrolCommand());
   //    m_driverController.b().whileTrue(pidcontroller.pidcontrolCommand());
       // .onTrue(runOnce(() -> m_exampleSubsystem.PID(), m_exampleSubsystem));
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.




  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return Autos.exampleAuto(m_exampleSubsystem);
    return(null);
  }
}

