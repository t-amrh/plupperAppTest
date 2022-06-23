package com.haw.main;

import java.util.LinkedList;

public class SessionPresenter implements IPresenter{

    public LinkedList<String> messages = new LinkedList<>();

    @Override
    public void receiveMessage(String msg) {
        messages.add(msg);
    }
}
