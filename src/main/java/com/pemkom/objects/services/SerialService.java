package com.pemkom.objects.services;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.pemkom.objects.serial.SerialDataHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SerialService {

    private static SerialService instance;
    private SerialPort activePort;
    private final List<SerialDataHandler<String>> handlers = new ArrayList<>();

    // Singleton - constructor private
    private SerialService() {
    }

    public static synchronized SerialService getInstance() {
        if (instance == null) {
            instance = new SerialService();
        }
        return instance;
    }

    public void addHandler(SerialDataHandler<String> handler) {
        if (!handlers.contains(handler)) {
            handlers.add(handler);
        }
    }

    public void removeHandler(SerialDataHandler<String> handler) {
        handlers.remove(handler);
    }

    public boolean connect(String portName, int baudRate) {
        if (activePort != null && activePort.isOpen()) {
            return true;
        }

        activePort = SerialPort.getCommPort(portName);
        activePort.setBaudRate(baudRate);
        activePort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 1000, 0
        );

        if (activePort.openPort()) {
            System.out.println("INFO: Port " + portName + " terbuka.");
            setupListener();
            return true;
        } else {
            System.err.println("ERROR: Gagal membuka port " + portName);
            return false;
        }
    }

    private void setupListener() {
        activePort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType()
                        != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }
                try (Scanner scanner = new Scanner(activePort.getInputStream())) {
                    if (scanner.hasNextLine()) {
                        String data = scanner.nextLine().trim();
                        if (!data.isEmpty()) {
                            broadcast(data);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    private void broadcast(String data) {
        for (SerialDataHandler<String> handler : handlers) {
            handler.onDataReceived(data);
        }
    }

    public void disconnect() {
        if (activePort != null && activePort.isOpen()) {
            activePort.removeDataListener();
            activePort.closePort();
            System.out.println("INFO: Port ditutup.");
        }
    }

    public boolean isConnected() {
        return activePort != null && activePort.isOpen();
    }


    public void simulateBroadcast(String dummyData) {
        System.out.println("SIMULASI: Menerima UID " + dummyData);
        broadcast(dummyData); // memanggil metode privat yang sudah ada
    }

}
