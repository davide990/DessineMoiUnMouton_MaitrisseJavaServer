package dessine_module;


/**
* dessine_module/StringArrayHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* mercredi 25 janvier 2017 21 h 40 CET
*/

public final class StringArrayHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public StringArrayHolder ()
  {
  }

  public StringArrayHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = dessine_module.StringArrayHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    dessine_module.StringArrayHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return dessine_module.StringArrayHelper.type ();
  }

}
