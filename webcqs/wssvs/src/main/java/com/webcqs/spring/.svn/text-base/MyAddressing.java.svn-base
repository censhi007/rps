package com.webcqs.spring;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.addressing.AddressingHeaders;
import org.codehaus.xfire.addressing.AddressingHeadersFactory;
import org.codehaus.xfire.addressing.AddressingInHandler;
import org.codehaus.xfire.addressing.AddressingOperationInfo;
import org.codehaus.xfire.addressing.EndpointReference;
import org.codehaus.xfire.addressing.RandomGUID;
import org.codehaus.xfire.exchange.AbstractMessage;
import org.codehaus.xfire.exchange.InMessage;
import org.codehaus.xfire.exchange.MessageExchange;
import org.codehaus.xfire.exchange.OutMessage;
import org.codehaus.xfire.fault.XFireFault;
import org.codehaus.xfire.service.OperationInfo;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.transport.Channel;
import org.codehaus.xfire.transport.Transport;
import org.codehaus.xfire.transport.dead.DeadLetterTransport;
import org.jdom.Attribute;
import org.jdom.Element;

public class MyAddressing extends AddressingInHandler 
{
  public static final Object ADRESSING_HEADERS = "xfire-ws-adressing-headers";

  public static final Object ADRESSING_FACTORY = "xfire-ws-adressing-factory";

  @SuppressWarnings("rawtypes")
private List factories = new ArrayList();

  public MyAddressing()
  {
    setPhase("pre-dispatch");
    createFactories();
  }


  @SuppressWarnings("rawtypes")
public List getFactories()
  {
    return this.factories;
  }

  @SuppressWarnings("rawtypes")
public void invoke(MessageContext context)
    throws Exception
  {
    for (Iterator itr = this.factories.iterator(); itr.hasNext(); )
    {
      AddressingHeadersFactory factory = (AddressingHeadersFactory)itr.next();

      InMessage msg = context.getInMessage();
      Element header = msg.getHeader();

      if ((header != null) && (factory.hasHeaders(header)))
      {
        AddressingHeaders headers = null;
        try
        {
          headers = factory.createHeaders(header);
          msg.setProperty(ADRESSING_HEADERS, headers);
          msg.setProperty(ADRESSING_FACTORY, factory);

          context.setId(headers.getRelatesTo());

          Service service = getService(headers, context);
          if (service != null)
          {
            context.setService(service);
          }
          else
          {
            service = context.getService();
          }

          @SuppressWarnings("unused")
          OperationInfo op = context.getExchange().getOperation();
          AddressingOperationInfo aop = AddressingOperationInfo.getOperationByInAction(service.getServiceInfo(), headers.getAction());

          if (aop == null)
          {
            aop = AddressingOperationInfo.getOperationByOutAction(service.getServiceInfo(), headers.getAction());

            if (aop != null)
            {
              context.setId(headers.getRelatesTo());
              return;
            }
          }

          if (aop == null)
          {
            throw new XFireFault("Action '" + headers.getAction() + "' was not found for service " + headers.getTo(), XFireFault.SENDER);
          }

          MessageExchange exchange = context.getExchange();
          exchange.setOperation(aop.getOperationInfo());

          EndpointReference faultTo = headers.getFaultTo();
          OutMessage faultMsg = null;
          if (faultTo != null)
          {
            faultMsg = processEPR(context, faultTo, aop, headers, factory);
          }
          else
          {
            faultMsg = createDefaultMessage(context, aop, headers, factory);
          }
          exchange.setFaultMessage(faultMsg);

          EndpointReference replyTo = headers.getReplyTo();
          OutMessage outMessage = null;
          if (replyTo != null)
          {
            outMessage = processEPR(context, replyTo, aop, headers, factory);
          }
          else
          {
            outMessage = createDefaultMessage(context, aop, headers, factory);
          }
          exchange.setOutMessage(outMessage);
        }
        catch (XFireFault fault)
        {
          AbstractMessage faultMsg = context.getExchange().getFaultMessage();
          AddressingHeaders outHeaders = (AddressingHeaders)faultMsg.getProperty(ADRESSING_HEADERS);
          if (outHeaders == null)
          {
            outHeaders = new AddressingHeaders();

            if (headers != null) {
              outHeaders.setRelatesTo(headers.getMessageID());
            }
            outHeaders.setAction("http://www.w3.org/2005/08/addressing/fault");
            faultMsg.setProperty(ADRESSING_HEADERS, outHeaders);
            faultMsg.setProperty(ADRESSING_FACTORY, factory);
          }
          throw fault;
        }
      }
    }
  }

