package miniTwit;

import java.awt.EventQueue;

/**
 * The Driver class creates a runnable instance of the MiniTwit program.
 * @author K
 *
 */
public class Driver {
	static AdminCPanel cPanel = AdminCPanel.getInstance();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                //
//            }
//        });
		
		 EventQueue.invokeLater(new Runnable() {
	            public void run() {
	                try {
	                    cPanel.setVisible(true);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        });
	}

}
