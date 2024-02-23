package com.czb.lhjypt.fxofront.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.SessionNotFound;
import quickfix.field.*;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.QuoteRequest;

@Service
@Slf4j
public class XXPriceMessageService implements IPriceMessageService {
    @Override
    public void Subscribe(SessionID sessionID) {
        log.info("XXPriceMessageService.Subscribe()");
    }

    @Override
    public void UnSubsribe(SessionID sessionID) {
        log.info("XXPriceMessageService.UnSubsribe()");
    }

    public void SendQuoteRequest(SessionID sessionID) {
        log.info("XXPriceMessageService.SendQuoteRequest()");
        // 具体set哪些字段，参考你的FIX44.modified.xml
        quickfix.fix42.QuoteRequest req = new quickfix.fix42.QuoteRequest();
        req.set(new QuoteReqID("QuoteReqID"));
        // 重复组的设置
        QuoteRequest.NoRelatedSym symGroup1 = new QuoteRequest.NoRelatedSym();
        symGroup1.set(new Symbol("USD/CNY"));
        symGroup1.set(new QuoteRequestType(1));
        symGroup1.set(new OrderQty(10000.0));
        symGroup1.set(new Currency("USD"));
        req.addGroup(symGroup1);
        System.out.println("Sending sendQuoteRequest");
        try {
            Session.sendToTarget(req, sessionID);
        } catch (SessionNotFound e) {
            log.error("SessionNotFound", e);
        }catch (Exception ex) {
            log.error("Unknown Error", ex);
        }
    }
    public void sendMarketDataRequest(SessionID sessionID) {
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
        try {
            Session.sendToTarget(req, sessionID);
        } catch (SessionNotFound e) {
            log.error("SessionNotFound", e);
        }catch (Exception ex) {
            log.error("Unknown Error", ex);
        }
    }

}
