// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Servo;


public class Example extends SubsystemBase {

  private int cnt = 0;
  private int maxcnt = 50;
  private int x = 0, y = 0;
  private int xinc = 1, yinc = 1;

  NetworkTableInstance inst;
  NetworkTable table;
  
  IntegerPublisher xPub;
  IntegerPublisher yPub;

  BooleanPublisher xButton;
  DoublePublisher xStick;

  DutyCycleEncoder de1 = new DutyCycleEncoder(0);
  long [] de1val = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
  
  Servo s0;

  /** Creates a new ExampleSubsystem. */
  public Example()
  {
    inst = NetworkTableInstance.getDefault();
    table = inst.getTable("frc695_test_table");

    xPub = table.getIntegerTopic("x").publish();
    xPub.set(0);

    yPub = table.getIntegerTopic("y").publish();
    yPub.set(0);

    xButton = table.getBooleanTopic("xButton").publish();
    xButton.set(false);

    xStick = table.getDoubleTopic("xStick").publish();
    xStick.set(0);

    s0 = new Servo(0);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }



  public
  Command 
  setColorBlue() {
        return
  new 
  FunctionalCommand(
  
      // ** INIT
      () -> {


      },
   
      // ** EXECUTE
      ()-> {
      System.out.println("making color blue");
    },
  
      // ** ON INTERRUPTED
        interrupted -> 
        {

        },
  
      // ** END CONDITION
      ()-> false,
  
      // ** REQUIREMENTS
      this);
    }
  
  



  

  public Command testCommand1(DoubleSupplier targetvalue)
  {
    return new FunctionalCommand(

      // ** INIT
      ()-> testInit1(),
      
      // ** EXECUTE
      ()-> testExecute1(targetvalue.getAsDouble()),
      
      // ** ON INTERRUPTED
      interrupted-> testInterrupt1(),
      
      // ** END CONDITION
      ()-> testEndCondition1(),

      // ** REQUIREMENTS
      this);

  }

  public Command servo_On(DoubleSupplier targetvalue)
  {
    return new FunctionalCommand(

      // ** INIT
      () -> {
        s0.set(targetvalue.getAsDouble());

      },
      
      // ** EXECUTE
      ()-> {
        //s0.set(targetvalue.getAsDouble());
      },
      
      // ** ON INTERRUPTED
      interrupted-> {
        s0.set(0);
      },
      
      // ** END CONDITION
      ()-> true,

      // ** REQUIREMENTS
      this);

  }

  public Command servo_Off()
  {
    return new FunctionalCommand(

      // ** INIT
      () -> {
        s0.set(0.5);

      },
      
      // ** EXECUTE
      ()-> {
        //s0.set(targetvalue.getAsDouble());
      },
      
      // ** ON INTERRUPTED
      interrupted-> {},
      
      // ** END CONDITION
      ()-> true,

      // ** REQUIREMENTS
      this);

  }

  public void testInit1()
  {
  }

  private void testExecute1(double value)
  {
    xStick.set(value);
    maxcnt = (int) (50 - Math.abs(value) * 50);
    RobotContainer.getLED().testLED(7);
    //cnt = 0;
  }

  private void testInterrupt1()
  {
    xButton.set(false);
  }

  private boolean testEndCondition1()
  {
    return(false);
  }


  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {

    long avgval = 0;

    if (de1.isConnected() == true)
    {
      for(int lp=0; lp<de1val.length-1; lp++)
      {
        de1val[lp] = de1val[lp+1];
        avgval = avgval + de1val[lp];
      }
      de1val[de1val.length-1] = Math.round(de1.getAbsolutePosition() * 1000);
      avgval = avgval + de1val[de1val.length-1];
      //System.out.println((double) avgval / de1val.length);
      if (DriverStation.isTeleopEnabled())
      {
      //  System.out.println(de1.getAbsolutePosition());
      }
    }

    if (DriverStation.isTeleopEnabled() == true)
    {

//      s0.set(-0.6);

    // This method will be called once per scheduler run
    if (++cnt == maxcnt)
    {

      cnt = 0;
      x = x + xinc;
      if (x == 40)
      {
        xinc = xinc * -1;
      }
      if (x == 0)
      {
        xinc = xinc * -1;
      }
      y = y + yinc;
      if (y == 30)
      {
        yinc = yinc * -1;
      }
      if (y == 0)
      {
        yinc = yinc * -1;
      }
      xPub.set(x);
      yPub.set(y);
    }

  }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
