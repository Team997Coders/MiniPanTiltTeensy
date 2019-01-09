import edu.wpi.first.wpilibj.command.Scheduler;

import hardware.arduino.Servo;

import org.team997coders.deepspace2019.commands.SlewCamera;
import org.team997coders.deepspace2019.commands.CenterCamera;
import org.team997coders.deepspace2019.subsystems.CameraMount;


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
    SlewCamera slewCamera = new SlewCamera(cameraMount, panRateProvider, tiltRateProvider);

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
