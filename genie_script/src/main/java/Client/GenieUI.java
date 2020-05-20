package Client;

import SocketConnection.QueryCommand;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.*;
import java.security.Key;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenieUI {
    private static int PORT = 11111;
    private static String IP = "localhost";

    private static long prevTime;
    // 30 mins delay for update
    private static long DELAY_UPDATE = 12 * 60 * 60 * 1000;

    private static final Path PATH = Paths.get
            ("src/main/resources/").toAbsolutePath();
    private static final String GENIE_DB_NAME = "TestData/user.json";
    private static final String CLIENT_KEY_STORE_PASSWORD = "client";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "client";
    private static final String CLIENT_KEY_PATH = "/client_ks.jks";
    private static final String TRUST_SERVER_KEY_PATH = "/serverTrust_ks.jks";

    public static QueryCommand COMMAND = null;
    public static String FILE_UPLOAD_PATH = "";

    private JPanel panelMain;

    private JTextField ipField;
    private JTextField portField;
    private JButton sendUpdateButton;
    private JButton updateIPButton;
    private JButton updatePortButton;
    private JTextArea consoleTextArea;
    private JScrollPane consoleScrollPane;
    private JTextField pathTextArea;
    private JButton pathButton;
    private JSpinner updateIntervalHours;

    public GenieUI() {
        // Set print stream to output textarea
        PrintStream printStream = new PrintStream(new ConsoleOutputStream(consoleTextArea));
        System.setOut(printStream);
        System.setErr(printStream);

        // Allow for scrolling
        DefaultCaret caret = (DefaultCaret)consoleTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // Default set ip and port
        ipField.setText(IP);
        portField.setText(String.valueOf(PORT));
        pathTextArea.setText(FILE_UPLOAD_PATH);
        updateIntervalHours.setValue(12);
        updateIntervalHours.setMinimumSize(new Dimension(1,1));

        updateIntervalHours.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                DELAY_UPDATE =
                        60 * 60 * 1000 * (Integer)updateIntervalHours.getValue();
                System.out.println("New Delay Set, please manually update for" +
                        " change");
            }
        });
        // Update IP button
        updateIPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IP = ipField.getText();
                System.out.println("IP has been set to : " + IP);
            }
        });
        // Update port button
        updatePortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PORT = Integer.parseInt(portField.getText());
                System.out.println("Port has been set to : " + PORT);
            }
        });
        // Update IP button
        pathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                COMMAND = QueryCommand.APPOINTMENT;
                FILE_UPLOAD_PATH = pathTextArea.getText();
                System.out.println("Command has been set to : " + COMMAND);
                System.out.println("Path has been set to : " + FILE_UPLOAD_PATH);
            }
        });

        /*******TODO: Add a new button for patients*********/
//        pathButton1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                COMMAND = QueryCommand.PATIENT;
//                FILE_UPLOAD_PATH = pathTextArea.getText();
//                System.out.println("Command has been set to : " + COMMAND);
//                System.out.println("Path has been set to : " + FILE_UPLOAD_PATH);
//            }
//        });

        // Create new socket and send update
        sendUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Send Update");
                try {
                    Socket clientSocket = initSSLSocket();
//                    Socket clientSocket = new Socket(IP, PORT);
                    TCPClient tcpClient = new TCPClient(clientSocket);
                    Thread tcpThread = new Thread(tcpClient);
                    tcpThread.start();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * Instantiate GENIE UI
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Init UI
        JFrame frame = new JFrame("Genie Script Application");
        frame.setContentPane(new GenieUI().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 400);


        System.out.println("Client Scheduled Script Running");
        prevTime = System.currentTimeMillis();

        // Scheduler service
        WatchService watchService = FileSystems.getDefault().newWatchService();
        PATH.register(
                watchService,
                StandardWatchEventKinds.ENTRY_MODIFY);

        // Init socket connection and wait for scheduler
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                final Path changed = (Path) event.context();
                if (changed.endsWith(GENIE_DB_NAME) && System.currentTimeMillis() >
                        prevTime){
                    System.out.println("Send Update");
//                    Socket clientSocket = new Socket(IP, PORT);
                    Socket clientSocket = initSSLSocket();
                    TCPClient tcpClient = new TCPClient(clientSocket);
                    Thread tcpThread = new Thread(tcpClient);
                    tcpThread.start();
                    prevTime = System.currentTimeMillis() + DELAY_UPDATE;
                }
            }
            key.reset();
        }
    }

    public static Socket initSSLSocket() {
        try{
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(PATH + CLIENT_KEY_PATH), CLIENT_KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());

            KeyStore tks = KeyStore.getInstance("JKS");
            tks.load(new FileInputStream(PATH + TRUST_SERVER_KEY_PATH), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(tks);

            SSLContext context = SSLContext.getInstance("SSL");

            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            Socket sslSocket = context.getSocketFactory().createSocket(IP, PORT);

            return sslSocket;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}