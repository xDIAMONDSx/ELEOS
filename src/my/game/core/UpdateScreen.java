package my.game.core;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class UpdateScreen extends JFrame {
	
	private static final long serialVersionUID = 8168001537955376514L;
	private JPanel contentPane;
	public boolean focus = true;

	/**
	 * Create the frame.
	 */
	public UpdateScreen(String oldVer, String newVer, boolean important) {
		focus = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 204);
		setLocationRelativeTo(null);
		setTitle("A new version is available");
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				focus = false;
			}

			@Override
			public void windowClosed(WindowEvent e) {
				focus = false;
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setSize(300, 300);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblThereIsAn;
        if(important) {
            lblThereIsAn = new JLabel("There is an IMPORTANT update available");
            lblThereIsAn.setBounds(54/8, 12, 1000, 14);
        } else {
            lblThereIsAn = new JLabel("There is an update available");
            lblThereIsAn.setBounds(54-("There is an update available".length()/2), 12, 1000, 14);
        }
		contentPane.add(lblThereIsAn);

		JLabel lblTheNewVersion = new JLabel("The new version is " + oldVer);
		lblTheNewVersion.setBounds(54, 33, 216, 14);
		contentPane.add(lblTheNewVersion);

		JLabel lblYouHaveVersion = new JLabel("You have version " + newVer);
		lblYouHaveVersion.setBounds(54, 59, 194, 14);
		contentPane.add(lblYouHaveVersion);

		JButton btnOk = new JButton("Ok");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				focus = false;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				focus = false;
			}
		});
		btnOk.setBounds(99, 85, 89, 23);
		contentPane.add(btnOk);
		
		JLabel lblHttpsrawgithubusercontentcomxdiamondsxxdiamondsxgithubiomastereleosversionstxt = new JLabel("https://github.com/DropDemBits/ELEOS\r\n");
		lblHttpsrawgithubusercontentcomxdiamondsxxdiamondsxgithubiomastereleosversionstxt.setBounds(12, 128, 1000, 14);
		contentPane.add(lblHttpsrawgithubusercontentcomxdiamondsxxdiamondsxgithubiomastereleosversionstxt);
	}
    
    public void display() {
        setVisible(true);
        
		while (focus) {
			requestFocus();
		}
		setVisible(false);
    }
    
}
