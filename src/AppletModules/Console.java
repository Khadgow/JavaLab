package AppletModules;


import RabbitsPackage.ControlFiles.Habitat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Console extends JDialog {
    private JTextArea textArea = new JTextArea();
    private JTextField textField = new JTextField();
    private String text = "Here you can reduce amount of albinos rabbits.\n" +
            "ReduceAlb, then parameter N, where N - percentage of reduce.\n";
    private Habitat habitat;

    public Console(JFrame owner, Habitat habitat) {
        super(owner,"Console", false);
        this.habitat = habitat;

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setText(text);

        setLayout(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(490,420));
        add(scrollPane);
        textField.setPreferredSize(new Dimension(495,25));
        add(textField);
    }

    public void showConsole(){
        textField.setFocusable(true);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    text += textField.getText() + "\n";
                    textArea.setText(text);
                    if (textField.getText().contains("/reduceAlb")) {
                        String string = textField.getText();
                        Pattern pattern = Pattern.compile("\\d+");
                        Matcher matcher = pattern.matcher(string);
                        while (matcher.find()) {
                            double percentage = Double.parseDouble(matcher.group());
                            System.out.println(percentage);
                            int countToRemove = (int)(habitat.numberOfAlbinos * percentage/100);
                            habitat.ReduceAlbinos(countToRemove);
                        }
                    } else {
                        text += "Unknown command. Insert /reduceAlb N, where N - percentage of reduce.\n";
                    }

                    textField.setText(null);
                    textArea.setText(text);
                }
            }
        });
        setVisible(true);
    }
}
