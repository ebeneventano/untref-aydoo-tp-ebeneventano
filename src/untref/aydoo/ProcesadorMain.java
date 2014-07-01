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
	    
	    JLabel labelPathEscucharDir = new JLabel("Ingrese el path: ");
	    labelPathEscucharDir.setBounds(50, 60, 140, 30);
	    panel.add(labelPathEscucharDir);
	    
	    final JTextField pathSeleccionadoParaEscuchar = new JTextField();
	    pathSeleccionadoParaEscuchar.setBounds(150, 60, 140, 30);
	    panel.add(pathSeleccionadoParaEscuchar);

	    JButton botonEscucharDir = new JButton("Escuchar Directorio");
	    botonEscucharDir.setBounds(300, 60, 200, 30);
	       
	    botonEscucharDir.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent event) {
				ThreadListener thread = new ThreadListener(pathSeleccionadoParaEscuchar.getText());
				thread.start();
	          }
	    	});
	    
	    panel.add(botonEscucharDir);
	    
	    JLabel labelPathProcesarDir = new JLabel("Ingrese el path: ");
	    labelPathProcesarDir.setBounds(50, 100, 140, 30);
	    panel.add(labelPathProcesarDir);
	    
	    final JTextField pathSeleccionadoParaProcesar = new JTextField();
	    pathSeleccionadoParaProcesar.setBounds(150, 100, 140, 30);
	    panel.add(pathSeleccionadoParaProcesar);
	    
	    JButton botonProcesar = new JButton("Procesar Directorio");
	    botonProcesar.setBounds(300, 100, 200, 30);
	       
	    botonProcesar.addActionListener(new ActionListener() {
	    	@Override
	        public void actionPerformed(ActionEvent event) {
				ThreadProcessor thread = new ThreadProcessor(pathSeleccionadoParaProcesar.getText());
				thread.start();
	          }
	    	});
	    
	    panel.add(botonProcesar);

	    setTitle("Procesador Estadistico");
	    setSize(600, 200);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ProcesadorMain procesadorMain = new ProcesadorMain();
                procesadorMain.setVisible(true);
            }
        });

	}
}
