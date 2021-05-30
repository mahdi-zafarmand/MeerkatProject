/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.elements;

import javafx.scene.image.Image;

/**
 *
 * @author talat
 */
public class MeerkatVertexImage extends Image {
    private final String url;

    public MeerkatVertexImage(String url) {
        super(url);
        this.url = url;
    }

    public String getURL() {
        return url;
    }
}