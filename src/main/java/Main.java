import edu.wpi.first.wpilibj.command.Scheduler;

import org.team997coders.spartanlib.hardware.arduino.Servo;

import org.team997coders.spartanlib.commands.SlewCamera;
import org.team997coders.spartanlib.commands.CenterCamera;
import org.team997coders.spartanlib.subsystems.CameraMount;
import org.team997coders.wpilibj.SchedulerTask;

/**
 * This class runs a command processor which listens for camera mount
 * commands and carries out those commands. It runs on a Teensyduino
 * 3.5. The commands and subsystems are portable to a roboRio if one
 * so desires to run the camera mount directly.
 * 
 * @see CommandProcessor
 */
public class Main {
  static int panServoPin = 36;  
  static int tiltServoPin = 35;

  /**
   * Main function entry point for app
   */
  public static void main(String[] args) {
    // Wire up hardware
    Servo panServo = new Servo(panServoPin);
    Servo tiltServo = new Servo(tiltServoPin, 544, 2250);
    CameraMount cameraMount = new CameraMount(panServo, tiltServo, 0, 120, 10, 170);

    // Wire up commands
    CenterCamera centerCamera = new CenterCamera(cameraMount);
    JoystickValueProvider panRateProvider = new JoystickValueProvider();
    JoystickValueProvider tiltRateProvider = new JoystickValueProvider();
    // Set rates: 180 degrees in 2 seconds with scheduler pumping every 20ms
    // Which should be 1.8 degrees per scheduler tick
    SlewCamera slewCamera = new SlewCamera(cameraMount, panRateProvider, tiltRateProvider, 2, 20.0);

    // Wire up command processor
    CommandProcessorValueBuilder valueBuilder = new CommandProcessorValueBuilder();
    CommandProcessor commandProcessor = new CommandProcessor(
        System.in,
        System.out,
        valueBuilder,
        panRateProvider,
        tiltRateProvider,
        centerCamera,
        slewCamera);

    // Start command scheduler
    SchedulerTask schedulerTask = new SchedulerTask(Scheduler.getInstance());
    schedulerTask.start();

    // Make sure mount knows where it is initially
    centerCamera.start();

    // Process commands
    while(true) {
      commandProcessor.process();
    }  
  }
}  
