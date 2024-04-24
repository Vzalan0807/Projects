package Chess.GUI;

import Chess.Babuk.*;
import Chess.Szin;
import Chess.jatekos.LepesValosit;
import Chess.tabla.Lepes;
import Chess.tabla.Mezo;
import Chess.tabla.Tabla;
import Chess.tabla.TablaUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import static Chess.tabla.Lepes.*;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;
import static javax.swing.WindowConstants.*;

public class SakkTabla {

    private final JFrame jatekPanel;

    int feketeLepesSzam;
    int feherLepesszam;

    private Tabla jatekTabla;
    private final UtottBabukPanel utottBabukPanel;
    private final LepesNyilvantarto lepesNyilvantartas;
    private TablaPanel tablaPanel;
    private final StartPanel startPanel;
    private final EndPanel endPanel;
    private TopListaPanel topListaPanel;
    private static final Dimension jatekDimension = new Dimension(800, 800);
    private static final Dimension tablaPanelDim = new Dimension(400, 400);
    private static final Dimension mezoPanelDim = new Dimension(20, 20);
    private static final String iconPath = "icons/";

    private Mezo kiinduloPont;
    private Mezo celMezo;
    private Babu mozgatottBabu;

    private String feherNev;
    private String feketeNev;

    boolean ujJatek;

