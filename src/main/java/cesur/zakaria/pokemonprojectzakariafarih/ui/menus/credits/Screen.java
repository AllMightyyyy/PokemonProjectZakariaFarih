package cesur.zakaria.pokemonprojectzakariafarih.ui.menus.credits;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;


public class Screen extends JPanel implements ActionListener {
    Timer creditTimer = new Timer(20, this);
    String text;
    int textY = 600;
    public Screen() {
        playMusic("cesur/zakaria/pokemonprojectzakariafarih/ui/menus/credits/wemadeit.wav", 0.05f);
        JFrame window = new JFrame("Credit Roll");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.add(this);
        window.setVisible(true);
        this.setBackground(Color.BLACK);

        text = "APPLICATION TITLE\n" +
                "\n" +
                "A Solo Venture by\n" +
                "Zakaria Farih\n" +
                "\n" +
                "Development, Design, & Programming\n" +
                "Zakaria Farih\n" +
                "\n" +
                "Artwork & Visual Design\n" +
                "Zakaria Farih\n" +
                "\n" +
                "Music and Sound Effects\n" +
                "Zakaria Farih\n" +
                "\n" +
                "Special Thanks\n" +
                "To my family and friends for their support,\n" +
                "And to you, for experiencing my creation.\n" +
                "\n" +
                "Your encouragement and feedback have been invaluable.\n" +
                "\n" +
                "© 2024 Zakaria LLC. All rights reserved.";


        creditTimer.start();
    }
    public void playMusic(String filePath, float volume) {
        try {
            URL url = this.getClass().getClassLoader().getResource(filePath);
            if (url == null) {
                System.out.println("Resource not found: " + filePath);
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            if (volume != 1f) { // 1f is default volume (no change). Check if volume change is needed.
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                // Convert volume from linear scale to dB. If volume is 0.0, this will mute the clip.
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
            clip.setMicrosecondPosition(27000000);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Timers New Roman", Font.PLAIN, 28));
        g2d.setColor(Color.white);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int y = textY;

        for(String line : text.split("\n")) {
            int stringLength = (int)g2d.getFontMetrics().getStringBounds(line, g2d).getWidth();
            int x = getWidth()/2 - stringLength/2;
            g2d.drawString(line, 200, y += 28);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        textY--;
        if(textY < -400) {
            creditTimer.stop();
        }

        repaint();
    }
}
