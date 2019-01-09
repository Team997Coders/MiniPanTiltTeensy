package hardware.arduino;

import org.team997coders.deepspace2019.interfaces.IServo;

import haiku.vm.NativeCHeader;
import haiku.vm.NativeCppFunction;
import haiku.vm.NativeCppMethod;

@NativeCHeader(cImpl = "#include <Servo.h>")
public class Servo implements IServo {
  private Servo realSubject;

  public Servo() {
    this(0);
  }

  public Servo(int pinArg) {
    realSubject = Servo.Servo();
    attach(pinArg);
  }

  public Servo(int pinArg, int angleInDegrees) {
    this(pinArg);
    write(angleInDegrees);
  }

  public Servo(int pinArg, int minPulseInuS, int maxPulseInuS) {
    realSubject = Servo.Servo();
    attachWithMinMax(pinArg, minPulseInuS, maxPulseInuS);
  }

  public Servo(int pinArg, int angleInDegrees, int minPulseInuS, int maxPulseInuS) {
    this(pinArg, minPulseInuS, maxPulseInuS);
    write(angleInDegrees);
  }

  @NativeCppMethod
  private native static Servo Servo();

  @NativeCppMethod
  public native byte attach(int pinArg);

  @NativeCppFunction(cImpl="return getRealCppSubject(Servo, obj)->attach(arg1, arg2, arg3);")
  public native byte attachWithMinMax(int pinArg, int minPulseInuS, int maxPulseInuS);

  @NativeCppMethod
  public native byte attached();

  @NativeCppMethod
  public native void detach();

  @NativeCppMethod
  public native byte read();

  @NativeCppMethod
  public native void write(int angleInDegrees);

  @NativeCppMethod
  public native void writeMicroseconds(int uS);
}