package com.example.ex05.computer;

import org.springframework.stereotype.Component;

@Component
public class Computer {
    public void turnOn(){
        System.out.println("컴퓨터를 켜요...");
    }
}
