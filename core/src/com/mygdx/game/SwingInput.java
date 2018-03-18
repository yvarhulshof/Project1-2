import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class SwingInput {
  
	public static void main(String[] args) {
		
		
		
		
		JTextArea nameArea = new JTextArea(10, 30);		
		//JLabel label = new JLabel("wololoooo");        
		//label.setSize(400,100);     
		JLabel nameLabel = new JLabel("Initial Direction (degrees/360): ");
		JTextField nameField = new JTextField(10);
		JLabel passLabel = new JLabel("Initial Speed(m/s): ");
		JTextField passField = new JTextField(10);
      
		JButton button = new JButton("Swing!");
		JPanel controlPanel = new JPanel();
		controlPanel.add(nameLabel);
		controlPanel.add(nameField);
		controlPanel.add(passLabel);
		controlPanel.add(passField);
		controlPanel.add(button);
		//controlPanel.add(label);
		JFrame controlFrame = new JFrame();
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlFrame.add(controlPanel);
		controlFrame.setSize(200, 200);
		controlFrame.setVisible(true);
		
		
		
		
		
		
		class InputListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				
				double dir = 0;
				double spd = 0;
				dir = (Double.parseDouble(nameField.getText()));
				System.out.println(dir);
				spd = (Double.parseDouble(passField.getText()));
				System.out.println(spd);				
				
				
    }
    
    
}

ActionListener listener = new InputListener();
button.addActionListener(listener);
}
}
