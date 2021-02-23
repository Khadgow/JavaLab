package view;

import controller.Controller;
import model.habitat.Habitat;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ControlPanel extends JPanel {
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
    JButton saveButton;
    JRadioButton showTimeButton;
    JRadioButton hideTimeButton;
    JDialog dialog;
    JDialog dialogError;
    JPanel configurationPanel;
    JTextField periodOfOrdinary;
    JTextField periodOfAlbinos;
    JTextField percentOfAlbinos;
    JComboBox<String> chanceOfOrdinary = new JComboBox<String>();
    MyFrame frame;
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
        buttonsLast = new JPanel(new GridLayout(2, 1));
        buttonsStartStopPanel.setPreferredSize(new Dimension(200, 200));
        buttonsStartStopPanel.setBackground(Color.decode("#E6E6FA"));
        dialog = new JDialog(frame, "Settings", true);
        configurationPanel = new JPanel(new GridLayout(4, 2));

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        showTimeButton = new JRadioButton("Show time");
        hideTimeButton = new JRadioButton("Hide time");
        showInfoButton = new JButton("Show information");
        settingsButton = new JButton("Settings");
        periodOfOrdinary = new JTextField("1");
        periodOfAlbinos = new JTextField("2");
        percentOfAlbinos = new JTextField("50");
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

        buttonsStartStopPanel.add(startButton);
        buttonsStartStopPanel.add(stopButton);
        buttonsShowHideGroup.add(showTimeButton);
        buttonsShowHideGroup.add(hideTimeButton);
        buttonsShowHidePanel.add(showTimeButton);
        buttonsShowHidePanel.add(hideTimeButton);
        buttonsLast.add(showInfoButton);
        buttonsLast.add(settingsButton);
        for (int i = 10; i<=100; i+=10){
            chanceOfOrdinary.addItem(i+"%");
        }

        chanceOfOrdinary.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        periodOfAlbinos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        periodOfOrdinary.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        percentOfAlbinos.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        configurationPanel.add(new JLabel("Chance", JLabel.CENTER));
        configurationPanel.add(chanceOfOrdinary);

        configurationPanel.add(new JLabel("Period for albinos", JLabel.CENTER));
        configurationPanel.add(periodOfAlbinos);

        configurationPanel.add(new JLabel("Period for ordinary", JLabel.CENTER));
        configurationPanel.add(periodOfOrdinary);

        configurationPanel.add(new JLabel("Albino percentage ", JLabel.CENTER));
        configurationPanel.add(percentOfAlbinos);

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
        dialog.setPreferredSize(new Dimension(400,400));
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
            controller.startBornProcess();
        });
        stopButton.addActionListener(listener -> {
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
            controller.stopBornProcess();
        });

        showTimeButton.addActionListener(listener -> {
            timeLabel.setVisible(true);
        });
        hideTimeButton.addActionListener(listener -> {
            timeLabel.setVisible(false);
        });
        showInfoButton.addActionListener(listener -> {
            if (controller.isBornProcessOn()) {
                controller.stopBornProcess();
                disableStopButton();
            }
            frame.showFinishDialog();
        });
        settingsButton.addActionListener(listener -> {
            dialog.setVisible(true);

        });
        saveButton.addActionListener(listener -> {
            controller.stopBornProcessFinally();
            boolean error = false;
            int P1, N1,N2;
            double K;
            String P1S =chanceOfOrdinary.getSelectedItem().toString();
            P1 = Integer.parseInt(P1S.substring(0,P1S.length()-1));
            N1 = Integer.parseInt(periodOfOrdinary.getText());
            N2 = Integer.parseInt(periodOfAlbinos.getText());
            K = Double.parseDouble(percentOfAlbinos.getText());
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
            if(K<1 || K>100){
                error = true;
                K = 100;
                percentOfAlbinos.setText("100");
            }
            if(error){
                dialogError.setVisible(true);
            }
            frame.configureHabitat(new Habitat(N1,N2,P1, K ,frame));
            dialog.setVisible(false);

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
}
