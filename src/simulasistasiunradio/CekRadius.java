/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulasistasiunradio;

/**
 *
 * @author fadhlan
 */
    class CekRadius implements Runnable {
    private int radius = 30;
    private javax.swing.JComboBox<String> z;
    private final Input in;
  
    public CekRadius(javax.swing.JComboBox<String> z, Input in){
        this.z = z;
        this.in = in;
    }
    @Override
    public void run() {
        while(true) {
            int r = Integer.valueOf(z.getSelectedItem().toString());
            if(radius != r) {
                in.radiusLuar = r;
                radius = r;
            }
        }
    }
}
