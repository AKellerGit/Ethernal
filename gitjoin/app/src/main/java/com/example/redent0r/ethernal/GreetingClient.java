package com.example.redent0r.ethernal;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by redent0r
 */

class GreetingClient extends Thread {

    private static final String TAG = GreetingClient.class.getSimpleName();
    String serverName = "159.65.161.113";
    int portNumber = 3000;
    HomeFragment homeFragment;
    PrintWriter pwrite;

    GreetingClient(HomeFragment homeFragment) {
        super();
        this.homeFragment = homeFragment;
    }

    void sendMessageToServer(String message) {
        pwrite.println(message);
    }

    @Override
    public void run() {
        try {
            Socket sock = new Socket(serverName, portNumber);
            // reading from keyboard (keyRead object)
            BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
            // sending to client (pwrite object)
            OutputStream ostream = sock.getOutputStream();
            pwrite = new PrintWriter(ostream, true);

            // receiving from server ( receiveRead  object)
            InputStream istream = sock.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

            String receiveMessage;
            String sendMessage;

            while (true) {

                sendMessage = keyRead.readLine();  // keyboard reading
                pwrite.println(sendMessage);       // sending to server
                pwrite.flush();                    // flush the data
                if ((receiveMessage = receiveRead.readLine()) != null) //receive from server
                {
                    final String messageToDisplay = receiveMessage;
                    homeFragment.getActivity().
                            runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  homeFragment.tvStatus.setText(messageToDisplay);
                                              }
                                          }
                            );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

