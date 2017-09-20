/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulasistasiunradio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import static simulasistasiunradio.InterfaceMain.Input;

/**
 *
 * @author gdwyn
 */
public class DataFile {
    String formatText = "Eek document";
    String format = "eek";
    
    public void load(InterfaceMain main) {
        JFileChooser openFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(formatText,format);
        openFile.setFileFilter(filter);
        int returnVal = openFile.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = openFile.getSelectedFile();
            String path = file.toString();
            if(!path.endsWith("."+format)) {
                new PopUp().show_error("Format file tidak sesuai","File");
            }
            else {
                try {
                    FileReader reader = new FileReader(path);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    ArrayList<Koordinat> points = new ArrayList();
                    while ((line = bufferedReader.readLine()) != null) {
                        String data[] = line.split(" ");
                        if(data.length == 3) {
                            int x = Integer.valueOf(data[0]);
                            int y = Integer.valueOf(data[1]);
                            int radius = Integer.valueOf(data[2]);
                            Koordinat temp = new Koordinat(x,y,radius);
                            points.add(temp);
                        }
                        else break;
                    }
                    reader.close();
                    if(points.size() > 0) {
                        main.Input.clear();
                        main.Input.clear();
                        main.Input.points = points;
                        main.Input.repaint();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void save(InterfaceKesimpulan kesimpulan) {
        JFileChooser saveFile = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(formatText,format);
        saveFile.setFileFilter(filter);
        int returnVal = saveFile.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = saveFile.getSelectedFile();
            
            //clear jika file sudah isi data
            PrintWriter writers;
            try {
                writers = new PrintWriter(file);
                writers.print("");
                writers.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InterfaceKesimpulan.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String path = file.toPath().toString();
            if(!path.endsWith("."+format)) path+=("."+format);
            try {
                if(kesimpulan.main.Input.points != null) {
                    FileWriter writer = new FileWriter(path, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    for(int i=0;i<kesimpulan.main.Input.points.size();i++) {
                        bufferedWriter.write(kesimpulan.main.Input.points.get(i).x+" "+kesimpulan.main.Input.points.get(i).y+" "+kesimpulan.main.Input.points.get(i).radius);
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}