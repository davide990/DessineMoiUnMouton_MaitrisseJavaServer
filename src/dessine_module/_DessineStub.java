package dessine_module;


/**
* dessine_module/_DessineStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* mercredi 25 janvier 2017 22 h 03 CET
*/

public class _DessineStub extends org.omg.CORBA.portable.ObjectImpl implements dessine_module.Dessine
{

  public boolean sendImage (dessine_module.Image img, dessine_module.HostInfo destination) throws dessine_module.Reject
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sendImage", true);
                dessine_module.ImageHelper.write ($out, img);
                dessine_module.HostInfoHelper.write ($out, destination);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:dessine_module/Reject:1.0"))
                    throw dessine_module.RejectHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return sendImage (img, destination        );
            } finally {
                _releaseReply ($in);
            }
  } // sendImage

  public boolean sendComments (String[] comments, dessine_module.HostInfo destination) throws dessine_module.Reject
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sendComments", true);
                dessine_module.StringArrayHelper.write ($out, comments);
                dessine_module.HostInfoHelper.write ($out, destination);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:dessine_module/Reject:1.0"))
                    throw dessine_module.RejectHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return sendComments (comments, destination        );
            } finally {
                _releaseReply ($in);
            }
  } // sendComments

  public boolean sendImageWithComments (dessine_module.Image img, String[] comments, dessine_module.HostInfo destination) throws dessine_module.Reject
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sendImageWithComments", true);
                dessine_module.ImageHelper.write ($out, img);
                dessine_module.StringArrayHelper.write ($out, comments);
                dessine_module.HostInfoHelper.write ($out, destination);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:dessine_module/Reject:1.0"))
                    throw dessine_module.RejectHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return sendImageWithComments (img, comments, destination        );
            } finally {
                _releaseReply ($in);
            }
  } // sendImageWithComments

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:dessine_module/Dessine:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _DessineStub