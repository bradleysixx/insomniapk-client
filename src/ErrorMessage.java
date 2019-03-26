import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Jonathan
 */
@SuppressWarnings("serial")
public class ErrorMessage extends javax.swing.JFrame {

	/**
	 * Creates new form ErrorMessage
	 */
	public ErrorMessage(final String message) {
		initComponents(message);
	}

	private void initComponents(String message) {
		try {
			if (message == null || message.trim().isEmpty()) {
				dispose();
				return;
			}
			copyButton = new javax.swing.JButton();
			messageScroll = new javax.swing.JScrollPane();
			errorMessage = new javax.swing.JTextArea(message);
			informationLabel = new javax.swing.JLabel();

			setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			setTitle("Oops, an error occurred!");

			copyButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					StringSelection string = new StringSelection(errorMessage.getText());
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(string, null);
				}
			});

			copyButton.setText("Copy to Clipboard");

			errorMessage.setEditable(false);
			errorMessage.setColumns(20);
			errorMessage.setLineWrap(true);
			errorMessage.setRows(5);
			messageScroll.setViewportView(errorMessage);

			informationLabel.setText("Oh no, an error has occurred! Please copy and paste this error message to the bug reports section of our forum (You need to in order to have a chance for a refund)!");

			javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(messageScroll).addComponent(copyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap()).addGroup(layout.createSequentialGroup().addGap(50, 50, 50).addComponent(informationLabel).addContainerGap(74, Short.MAX_VALUE)));
			layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(20, 20, 20).addComponent(informationLabel).addGap(18, 18, 18).addComponent(messageScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(copyButton).addContainerGap()));

			pack();
			setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// </editor-fold>

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		new ErrorMessage("    f");
	}

	// Variables declaration - do not modify
	private javax.swing.JButton copyButton;
	private javax.swing.JLabel informationLabel;
	private javax.swing.JScrollPane messageScroll;
	private javax.swing.JTextArea errorMessage;
	// End of variables declaration
}