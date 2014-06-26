import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	    
	    JLabel label1 = new JLabel("Ingrese el path: ");
	    label1.setBounds(50, 60, 140, 30);
	    panel.add(label1);
	    
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
	
	public static void main(String[] args) throws IOException {
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ProcesadorMain ex = new ProcesadorMain();
                ex.setVisible(true);
            }
        });
        
//		int opcion = -1;
//		while(opcion != 0){
//			System.out.println("Seleccine una opcion: ");
//			System.out.println("1. Escuchar un directorio");
//			System.out.println("2. Procesar un directorio");
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			opcion = Integer.valueOf(br.readLine());
//			if(opcion == 1){
//				BufferedReader bufferListener = new BufferedReader(new InputStreamReader(System.in));
//				System.out.println("Ingrese la ruta del directorio que desea escuchar");
//				ThreadListener thread = new ThreadListener(bufferListener.readLine());
//				thread.start();
//			}else if(opcion == 2){
//				BufferedReader bufferListener = new BufferedReader(new InputStreamReader(System.in));
//				System.out.println("Ingrese la ruta del directorio que desea procesar");
//				ThreadProcessor thread = new ThreadProcessor(bufferListener.readLine());
//				thread.start();
//			}else{
//				System.out.println("Opcion incorrecto, vuelva a ingresar otra opcion");
//			}
//		}
	}
}
