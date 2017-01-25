package dessine_module;


/**
* dessine_module/CommunicationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from interface.idl
* mercredi 25 janvier 2017 22 h 03 CET
*/

public interface CommunicationOperations 
{
  void registerProducer (dessine_module.HostInfo info) throws dessine_module.Reject;
  String pushImage (dessine_module.Image image, dessine_module.HostInfo producerInfo) throws dessine_module.Reject;

  //Return a ticket that will be used for pulling the response
  void readyForPull (String ticket, dessine_module.HostInfo producerInfo);
  String[] pullComments (String ticket) throws dessine_module.Reject;
  void disconnect (dessine_module.HostInfo info);
} // interface CommunicationOperations
