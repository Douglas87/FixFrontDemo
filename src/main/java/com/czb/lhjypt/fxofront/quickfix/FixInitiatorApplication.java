package com.czb.lhjypt.fxofront.quickfix;

import com.czb.lhjypt.fxofront.service.XXPriceMessageService;
import com.czb.lhjypt.fxofront.service.XXTradeMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import quickfix.Message;
import quickfix.*;
import quickfix.MessageCracker;
import quickfix.field.*;
import quickfix.fix44.*;

import java.time.LocalDateTime;

@Component
@Slf4j
public class FixInitiatorApplication extends MessageCracker implements Application {

    @Autowired
    XXPriceMessageService xxPriceMessageService;

    @Autowired
    XXTradeMessageService xxTradeMessageService;

    // 以下是Application的固定七件套
    @Override
    public void onCreate(SessionID sessionId) {
        System.out.println("onCreate is called");
    }

    @Override
    public void onLogon(SessionID sessionId) {
        System.out.println("onLogon is called, 登录成功 "+ sessionId);
        if (sessionId.getSenderCompID().equals("QUICKFIX_INITIATOR1")) {
            try {
                xxPriceMessageService.SendQuoteRequest(sessionId);
            } catch (Exception e) {
                log.error("Unknown Error", e);
            }
        }
    }

    @Override
    public void onLogout(SessionID sessionId) {
        System.out.println("onLogout is called, 登出成功 " + sessionId);
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("toAdmin is called");
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        System.out.println("fromAdmin is called");
    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        System.out.println("toApp is called: " + message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws UnsupportedMessageType, IncorrectTagValue, FieldNotFound {
        System.out.println("fromApp is called");
        crack(message, sessionId);
    }

    // 以下是你可以自定义的消息接收器，来自MessageCracker
    @Override
    public void onMessage(Message message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        if(message instanceof ExecutionReport) {
            ExecutionReport executionReport = (ExecutionReport) message;
            System.out.println("Received ExecutionReport: " + executionReport + ", sessionID: " + sessionID);
            System.out.println(String.format("clOrderID: %s, symbol: %s, side: %s",
                    executionReport.getClOrdID().getValue(),
                    executionReport.getSymbol().getValue(),
                    executionReport.getSide().getValue()));
        } else if(message instanceof quickfix.fix42.Quote) {
            quickfix.fix42.Quote quote = (quickfix.fix42.Quote) message;
            System.out.println("Received Quote: " + quote + ", sessionID: " + sessionID);
        }
    }

    /**
     * 订阅行情消息
     *
     * @param sessionID
     * @throws SessionNotFound
     */
    public void sendQuoteRequest(SessionID sessionID) throws SessionNotFound {
        // 具体set哪些字段，参考你的FIX44.modified.xml
        QuoteRequest req = new QuoteRequest();
        req.set(new QuoteReqID("QuoteReqID"));
        // 重复组的设置
        QuoteRequest.NoRelatedSym symGroup1 = new QuoteRequest.NoRelatedSym();
        symGroup1.set(new Symbol("USD/CNY"));
        symGroup1.set(new QuoteRequestType(1));
        symGroup1.set(new OrderQty(10000.0));
        req.addGroup(symGroup1);
        System.out.println("Sending sendQuoteRequest");
        Session.sendToTarget(req, sessionID);
    }

    /**
     * 订阅行情消息
     *
     * @param sessionID
     * @throws SessionNotFound
     */
    public void sendMarketDataRequest(SessionID sessionID) throws SessionNotFound {
        // 具体set哪些字段，参考你的FIX44.modified.xml
        MarketDataRequest req = new MarketDataRequest();
        req.set(new MDReqID("mockedMDReqID"));
        req.set(new SubscriptionRequestType('1'));

        // 重复组的设置
        MarketDataRequest.NoRelatedSym symGroup1 = new MarketDataRequest.NoRelatedSym();
        symGroup1.set(new Symbol("mockedSymbol1"));
        req.addGroup(symGroup1);

        MarketDataRequest.NoRelatedSym symGroup2 = new MarketDataRequest.NoRelatedSym();
        symGroup2.set(new Symbol("mockedSymbol2"));
        req.addGroup(symGroup2);

        System.out.println("Sending MarketDataRequest");
        Session.sendToTarget(req, sessionID);
    }

    /**
     * 下单
     *
     * @param sessionID
     * @throws SessionNotFound
     */
    public void sendNewOrderRequest(SessionID sessionID) throws SessionNotFound {
        NewOrderSingle order = new NewOrderSingle();
        LocalDateTime date = LocalDateTime.now();
        order.set(new ClOrdID("mockedClOrdID"));
        order.set(new Account("mockedAccount"));
        order.set(new HandlInst('1'));
        order.set(new OrderQty(45.00));
        order.set(new Price(25.88));
        order.set(new Symbol("mockedSymbol"));
        order.set(new Side(Side.BUY)); // 对于枚举型对象也可以这么设置
        order.set(new OrdType(OrdType.LIMIT));
        Session.sendToTarget(order, sessionID);
    }
}
