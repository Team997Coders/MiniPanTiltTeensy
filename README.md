# MiniPanTiltTeensy Project

This project demonstrates the use of a [Teensy 3.5](https://www.pjrc.com/store/teensy35.html) to prototype an FRC robot subsystem, developed in Java. It provides slew and center capabilities to an Adafruit [Mini Pan-Tilt](https://www.adafruit.com/product/1967). The developed code implements WPILib commands and a subsystem. A subset of the WPILib command scheduler has been ported and is run at startup. The commands and subsystem can be directly deployed on a roboRio (if an IServo is implemented against the WPILib) if desired.

This gives the flexibility to run the subsystem as a standalone unit (plugging the teensy into the roboRio), or to plug the servos directly into the roboRio. To call the subsystem as a standalone unit, you would send serial commands using the WPILib SerialPort class. See the [CameraMountJoystickDriver](https://github.com/Team997Coders/CameraMountJoystickDriver) project for a standalone Windows Java application that implements the [serial interface]().

## Requirements

1. [Arduino IDE](https://www.arduino.cc/en/Main/Software) (which you do not need to use as an IDE)
2. [Teensyduino](https://www.pjrc.com/teensy/teensyduino.html)
3. [HaikuVM](https://github.com/chuckb/haikuVM) (put this in c:/HaikuVM directory)
4. [Realterm](https://sourceforge.net/projects/realterm/) (optional if you want to send commands manually to the teensy)

And of course VSCode for FRC development (which includes the JDK and gradle).

## Deployment

1. Plug in the pan servo (yellow wire) to pin 36 and tilt servo to pin 35 on the teensy and make sure the servos have power. You can plug them in to the 3v3 power on the teensy.
2. ```gradlew deploy```
3. The Teensy Loader should appear.
4. Push the button on the teensy and observe the generated hex file being uploaded to the teensy.

## Usage

1. Use Realterm to connect to the teensy COM port (COM4 on my Windows 10 system). (56700, 8, 1, N which is the Realterm default)
