package dessine_module;

/**
* dessine_module/HostInfoHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* mercredi 25 janvier 2017 22 h 03 CET
*/

public final class HostInfoHolder implements org.omg.CORBA.portable.Streamable
{
  public dessine_module.HostInfo value = null;

  public HostInfoHolder ()
  {
  }

  public HostInfoHolder (dessine_module.HostInfo initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = dessine_module.HostInfoHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    dessine_module.HostInfoHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return dessine_module.HostInfoHelper.type ();
  }

}
