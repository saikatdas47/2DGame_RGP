package multiplayer;
import java.io.*;
import java.net.*;

public class Game  {

// A Java program for a ClientSide


        // initialize socket and input output streams
        private Socket socket = null;
        private DataInputStream input = null;
        private DataOutputStream out = null;

        // constructor to put ip address and port
        public Game(String address, int port)
        {

            // establish a connection
            try {

                socket = new Socket(address, port);

                System.out.println("Connected");

                // takes input from terminal
                input = new DataInputStream(System.in);

                // sends output to the socket
                out = new DataOutputStream(
                        socket.getOutputStream());
            }

            catch (UnknownHostException u) {

                System.out.println(u);
            }

            catch (IOException i) {

                System.out.println(i);
            }

            // string to read message from input
            String line = "";

            // keep reading until "End" is input
            while (!line.equals("End")) {

                try {

                    line = input.readLine();

                    out.writeUTF(line);
                }

                catch (IOException i) {

                    System.out.println(i);
                }
            }

            // close the connection
            try {

                input.close();

                out.close();

                socket.close();
            }

            catch (IOException i) {

                System.out.println(i);
            }
        }

        public static void main(String[] args)
        {

            Game client
                    = new Game("127.0.0.1", 5000);
        }
    }



