import frc.robot.subsystems.intake.IntakeState;
import org.junit.jupiter.api.Test;

public class IntakeTest {
  @Test
  public void intakeStateTest() {
    double onPower = IntakeState.ON.power();
    double offPower = IntakeState.OFF.power();
    double reversePower = IntakeState.REVERSE.power();

    assert onPower > 0;
    assert onPower <= 1;
    assert offPower == 0;
    assert reversePower < 0;
    assert reversePower >= -1;

    IntakeState[] possibleStates = IntakeState.values();
    for (IntakeState state : possibleStates) {
      double power = state.power();
      assert power <= 1;
      assert power >= -1;
    }
  }
}