  private OutMessage createDefaultMessage(MessageContext context, AddressingOperationInfo aoi, AddressingHeaders inHeaders, AddressingHeadersFactory factory)
  {
    OutMessage outMessage = context.getOutMessage();

    AddressingHeaders headers = new AddressingHeaders();
    headers.setTo(factory.getAnonymousUri());
    headers.setRelatesTo(inHeaders.getMessageID());

    headers.setAction(aoi.getOutAction());
    outMessage.setProperty(ADRESSING_HEADERS, headers);
    outMessage.setProperty(ADRESSING_FACTORY, factory);

    return outMessage;
  }

  private boolean isNoneAddress(AddressingHeadersFactory factory, String addr)
  {
    return (factory.getNoneUri() != null) && (factory.getNoneUri().equals(addr));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
protected OutMessage processEPR(MessageContext context, EndpointReference epr, AddressingOperationInfo aoi, AddressingHeaders inHeaders, AddressingHeadersFactory factory)
    throws XFireFault, Exception
  {
    String addr = epr.getAddress();
    OutMessage outMessage = null;

    boolean isFault = epr.getName().equals("FaultTo");
    Transport t = null;
    Channel c = null;

    if (addr == null)
    {
      throw new XFireFault("Invalid ReplyTo address.", XFireFault.SENDER);
    }

    if (addr.equals(factory.getAnonymousUri()))
    {
      outMessage = new OutMessage("urn:xfire:channel:backchannel");
      c = context.getInMessage().getChannel();
      t = c.getTransport();
    }
    else if (isNoneAddress(factory, addr))
    {
      t = new DeadLetterTransport();
      outMessage = new OutMessage(addr);
      c = t.createChannel();
    }
    else
    {
      outMessage = new OutMessage(addr);
      t = context.getXFire().getTransportManager().getTransportForUri(addr);
      c = t.createChannel();
    }

    outMessage.setChannel(c);
    outMessage.setSoapVersion(context.getExchange().getInMessage().getSoapVersion());

    if (t == null)
    {
      throw new XFireFault("URL was not recognized: " + addr, XFireFault.SENDER);
    }

    AddressingHeaders headers = new AddressingHeaders();

    if (!isFault)
    {
      headers.setTo(addr);
      headers.setAction(aoi.getOutAction());
    }
    else
    {
      headers.setAction("http://www.w3.org/2005/08/addressing/fault");
    }

    headers.setMessageID("urn:uuid:" + new RandomGUID(false).toString());
    headers.setRelatesTo(inHeaders.getMessageID());

    Element refParam = epr.getReferenceParametersElement();
    if (refParam != null)
    {
      List refs = refParam.cloneContent();

      List params = new ArrayList();
      for (int i = 0; i < refs.size(); i++)
      {
        if (!(refs.get(i) instanceof Element))
          continue;
        Element e = (Element)refs.get(i);
        e.setAttribute(new Attribute("isReferenceParameter", "true", epr.getNamespace()));
        params.add(e);
      }

      headers.setReferenceParameters(params);
    }

    outMessage.setProperty(ADRESSING_HEADERS, headers);
    outMessage.setProperty(ADRESSING_FACTORY, factory);

    return outMessage;
  }

  protected Service getService(AddressingHeaders headers, MessageContext context)
  {
    String serviceName = null;

    if (headers.getTo() != null)
    {
      int i = headers.getTo().lastIndexOf('/');
      serviceName = headers.getTo().substring(i + 1);
    }

    if (serviceName == null)
    {
      return null;
    }

    return context.getXFire().getServiceRegistry().getService(serviceName);
  }
}