package janela;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.math.BigInteger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import business.ISuperFatorial;
import business.SuperFatorial;
import business.SuperFatorialCached;
import business.SuperFatorialCachedDisc;
import exceptions.InputException;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Janela1 {

	private JFrame frmCalculadoraDeSuperfatorial;
	private JTextField valorASerCalculado;
	private final ButtonGroup tipoCalculo = new ButtonGroup();
	private JTextField resultado;
	private ISuperFatorial spF = new SuperFatorial();
	private ISuperFatorial spFc = new SuperFatorialCached();
	private ISuperFatorial spFcD = new SuperFatorialCachedDisc();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Janela1 window = new Janela1();
					window.frmCalculadoraDeSuperfatorial.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Janela1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalculadoraDeSuperfatorial = new JFrame();
		frmCalculadoraDeSuperfatorial.setTitle("Calculadora de SuperFatorial");
		frmCalculadoraDeSuperfatorial.setFont(new Font("Arial", Font.PLAIN, 12));
		frmCalculadoraDeSuperfatorial.setBounds(100, 100, 450, 470);
		frmCalculadoraDeSuperfatorial.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculadoraDeSuperfatorial.getContentPane().setLayout(null);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(10, 11, 241, 14);
		frmCalculadoraDeSuperfatorial.getContentPane().add(lblValor);
		
		valorASerCalculado = new JTextField();
		valorASerCalculado.setBounds(10, 36, 414, 20);
		frmCalculadoraDeSuperfatorial.getContentPane().add(valorASerCalculado);
		valorASerCalculado.setColumns(10);
		
		JLabel lblTipoDeCalculo = new JLabel("Tipo de C\u00E1lculo:");
		lblTipoDeCalculo.setBounds(10, 67, 241, 14);
		frmCalculadoraDeSuperfatorial.getContentPane().add(lblTipoDeCalculo);
		
		JRadioButton semCache = new JRadioButton("Direto (sem cache)");
		semCache.setSelected(true);
		tipoCalculo.add(semCache);
		semCache.setBounds(10, 91, 261, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(semCache);
		
		JRadioButton cacheMemoria = new JRadioButton("Com cache em mem\u00F3ria");
		tipoCalculo.add(cacheMemoria);
		cacheMemoria.setBounds(10, 117, 261, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(cacheMemoria);
		
		JRadioButton cacheDisco = new JRadioButton("Com cahe em disco");
		tipoCalculo.add(cacheDisco);
		cacheDisco.setBounds(10, 143, 261, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(cacheDisco);
		
		JLabel lblResultado = new JLabel("Resultado:");
		lblResultado.setBounds(10, 173, 241, 14);
		frmCalculadoraDeSuperfatorial.getContentPane().add(lblResultado);
		
		resultado = new JTextField();
		resultado.setBounds(10, 198, 414, 187);
		frmCalculadoraDeSuperfatorial.getContentPane().add(resultado);
		resultado.setColumns(10);

		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resultado.setText("");
				if (tipoCalculo.getSelection()==semCache.getModel()) {
					BigInteger superfat = null;
					BigInteger tmp=new BigInteger(valorASerCalculado.getText());
					try {
						superfat = spF.getSuperFatorial(tmp);
					} catch (InputException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (superfat==null)
						valorInvalido();
					else
					resultado.setText(superfat.toString());
					
				}
				else if (tipoCalculo.getSelection()==cacheMemoria.getModel()) {
					BigInteger superfat = null;
					BigInteger tmp=new BigInteger(valorASerCalculado.getText());
					try {
						superfat = spFc.getSuperFatorial(tmp);
					} catch (InputException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (superfat==null)
						valorInvalido();
					else
					resultado.setText(superfat.toString());
				}
				else if (tipoCalculo.getSelection()==cacheDisco.getModel()) {
					BigInteger superfat = null;
					BigInteger tmp=new BigInteger(valorASerCalculado.getText());
					try {
						superfat = spFcD.getSuperFatorial(tmp);
					} catch (InputException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (superfat==null)
						valorInvalido();
					else if( superfat.equals(BigInteger.ONE.negate()) )
						resultado.setText("Não é possivel Calcular esse fatorial. Valor Muito Grande. Tente com um valor valido");
					else
						resultado.setText(superfat.toString());
				}
			}

			private void valorInvalido() {
				{
					resultado.setText("Não é possivel Calcular esse fatorial.Tente com um valor valido");
				}
			}
		});
		btnCalcular.setBounds(236, 396, 89, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(btnCalcular);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.exit(0);
				int i = JOptionPane.showConfirmDialog(null,"Deseja realmente sair?", "Saída",JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION)	System.exit(0);
			}
		});
		btnCancelar.setBounds(335, 396, 89, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(btnCancelar);
	}
}