    //A fo osztalya a GUI-nak
    public SakkTabla(){


        jatekPanel = new JFrame("Sakk powered by: Varju Zalan Peter");
        final JMenuBar tablaMenu = menuBarLetrehoz();

        //Inicializálás
        this.lepesNyilvantartas = new LepesNyilvantarto();
        this.jatekPanel.setLayout(new BorderLayout());
        this.jatekPanel.setSize(jatekDimension);
        this.jatekPanel.add(tablaMenu, BorderLayout.NORTH);
        this.jatekPanel.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.utottBabukPanel = new UtottBabukPanel();
        this.startPanel = new StartPanel();
        this.endPanel = new EndPanel(this);
        jatekPanel.add(startPanel, BorderLayout.CENTER);
        this.jatekPanel.setVisible(true);
        ujJatek = false;

        //KezdésGomb létrehozunk vele egy új táblát
        startPanel.kezdGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(startPanel.getFeherNev() != null && startPanel.getFeketeNev() != null && !startPanel.getFeketeNev().equals(startPanel.getFeherNev())){
                    feherNev = startPanel.getFeherNev();
                    feketeNev = startPanel.getFeketeNev();
                    jatekTabla = Tabla.alapTabla();
                    tablaPanel = new TablaPanel();
                    tablaToFile(null);
                    lepesNyilvantartas.nyilvantartoToFile();
                    jatekPanel.add(tablaPanel, BorderLayout.CENTER);
                    jatekPanel.add(utottBabukPanel, BorderLayout.WEST);
                    System.out.println(jatekTabla.getMezo(54).getBabu().isElso());
                    startPanel.setVisible(false);
                }
            }
        });

        //Korábbi betöltése gomb --> Betöltjük a korábbi pályát ha van ilyen, ha nincs indítunk egy újat
        startPanel.korabbiBetoltGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startPanel.getFeherNev() != null && startPanel.getFeketeNev() != null && !startPanel.getFeketeNev().equals(startPanel.getFeherNev())) {
                    String[] korabbiString = tablaFromFile();
                    Szin ujKezd = szinFromFile();
                    feherNev = startPanel.getFeherNev();
                    feketeNev = startPanel.getFeketeNev();
                    if (korabbiString == null) {
                        jatekTabla = Tabla.alapTabla();
                    } else {
                        lepesNyilvantartas.nyilvantartoFromFile();
                        jatekTabla = Tabla.korabbiBetolt(korabbiString, ujKezd);
                        utottBabukPanel.hozzaAd(lepesNyilvantartas);
                    }
                    tablaPanel = new TablaPanel();
                    jatekPanel.add(tablaPanel, BorderLayout.CENTER);
                    jatekPanel.add(utottBabukPanel, BorderLayout.WEST);
                    startPanel.setVisible(false);
                }
            }
        });

        // Ha matt vagy patt van akkor endpanel új játék gombjával lehet új játékot indítani
        endPanel.ujJatek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               endPanel.ujTabla.jatekPanel.dispose();
               endPanel.ujTabla = new SakkTabla();
            }
        });

        //A kilépés gombal pedig egyszerűen ki lehet lépni
        endPanel.kilepes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lepesNyilvantartas.nyilvantartoToFile();
                tablaToFile(null);
                System.exit(0);
            }
        });

    }

    //Elmenti a bemeneti tábla állását egy file-ba illetve azt is, hogy ki a soronkövetkező
    public void tablaToFile(Tabla inTabla){
        File f = new File("ElozoGame.txt");
        if(!f.exists()){
            try{
                f.createNewFile();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        try {
            FileWriter fw = new FileWriter(f);
            if(inTabla == null){
                fw.close();
            }else {
                for (int i = 0; i < 64; i++) {

                    Mezo mezo = inTabla.getMezo(i);
                        //Ír egy sima - jelet ha nincs Bábú a mezőn
                    if (mezo.getBabu() == null) {
                        fw.write("- ");

                        //Fehér Bábúk mentése
                    } else if (mezo.getBabu().getSzin().equals(Szin.FEHER)) {
                        if (mezo.getBabu().getFajta().isKiraly()) {
                            fw.write("WK ");
                        } else if (mezo.getBabu().getFajta().isKiralyno()) {
                            fw.write("WQ ");
                        } else if (mezo.getBabu().getFajta().isFuto()) {
                            fw.write("WF ");
                        } else if (mezo.getBabu().getFajta().isBastya()) {
                            fw.write("WB ");
                        } else if (mezo.getBabu().getFajta().isCsiko()) {
                            fw.write("WC ");
                        } else if (mezo.getBabu().getFajta().isGyalog()) {
                            fw.write("WG ");
                        }

                        //Fekete Bábúk mentése
                    } else if (mezo.getBabu().getSzin().isFekete()) {
                        if (mezo.getBabu().getFajta().isKiraly()) {
                            fw.write("BK ");
                        } else if (mezo.getBabu().getFajta().isKiralyno()) {
                            fw.write("BQ ");
                        } else if (mezo.getBabu().getFajta().isFuto()) {
                            fw.write("BF ");
                        } else if (mezo.getBabu().getFajta().isBastya()) {
                            fw.write("BB ");
                        } else if (mezo.getBabu().getFajta().isCsiko()) {
                            fw.write("BC ");
                        } else if (mezo.getBabu().getFajta().isGyalog()) {
                            fw.write("BG ");
                        }
                    }
                }
                fw.write("\n");
                fw.write(inTabla.getJelenJatekos().getSzin().toString());
                fw.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //Beolvas egy táblát file-ból String formátumban(ezt ezután a megfelelő metódus táblává konvertálja
    public String[] tablaFromFile(){

        File f = new File("ElozoGame.txt");
        if(!f.exists()){
            System.out.println("Nincs ilyen file");
            return null;
        }

        String[] visszaTomb;

        try {

            Scanner sc = new Scanner(new FileReader(f));
            String sor = sc.nextLine();

            if(sor.isEmpty()){
                return null;
            }

            visszaTomb = sor.split(" ");
            return visszaTomb;

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    //Beolvassa az éppen soron levő játékost a file-ból
    public Szin szinFromFile(){
        File f = new File("ElozoGame.txt");
        try {
            Scanner sc = new Scanner(new FileReader(f));
            String sor = "";
            while(sc.hasNextLine()){
                sor = sc.nextLine();
            }
            if(sor.equals("W")){
                return Szin.FEHER;
            }else if(sor.equals("B")){
                return Szin.FEKETE;
            }else{
                return null;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Létrehozza a fenti menü sávot
    private JMenuBar menuBarLetrehoz() {
        final JMenuBar vissza = new JMenuBar();
        vissza.add(fileMenuLetrehoz());
        return vissza;
    }

    //Létrehozza rajta a File opciót
    private JMenu fileMenuLetrehoz() {


        final JMenu fileMenu = new JMenu("File");

        final JMenuItem topList = new JMenuItem("Toplista");

        //ha a toplist-ra nyomunk akkor kiadja az első 3 embert aki a legkevesebb lépésből adott mattot
        topList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(tablaPanel != null){
                   tablaPanel.setVisible(false);
               }
               utottBabukPanel.setVisible(false);
               startPanel.setVisible(false);
               topListaPanel = new TopListaPanel();
               jatekPanel.add(topListaPanel);
               topListaPanel.setVisible(true);
               topListaPanel.visszaGomb.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       topListaPanel.setVisible(false);
                       if(tablaPanel != null){
                           tablaPanel.setVisible(true);
                           startPanel.setVisible(false);
                           utottBabukPanel.setVisible(true);
                       }else {
                           startPanel.setVisible(true);
                           utottBabukPanel.setVisible(true);
                       }
                   }
               });
            }
        });

        //Kilép a játékból és elmenti a pályát (A pályát csak így tudjuk menteni, mivel így tudunk szabályosan kilépni a játékból
        final JMenuItem exitButton = new JMenuItem("Mentes es Kilepes");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablaToFile(jatekTabla);
                lepesNyilvantartas.nyilvantartoToFile();
                System.exit(0);
            }
        });

        fileMenu.add(topList);
        fileMenu.add(exitButton);
        return fileMenu;
    }

    //Ez a panel tartalmazza a mezoket tartalmazo paneleket
    private class TablaPanel extends JPanel{
        final List<MezoPanel> mezoPanelList;

        TablaPanel(){
            super(new GridLayout(8,8));
            this.mezoPanelList = new ArrayList<>();
            for(int i = 0; i < 64; i++){
                final MezoPanel mezoPanel = new MezoPanel(this, i);
                this.mezoPanelList.add(mezoPanel);
                add(mezoPanel);
            }
            setPreferredSize(SakkTabla.tablaPanelDim);
            validate();
        }

        //Megcsinálja a táblát
        public void tablaRajzol(Tabla jatekTabla){
            removeAll();
            for(final MezoPanel mPanel : mezoPanelList){
                mPanel.mezoRajzol(jatekTabla);
                add(mPanel);
            }
            validate();
            repaint();
        }

    }

    // A kezdéskor megjelenő panel
    private static class StartPanel extends JPanel{

        JTextField feherNev;
        JTextField feketeNev;

        JButton kezdGomb;

        JButton korabbiBetoltGomb;


        StartPanel(){
            super(null);
            setPreferredSize(new Dimension(350, 350));
            JLabel text1 = new JLabel("Feher Jatekos neve: ");
            text1.setFont(new Font("Arial", Font.BOLD, 18));
            text1.setBounds(100, 100, 200, 100);
            JLabel text2 = new JLabel("Fekete Jatekos neve: ");
            text2.setFont(new Font("Arial", Font.BOLD, 18));
            text2.setBounds(100, 200, 200, 100);


            feherNev = new JTextField();
            feherNev.setBounds(300, 125, 200, 50);
            feherNev.setFont(new Font("Arial", Font.BOLD, 16));
            feketeNev = new JTextField();
            feketeNev.setFont(new Font("Arial", Font.BOLD, 16));
            feketeNev.setBounds(300, 225, 200, 50);

            //Elinditjuk vele a játékot
            kezdGomb = new JButton("Kezdes");
            kezdGomb.setFont(new Font("Arial", Font.BOLD, 18));
            kezdGomb.setBounds(300, 400, 200, 120);

            //Betöltünk vele korábbi játékot
            korabbiBetoltGomb = new JButton("Elozo betoltese");
            korabbiBetoltGomb.setFont(new Font("Arial", Font.BOLD, 18));
            korabbiBetoltGomb.setBounds(300, 600, 200, 120);


            add(feherNev);
            add(feketeNev);
            add(kezdGomb);
            add(korabbiBetoltGomb);
            add(text1);
            add(text2);
        }

        public String getFeherNev(){
            if(feherNev.getText().isEmpty()){
                return null;
            }
            return feherNev.getText();
        }

        public String getFeketeNev(){
            if(feketeNev.getText().isEmpty()){
                return null;
            }
            return feketeNev.getText();
        }

    }

    // A végén megjelenő panel
    public static class EndPanel extends JPanel{

        JButton ujJatek;
        JButton kilepes;
        JLabel kiiras;

        public SakkTabla ujTabla;

        public EndPanel(SakkTabla inTabla){
            super(null);
            ujTabla = inTabla;
            setPreferredSize(new Dimension(350, 350));
            kiiras = new JLabel();
            ujJatek = new JButton("Uj Jatek");
            kilepes = new JButton("Kilepes");
            ujJatek.setFont(new Font("Arial", Font.BOLD, 18));
            ujJatek.setBounds(300, 500, 200, 70);
            kilepes.setFont(new Font("Arial", Font.BOLD, 18));
            kilepes.setBounds(300, 600, 200, 70);
        }

        //Megjeleníti a panelt
        public void megJelenit(String nev){
            kiiras = new JLabel("Vege a jateknak a gyoztes: " + nev);
            kiiras.setFont(new Font("Arial", Font.BOLD, 18));
            kiiras.setBounds(200, 200, 400, 100);
            add(kiiras);
            add(ujJatek);
            add(kilepes);
        }


    }


    //A toplista panelje
    public static class TopListaPanel extends JPanel{

        JLabel elsohely;
        JLabel masodikHely;
        JLabel harmadikHely;
        JButton visszaGomb;


        // A konstruktorban beolvassuka file-bol a listát és figyelünk azokra az esetekre, ha nincs 3 adat a file-ban
        public TopListaPanel(){
            super(null);
            List<String[]> bemenetTomb = new ArrayList<>();

            File fn = new File("Toplista.txt");
            try {
                Scanner sc = new Scanner(new FileReader(fn));
                while (sc.hasNextLine()) {
                    String sor = sc.nextLine();
                    if(sor.isEmpty()){
                        break;
                    }
                    String[] sTomb = sor.split(" ");
                    bemenetTomb.add(sTomb);
                }
                    Collections.sort(bemenetTomb, new Comparator<String[]>() {
                        @Override
                        public int compare(String[] o1, String[] o2) {
                            return Integer.compare(Integer.parseInt(o1[1]), Integer.parseInt(o2[1]));
                        }
                    });

            if(!bemenetTomb.isEmpty()) {
                if (bemenetTomb.size() >= 3) {
                    elsohely = new JLabel("1. hely: " + bemenetTomb.get(0)[0] + bemenetTomb.get(0)[1]);
                    masodikHely = new JLabel("2. hely: " + bemenetTomb.get(1)[0] + bemenetTomb.get(1)[1]);
                    harmadikHely = new JLabel("3. hely: " + bemenetTomb.get(2)[0] + bemenetTomb.get(2)[1]);

                    elsohely.setFont(new Font("Arial", Font.BOLD, 18));
                    elsohely.setBounds(340, 200, 400, 100);

                    masodikHely.setFont(new Font("Arial", Font.BOLD, 18));
                    masodikHely.setBounds(340, 300, 400, 100);

                    harmadikHely.setFont(new Font("Arial", Font.BOLD, 18));
                    harmadikHely.setBounds(340, 400, 400, 100);

                    elsohely.setVisible(true);
                    masodikHely.setVisible(true);
                    harmadikHely.setVisible(true);

                    this.add(elsohely);
                    this.add(masodikHely);
                    this.add(harmadikHely);
                } else if (bemenetTomb.size() == 2) {
                    elsohely = new JLabel("1. hely: " + bemenetTomb.get(0)[0] + bemenetTomb.get(0)[1]);
                    masodikHely = new JLabel("2. hely: " + bemenetTomb.get(1)[0] + bemenetTomb.get(1)[1]);

                    elsohely.setFont(new Font("Arial", Font.BOLD, 18));
                    elsohely.setBounds(340, 200, 400, 100);

                    masodikHely.setFont(new Font("Arial", Font.BOLD, 18));
                    masodikHely.setBounds(340, 300, 400, 100);

                    this.add(elsohely);
                    this.add(masodikHely);
                } else if (bemenetTomb.size() == 1) {
                    elsohely = new JLabel("1. hely: " + bemenetTomb.get(0)[0] + bemenetTomb.get(0)[1]);
                    elsohely.setFont(new Font("Arial", Font.BOLD, 18));
                    elsohely.setBounds(340, 200, 400, 100);
                    this.add(elsohely);
                } else {
                    elsohely = null;
                    masodikHely = null;
                    harmadikHely = null;
                }
            }
                visszaGomb = new JButton("Vissza");
                visszaGomb.setFont(new Font("Arial", Font.BOLD, 18));
                visszaGomb.setBounds(300, 500, 200, 70);
                add(visszaGomb);

            }catch (Exception e){
                e.printStackTrace();
            }
        }




    }

    //A mezők paneljét definiáló osztály
    private class MezoPanel extends JPanel{

        private final int mezoKoord;

        // A konstruktorban minden mezőnek állítunk egy Listenert, hogy tudjuk, mikor melyikre kattintottunk
        MezoPanel(TablaPanel tablaPanel, int inMezKoord){
            super(new GridLayout());
            this.mezoKoord = inMezKoord;
            setPreferredSize(mezoPanelDim);
            mezoSzinBeallit();
            babuIconBeallit(jatekTabla);

            addMouseListener(new MouseListener() {
                @Override
                // Ha jobb egérgombal kattintunk akkor a kijelölt bábút eldobja
                public void mouseClicked(MouseEvent e) {
                    if(isRightMouseButton(e)){
                        kiinduloPont = null;
                        celMezo = null;
                        mozgatottBabu = null;

                    //Ha bal egérgombal kattintuk akkor 2 lehetőség van
                    }else if(isLeftMouseButton(e)){

                        if(kiinduloPont == null){
                            //Elso kattintás
                            kiinduloPont = jatekTabla.getMezo(mezoKoord);
                            mozgatottBabu = kiinduloPont.getBabu();
                            if(mozgatottBabu == null){
                                kiinduloPont = null;
                            }
                            //Második kattintás
                        }else{

                            //A LepesValosít osztály eldönti, hogy egy lépés megvalósítható-e, mivel úgymond egy virtuális táblán elvégzi és megnézi az utána lévő állapotot...
                            //A LepesValosit osztálynál részletesebben..
                            celMezo = jatekTabla.getMezo(mezoKoord);
                            Lepes lepes = Lepes.lepesCsinal(jatekTabla, kiinduloPont.getMezoKoord(), celMezo.getMezoKoord());
                            LepesValosit valosit = jatekTabla.jelenJatekos.lep(lepes);
                            String gyoztNev;

                            if(jatekTabla.getJelenJatekos().getSzin().isFeher()){
                                gyoztNev = feherNev;
                            }else{
                                gyoztNev = feketeNev;
                            }

                            //Ha egy lépés elvégezhető, akkor elvégezzük és növeljük a lépésszámot
                            if(valosit.getLepesAllapot().isVegrehajtva()){


                                jatekTabla = valosit.getTabla();
                                lepesNyilvantartas.addLepes(lepes);


                                if(jatekTabla.getJelenJatekos().getSzin().isFeher()){
                                    feherLepesszam++;
                                }else{
                                    feketeLepesSzam++;
                                }

                                //Ha matt van akkor vége a játéknak és előhozhatjuk az endPanelt
                                if(jatekTabla.getJelenJatekos().isSakkMatt()){
                                    jatekPanel.add(endPanel, BorderLayout.CENTER);
                                    endPanel.megJelenit(gyoztNev);
                                    endPanel.setVisible(true);
                                    tablaPanel.setVisible(false);
                                    utottBabukPanel.setVisible(false);
                                    tablaToFile(null);
                                    if(jatekTabla.getJelenJatekos().getSzin().isFeher()){
                                        gyoztesToFile(gyoztNev, feherLepesszam);
                                    }else{
                                        gyoztesToFile(gyoztNev, feketeLepesSzam);
                                    }

                                //Ha patt van, akkor szintén vége van csak másik szöveget kell kiírni, illetve, nem kell elmenteni a lépését a győztesnek
                                }else if(jatekTabla.getJelenJatekos().isPatt()){
                                    String patt = "Patt helyzet";
                                    jatekPanel.add(endPanel, BorderLayout.CENTER);
                                    endPanel.megJelenit(patt);
                                    endPanel.setVisible(true);
                                    tablaPanel.setVisible(false);
                                    utottBabukPanel.setVisible(false);
                                    tablaToFile(null);
                                }
                            }
                            kiinduloPont = null;
                            celMezo = null;
                            mozgatottBabu =null;
                        }

                        //Minden lépés után frissíteni kell a táblát illetve a leütött bábúkat tartalmazó panelt-t
                        //Ehhez én az invokeLater metódust használom
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                utottBabukPanel.hozzaAd(lepesNyilvantartas);
                                tablaPanel.tablaRajzol(jatekTabla);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            validate();
        }

        //Egy mezőt megalkot és kiemeli, ha ide lehet lépni, beállítja az itt lévő bábú iconját, illetve kiemeli a király, bizonyos esetekben...később..
        public void mezoRajzol(Tabla inTabla){
            mezoSzinBeallit();
            babuIconBeallit(inTabla);
            kiralyKiemel(inTabla);
            lehetsKiemel(inTabla);
            validate();
            repaint();
        }

        //Elmenti a győztes fél lépésszámét file-ba
        public void gyoztesToFile(String gyoztNev, int lepes) {
            File fn = new File("Toplista.txt");
            if(!fn.exists()){
                try {
                    fn.createNewFile();
                }catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            try {
                FileWriter fw = new FileWriter(fn, true);
                fw.write(gyoztNev + ": " + Integer.toString(lepes) + "\n");
                fw.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }


        //Kiemeli a királyt pirossal ha sakk-matt, sárgával-ha sakk, naranccsal-ha patt
        public void kiralyKiemel(Tabla inTabla){
            if(inTabla.getJelenJatekos().isSakkban() && !inTabla.getJelenJatekos().isSakkMatt() && !inTabla.getJelenJatekos().isPatt()){
                int kiralyMezoKoord = inTabla.getJelenJatekos().getJatekosKiraly().getBabuPos();
                for(MezoPanel panel : tablaPanel.mezoPanelList){
                    if(panel.mezoKoord == kiralyMezoKoord){
                        panel.setBackground(Color.YELLOW);
                    }
                }
            }else if(inTabla.getJelenJatekos().isSakkMatt() && inTabla.getJelenJatekos().isSakkban() && !inTabla.getJelenJatekos().isPatt()){
                int kiralyMezoKoord = inTabla.getJelenJatekos().getJatekosKiraly().getBabuPos();
                for(MezoPanel panel : tablaPanel.mezoPanelList){
                    if(panel.mezoKoord == kiralyMezoKoord){
                        panel.setBackground(Color.RED);
                    }
                }
            }else if(inTabla.getJelenJatekos().isPatt() && inTabla.getJelenJatekos().isSakkban() && !inTabla.getJelenJatekos().isSakkMatt()){
                int kiralyMezoKoord = inTabla.getJelenJatekos().getJatekosKiraly().getBabuPos();
                for(MezoPanel panel : tablaPanel.mezoPanelList){
                    if(panel.mezoKoord == kiralyMezoKoord){
                        panel.setBackground(new Color(255, 102,0));
                    }
                }
            }
        }


        //Beállítja a mező színét attól függően, hogy hol helyezkedik el
        private void mezoSzinBeallit() {
            if(TablaUtil.ElsoSor[this.mezoKoord] || TablaUtil.HarmadikSor[this.mezoKoord] || TablaUtil.OtodikSor[this.mezoKoord] || TablaUtil.HetedikSor[this.mezoKoord]){

                setBackground(this.mezoKoord % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK);

            }else if(TablaUtil.MasodikSor[this.mezoKoord] || TablaUtil.NegyedikSor[this.mezoKoord] || TablaUtil.HatodikSor[this.mezoKoord] || TablaUtil.NyolcadikSor[this.mezoKoord]){

                setBackground(this.mezoKoord % 2 != 0 ? Color.LIGHT_GRAY : Color.BLACK);

            }
        }

        //Kiemeli azokat a mezőket ahova tudunk lépni
        private void lehetsKiemel(Tabla inTabla){
                    for(final Lepes lepes : babuLehetsLepesek(inTabla)){
                        if(lepes.getCelKoord() == this.mezoKoord){
                                if(jatekTabla.getMezo(this.mezoKoord).vanBabu()){
                                    //Piros ha ott egy bábút le kell ütnünk
                                    setBackground(Color.RED);
                                }else{
                                    //Zöld ha csak simán oda tudunk lépni
                                    setBackground(Color.GREEN);
                                }
                        }
                    }
        }

        //Visszaadja a jelenlegi bábúnak(amivel lépni szeretnénk) a lehetséges lépéseit
        private Collection<Lepes> babuLehetsLepesek(Tabla inTabla){
            if(mozgatottBabu != null && mozgatottBabu.getSzin() == inTabla.getJelenJatekos().getSzin()){
                return mozgatottBabu.szabLepesek(inTabla);
            }
            return Collections.emptyList();
        }

        //Beállítja az adott Bábúhoz tartozó icont az icons mappából
        private void babuIconBeallit(Tabla tabla){
            this.removeAll();
            if(tabla.getMezo(this.mezoKoord).vanBabu()){
                try {
                    BufferedImage icon = ImageIO.read(new File(iconPath + tabla.getMezo(this.mezoKoord).getBabu().getSzin().toString().substring(0, 1) + tabla.getMezo(this.mezoKoord).getBabu().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(icon)));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }


    //Nyilvántartja az eddigi lépéseket, így lehet tudni, hogy melyek a már leütött bábúk
    //Ez főleg a már leütött bábúk paneljához kell
    public static class LepesNyilvantarto{

        private final List<Lepes> lepesek;

        public LepesNyilvantarto(){
            lepesek = new ArrayList<>();
        }

        public List<Lepes> getLepesek() {
            return this.lepesek;
        }

        void addLepes(final Lepes lepes) {
            this.lepesek.add(lepes);
        }

        void nyilvantartoToFile(){
            File file = new File("EddigiUtottek.txt");
            if(!file.exists()){
                try {
                    file.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            try {

                FileWriter fw = new FileWriter(file);
                for(Lepes lepes : lepesek){
                    if(lepes.isTamadas()){
                        fw.write(lepes.getTamadottBabu().getSzin().toString() + lepes.getTamadottBabu().toString() + " ");
                    }
                }
                fw.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        void nyilvantartoFromFile(){
            File file = new File("EddigiUtottek.txt");
            try{

                Scanner sc = new Scanner(new FileReader(file));
                String sor = sc.nextLine();
                String[] sorTomb = sor.split(" ");
                for(int i = 0; i < sorTomb.length; i++){
                    switch (sorTomb[i]){
                        //Fehérek beállítása
                        case "WG":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Gyalog(0, Szin.FEHER)));
                            break;
                        case "WC":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Csiko(0, Szin.FEHER)));
                            break;
                        case "WB":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Bastya(0, Szin.FEHER)));;
                            break;
                        case "WF":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Futo(0, Szin.FEHER)));
                            break;
                        case "WQ":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Kiralyno(0, Szin.FEHER)));
                            break;
                        case "WK":
                            lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Kiraly(0, Szin.FEHER)));;
                            break;

                        //Feketék beállítása
                        case "BG":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEHER, false), 0, new Gyalog(0, Szin.FEKETE)));
                            break;
                        case "BC":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Csiko(0, Szin.FEKETE)));
                            break;
                        case "BB":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Bastya(0, Szin.FEKETE)));
                            break;
                        case "BF":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Futo(0, Szin.FEKETE)));
                            break;
                        case "BQ":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Kiralyno(0, Szin.FEKETE)));
                            break;
                        case "BK":
                            this.lepesek.add(new TamadoLepes(null, new Gyalog(1, Szin.FEKETE, false), 0, new Kiraly(0, Szin.FEKETE)));
                    }

                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

}
