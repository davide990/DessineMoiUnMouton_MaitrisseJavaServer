package dessine_module;

/**
* dessine_module/HostTypeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* mercredi 25 janvier 2017 21 h 40 CET
*/

public final class HostTypeHolder implements org.omg.CORBA.portable.Streamable
{
  public dessine_module.HostType value = null;

  public HostTypeHolder ()
  {
  }

  public HostTypeHolder (dessine_module.HostType initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = dessine_module.HostTypeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    dessine_module.HostTypeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return dessine_module.HostTypeHelper.type ();
  }

}
