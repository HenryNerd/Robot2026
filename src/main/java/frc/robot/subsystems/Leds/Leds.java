package frc.robot.subsystems.Leds;

import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.Logger;

public class Leds extends SubsystemBase {

  
  public final LedsIO LedsIO;
  public boolean isDisabled = false;
  public boolean isAiming = false;
  public boolean isLocked = false;
  


  public Leds(LedsIO LedsIO) {
    this.LedsIO = LedsIO;
  }

  @Override
  public void periodic() {
    if (isAiming){
      LedsIO.setSolid(0, 255, 0);
    } else if (isLocked){
      LedsIO.setBlink(0, 255, 0, 4);
    }
    if (isDisabled){
      LedsIO.setSolid(255, 0, 0);
    }
    

  }

}
