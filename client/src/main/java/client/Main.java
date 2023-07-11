package client;

import client.utility.Runner;
import common.utility.CustomConsole;

import java.io.IOException;
import java.net.InetAddress;

public class Main {

    private static final int port = 11111;
    public static void main(String[] args) {

        try {
            var client = new UDPclient(InetAddress.getLocalHost(), port);
            var runner = new Runner(client);
            runner.interactiveMode();
        } catch (IOException e) {
            CustomConsole.printLn("Невозможно подключиться к серверу.");
        }
    }}