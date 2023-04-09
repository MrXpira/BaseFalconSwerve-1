// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.led.*;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.ColorFlowAnimation.Direction;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;
import com.ctre.phoenix.led.TwinkleAnimation.TwinklePercent;
import com.ctre.phoenix.led.TwinkleOffAnimation.TwinkleOffPercent;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CANdleSubsystem extends SubsystemBase {
CANdle candle = new CANdle(40, Constants.CANBUS);

private final int LedCount = 107;

private Animation toAnimate;
  /** Creates a new CANdleSubsystem. */
  public CANdleSubsystem() {
    CANdleConfiguration configAll = new CANdleConfiguration();
    configAll.statusLedOffWhenActive = true;
    configAll.disableWhenLOS = false;
    configAll.stripType = LEDStripType.GRB;
    configAll.brightnessScalar = 0.3;
    configAll.vBatOutputMode = VBatOutputMode.Modulated;
    
    candle.configAllSettings(configAll, 100);

    toAnimate = new TwinkleOffAnimation(157, 3, 252, 0, 0.8, LedCount, TwinkleOffPercent.Percent100);
  }

  @Override
  public void periodic() {
    candle.animate(toAnimate);
    //candle.animate(new StrobeAnimation(157, 3, 252, 0, .1, LedCount));
  }

  public Command shootingLightsFlash() {
    return this.run(() -> toAnimate = new StrobeAnimation(157, 3, 252, 0, 98.0 / 256, LedCount));
  }

  public Command idleLED() {
    return this.run(() -> toAnimate = new TwinkleOffAnimation(157, 3, 252, 0, 0.8, LedCount, TwinkleOffPercent.Percent100));
  }

  public Command cannonLights() {
    return this.run(() -> toAnimate = new RainbowAnimation(1, 0.4, LedCount));
  }

  public Command intake() {
    return this.run(() -> toAnimate = new ColorFlowAnimation(255, 255, 0, 0, 0.7, LedCount, Direction.Forward));
  }
}
