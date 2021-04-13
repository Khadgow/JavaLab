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

public class MyFrame extends JFrame implements KeyListener {
    Habitat habitat;
    Controller controller;
    MyField myField;
    ControlPanel controlPanel;
    JLabel timeLabel;
    int time;
    final private int controlPanelSize = 200;
    final private String confFile = "config/config.txt";

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
                if(timeLabel.isVisible()){
                    controlPanel.disableShowButton();
                } else {
                    controlPanel.disableHideButton();
                }
                timeLabel.setVisible(!timeLabel.isVisible());
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
        //habitat.switchAI();
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
       // habitat.switchAI();
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


}
