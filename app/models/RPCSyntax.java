package models;

/**
 * Created by nikita on 02.04.15.
 */


import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import models.client.*;

import java.util.List;

/**
 *
 * @author Nikita Gerasimov <tariel-x@ya.ru>
 */
public class RPCSyntax
{
    private static Logger log = LoggerFactory.getLogger(RPCSyntax.class);
    private static final Boolean BLOCK = false;
    private TTransport Transport;
    TProtocol Protocol;


    public RPCSyntax(String server, Integer port)
    {
        try {
            if (BLOCK) {
                Transport = new TSocket(server, port);
                Transport.open();

                Protocol = new TBinaryProtocol(Transport);
            } else {
                Transport = new TFramedTransport(new TSocket(server, port));
                Protocol = new TBinaryProtocol(Transport);

                Transport.open();

            }
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        Transport.close();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> tagSentence(List<String> sentence, String language)
    {
        List<String> ret = null;
        ParseServer.Client client = new ParseServer.Client(Protocol);
        try
        {
            ret = client.ParseText(language, sentence);
        }
        catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        return ret;
    }
}

