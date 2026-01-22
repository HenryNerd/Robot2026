package frc.robot.util;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

public class AngleUtils {
  public static Rotation2d getDirectionToPosition(
      Translation2d startPosition, Translation2d endPosition) {
    Translation2d difference = endPosition.minus(startPosition);
    return difference.getAngle();
  }
}
