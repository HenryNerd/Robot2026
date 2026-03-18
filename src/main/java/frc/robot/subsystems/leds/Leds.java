package frc.robot.subsystems.leds;

import badgerutils.triggers.AllianceTriggers;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Leds extends SubsystemBase {

  public final LedsIO LedsIO;
  public boolean isDisabled = false;
  public boolean isShooting = false;
  public boolean isInShootingTolerance = false;
  public Color currentColor = new Color(0, 0, 0);

  public Leds(LedsIO LedsIO) {
    this.LedsIO = LedsIO;
  }

  @Override
  public void periodic() {
    if (DriverStation.isDisabled()) {
      currentColor =
          new Color(
              AllianceTriggers.isRedAlliance() ? 255 : 0,
              0,
              AllianceTriggers.isRedAlliance() ? 0 : 255);
      LedsIO.setSolid(
          (int) (currentColor.red * 255),
          (int) (currentColor.green * 255),
          (int) (currentColor.blue * 255));
    } else if (isShooting && !isInShootingTolerance) {
      currentColor = new Color(150, 150, 0);
      LedsIO.setSolid(
          (int) (currentColor.red * 255),
          (int) (currentColor.green * 255),
          (int) (currentColor.blue * 255));
    } else if (isShooting) {
      currentColor = new Color(0, 255, 0);
      LedsIO.setBlink(
          (int) (currentColor.red * 255),
          (int) (currentColor.green * 255),
          (int) (currentColor.blue * 255),
          (int) (1));
    } else {
      currentColor =
          new Color(
              AllianceTriggers.isRedAlliance() ? 255 : 0,
              0,
              AllianceTriggers.isRedAlliance() ? 0 : 255);
      LedsIO.setSolid(
          (int) (currentColor.red * 255),
          (int) (currentColor.green * 255),
          (int) (currentColor.blue * 255));
    }

    Logger.recordOutput("Leds/isDisabled", isDisabled);
    Logger.recordOutput("Leds/isShooting", isShooting);
    Logger.recordOutput("Leds/isInShootingTolerance", isInShootingTolerance);
    Logger.recordOutput("Leds/color", currentColor);
  }
}
