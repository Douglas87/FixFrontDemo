package com.czb.lhjypt.fxofront.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix44.NewOrderSingle;

import java.time.LocalDateTime;

@Service
@Slf4j
public class XXTradeMessageService implements ITradeMessageService {
    @Override
    public void NewOrder(SessionID sessionID) {
        log.info("XXTradeMessageService.NewOrder()");
    }

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
