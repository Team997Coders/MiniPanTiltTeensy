package org.team997coders.deepspace2019.subsystems;

import org.junit.*;

import org.team997coders.deepspace2019.helpers.TestLimitsHelper;

import static org.junit.Assert.*;

/**
 * Units tests for the CameraMount subsystem
 */
public class CameraMountUnitTest {

  @Test
  public void itCanDetectWhenAtLowerPanLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 5, 180);

    // Act
    helper.getCameraMount().panToAngle(4);

    // Assert
    assertTrue(helper.getCameraMount().atPanLimit());
  }

  @Test
  public void itCanDetectWhenAtUpperPanLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 5, 170);

    // Act
    helper.getCameraMount().panToAngle(180);

    // Assert
    assertTrue(helper.getCameraMount().atPanLimit());
  }

  @Test
  public void itCanDetectWhenAtLowerTiltLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 5, 180);

    // Act
    helper.getCameraMount().tiltToAngle(4);

    // Assert
    assertTrue(helper.getCameraMount().atTiltLimit());
  }

  @Test
  public void itCanDetectWhenAtUpperTiltLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 170, 5, 170);

    // Act
    helper.getCameraMount().tiltToAngle(180);

    // Assert
    assertTrue(helper.getCameraMount().atTiltLimit());
  }

  @Test
  public void itCanDetectWhenWithinPanLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 100, 150);

    // Act
    helper.getCameraMount().panToAngle(125);

    // Assert
    assertTrue(!helper.getCameraMount().atPanLimit());
  }

  @Test
  public void itCanDetectWhenWithinTiltLimit() {
    // Assemble
    TestLimitsHelper helper = new TestLimitsHelper(10, 180, 100, 150);

    // Act
    helper.getCameraMount().tiltToAngle(100);

    // Assert
    assertTrue(!helper.getCameraMount().atTiltLimit());
  }
}
