package org.team997coders.deepspace2019.interfaces;

/**
 * The IServo interface for interacting with any servo
 */
public interface IServo {
  byte attach(int pinArg);
  byte attached();
  void detach();
  byte read();
  void write(int angleInDegrees);
}
