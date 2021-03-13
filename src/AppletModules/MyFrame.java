package AppletModules;

import RabbitsPackage.ControlFiles.Controller;
import RabbitsPackage.ControlFiles.Habitat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyFrame extends JFrame implements KeyListener {
    Habitat habitat;
    Controller controller;
    MyField myField;
    ControlPanel controlPanel;
    JLabel timeLabel;
    int time;
    final private int controlPanelSize = 200;

    public MyFrame() {
        habitat = new Habitat(1, 2, 100, 50, this, 5, 5);
        myField = new MyField();
        controller = new Controller(myField, habitat, this);
        habitat.confifureController(controller);
        myField.configureController(controller);
        controlPanel = new ControlPanel();
        controlPanel.configureController(controller);
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
                if (!controller.isBornProcessOn()) {
                    controller.startBornProcess();
                    controlPanel.disableStartButton();
                }
                break;
            case 'e':
                if (controller.isBornProcessOn()) {
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
        controller.configurateHabitat(habitat);
        controlPanel.configureLists(habitat.getTimeList(), habitat.getIdList());
    }

    public void showFinishDialog() {
        JDialog dialog = new JDialog(this, "Born process is finished", true);
        JPanel panel = new JPanel(new GridLayout(7, 1));

        JLabel messageLabel = createLabel("Born process is finished. Here are results: ",
                SwingConstants.CENTER,
                new Font("Serif", Font.BOLD, 16),
                Color.BLACK);

        JLabel ordinaryRabbitsLabel = createLabel("Ordinary: " + controller.getOrdinaryRabbitsAmount(),
                SwingConstants.CENTER,
                new Font("Courier New", Font.ITALIC, 16),
                Color.decode("#531C1C"));

        JLabel albinosRabbitsLabel = createLabel("Albinos: " + controller.getAlbinosRabbitsAmount(),
                SwingConstants.CENTER,
                new Font("Times New Roman", Font.BOLD, 16),
                Color.decode("#32527b"));

        JLabel allRabbitsCount = createLabel("All rabbits: " + controller.getAllRabbitsCount(),
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
            controller.startBornProcess();
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

}
