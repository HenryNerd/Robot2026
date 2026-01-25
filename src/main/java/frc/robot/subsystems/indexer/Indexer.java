package frc.robot.subsystems.indexer;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class Indexer extends SubsystemBase {

  public final IndexerIOInputsAutoLogged inputs = new IndexerIOInputsAutoLogged();
  public final IndexerIO indexerIO;

  public Indexer(IndexerIO indexerIO) {
    this.indexerIO = indexerIO;
  }

  @Override
  public void periodic() {
    indexerIO.updateInputs(inputs);
    Logger.processInputs(getName(), inputs);
  }

  public void setPower(double power) {
    indexerIO.setPower(power);
  }

  @AutoLogOutput
  public Command runSpeed(DoubleSupplier speed) {
    return runEnd(() -> indexerIO.setPower(speed.getAsDouble()), () -> indexerIO.setPower(0.0));
  }
}
