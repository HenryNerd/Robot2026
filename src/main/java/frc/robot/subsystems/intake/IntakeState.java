package frc.robot.subsystems.intake;

public enum IntakeState {
  ON,
  OFF,
  REVERSE;

  public double power() {
    switch (this) {
      case ON:
        return 0.5;
      case OFF:
        return 0;
      case REVERSE:
        return -0.5;
    }
    // If something goes wrong, off is the best option
    return 0;
  }
}
