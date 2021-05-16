import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;

class RoundedBorderCreater implements Border {

	/*
	 * is used to style for buttons border
	 */
	
    private int radius;

    RoundedBorderCreater(int radius) {
        this.radius = radius;
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }
    public boolean isBorderOpaque() {
        return true;
    }
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }   
}

	public class Create_Frame_Main extends JPanel {
	/*
	 * create jpanel to added created buttons and labels
	 * and add feature to created buttons and labels
	 * and add addActionListener to buttons as own process
	 */
	
	private static final long serialVersionUID = 1L;
	public static final String FRAME_TITLE = "CALCULATE CHESS SCORE";
    private  JLabel label_file_select = new JLabel("Selected File:", SwingConstants.CENTER);
  	private JButton button_file_select = new JButton("SELECT FILE to READ");
  	private JLabel label_points_result = new JLabel("Score:", SwingConstants.CENTER);
  	private JButton button_calculate_points = new JButton("CALCULATE SCORE");
  	private int labelWidth = 600;
  	private int labelHeight = 50;
  	private int buttonWidth = 150;
  	private int buttonHeight = 50;  	
    private static String filePath = null;
    private Calculate_Score classCalc = new Calculate_Score();
 
    public Create_Frame_Main(JFrame frame ,int FrameWidth,int FrameHeight) {              
            
        setBackground(Color.decode("#fff59d"));  
        setLayout(null);  
        
        label_file_select.setBounds(FrameWidth/2-labelWidth/2,FrameHeight/4, labelWidth,labelHeight);
        label_file_select.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        label_file_select.setForeground(Color.decode("#283593"));
        
        
        button_file_select.setForeground(Color.decode("#3f51b5"));
        button_file_select.setBackground(Color.decode("#fff8e1"));        
        button_file_select.setBorder(new RoundedBorderCreater(10));        
        button_file_select.setFocusable(false); // Take out the border around the text
        button_file_select.setBounds(FrameWidth/2-buttonWidth/2,FrameHeight/4-buttonHeight, 
        							buttonWidth,buttonHeight);                      
        button_file_select.addActionListener(new ActionListener() {
  	      public void actionPerformed(ActionEvent ae) {
  	        JFileChooser fileChooser = new JFileChooser();
  	        int returnValue = fileChooser.showOpenDialog(null);
  	        if (returnValue == JFileChooser.APPROVE_OPTION) 
  	        {
  	          File selectedFile = fileChooser.getSelectedFile();
  	          if(selectedFile.canExecute())
  	          {
  	        	  filePath = selectedFile.getPath();
  	        	  label_file_select.setText("Selected File:"+filePath);
  	        	  System.out.println(selectedFile.getPath()+"-"+selectedFile.getName());
  	          }
  	          
  	        }
  	      }
  	    });
  	   	
        label_points_result.setBounds(FrameWidth/2-labelWidth/2,FrameHeight/2, labelWidth,labelHeight);
        label_points_result.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        label_points_result.setForeground(Color.decode("#283593"));
        
        button_calculate_points.setForeground(Color.decode("#3f51b5"));
        button_calculate_points.setBackground(Color.decode("#fff8e1"));
        
        button_calculate_points.setBorder(new RoundedBorderCreater(10));        
        button_calculate_points.setFocusable(false);        
        button_calculate_points.setBounds(FrameWidth/2-buttonWidth/2,FrameHeight/3+buttonHeight, 
        								buttonWidth,buttonHeight);
        
                
        button_calculate_points.addActionListener(new ActionListener() {
  	      public void actionPerformed(ActionEvent ae) {
  	    	System.out.println("path:"+filePath);
  	    	String score = classCalc.getScoreFromCalculator(filePath);
  	    	
  	    	if(score.contains("hata"))
  	    	{
  	    		JOptionPane.showMessageDialog(frame, score);	
  	    	}
  	    	else
  	    	{
  	    		label_points_result.setText(score);	
  	    	}
  	    	
  	      }
  	    });
  	   	                           
        add(button_file_select);
        add(label_file_select);
        add(button_calculate_points);
        add(label_points_result);    	
    }

    private static void createAndShowGui() { 
    	/*
    	 * create jframe and call to jpanel
    	 */
        JFrame start_frame = new JFrame(FRAME_TITLE);
        int FrameWidth = 800, FrameHeight = 600;
        Create_Frame_Main mainPanel = new Create_Frame_Main(start_frame,FrameWidth,FrameHeight);
        
        start_frame.setPreferredSize(new Dimension(FrameWidth, FrameHeight)); 
        start_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	start_frame.add(mainPanel);
    	start_frame.setResizable(false);         
        start_frame.pack();
   	    start_frame.setLocationRelativeTo(null);	    
        start_frame.setVisible(true);    	
    }

    
    public static void main(String[] args) {
    	createAndShowGui();// SwingUtilities.invokeLater(() -> createAndShowGui());

    }
}