import org.team997coders.deepspace2019.interfaces.IJoystickValueProvider;

public class JoystickValueProvider implements IJoystickValueProvider {
  private double m_value = 0;

  public JoystickValueProvider() {
  }

  public double getValue() {
    return m_value;
  }

  public void setValue(double value) {
    m_value = value;
  }
}