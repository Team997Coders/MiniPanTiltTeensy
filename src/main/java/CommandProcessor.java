import java.io.InputStream;
import java.io.PrintStream;

import org.team997coders.spartanlib.commands.SlewCamera;
import org.team997coders.spartanlib.commands.CenterCamera;

/**
 * This is a state machine to gather input from presumably
 * a joystick and translate that input into commands that can
 * operate the camera mount.
 */
public class CommandProcessor {
  private final InputStream m_input;
  private final PrintStream m_output;
  private final CommandProcessorValueBuilder m_valueBuilder;
  private final JoystickValueProvider m_panValueProvider;
  private final JoystickValueProvider m_tiltValueProvider;
  private final CenterCamera m_centerCamera;
  private final SlewCamera m_slewCamera;

  private boolean echo = false;

  /**
   * Constructor for the command processor
   * 
   * @param input               The input stream to read command characters from
   * @param output              The output stream to write responses to
   * @param valueBuilder        The value builder to collect command values
   * @param panValueProvider    The pan value provider to give values to downstream commands
   * @param tiltValueProvider   The tilt value provider to give values to downstream commands
   * @param centerCamera        The center camera command to call when centering
   * @param slewCamera          The slew camera command to call when slewing axis
   */
  public CommandProcessor(InputStream input, 
      PrintStream output, 
      CommandProcessorValueBuilder valueBuilder,
      JoystickValueProvider panValueProvider,
      JoystickValueProvider tiltValueProvider,
      CenterCamera centerCamera,
      SlewCamera slewCamera) {

    // Keep references to dependencies
    m_input = input;
    m_output = output;
    m_valueBuilder = valueBuilder;
    m_panValueProvider = panValueProvider;
    m_tiltValueProvider = tiltValueProvider;
    m_centerCamera = centerCamera;
    m_slewCamera = slewCamera;

    // Reset the value builder to make sure we are ready to build
    m_valueBuilder.reset();

    // Tell who ever is listening that we are ready
    m_output.print("Ready");
  }

  /**
   * Provide feedback to the listener that characters are being received
   * and/or commands are being processed. If the echo command has not been
   * received, then CRLF will not be sent between line breaks and input
   * characters will not be echoed back.
   */
  private void acknowledge(char input, boolean commandPerformed) {
    if (echo) {
      m_output.print(input);
      if (commandPerformed) {
        m_output.println("");
      }
    }
    if (commandPerformed) {
      m_output.print("Ok");
      if (echo) {
        m_output.println("");
      }
    }
  }

  /**
   * Process input characters and perform camera mount movement commands.<p>
   * Commands are as follows:<p>
   * <ul>
   *   <li>e - Turn echo on/off (defaults to off). If on, this will echo all command characters
   *           and send a CRLF once commands are executed</li>
   *   <li>c - Center pan and tilt at 90 degrees
   *   <li>p[-]nnn - Pan in a positive or negative direction at a speed given by the percentage of maximum
   *   <li>t[-]nnn - Tilt in a positive or negative direction at a speed given by the percentage of maximum
   * </ul>
   * Responses are as follows (note no CRLF follows these unless echo is on):<p>
   * <ul>
   *   <li>Ready - Upon first connection to the serial port
   *   <li>Ok - Sent in response to receiving a command
   * </ul>
   */
  public void process() {
    try {  
      char input = (char) m_input.read();
      switch(input) {
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7': case '8': case '9':
          m_valueBuilder.addNumeral(input);
          acknowledge(input, false);
          break;
        case '-':
          m_valueBuilder.setNegative();
          acknowledge(input, false);
          break;
        case 'p':
          m_panValueProvider.setValue(m_valueBuilder.getValue());
          m_slewCamera.start();
          acknowledge(input, true);
          m_valueBuilder.reset();
          break;
        case 't':
          m_tiltValueProvider.setValue(m_valueBuilder.getValue());
          m_slewCamera.start();
          acknowledge(input, true);
          m_valueBuilder.reset();
          break;
        case 'c':
          m_centerCamera.start();
          acknowledge(input, true);
          m_valueBuilder.reset();
          break;
        case 'e':
          echo = !echo;
          acknowledge(input, true);
          m_valueBuilder.reset();
          break;
        case 'r':
          acknowledge(input, true);
          m_valueBuilder.reset();
          break;
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}