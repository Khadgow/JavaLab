package AppletModules;

import RabbitsPackage.ControlFiles.Controller;
import RabbitsPackage.ControlFiles.Habitat;
import RabbitsPackage.Rabbits.AlbinosRabbitAI;
import RabbitsPackage.Rabbits.OrdinaryRabbitAI;
import RabbitsPackage.Rabbits.Rabbit;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Vector;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ControlPanel extends JPanel {
    Habitat habitat;
    JPanel buttonsStartStopPanel;
    ButtonGroup  buttonsShowHideGroup;
    JPanel  buttonsShowHidePanel;
    JPanel buttonsLast;
    Controller controller;
    JLabel timeLabel;
    JButton startButton;
    JButton stopButton;
    JButton showInfoButton;
    JButton settingsButton;
    JButton showObjectsButton;
    JButton saveButton;
    JButton startStopMoveOrdinary;
    JButton startStopMoveAlbinos;
    JButton saveObjectsButton;
    JButton loadObjectsButton;
    JRadioButton showTimeButton;
    JRadioButton hideTimeButton;
    JDialog dialog;
    JDialog dialogError;
    JPanel configurationPanel;
    JTextField periodOfOrdinary;
    JTextField periodOfAlbinos;
    JTextField percentOfAlbinos;
    JTextField livingTimeOrdinary;
    JTextField livingTimeAlbinos;
    JComboBox<String> chanceOfOrdinary = new JComboBox<String>();
    JComboBox<String> ordinaryAIPriority = new JComboBox<String>();
    JComboBox<String> albinosAIPriority = new JComboBox<String>();
    MyFrame frame;
    AlbinosRabbitAI albinosAI;
    OrdinaryRabbitAI ordinaryAI;
    int P1 = 100, N1 = 1,N2 = 2, livingTimeOrdinaryI = 5, livingTimeAlbinosI = 5;
    double K = 50;

    HashMap<String,String> timeList;
    TreeSet<String> idList;
    Vector<Rabbit> rabbitList;
    ControlPanel() {
        super();
        setLayout(new GridLayout(3, 1));
        configureButtonsPanel();
        setPreferredSize(new Dimension(200, 600));
    }

    private void configureButtonsPanel() {
        buttonsStartStopPanel = new JPanel(new GridLayout(2, 1));
        buttonsShowHideGroup  = new ButtonGroup ();
        buttonsShowHidePanel = new JPanel(new GridLayout(2, 1));
        buttonsLast = new JPanel(new GridLayout(7, 1));
        buttonsStartStopPanel.setPreferredSize(new Dimension(200, 200));
        buttonsStartStopPanel.setBackground(Color.decode("#E6E6FA"));
        dialog = new JDialog(frame, "Settings", true);
        configurationPanel = new JPanel(new GridLayout(8, 2));


        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        showTimeButton = new JRadioButton("Show time");
        hideTimeButton = new JRadioButton("Hide time");
        showInfoButton = new JButton("Show information");
        settingsButton = new JButton("Settings");
        showObjectsButton = new JButton("Show objects");
        startStopMoveOrdinary = new JButton("Stop ordinary move");
        startStopMoveAlbinos = new JButton("Stop albinos move");
        saveObjectsButton = new JButton("Save objects");
        loadObjectsButton = new JButton("Load objects");
        periodOfOrdinary = new JTextField("1");
        periodOfAlbinos = new JTextField("2");
        percentOfAlbinos = new JTextField("50");
        livingTimeAlbinos = new JTextField("5");
        livingTimeOrdinary = new JTextField("5");

        saveButton = new JButton("Save");
        dialogError = new JDialog(frame, "Error", true);
        dialogError.setLayout(new FlowLayout());
        dialogError.add(new JLabel("Введены неверные значения, использованы значения по умолчанию"));

        dialogError.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialogError.setPreferredSize(new Dimension(500,200));
        dialogError.setResizable(false);
        dialogError.pack();
        dialogError.setLocationRelativeTo(this);


        stopButton.setFocusable(false);
        startButton.setFocusable(false);
        showTimeButton.setFocusable(false);
        hideTimeButton.setFocusable(false);
        showInfoButton.setFocusable(false);
        settingsButton.setFocusable(false);
        showObjectsButton.setFocusable(false);
        startStopMoveOrdinary.setFocusable(false);
        startStopMoveAlbinos.setFocusable(false);
        saveObjectsButton.setFocusable(false);
        loadObjectsButton.setFocusable(false);

        buttonsStartStopPanel.add(startButton);
        buttonsStartStopPanel.add(stopButton);
        buttonsShowHideGroup.add(showTimeButton);
        buttonsShowHideGroup.add(hideTimeButton);
        buttonsShowHidePanel.add(showTimeButton);
        buttonsShowHidePanel.add(hideTimeButton);
        buttonsLast.add(showInfoButton);
        buttonsLast.add(settingsButton);
        buttonsLast.add(showObjectsButton);
        buttonsLast.add(startStopMoveOrdinary);
        buttonsLast.add(startStopMoveAlbinos);
        buttonsLast.add(saveObjectsButton);
        buttonsLast.add(loadObjectsButton);
        for (int i = 10; i<=100; i+=10){
            chanceOfOrdinary.addItem(i+"%");
        }

        chanceOfOrdinary.setSelectedIndex(9);

        ordinaryAIPriority.addItem("High");
        ordinaryAIPriority.addItem("Normal");
        ordinaryAIPriority.addItem("Low");

        albinosAIPriority.addItem("High");
        albinosAIPriority.addItem("Normal");
        albinosAIPriority.addItem("Low");

        chanceOfOrdinary.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        periodOfAlbinos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        periodOfOrdinary.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        percentOfAlbinos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        livingTimeOrdinary.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        livingTimeAlbinos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        ordinaryAIPriority.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        albinosAIPriority.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        configurationPanel.add(new JLabel("Chance", JLabel.CENTER));
        configurationPanel.add(chanceOfOrdinary);

        configurationPanel.add(new JLabel("Period for albinos", JLabel.CENTER));
        configurationPanel.add(periodOfAlbinos);

        configurationPanel.add(new JLabel("Period for ordinary", JLabel.CENTER));
        configurationPanel.add(periodOfOrdinary);

        configurationPanel.add(new JLabel("Albino percentage ", JLabel.CENTER));
        configurationPanel.add(percentOfAlbinos);

        configurationPanel.add(new JLabel("Albino living time ", JLabel.CENTER));
        configurationPanel.add(livingTimeAlbinos);

        configurationPanel.add(new JLabel("Ordinary living time ", JLabel.CENTER));
        configurationPanel.add(livingTimeOrdinary);

        configurationPanel.add(new JLabel("Ordinary AI priority ", JLabel.CENTER));
        configurationPanel.add(ordinaryAIPriority);

        configurationPanel.add(new JLabel("Albinos AI priority ", JLabel.CENTER));
        configurationPanel.add(albinosAIPriority);

        GridBagLayout gbl = new GridBagLayout();
        dialog.setLayout(gbl);
        GridBagConstraints c =  new GridBagConstraints();

        c.anchor = GridBagConstraints.NORTH;
        c.fill   = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth  = GridBagConstraints.REMAINDER;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(40, 0, 0, 0);
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 0.0;
        c.weighty = 0.0;


        gbl.setConstraints(configurationPanel, c);
        dialog.add(configurationPanel);

        gbl.setConstraints(saveButton, c);
        dialog.add(saveButton);



        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setPreferredSize(new Dimension(400,550));
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(this);

        buttonsStartStopPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                "start/stop",
                TitledBorder.TOP,
                TitledBorder.RIGHT), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        buttonsShowHidePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                "show/hide",
                TitledBorder.TOP,
                TitledBorder.RIGHT), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        buttonsStartStopPanel.setVisible(true);
        buttonsShowHidePanel.setVisible(true);

        add(buttonsStartStopPanel);
        add(buttonsShowHidePanel);
        add(buttonsLast);

        stopButton.setEnabled(false);
        showTimeButton.setSelected(true);
        startButton.addActionListener(listener -> {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            controller.startCreateProcess();
            this.ordinaryAI = habitat.getOrdinaryAI();
            this.albinosAI = habitat.getAlbinosAI();


        });
        stopButton.addActionListener(listener -> {
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
            controller.stopCreateProcess();
            albinosAI.pause();
            ordinaryAI.pause();
        });

        showTimeButton.addActionListener(listener -> {
            timeLabel.setVisible(true);
        });
        hideTimeButton.addActionListener(listener -> {
            timeLabel.setVisible(false);
        });
        startStopMoveOrdinary.addActionListener(listener -> {
            ordinaryAI.changeState();
            if(ordinaryAI.isRunning()){
                startStopMoveOrdinary.setText("Stop ordinary move");
            } else {
                startStopMoveOrdinary.setText("Continue ordinary move");
            }


        });
        startStopMoveAlbinos.addActionListener(listener -> {
            albinosAI.changeState();
            if(albinosAI.isRunning()){
                startStopMoveAlbinos.setText("Stop albinos move");
            } else {
                startStopMoveAlbinos.setText("Continue albinos move");
            }
        });
        showInfoButton.addActionListener(listener -> {
            if (controller.isCreateProcessOn()) {
                controller.stopCreateProcess();
                disableStopButton();
            }
            frame.showFinishDialog();
        });
        settingsButton.addActionListener(listener -> {
            dialog.setVisible(true);
        });
        saveObjectsButton.addActionListener(listener -> {
            writeDat("config/objects.dat");
        });
        loadObjectsButton.addActionListener(listener -> {
            readDat("config/objects.dat");
        });
        saveButton.addActionListener(listener -> {
            controller.stopCreateProcessFinally();
            boolean error = false;
            disableStopButton();
            String P1S = chanceOfOrdinary.getSelectedItem().toString();
            if(ordinaryAIPriority.getSelectedItem().toString().equals("High")){
                ordinaryAI.setPriority(Thread.MAX_PRIORITY);
            } else if (ordinaryAIPriority.getSelectedItem().toString().equals("Low")) {
                ordinaryAI.setPriority(Thread.MIN_PRIORITY);
            } else {
                ordinaryAI.setPriority(Thread.NORM_PRIORITY);
            }

            if(albinosAIPriority.getSelectedItem().toString().equals("High")){
                albinosAI.setPriority(Thread.MAX_PRIORITY);
            } else if (albinosAIPriority.getSelectedItem().toString().equals("Low")) {
                albinosAI.setPriority(Thread.MIN_PRIORITY);
            } else {
                albinosAI.setPriority(Thread.NORM_PRIORITY);
            }
            try {
                P1 = Integer.parseInt(P1S.substring(0, P1S.length() - 1));
                N1 = Integer.parseInt(periodOfOrdinary.getText());
                N2 = Integer.parseInt(periodOfAlbinos.getText());
                K = Double.parseDouble(percentOfAlbinos.getText());
                livingTimeOrdinaryI = Integer.parseInt(livingTimeOrdinary.getText());
                livingTimeAlbinosI = Integer.parseInt(livingTimeAlbinos.getText());
            }
            catch (NumberFormatException e){
                error = true;
                periodOfOrdinary.setText("1");
                periodOfAlbinos.setText("2");
                livingTimeOrdinary.setText("5");
                livingTimeAlbinos.setText("5");
                percentOfAlbinos.setText("50");
                chanceOfOrdinary.setSelectedIndex(9);
            }
            if(N1<1){
                error = true;
                N1 = 1;
                periodOfOrdinary.setText("1");
            }
            if(N2<1){
                error = true;
                N2 = 2;
                periodOfAlbinos.setText("2");
            }
            if(livingTimeOrdinaryI<1){
                error = true;
                livingTimeOrdinaryI = 5;
                livingTimeOrdinary.setText("5");
            }
            if(livingTimeAlbinosI<1){
                error = true;
                livingTimeAlbinosI = 5;
                livingTimeAlbinos.setText("5");
            }
            if(K<1 || K>100){
                error = true;
                K = 50;
                percentOfAlbinos.setText("50");
            }
            if(error){
                dialogError.setVisible(true);
            }
            habitat.changeSettings(P1, N1, N2, K, livingTimeOrdinaryI, livingTimeAlbinosI);
            dialog.setVisible(false);

        });
        showObjectsButton.addActionListener(listener -> {
            ObjectsFrame objectsFrame = new ObjectsFrame(timeList, idList, frame, "Object list", true);
            controller.stopCreateProcess();
            objectsFrame.update();
            objectsFrame.setVisible(true);
            controller.startCreateProcess();
        });
    }

    public void configureController(Controller controller) {
        this.controller = controller;
    }
    public void configureTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }
    public void configureFrame(MyFrame frame) {
        this.frame = frame;
    }
    public void configureLists(HashMap<String, String> timeList, TreeSet<String> idList) {
        this.timeList = timeList;
        this.idList = idList;
    }
    public void configureHabitat(Habitat habitat) {
        this.habitat = habitat;
        this.ordinaryAI = habitat.getOrdinaryAI();
        this.albinosAI = habitat.getAlbinosAI();
    }
    public void configureThreads(AlbinosRabbitAI albinosAI, OrdinaryRabbitAI ordinaryAI) {
        this.albinosAI = albinosAI;
        this.ordinaryAI = ordinaryAI;
    }
    private void writeDat(String fileName) {
        ObjectOutputStream oos;
        rabbitList = habitat.getRabbitsList();
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName, false));
            for (Rabbit rabbit : rabbitList) {
                System.out.println(rabbit.id);
            }
            oos.writeObject(rabbitList);
            oos.close();
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    private void readDat(String fileName) {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            rabbitList.clear();
            timeList.clear();
            idList.clear();
            Vector<Rabbit> newArr = (Vector<Rabbit>) ois.readObject();
            rabbitList = (Vector<Rabbit>) ois.readObject();

            for (Rabbit rabbit : rabbitList) {
                idList.add(Integer.toString(rabbit.id));
                timeList.put(Integer.toString(rabbit.id), Integer.toString(habitat.totalTime));
            }
            ois.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public void disableStartButton(){
        this.startButton.setEnabled(false);
        this.stopButton.setEnabled(true);
    }
    public void disableStopButton(){
        this.startButton.setEnabled(true);
        this.stopButton.setEnabled(false);
    }
    public void disableShowButton(){
        this.showTimeButton.setSelected(false);
        this.hideTimeButton.setSelected(true);
    }
    public void disableHideButton(){
        this.showTimeButton.setSelected(true);
        this.hideTimeButton.setSelected(false);
    }
    public int getP1 (){
        return P1;
    }
    public int getN1 (){
        return N1;
    }
    public int getN2 (){
        return N2;
    }

    public double getK(){
        return K;
    }

    public int getLivingTimeOrdinaryI (){
        return livingTimeOrdinaryI;
    }
    public int getLivingTimeAlbinosI (){
        return livingTimeAlbinosI;
    }

    public String getAlbinosAIPriority() {
        return albinosAIPriority.getSelectedItem().toString();
    }

    public String getOrdinaryAIPriority() {
        return ordinaryAIPriority.getSelectedItem().toString();
    }

    public void setNewConfig(String [] newConfig){
        periodOfOrdinary.setText(newConfig[1]);
        periodOfAlbinos.setText(newConfig[2]);
        percentOfAlbinos.setText(newConfig[3]);
        livingTimeOrdinary.setText(newConfig[4]);
        livingTimeAlbinos.setText(newConfig[5]);
        chanceOfOrdinary.setSelectedIndex(newConfig[0].charAt(0)-1);
        if(newConfig[6].equals("High")){
            ordinaryAIPriority.setSelectedIndex(0);
        } else if (newConfig[6].equals("Low")) {
            ordinaryAIPriority.setSelectedIndex(1);
        } else {
            ordinaryAIPriority.setSelectedIndex(2);
        }
        if(newConfig[7].equals("High")){
            albinosAIPriority.setSelectedIndex(0);
        } else if (newConfig[7].equals("Low")) {
            albinosAIPriority.setSelectedIndex(1);
        } else {
            albinosAIPriority.setSelectedIndex(2);
        }
    }
}