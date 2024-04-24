package Chess.GUI;

import Chess.Babuk.Babu;
import Chess.GUI.SakkTabla.LepesNyilvantarto;
import Chess.tabla.Lepes;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//A leütött bábúkat tartalmazó panel
public class UtottBabukPanel extends JPanel {

    private final JPanel feherUtottPanel;
    private final JPanel feketeUtottPanel;

    public UtottBabukPanel(){
        super(new BorderLayout());
        setBackground(new Color(51,153,255));
        this.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        this.feherUtottPanel = new JPanel(new GridLayout(8, 2));
        this.feketeUtottPanel = new JPanel(new GridLayout(8, 2));
        this.feherUtottPanel.setBackground(new Color(204, 0, 0));
        this.feketeUtottPanel.setBackground(new Color(153, 0, 0));
        this.add(feketeUtottPanel, BorderLayout.SOUTH);
        this.add(feherUtottPanel, BorderLayout.NORTH);
        this.setPreferredSize(new Dimension(50, 100));
    }

    //Ha meghivjuk ezt a metódust, akkor a fejlécben megadható nyilvántartó alapján újra kalibrálja az eddig leütött bábúkat
    //Ezután sorbaa rendezi őket az értékük alapján
    //Ezekutén pedig kirakja az icon-jukat a panelre
    public void hozzaAd(final LepesNyilvantarto nyilvantarto){

        this.feherUtottPanel.removeAll();
        this.feketeUtottPanel.removeAll();

        final List<Babu> feherUtottBabu = new ArrayList<>();
        final List<Babu> feketeUtottBabu = new ArrayList<>();

        for(Lepes lepes : nyilvantarto.getLepesek()){
            if(lepes.isTamadas()){
                Babu tBabu = lepes.getTamadottBabu();
                if(tBabu.getSzin().isFeher()){
                    feherUtottBabu.add(tBabu);
                }else{
                    feketeUtottBabu.add(tBabu);
                }
            }

        }

        //Osszehasonlítja 2 bábú értékét
        Collections.sort(feherUtottBabu, new Comparator<Babu>() {
            @Override
            public int compare(Babu o1, Babu o2) {
                return Integer.compare(o1.getErtek(), o2.getErtek());
            }
        });

        Collections.sort(feketeUtottBabu, new Comparator<Babu>() {
            @Override
            public int compare(Babu o1, Babu o2) {
                return Integer.compare(o1.getErtek(), o2.getErtek());
            }
        });

        for(Babu fBabu : feherUtottBabu){
            try{
                BufferedImage kep = ImageIO.read(new File("icons/" + fBabu.getSzin().toString().substring(0 ,1) + fBabu.getFajta().toString() + ".gif"));
                ImageIcon icon = new ImageIcon(kep);
                JLabel iconLabel = new JLabel(icon);
                feherUtottPanel.add(iconLabel);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        for(Babu fBabu : feketeUtottBabu){
            try{
                BufferedImage kep = ImageIO.read(new File("icons/" + fBabu.getSzin().toString().substring(0 ,1) + fBabu.getFajta().toString() + ".gif"));
                ImageIcon icon = new ImageIcon(kep);
                JLabel iconLabel = new JLabel(icon);
                feketeUtottPanel.add(iconLabel);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        validate();

    }

}
