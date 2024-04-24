package GUI;

import Engine.Szamolo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class GUI {

    JFrame keret;
    SzamolPanel szPanel;
    Dimension keretMeret = new Dimension(1000, 700);

    public GUI(){
        keret = new JFrame("Másodfokú egyenlet megoldó");
        keret.setSize(keretMeret);
        keret.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        keret.setLayout(new BorderLayout());

        szPanel = new SzamolPanel(this);
        keret.add(szPanel, BorderLayout.CENTER);

        keret.setVisible(true);
    }

    public static class SzamolPanel extends JPanel{

        JButton szamol;
        JButton nullaz;
        JButton exit;
        GUI cGui;
        JTextField aBevisz;
        JTextField bBevisz;
        JTextField cBevisz;
        JTextField dEredm;
        JLabel aLabel;
        JLabel bLabel;
        JLabel cLabel;
        JLabel x1Label;
        JLabel x2Label;
        JLabel dLabel;
        JLabel cimLabel;
        JLabel errorLabel;

        JTextField eredm1;
        JTextField eredm2;

        Dimension panelDim = new Dimension(1000, 700);

        public SzamolPanel(GUI iGui){

            cGui = iGui;

            aBevisz = new JTextField();
            bBevisz = new JTextField();
            cBevisz = new JTextField();
            eredm1 = new JTextField();
            eredm2 = new JTextField();
            dEredm = new JTextField();

            aLabel = new JLabel("a:");
            bLabel = new JLabel("b:");
            cLabel = new JLabel("c:");
            dLabel = new JLabel("D (Ha <0 nincs eredmeny) :");

            cimLabel = new JLabel("Masodfoku egyenlet megoldo by VZalan");
            errorLabel = new JLabel("Nem letezik az egyenletnek valos megoldasa");
            x1Label = new JLabel("X1:");
            x2Label = new JLabel("X2:");

            this.setSize(panelDim);
            this.setLayout(null);
            this.setBackground(new Color(153, 204, 255));

            nullaz = new JButton("Nullaz");
            szamol = new JButton("Szamol");
            exit = new JButton("Kilepes");

            aBevisz.setFont(new Font("Arial", Font.BOLD, 20));
            bBevisz.setFont(new Font("Arial", Font.BOLD, 20));
            cBevisz.setFont(new Font("Arial", Font.BOLD, 20));
            eredm1.setFont(new Font("Arial", Font.BOLD, 20));
            eredm2.setFont(new Font("Arial", Font.BOLD, 20));
            dEredm.setFont(new Font("Arial", Font.BOLD, 20));

            nullaz.setFont(new Font("Arial", Font.BOLD, 20));
            szamol.setFont(new Font("Arial", Font.BOLD, 20));
            exit.setFont(new Font("Arial", Font.BOLD, 20));

            aLabel.setFont(new Font("Arial", Font.BOLD, 20));
            bLabel.setFont(new Font("Arial", Font.BOLD, 20));
            cLabel.setFont(new Font("Arial", Font.BOLD, 20));
            dLabel.setFont(new Font("Arial", Font.BOLD, 20));

            cimLabel.setFont(new Font("Arial", Font.BOLD, 25));
            cimLabel.setForeground(new Color(0, 153, 0));
            cimLabel.setBounds(250, 10, 500, 75);

            errorLabel.setFont(new Font("Arial", Font.BOLD, 25));
            errorLabel.setForeground(new Color(153, 0, 0));
            errorLabel.setBounds(200, 470, 600, 75);

            x1Label.setFont(new Font("Arial", Font.BOLD, 20));
            x1Label.setBounds(360, 140, 100, 75);
            x2Label.setFont(new Font("Arial", Font.BOLD, 20));
            x2Label.setBounds(360, 240, 100, 75);

            dLabel.setFont(new Font("Arial", Font.BOLD, 20));
            dLabel.setBounds(140, 390, 300, 75);

            aLabel.setFont(new Font("Arial", Font.BOLD, 20));
            aLabel.setBounds(680, 90, 70, 75);
            bLabel.setFont(new Font("Arial", Font.BOLD, 20));
            bLabel.setBounds(680, 190, 70, 75);
            cLabel.setFont(new Font("Arial", Font.BOLD, 20));
            cLabel.setBounds(680, 290, 70, 75);


            nullaz.setBounds(550, 550, 150, 75);
            exit.setBounds(750, 550, 150, 75);
            szamol.setBounds(350, 550, 150, 75);

            nullaz.setBackground(new Color(50, 36, 240));
            exit.setBackground(new Color(255, 0, 0));
            szamol.setBackground(new Color(0, 204, 0));

            szamol.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eredm1.setText("");
                    eredm2.setText("");

                    dEredm.setText("");

                    errorLabel.setVisible(false);
                    if(!aBevisz.getText().equals("") && !bBevisz.getText().equals("") && !cBevisz.getText().equals("")){
                        try{
                           double a = Double.parseDouble(aBevisz.getText());
                           double b = Double.parseDouble(bBevisz.getText());
                           double c = Double.parseDouble(cBevisz.getText());
                           DecimalFormat dc = new DecimalFormat("0.00");
                           Szamolo szam = new Szamolo(a, b, c);
                           dEredm.setText(dc.format(szam.getD()));
                           if(szam.getD() < 0){
                               errorLabel.setVisible(true);
                           }else{
                               eredm1.setText(dc.format(szam.getX1()));
                               eredm2.setText(dc.format(szam.getX2()));
                           }
                        }catch (NumberFormatException exception){
                            System.out.println();
                        }
                    }
                }
            });

            nullaz.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    eredm1.setText("");
                    eredm2.setText("");
                    aBevisz.setText("");
                    bBevisz.setText("");
                    cBevisz.setText("");
                    dEredm.setText("");
                    errorLabel.setVisible(false);
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            aBevisz.setBounds(700, 100, 100, 50);
            bBevisz.setBounds(700, 200, 100, 50);
            cBevisz.setBounds(700, 300, 100, 50);
            eredm1.setBounds(400, 150, 100, 50);
            eredm1.setEditable(false);
            eredm2.setBounds(400, 250, 100, 50);
            eredm2.setEditable(false);
            dEredm.setBounds(400, 400, 100, 50);
            dEredm.setEditable(false);

            this.add(cimLabel);

            this.add(errorLabel);
            errorLabel.setVisible(false);

            this.add(nullaz);
            this.add(szamol);
            this.add(exit);

            this.add(x1Label);
            this.add(x2Label);
            this.add(dLabel);
            this.add(aLabel);
            this.add(bLabel);
            this.add(cLabel);

            this.add(aBevisz);
            this.add(bBevisz);
            this.add(cBevisz);
            this.add(eredm1);
            this.add(eredm2);
            this.add(dEredm);


            this.setVisible(true);
        }


    }


}
