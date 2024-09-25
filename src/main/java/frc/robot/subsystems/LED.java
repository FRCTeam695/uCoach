// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import java.util.function.DoubleSupplier;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.IntegerPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class LED extends SubsystemBase {

  private AddressableLED myLed;
  private AddressableLEDBuffer myLedBuffer;

  private int idlecnt;

  public LED() {

    // Initialize LED and buffer
    myLed = new AddressableLED(1);
    myLedBuffer = new AddressableLEDBuffer(8);
    myLed.setLength(myLedBuffer.getLength());
    myLed.setData(myLedBuffer);
    myLed.start();

    setDefaultCommand(idle());


  }

  public enum Color
  {
    WHITE(1, 1, 1),
    PURPLE(1, 0, 1),
    ORANGE(255, 20, 0),
    YELLOW(255, 255, 0),
    RED(255, 0, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    BLACK(0, 0, 0);

    private final int r;
    private final int g;
    private final int b;

    private Color(final int r,final int g,final int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

  }

public Command idle() {
    return new FunctionalCommand(

    // ** INIT
    () ->
    {
      idlecnt = 0;
    },

    // ** EXECUTE
    ()->
    {
      Color c = Color.PURPLE;
      if (DriverStation.isTeleopEnabled() == true)
      {
        c = Color.ORANGE;
      }
      if (idlecnt-- == 0)
      {
        idlecnt = 50;
      }
      if (idlecnt < 25)
      {
        c = Color.BLACK;
      }
      for (int i = 0; i < myLedBuffer.getLength(); i++) {
          myLedBuffer.setRGB(i, c.r, c.g, c.b);
      }
      myLed.setData(myLedBuffer);
    },

    // ** ON INTERRUPTED
    interrupted -> {},

    // ** END CONDITION
    ()-> false,

    // ** REQUIREMENTS
    this).ignoringDisable(true);
  }

public Command setColor2(Color c) {
    return new FunctionalCommand(

    // ** INIT
    () -> {},

    // ** EXECUTE
    ()->
    {
        for (var i = 0; i < myLedBuffer.getLength(); i++) {
            myLedBuffer.setRGB(i, c.r, c.g, c.b);
        }
        myLed.setData(myLedBuffer);
    },

    // ** ON INTERRUPTED
    interrupted -> {},

    // ** END CONDITION
    ()-> false,

    // ** REQUIREMENTS
    this);
  }

  public int testLED(int arg)
  {
    int i = 0;
    i += arg;
    return(i);
  }

  @Override
  public void periodic() {
    //red();
  }

}
