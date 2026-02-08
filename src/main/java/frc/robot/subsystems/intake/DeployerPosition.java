package frc.robot.subsystems.intake;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.units.measure.Angle;

public enum DeployerPosition {
  // Angles are from CAD; 0 is the retracted state; extended is just relative to the retracted state
  RETRACTED(Degrees.of(0)),
  EXTENDED(Degrees.of(119.51));

  private Angle angle;

  private DeployerPosition(Angle a) {
    this.angle = a;
  }

  public Angle deployerPosition() {
    return angle;
  }
}
