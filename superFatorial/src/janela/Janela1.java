package janela;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import java.math.BigInteger;
import java.util.HashMap;

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
	private HashMap<String,ISuperFatorial> spF = new HashMap<>(); 
	
	private static final String OP1 = "Direto (sem cache)";
	private static final String OP2 = "Com cache em mem\u00F3ria";
	private static final String OP3 = "Com cahe em disco";
	
	private JRadioButton semCache = new JRadioButton(OP1);
	private JRadioButton cacheMemoria = new JRadioButton(OP2);
	private JRadioButton cacheDisco = new JRadioButton(OP3);

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
		spF.put(OP1,new SuperFatorial() );
		spF.put(OP2,new SuperFatorialCached() );
		spF.put(OP3,new SuperFatorialCachedDisc() );
		
		semCache.setActionCommand( semCache.getText() );
		cacheMemoria.setActionCommand( cacheMemoria.getText() );
		cacheDisco.setActionCommand( cacheDisco.getText() );
		
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

		semCache.setSelected(true);
		tipoCalculo.add(semCache);
		semCache.setBounds(10, 91, 261, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(semCache);

		tipoCalculo.add(cacheMemoria);
		cacheMemoria.setBounds(10, 117, 261, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(cacheMemoria);

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
			public void actionPerformed(ActionEvent evento) {
				
				resultado.setText("");
				BigInteger superfat = null;
				BigInteger tmp = new BigInteger(valorASerCalculado.getText());
				try {
					superfat = botaoSelecionado().getSuperFatorial(tmp);
					if (superfat == null)
						valorInvalido(1);
					else if( isNegativo(superfat))
						valorInvalido(2);
					else
						resultado.setText(superfat.toString());

				} catch (InputException e) {
					valorInvalido(1);
				}
			}

			private ISuperFatorial botaoSelecionado() {

				if( semCache.isSelected() ) {
					return spF.get(semCache.getText()); 
				} else 
					if( cacheMemoria.isSelected() )
						return spF.get(cacheMemoria.getText());

				return spF.get(cacheDisco.getText());
			}

			private void valorInvalido(int op) {
				if (op==1)
					resultado.setText("Não é possivel Calcular esse fatorial.Tente com um valor valido");
				else
					resultado.setText("Não é possivel Calcular esse fatorial. Valor Muito Grande. Tente com um valor valido");
			}
		});
		btnCalcular.setBounds(236, 396, 89, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(btnCalcular);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.exit(0);
				int i = JOptionPane.showConfirmDialog(null,"Deseja realmente sair?", "Saída",JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		btnCancelar.setBounds(335, 396, 89, 23);
		frmCalculadoraDeSuperfatorial.getContentPane().add(btnCancelar);
	}
	private boolean isNegativo(BigInteger numero) {
		if (numero.compareTo(BigInteger.ZERO)==-1) {
			return true;
		}
		return false;
	}
}
