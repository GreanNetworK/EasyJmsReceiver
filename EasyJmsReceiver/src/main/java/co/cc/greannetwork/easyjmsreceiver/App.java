package co.cc.greannetwork.easyjmsreceiver;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JMSException
    {
        QueueConnection con = createConnection();
        QueueSession createQueueSession = con.createQueueSession(true, Session.SESSION_TRANSACTED);
        javax.jms.Queue createQueue = createQueueSession.createQueue("MyQueue");
        QueueReceiver createReceiver = createQueueSession.createReceiver(createQueue);
        con.start();
        createReceiver.setMessageListener(new MessageListener() {

            public void onMessage(Message msg) {
                    try {
                        TextMessage tm = (TextMessage) msg;
                        System.out.println("JMS Receiver Get Message : "+tm.getText());
                    } catch (JMSException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
// ไม่ปิด Connection เพื่อให้ Receiver ทำการ Listener ไปเรื่อยๆ
//        createQueueSession.close();
//        con.close();
    }
    
    private static QueueConnection createConnection() throws JMSException {
        com.sun.messaging.QueueConnectionFactory qcf = new com.sun.messaging.QueueConnectionFactory();
        qcf.setProperty(com.sun.messaging.ConnectionConfiguration.imqAddressList, "192.168.17.129:7676");
        qcf.setProperty(com.sun.messaging.ConnectionConfiguration.imqDefaultUsername, "admin");
        qcf.setProperty(com.sun.messaging.ConnectionConfiguration.imqDefaultPassword, "admin");
        return qcf.createQueueConnection();
    }
}
