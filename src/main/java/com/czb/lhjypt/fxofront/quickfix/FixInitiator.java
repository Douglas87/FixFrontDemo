package com.czb.lhjypt.fxofront.quickfix;

import com.czb.lhjypt.fxofront.util.SpringContextUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.*;

import java.util.List;

@Component
@Slf4j
public class FixInitiator {

    @Autowired
    FixInitiatorApplication fixInitiatorApplication;
    @Getter
    SocketInitiator initiator;
    @Getter
    SessionSettings settings;
    @Getter
    List<Session> sessionList;

    public void StartApp() {
        try {
            settings = new SessionSettings("src/main/resources/quickfix.cfg");
        } catch (ConfigError configError) {
            log.error("config error! src/main/resources/quickfix.cfg", configError);
            System.out.println("config error! src/main/resources/quickfix.cfg" + configError);
        } catch (Exception e) {
            log.error("unknown error! src/main/resources/quickfix.cfg", e);
            System.out.println("unknown error! src/main/resources/quickfix.cfg" + e);
        }

        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);

        MessageFactory messageFactory = new DefaultMessageFactory(); // 不是quickfix.fix44.MessageFactory
        try {
            initiator = new SocketInitiator(fixInitiatorApplication, storeFactory, settings, logFactory, messageFactory);
            sessionList = initiator.getManagedSessions();
        } catch (ConfigError configError) {
            log.error("config error! src/main/resources/quickfix.cfg", configError);
            System.out.println("config error! src/main/resources/quickfix.cfg" + configError);
        } catch (Exception e) {
            log.error("unknown error! src/main/resources/quickfix.cfg", e);
            System.out.println("unknown error! src/main/resources/quickfix.cfg" + e);
        }
        startServer();
    }

    private void startServer() {
        try {
            initiator.start();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopServer() {
        initiator.stop();
    }

    public static void main(String[] args) {
        FixInitiator fixInitiator = new FixInitiator();
        fixInitiator.startServer();
        SessionID sessionID = new SessionID("FIX.4.4", "QUICKFIX_INITIATOR1", "QUICKFIX_ACCEPTOR");
        while (true) {
            // 等消息就行了
        }
        /*
        try {
            Thread.sleep(5000);
            application.sendMarketDataRequest(sessionID);
            Thread.sleep(5000);
            application.sendNewOrderRequest(sessionID);
            Thread.sleep(5000);
            application.sendQuoteRequest(sessionID);
        } catch (SessionNotFound | InterruptedException exception) {
            exception.printStackTrace();
        }*/
    }


}











