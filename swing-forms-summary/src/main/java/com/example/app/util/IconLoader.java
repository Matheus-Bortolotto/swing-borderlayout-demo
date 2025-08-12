
package com.example.app.util;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public final class IconLoader {
    private IconLoader(){}

    public static ImageIcon load(String path, int size) {
        URL url = IconLoader.class.getResource(path);
        if (url == null) return new ImageIcon();
        Image img = new ImageIcon(url).getImage();
        Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
