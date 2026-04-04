package frc.robot.subsystems.deploy;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.units.measure.Angle;

public enum DeployerPosition {
  RETRACTED(Degrees.of(0)),
  // Replace with actual angle once it is known
  EXTENDED(Degrees.of(25));

  private Angle angle;

  private DeployerPosition(Angle angle) {
    this.angle = angle;
  }

  public Angle getAngle() {
    return angle;
  }
}
