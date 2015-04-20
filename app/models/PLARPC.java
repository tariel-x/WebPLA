package models;

/**
 * Created by nikita on 02.04.15.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.*;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import opennlp.tools.sentdetect.*;

import models.placlient.PLAServer;

/**
 *
 * @author Nikita Gerasimov <tariel-x@ya.ru>
 */
public class PLARPC
{
    private static Logger log = LoggerFactory.getLogger(PLARPC.class);
    private static final Boolean BLOCK = false;
    private TTransport Transport;
    TProtocol Protocol;


    public PLARPC(String server, Integer port)
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
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        Transport.close();
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    public String toPla(List<String> sentences)
    {
        String ret = null;
        PLAServer.Client client = new PLAServer.Client(Protocol);
        try
        {
            ret = client.toDPL(sentences);
        }
        catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String toPL(List<String> sentences)
    {
        String ret = null;
        PLAServer.Client client = new PLAServer.Client(Protocol);
        try
        {
            ret = client.toPL(sentences);
        }
        catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String resolveAnaphora(List<String> sentences)
    {
        String ret = null;
        PLAServer.Client client = new PLAServer.Client(Protocol);
        try
        {
            ret = client.resolveAnaphora(sentences);
        }
        catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public String combo(List<String> sentences)
    {
        List<String> strs = new ArrayList<>();
        String ret = "";
        PLAServer.Client client = new PLAServer.Client(Protocol);
        try
        {
            strs = client.combo(sentences);
            ret = String.join("<br>", strs);
        }
        catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        return ret;
    }


}

