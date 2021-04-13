package AppletModules;

import RabbitsPackage.ControlFiles.Controller;
import RabbitsPackage.ControlFiles.Habitat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.StringJoiner;

public class MyFrame extends JFrame implements KeyListener {
    Habitat habitat;
    Controller controller;
    MyField myField;
    ControlPanel controlPanel;
    JLabel timeLabel;
    int time;
    final private int controlPanelSize = 200;
    final private String confFile = "config/config.txt";
    //------------------------------------------------------------------------
    private String host = "localhost";
    private String name;
    private int port = 3000;
    private Socket sock;
    private DataOutputStream outStream;
    private DataInputStream inStream;
    public static LinkedList<String> hostList = new LinkedList<String>();
    String[] settings;
    private boolean CONNECTED = false;

    public MyFrame() {
        habitat = new Habitat(1, 2, 100, 50, this, 50, 50);
        myField = new MyField();
        controller = new Controller(myField, habitat, this);
        habitat.confifureController(controller);
        myField.configureController(controller);
        controlPanel = new ControlPanel();
        controlPanel.configureController(controller);
        controlPanel.configureHabitat(habitat);
        controlPanel.configureLists(habitat.getTimeList(), habitat.getIdList());
        setTitle("Rabbits");
        setPreferredSize(new Dimension(habitat.getWidth() + controlPanelSize, habitat.getHeight()));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        myField.setPreferredSize(new Dimension(habitat.getWidth(), habitat.getHeight()));
        add(myField);
        add(controlPanel, BorderLayout.EAST);

        timeLabel = new JLabel("Passed time: " + time / 60 + " minutes " + time % 60 + " seconds", SwingConstants.CENTER);
        controlPanel.configureTimeLabel(timeLabel);
        controlPanel.configureFrame(this);
        myField.add(timeLabel, BorderLayout.SOUTH);
        addKeyListener(this);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        //connect();
        new NameDialog();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closed");
                try {
                    writeConfig(confFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        try {
            readConf(confFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeConfig(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(fileName));
        String conf = String.valueOf(controlPanel.getP1()) + '\n';
        conf += String.valueOf(controlPanel.getN1()) + '\n';
        conf += String.valueOf(controlPanel.getN2()) + '\n';
        conf += String.valueOf(controlPanel.getK()) + '\n';
        conf += String.valueOf(controlPanel.getLivingTimeOrdinaryI()) + '\n';
        conf += String.valueOf(controlPanel.getLivingTimeAlbinosI()) + '\n';
        conf += controlPanel.getOrdinaryAIPriority() + '\n';
        conf += controlPanel.getAlbinosAIPriority() + '\n';
        fos.write(conf.getBytes());
        fos.close();
    }

    private void readConf(String fileName) throws IOException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(fileName), 200);
        int ch;
        String line = "";
        int id = 0;
        String[] configArray = new String[8];
        while((ch = is.read()) != -1) {
            if (!String.valueOf((char)ch).equals("\n")) {
                line += String.valueOf((char)ch);
            } else {
                configArray[id] = line;
                line = "";
                id++;
            }
        }
        is.close();
        habitat.changeSettings(configArray);
        controlPanel.setNewConfig(configArray);
        settings = configArray;

    }

    public void updateTime(int time) {
        controlPanel.configureLists(habitat.getTimeList(), habitat.getIdList());
        this.time = time;
        timeLabel.setText("Passed time: " + time / 60 + " minutes " + time % 60 + " seconds");
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        switch (keyEvent.getKeyChar()) {
            case 'b':
                if (!controller.isCreateProcessOn()) {
                    controller.startCreateProcess();
                    controlPanel.disableStartButton();
                }
                break;
            case 'e':
                if (controller.isCreateProcessOn()) {
                    controller.stopCreateProcess();
                    controlPanel.disableStopButton();
                }
                showFinishDialog();
                break;
            case 't':
//                if(timeLabel.isVisible()){
//                    controlPanel.disableShowButton();
//                } else {
//                    controlPanel.disableHideButton();
//                }
//                timeLabel.setVisible(!timeLabel.isVisible());
                new NetworkDialog();
                break;
        }
    }

    public void configureController(Controller controller) {
        this.controller = controller;
    }

    public void configureHabitat(Habitat habitat) {
        this.habitat = habitat;
        habitat.confifureController(controller);
        controller.configurateHabitat(this.habitat);
        controlPanel.configureLists(habitat.getTimeList(), habitat.getIdList());
    }

    public void showFinishDialog() {
        JDialog dialog = new JDialog(this, "Create process is finished", true);
        JPanel panel = new JPanel(new GridLayout(7, 1));

        JLabel messageLabel = createLabel("Create process is finished. Here are results: ",
                SwingConstants.CENTER,
                new Font("Serif", Font.BOLD, 16),
                Color.BLACK);

        JLabel ordinaryRabbitsLabel = createLabel("Ordinary: " + habitat.numberOfOrdinary,
                SwingConstants.CENTER,
                new Font("Courier New", Font.ITALIC, 16),
                Color.decode("#531C1C"));

        JLabel albinosRabbitsLabel = createLabel("Albinos: " + habitat.numberOfAlbinos,
                SwingConstants.CENTER,
                new Font("Times New Roman", Font.BOLD, 16),
                Color.decode("#32527b"));

        JLabel allRabbitsCount = createLabel("All rabbits: " + habitat.countAllRabbits,
                SwingConstants.CENTER,
                new Font("Roboto", Font.ITALIC, 16),
                Color.blue);

        JLabel timeLabel = createLabel("Passed time: " + time / 60 + " minutes " + time % 60 + " seconds",
                SwingConstants.CENTER,
                new Font("Arial", Font.PLAIN, 16),
                Color.gray);
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");


        panel.add(messageLabel);
        panel.add(ordinaryRabbitsLabel);
        panel.add(albinosRabbitsLabel);
        panel.add(allRabbitsCount);
        panel.add(timeLabel);
        panel.add(okButton);
        panel.add(cancelButton);
        okButton.setFocusable(false);
        cancelButton.setFocusable(false);
        dialog.add(panel);

        cancelButton.addActionListener(listener -> {
            controlPanel.disableStartButton();
            controller.startCreateProcess();
            dialog.setVisible(false);
        });
        okButton.addActionListener(listener -> {
            controlPanel.disableStopButton();
            controller.stopCreateProcessFinally();
            dialog.setVisible(false);
        });

        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(400,300));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) { }

    @Override
    public void keyReleased(KeyEvent keyEvent) { }

    private JLabel createLabel(String text, int fontPosition, Font font, Color fontColor) {
        JLabel label = new JLabel(text, fontPosition);
        label.setFont(font);
        label.setForeground(fontColor);
        return label;
    }



    //----------------------------------------------
    //----------------------------------------------
    //----------------------------------------------

    private void connect() {
        try {
            sock = new Socket(host, port);
            outStream = new DataOutputStream(sock.getOutputStream());
            inStream = new DataInputStream(sock.getInputStream());
            CONNECTED = true;
        } catch (IOException e) {
            System.exit(-5);
        }
    }

    private void disconnect() {
        try {
            outStream.close();
            inStream.close();
            CONNECTED = false;
        } catch (IOException e) {
        } catch (Exception ex) {
            System.exit(-5);
        }
    }




    private class NameDialog extends JDialog {
        private JButton buttonOK;
        private int DIALOG_WIDTH = 200;
        private int DIALOG_HEIGHT = 100;

        public NameDialog() {
            setTitle("WELCOME");
            setModal(true);
            setAlwaysOnTop(true);
            setBounds(
                    habitat.getWidth() / 2 - DIALOG_WIDTH / 2,
                    habitat.getHeight() / 2 - DIALOG_HEIGHT / 2,
                    DIALOG_WIDTH,
                    DIALOG_HEIGHT
            );
            setLayout(new BorderLayout());

            JLabel label = new JLabel("Name: ");
            JTextField nameField = new JTextField();
            nameField.addActionListener(ae -> {
                name = nameField.getText();
                System.out.println(name);
                try {
                    outStream.writeUTF(name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                NameDialog.this.dispose();
            });

            buttonOK = new JButton("OK");
            add(label, BorderLayout.NORTH);
            add(buttonOK, BorderLayout.SOUTH);
            buttonOK.addActionListener(actionEvent -> {
                name = nameField.getText();
                System.out.println(name);
                NameDialog.this.dispose();
            });
            add(nameField, BorderLayout.CENTER);
            setVisible(true);
        }
    }

    private class NetworkDialog extends JDialog {
        private JTextField textField;
        private JButton buttonSEND;
        private JButton buttonDISCONNECT;
        private int DIALOG_WIDTH = 200;
        private int DIALOG_HEIGHT = 100;
        private String target = "";

        public NetworkDialog() {
            setTitle("NETWORK (" + name + ")");
            setAlwaysOnTop(true);
            setModal(false);
            setBounds(
                    habitat.getWidth() / 2 - DIALOG_WIDTH / 2,
                    habitat.getHeight() / 2 - DIALOG_HEIGHT / 2,
                    DIALOG_WIDTH,
                    DIALOG_HEIGHT
            );
            setLayout(new BorderLayout());
            setBackground(new Color(222,222,222));

            JComboBox hosts = new JComboBox();
            hosts.setFocusable(false);
            connect();

            try {
                outStream.writeUTF(name);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        int action = inStream.readInt();
                        switch (action) {
                            case 0:
                                hostList.clear();
                                hosts.removeAllItems();
                                int size = inStream.readInt();
                                for (int i = 0; i < size; i++) {
                                    hostList.addLast(inStream.readUTF());
                                    hosts.addItem(hostList.get(i));
                                    System.out.println(hostList.get(i));
                                }
                                hosts.addItemListener(e -> {
                                    System.out.println(e.getItem());
                                    target = e.getItem().toString();
                                });
                                if (size > 0)
                                    hosts.setSelectedIndex(0);
                                if (size == 1) {
                                    target = hosts.getItemAt(0).toString();
                                }
                                break;
                            case 1:
                                String data = inStream.readUTF();
                                System.out.println("Arrive");
                                System.out.println("k to set: " + data);
                                String[] dataArr = data.split(" ");
//                                Window.this.habitat.setK(data / 100.0);
//                                controlPanel.getSliderPanel().getSliderK().setValue(data);
//                                controlPanel.getComboBoxListPanel().getListK().setSelectedIndex((int) (controlPanel.getSliderPanel().getSliderK().getValue()
//                                        / 10.0));
                                habitat.changeSettings(dataArr);
                                controlPanel.setNewConfig(dataArr);
                                settings = dataArr;
                                break;
                        }
                    }

                } catch (Exception e) {
                    System.err.println(e);
                }
            });
            thread.setDaemon(true);
            thread.setName("Client updater");
            thread.start();

            buttonSEND = new JButton("Send");
            buttonSEND.addActionListener(actionEvent -> {
                StringJoiner joiner = new StringJoiner(" ");
                for (String set : settings){
                    joiner.add(set);
                }
                String data = joiner.toString();
                if (!target.equals("")) {
                    try {
                        System.out.println(target);
                        System.out.println(data);
                        outStream.writeInt(1);
                        outStream.writeUTF(target);
                        outStream.writeUTF(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            buttonDISCONNECT = new JButton("Disconnect");
            buttonDISCONNECT.addActionListener(actionEvent -> {
                try {
                    outStream.writeInt(2);
                    thread.interrupt();
                    disconnect();
                    NetworkDialog.this.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            JPanel hostPanel = new JPanel(new BorderLayout());
            hostPanel.add(new JLabel("Clients:"), BorderLayout.NORTH);
            hostPanel.add(hosts, BorderLayout.SOUTH);


            JPanel bp = new JPanel(new BorderLayout());
            bp.add(buttonSEND, BorderLayout.EAST);
            bp.add(buttonDISCONNECT, BorderLayout.WEST);

            add(hostPanel, BorderLayout.WEST);

            add(bp, BorderLayout.SOUTH);

            setVisible(true);
        }
    }
    public void setSettings(int P1, int N1, int N2, double K, int livingTimeOrdinary, int livingTimeAlbinos){
        //settings = new String[8];
        settings[0] = String.valueOf(P1);
        settings[1] = String.valueOf(N1);
        settings[2] = String.valueOf(N2);
        settings[3] = String.valueOf(K);
        settings[4] = String.valueOf(livingTimeOrdinary);
        settings[5] = String.valueOf(livingTimeAlbinos);

    }

}
