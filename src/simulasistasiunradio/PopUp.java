/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulasistasiunradio;

import javax.swing.JOptionPane;
/**
 *
 * @author gdwyn
 */
public class PopUp {
    public void show_error(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.ERROR_MESSAGE);
    }
    public void show_notif(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    public int show_yesno(String infoMessage, String titleBar) {
        return JOptionPane.showConfirmDialog(null, infoMessage, titleBar, JOptionPane.YES_NO_OPTION);
    }
}
