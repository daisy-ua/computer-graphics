package com.sample;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class Main extends JFrame {
    static SimpleUniverse universe;
    static Scene staircase1, staircase2, ball;
    static BranchGroup root;
    static Canvas3D canvas;

    static TransformGroup wholeBall;
    static Transform3D transform3D;

    public Main() throws IOException {
        configureWindow();
        configureCanvas();
        configureUniverse();
        addStairsToUniverse();
        addBallToUniverse();
        addAppearanceToObjects();
        addColorBackground();
        addLightToUniverse();
        ChangeViewAngle();
        root.compile();
        universe.addBranchGraph(root);
    }

    private void configureWindow()  {
        setTitle("lab5");
        setSize(760,680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas(){
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        getContentPane().add(canvas,BorderLayout.CENTER);
    }

    private void configureUniverse(){
        root = new BranchGroup();
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();
    }

    private void addStairsToUniverse() throws IOException{
        staircase1 = getSceneFromFile("source/staircase1.obj");
        staircase2 = getSceneFromFile("source/staircase1.obj");
        BranchGroup group = new BranchGroup();
        TransformGroup tg = new TransformGroup();
        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(new Vector3f(-1.2f, .4f, 0));
        tg.setTransform(transform3D);
        tg.addChild(staircase1.getSceneGroup());
        group.addChild(tg);
        group.addChild(staircase2.getSceneGroup());
        root = group;
    }

    public void addBallToUniverse() throws IOException {
        ball = getSceneFromFile("source/pokeball.obj");
        wholeBall = new TransformGroup();
        wholeBall.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transform3D = new Transform3D();
        transform3D.setTranslation(new Vector3f(-2.3f, .7f, 0));
        transform3D.setScale(0.1);
        wholeBall.setTransform(transform3D);
        wholeBall.addChild(ball.getSceneGroup());
        root.addChild(wholeBall);
    }

    Texture getTexture(String path) {
        TextureLoader textureLoader = new TextureLoader(path,"RGB",canvas);
        Texture texture = textureLoader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor( new Color4f( 0.0f, 1.0f, 0.0f, 0.0f ) );
        return texture;
    }

    Material getMaterial() {
        Material material = new Material();
        material.setAmbientColor (new Color3f(.15f, .15f, .15f));
        material.setDiffuseColor (new Color3f(1f, 1f, 1f));
        material.setSpecularColor(new Color3f(0f, 0f, 0));
        material.setEmissiveColor(new Color3f(0f, 0f, 0));
        material.setShininess(0.3f);
        return material;
    }

    private void addAppearance(Shape3D object, String texturePath){
        Appearance objectAppearance = new Appearance();
        objectAppearance.setTexture(getTexture(texturePath));
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        texAttr.setPerspectiveCorrectionMode(TextureAttributes.NICEST);
        objectAppearance.setTextureAttributes(texAttr);
        objectAppearance.setMaterial(getMaterial());
        object.setAppearance(objectAppearance);
    }

    private void addAppearanceToObjects() {
        addAppearance((Shape3D) staircase1.getNamedObjects().get("stairsteps_layer1"), "source/texture.jpg");
        addAppearance((Shape3D) staircase2.getNamedObjects().get("stairsteps_layer1"), "source/texture.jpg");
        addAppearance((Shape3D) ball.getNamedObjects().get("mesh_soccer_ball"), "source/ball.png");
    }

    private void addColorBackground(){
        Background background = new Background(new Color3f(Color.GREEN));
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }


    private void ChangeViewAngle(){
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        Transform3D vpTranslation = new Transform3D();
        Vector3f translationVector = new Vector3f(-.6F, .8F, 3.8F);
        vpTranslation.setTranslation(translationVector);
        vpGroup.setTransform(vpTranslation);
    }

    private void addLightToUniverse() {
        Color3f directionalLightColor = new Color3f(Color.WHITE);
        Color3f ambientLightColor = new Color3f(Color.WHITE);
        Vector3f lightDirection = new Vector3f(-3F, -3F, -3F);

        AmbientLight ambientLight = new AmbientLight(ambientLightColor);
        DirectionalLight directionalLight = new DirectionalLight(directionalLightColor, lightDirection);

        Bounds influenceRegion = new BoundingSphere();
        Transform3D transform3D = new Transform3D();
        transform3D.setScale(8);
        influenceRegion.transform(transform3D);

        ambientLight.setInfluencingBounds(influenceRegion);
        directionalLight.setInfluencingBounds(influenceRegion);
        root.addChild(ambientLight);
        root.addChild(directionalLight);
    }

    public static Scene getSceneFromFile(String location) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        return file.load(new FileReader(location));
    }

    public static void main(String[]args){
        try {
            Main window = new Main();
            new Animation(wholeBall, transform3D, window);
            window.setVisible(true);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
