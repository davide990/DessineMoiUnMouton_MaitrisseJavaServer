package dessine_module;

/**
* dessine_module/RejectHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* mercredi 25 janvier 2017 22 h 03 CET
*/

public final class RejectHolder implements org.omg.CORBA.portable.Streamable
{
  public dessine_module.Reject value = null;

  public RejectHolder ()
  {
  }

  public RejectHolder (dessine_module.Reject initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = dessine_module.RejectHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    dessine_module.RejectHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return dessine_module.RejectHelper.type ();
  }

}
