package com.mygdx.test;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

//import com.badlogic.gdx.tests.utils.GdxTest;

public class TerrainInstance {

    public Model terrainModel;
    public ModelInstance terrainInstance;
    public ModelBatch modelBatch;
    public Material material;
    public Vector3[] points;
    public Vector3[][] triangles;


    public TerrainInstance() {

        Vector3 t1 = new Vector3(5,0,0);
        Vector3 t2 = new Vector3(5,1,0);
        Vector3 t3 = new Vector3(5,0,1);
        Vector3 t4 = new Vector3(1,0,0);
        Vector3 t5 = new Vector3(1,1,0);
        Vector3 t6 = new Vector3(1,0,1);
        Vector3 t7 = new Vector3(2,0,0);
        Vector3 t8 = new Vector3(2,1,0);
        Vector3 t9 = new Vector3(2,0,1);
        Vector3 t10 = new Vector3(3,0,0);
        Vector3 t11 = new Vector3(3,1,0);
        Vector3 t12 = new Vector3(3,0,1);
        Vector3 t13 = new Vector3(4,0,0);
        Vector3 t14 = new Vector3(4,1,0);
        Vector3 t15 = new Vector3(4,0,1);



        triangles = new Vector3[][]{{t1, t2, t3}, {t4,t5,t6}, {t7,t8,t9}, {t10,t11,t12}, {t13,t14,t15}};

        material = new Material(ColorAttribute.createDiffuse(Color.GREEN));
        createTerrain();
    }

    public ModelInstance getTerrain() {

        return terrainInstance;
    }

    public void createTerrain() {

        int attr = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
        Texture myTexture = new Texture("badlogic.jpg");

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        modelBuilder.manage(myTexture);
        Random r = new Random();

        //creteTriangles();

        for(int i = 0; i < triangles.length; i++ ) {
            modelBuilder.part("face" + i+1, GL20.GL_TRIANGLES, attr,
                    new Material(ColorAttribute.createDiffuse(r.nextFloat(), r.nextFloat(), r.nextFloat(), 1)))
                    .triangle(triangles[i][0],triangles[i][1],triangles[i][2]);
        }
        //terrainModel = modelBuilder.createCone(5f, 5f, 5f, 50, material, Usage.Position | Usage.Normal);
        terrainInstance = new ModelInstance(modelBuilder.end(), 0, 0, 0);

    }
//    public void creteTriangles(){
//
//
//
//    }
}

/*

 */