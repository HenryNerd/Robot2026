package frc.robot.subsystems.intake;

import badgerutils.motor.MotorConfigUtils;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakeConstants {
  // Tuning doens't need to be perfect. We're only deploying once per match.
  public static final double KP = 0;
  public static final double KI = 0;
  public static final double KD = 0;

  public static final int INTAKE_LEFT_MOTOR_ID = 0;
  public static final int INTAKE_RIGHT_MOTOR_ID = 0;
  public static final int DEPLOYER_MOTOR_ID = 0;

  public static final MotorOutputConfigs LEFT_MOTOR_OUTPUT_CONFIGS =
      MotorConfigUtils.createMotorOutputConfig(
          InvertedValue.Clockwise_Positive, NeutralModeValue.Brake);
  public static final MotorOutputConfigs RIGHT_MOTOR_OUTPUT_CONFIGS =
      MotorConfigUtils.createMotorOutputConfig(
          InvertedValue.CounterClockwise_Positive, NeutralModeValue.Brake);
  public static final CurrentLimitsConfigs CURRENT_LIMITS_CONFIGS =
      new CurrentLimitsConfigs()
          .withStatorCurrentLimitEnable(false)
          .withSupplyCurrentLimitEnable(false);
  public static final Slot0Configs PID_CONFIGS =
      MotorConfigUtils.createPidConfig(KP, KI, KD, 0, 0, 0, 0, GravityTypeValue.Arm_Cosine);
}
