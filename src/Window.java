import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Window extends JFrame {
    private JButton a2ZlButton;
    private JButton a1ZlButton;
    private JPanel mainWindow;
    private JTextField textField1;
    private JButton potwierdzButton;
    private JButton a5ZlButton;
    private JTextArea bilet;
    private JLabel kwota;
    private JLabel wplaconaKwotaLabel;
    private JButton potwierdzNrRejestrButton;
    private JLabel podajNrLabel;
    private JButton zresetujKwoteButton;
    private JLabel poprawnyNumerRj;
    private JButton drukujButton;
    private JButton anulujButton;
    private int kwotaWplaty = 0;
    private String rejestracja = "";
    private Date data = new Date();

    private void setBilet() {
        Date dataKoncowa = new Date((long) (data.getTime() + (kwotaWplaty * 0.5) * (3600 * 1000)));
        bilet.setText(

                "bilet: \n" +
                        "Nr rejestracyjny pojazdu: " + rejestracja + "\n" +
                        "Data: " + data + "\n" +
                        "Kwota " + kwotaWplaty + " zl \n" +
                        "Ilosc czasu: " + kwotaWplaty * 0.5 + " godziny \n" +
                        "Data koncowa " + dataKoncowa
        );
    }

    public void setInvisible(){
        //making buttons invisible
        potwierdzNrRejestrButton.setVisible(true);
        bilet.setText("");
        a1ZlButton.setVisible(false);
        a2ZlButton.setVisible(false);
        a5ZlButton.setVisible(false);
        wplaconaKwotaLabel.setVisible(false);
        kwota.setVisible(false);
        potwierdzButton.setVisible(false);
        bilet.setVisible(false);
        zresetujKwoteButton.setVisible(false);
        poprawnyNumerRj.setVisible(false);
        drukujButton.setVisible(false);
    }

    Window() {
        this.setTitle("Parkomat");
        this.setContentPane(mainWindow);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.setVisible(true);
        this.pack();
        // on program start make buttons invisible
        setInvisible();


        // actions to buttons
        a1ZlButton.addActionListener(e -> {
            kwotaWplaty += 1;
            kwota.setText(kwotaWplaty + "zl");
        });
        a2ZlButton.addActionListener(e -> {
            kwotaWplaty += 2;
            kwota.setText(kwotaWplaty + "zl");
        });
        a5ZlButton.addActionListener(e -> {
            kwotaWplaty += 5;
            kwota.setText(kwotaWplaty + "zl");
        });
        potwierdzButton.addActionListener(e -> setBilet());
        zresetujKwoteButton.addActionListener(e -> {
            kwotaWplaty = 0;
            kwota.setText(kwotaWplaty + "zl");
            setBilet();
        });

        // on accept button make everything visible
        potwierdzNrRejestrButton.addActionListener(e -> {
            rejestracja = textField1.getText();
            if (rejestracja.length() >= 6 && rejestracja.length() <= 8) {
                podajNrLabel.setText("Numer rejestracyjny:");
                potwierdzNrRejestrButton.setVisible(false);
                a1ZlButton.setVisible(true);
                a2ZlButton.setVisible(true);
                a5ZlButton.setVisible(true);
                wplaconaKwotaLabel.setVisible(true);
                kwota.setVisible(true);
                potwierdzButton.setVisible(true);
                bilet.setVisible(true);
                zresetujKwoteButton.setVisible(true);
                poprawnyNumerRj.setVisible(false);
                drukujButton.setVisible(true);
                setBilet();
            } else {
                poprawnyNumerRj.setVisible(true);
            }
        });

        // open jfilechooser on drukujbutton click
        drukujButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.showDialog(null, "Zapisz");
            File plik = fileChooser.getSelectedFile();
            try {
                FileWriter zapis = new FileWriter(plik + ".txt");
                zapis.write(bilet.getText());
                zapis.close();
            } catch (IOException ex) {
                System.out.println("Nie zapisano pliku");
            }
        });

        // anuluj button restarts everything to start
        anulujButton.addActionListener(e -> {
            data = new Date();
            kwotaWplaty = 0;
            rejestracja = "";
            potwierdzNrRejestrButton.setVisible(true);
            bilet.setText("");
            setInvisible();
        });
    }
}
