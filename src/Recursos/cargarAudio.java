/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Recursos;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 *
 * @author jaime
 */
public class cargarAudio{
    public static Clip cargarSonido(final String ruta){
        Clip clip = null;
        try {
            InputStream is = ClassLoader.class.getResourceAsStream(ruta);
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }
}
