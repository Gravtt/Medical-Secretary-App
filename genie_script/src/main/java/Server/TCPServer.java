package Server;

import Client.JSONWriter;
import SocketConnection.QueryCommand;
import SocketConnection.SymmetricEncrypt;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;

/**
 * TCPServer handles JSON GENIE Data sent from the script
 */
public class TCPServer implements Runnable{

    private static final int PORT = 11111;
    private static boolean flag = false;
    private static final Logger log = LogManager.getLogger();
    private static final Path PATH = Paths.get
            ("src/main/resources/").toAbsolutePath();
    private static final String GENIE_DB_NAME = "TestData/appointment.json";
    private static final String CLIENT_KEY_STORE_PASSWORD = "client";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "client";
    private static final String CLIENT_KEY_PATH = "/client_ks.jks";
    private static final String TRUST_SERVER_KEY_PATH = "/serverTrust_ks.jks";

    public static String GENIE_INSTALL_PATH = "";
    DataManager dataManager = DataManager.getInstance();
    Socket connectionSocket;

    public TCPServer(Socket s){
        try{
            System.out.println("Client Connected");
            connectionSocket = s;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            // BufferedReader can handle arbitrary length string
            String msg;
            InputStream in = connectionSocket.getInputStream();

            DataInputStream dataInputStream = new DataInputStream(in);
            try {
                dataManager.ConnectionDB();
                while(!flag && (msg = dataInputStream.readUTF()) != null) {
//                     String data = SymmetricEncrypt.getInstance().decrypt(msg);
                    flag = processData(msg);
                }
                connectionSocket.close();
                System.out.println("Client Disconnected");
            } catch (SocketException e) {
                System.out.println("closed connection");
            } connectionSocket.close();
        }catch(Exception e){
            e.printStackTrace();}
    }

    public static void main(String argv[]) throws Exception
    {
        System.out.println("Threaded Server Running");

        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(PATH + CLIENT_KEY_PATH), CLIENT_KEY_STORE_PASSWORD.toCharArray());
        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(new FileInputStream(PATH + TRUST_SERVER_KEY_PATH), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());


        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(tks);

        SSLContext context = SSLContext.getInstance("SSL");

        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        SSLServerSocketFactory ssf = context.getServerSocketFactory();
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(PORT);

        while(true)
        {
            Socket sock = serverSocket.accept();
            TCPServer server = new TCPServer(sock);

            // Multi-threading, possibly not needed but better to have
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public boolean processData(String data) {
        JSONObject json = JSONReader.getInstance().parse(data);
        String command = (String) json.get("command");

        if (command.equals(QueryCommand.AUTHENTICATION.toString())){
            String secret = (String) json.get("secret");
            return authenticationHandler(secret);
        }
        else if (command.equals(QueryCommand.PATIENT.toString()))
            return userHandler((JSONObject) json.get("doc"));
        else if (command.equals(QueryCommand.APPOINTMENT.toString()))
            return apptHandler((JSONObject) json.get("doc"));
        else if (command.equals(QueryCommand.FILE.toString()))
            return fileHandler((JSONObject) json.get("doc"));
        else if (command.equals(QueryCommand.DISCONNECTION.toString()))
            return true;
        else
            return true;
    }

    public boolean authenticationHandler(String secretAuthenticate) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("src/main/resources/TestData/authentication_server.json"));
            JSONObject authentication = (JSONObject) obj;
            String secret = (String) authentication.get("secret");
            if (secret.equals(secretAuthenticate)) {
                log.info("Authentication success");
                return false;
            } else {
                log.info("Authentication failed");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean userHandler(JSONObject patient) {
        System.out.println(patient.toJSONString());
        dataManager.processPatient(patient);
        return false;
    }

    public boolean apptHandler(JSONObject appointment) {
        System.out.println(appointment.toJSONString());
        dataManager.processAppointment(appointment);
        return false;
    }

    public boolean fileHandler(JSONObject file) {
        int bytesRead = 0;
        int current = 0;
        InputStream in = null;

        try {
            in = connectionSocket.getInputStream();
            DataInputStream clientData = new DataInputStream(in);
            String fileName = (String)file.get("FileName");
            OutputStream output = new FileOutputStream(fileName);
            long size = (long)file.get("FileSize");
            byte[] buffer = new byte[1024];
            while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)
            {
                output.write(buffer, 0, bytesRead);
                size -= bytesRead;
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}