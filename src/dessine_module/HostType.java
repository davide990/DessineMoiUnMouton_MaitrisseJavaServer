package dessine_module;


/**
* dessine_module/HostType.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* mercredi 25 janvier 2017 21 h 40 CET
*/

public class HostType implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 2;
  private static dessine_module.HostType[] __array = new dessine_module.HostType [__size];

  public static final int _CLIENT = 0;
  public static final dessine_module.HostType CLIENT = new dessine_module.HostType(_CLIENT);
  public static final int _SERVER = 1;
  public static final dessine_module.HostType SERVER = new dessine_module.HostType(_SERVER);

  public int value ()
  {
    return __value;
  }

  public static dessine_module.HostType from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected HostType (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class HostType
