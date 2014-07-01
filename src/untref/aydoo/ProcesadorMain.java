package untref.aydoo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import untref.aydoo.threads.ThreadListener;
import untref.aydoo.threads.ThreadProcessor;

@SuppressWarnings("serial")
public class ProcesadorMain extends JFrame{
	
	public ProcesadorMain(){
		JPanel panel = new JPanel();
	    getContentPane().add(panel);

	    panel.setLayout(null);
	    
	    JLabel labelPath = new JLabel("Ingrese el path: ");
	    labelPath.setBounds(50, 60, 140, 30);
	    panel.add(labelPath);
	    
	    final JTextField textInput = new JTextField();
	    textInput.setBounds(150, 60, 140, 30);
	    panel.add(textInput);

	    JButton listenerButton = new JButton("Escuchar Directorio");
	    listenerButton.setBounds(300, 60, 200, 30);
	       
	    listenerButton.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent event) {
				ThreadListener thread = new ThreadListener(textInput.getText());
				thread.start();
	          }
	    	});
	    
	    panel.add(listenerButton);
	    
	    JLabel label2 = new JLabel("Ingrese el path: ");
	    label2.setBounds(50, 100, 140, 30);
	    panel.add(label2);
	    
	    final JTextField textInput2 = new JTextField();
	    textInput2.setBounds(150, 100, 140, 30);
	    panel.add(textInput2);
	    
	    JButton processButton = new JButton("Procesar Directorio");
	    processButton.setBounds(300, 100, 200, 30);
	       
	    processButton.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent event) {
				ThreadProcessor thread = new ThreadProcessor(textInput2.getText());
				thread.start();
	          }
	    	});
	    
	    panel.add(processButton);

	    setTitle("Procesador Estadistico");
	    setSize(600, 200);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ProcesadorMain ex = new ProcesadorMain();
                ex.setVisible(true);
            }
        });

	}
}
