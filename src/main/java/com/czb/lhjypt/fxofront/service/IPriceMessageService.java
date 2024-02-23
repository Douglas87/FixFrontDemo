package com.czb.lhjypt.fxofront.service;

import quickfix.SessionID;

public interface IPriceMessageService {

    public void Subscribe(SessionID sessionID);

    public void UnSubsribe(SessionID sessionID);
}
