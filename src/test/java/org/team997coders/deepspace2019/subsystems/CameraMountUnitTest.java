package org.team997coders.deepspace2019.subsystems;

import org.junit.*;

import org.team997coders.deepspace2019.interfaces.*;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class CameraMountUnitTest {
  @Test
  public void itCanDetectWhenAtLowerPanLimit() {
    // Assemble
    IServo panServoMock = mock(IServo.class);
    IServo tiltServoMock = mock(IServo.class);
    CameraMount cameraMount = new CameraMount(panServoMock, tiltServoMock, 10, 180, 5, 180);

    // Act
    cameraMount.panToAngle(4);

    // Assert
    assertTrue(cameraMount.atPanLimit());
  }
}