package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;


public class ObjectsFrame extends JDialog {
    //JButton okButton = new JButton("OK");
    HashMap<String, String> timeList;
    TreeSet<String> idList;
    public ObjectsFrame(HashMap<String, String> timeList, TreeSet<String> idList, JFrame p, String t, boolean modal){
        super(p, t,modal);
        this.timeList = timeList;
        this.idList = idList;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400,500));
        setResizable(false);
        pack();
        setLocationRelativeTo(this);
    }
    public void update(){

        JPanel objectsList = new JPanel();
        objectsList.setPreferredSize(new Dimension(300,500));

        int rows = !idList.isEmpty() ? idList.size() : 0 ;
        objectsList.setLayout(new GridLayout(rows+1,2));

        objectsList.add(new JLabel("Id", JLabel.CENTER));
        objectsList.add(new JLabel("Spawn time", JLabel.CENTER));


        if(!idList.isEmpty()) {
            Iterator<String> iter = idList.iterator();
            while (iter.hasNext()) {
                String id = iter.next();
                objectsList.add(new JLabel(id, JLabel.CENTER));
                objectsList.add(new JLabel(timeList.get(id), JLabel.CENTER));
            }
        }
        JScrollPane scrollpane = new JScrollPane(objectsList);
        add(scrollpane);

    }
}
