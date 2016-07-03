package usp.icmc.labes.examples;

import java.util.ArrayList;
import java.util.Random;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;

import usp.icmc.labes.model.ShapeObj;

public class Human implements ShapeObj {

	public BranchGroup createObject(BranchGroup branch) {

		TransformGroup humanBody = new TransformGroup();

		humanBody = createHuman();

		branch.addChild(humanBody);

		branch.setCapability(BranchGroup.ALLOW_DETACH);
		// Have Java 3D perform optimizations on this scene graph.
		// objRoot.compile();

		branch.setUserData(getClass().toString());
		return branch;
	}

	private TransformGroup createHuman() {
		TransformGroup Human_body = new TransformGroup();

		Transform3D tmpTrans = new Transform3D();

		Vector3f tmpVector = new Vector3f();

		// These will be created, attached the scene graph and then the variable
		// will be reused to initialize other sections of the scene graph.
		Cylinder tmpCyl;

		Sphere tmpSphere;

		TransformGroup tmpTG;

		// colors for use in the cones
		Color3f red = new Color3f(1.0f, 0.0f, 0.0f);

		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

		// center the body
		tmpVector.set(0.0f, -1.5f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_body.setTransform(tmpTrans);

		// Set up an appearance to make the body with red ambient,
		// black emmissive, red diffuse and white specular coloring
		Material material = new Material(red, black, red, white, 64);
		Appearance appearance = new Appearance();
		appearance.setMaterial(material);

		// offset and place the cylinder for the body
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, 1.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.75f, 3.0f, appearance);
		tmpTG.addChild(tmpCyl);

		// add the shape to the body
		Human_body.addChild(tmpTG);

		// create the r_shoulder TransformGroup
		TransformGroup Human_r_shoulder = new TransformGroup();
		Human_r_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_r_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		// translate from the waist
		tmpVector.set(-0.95f, 2.9f, -0.2f);
		tmpTrans.set(tmpVector);
		Human_r_shoulder.setTransform(tmpTrans);

		// place the sphere for the r_shoulder
		tmpSphere = new Sphere(0.22f, appearance);
		Human_r_shoulder.addChild(tmpSphere);

		// offset and place the cylinder for the r_shoulder
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance);
		tmpTG.addChild(tmpCyl);

		// add the shape to the r_shoulder
		Human_r_shoulder.addChild(tmpTG);

		// add the shoulder to the body group
		Human_body.addChild(Human_r_shoulder);

		// create the r_elbow TransformGroup
		TransformGroup Human_r_elbow = new TransformGroup();
		Human_r_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_r_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, -1.054f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_r_elbow.setTransform(tmpTrans);

		// place the sphere for the r_elbow
		tmpSphere = new Sphere(0.22f, appearance);
		Human_r_elbow.addChild(tmpSphere);

		// offset and place the cylinder for the r_shoulder
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance);
		tmpTG.addChild(tmpCyl);

		// add the shape to the r_shoulder
		Human_r_elbow.addChild(tmpTG);

		// add the elbow to the shoulder group
		Human_r_shoulder.addChild(Human_r_elbow);

		// create the l_shoulder TransformGroup
		TransformGroup Human_l_shoulder = new TransformGroup();
		Human_l_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_l_shoulder.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.95f, 2.9f, -0.2f);
		tmpTrans.set(tmpVector);
		Human_l_shoulder.setTransform(tmpTrans);

		// place the sphere for the l_shoulder
		tmpSphere = new Sphere(0.22f, appearance);
		Human_l_shoulder.addChild(tmpSphere);

		// offset and place the cylinder for the l_shoulder
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance);
		tmpTG.addChild(tmpCyl);

		// add the shape to the l_shoulder
		Human_l_shoulder.addChild(tmpTG);

		// add the shoulder to the body group
		Human_body.addChild(Human_l_shoulder);

		// create the r_elbow TransformGroup
		TransformGroup Human_l_elbow = new TransformGroup();
		Human_l_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		Human_l_elbow.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tmpVector.set(0.0f, -1.054f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_l_elbow.setTransform(tmpTrans);

		// place the sphere for the l_elbow
		tmpSphere = new Sphere(0.22f, appearance);
		Human_l_elbow.addChild(tmpSphere);

		// offset and place the cylinder for the l_elbow
		tmpTG = new TransformGroup();
		// offset the shape
		tmpVector.set(0.0f, -0.5f, 0.0f);
		tmpTrans.set(tmpVector);
		tmpTG.setTransform(tmpTrans);
		tmpCyl = new Cylinder(0.2f, 1.0f, appearance);
		tmpTG.addChild(tmpCyl);

		// add the shape to the l_elbow
		Human_l_elbow.addChild(tmpTG);

		// add the shoulder to the body group
		Human_l_shoulder.addChild(Human_l_elbow);

		// create the skullbase TransformGroup
		TransformGroup Human_skullbase = new TransformGroup();
		tmpVector.set(0.0f, 3.632f, 0.0f);
		tmpTrans.set(tmpVector);
		Human_skullbase.setTransform(tmpTrans);

		// offset and place the sphere for the skull
		tmpSphere = new Sphere(0.5f, appearance);

		// add the shape to the l_shoulder
		Human_skullbase.addChild(tmpSphere);

		// add the shoulder to the body group
		Human_body.addChild(Human_skullbase);

		return Human_body;

	}
	
	public BranchGroup createObject(TransformGroup humanBody) {

		BranchGroup branch = new BranchGroup();
		branch.addChild(humanBody);

		branch.setCapability(BranchGroup.ALLOW_DETACH);
		// Have Java 3D perform optimizations on this scene graph.
		// objRoot.compile();

		branch.setUserData(getClass().toString());
		return branch;
	}
	

	public ArrayList<BranchGroup> getHumanMutants() {

		ArrayList<BranchGroup> mutantsList = new ArrayList<BranchGroup>();
		
		TransformGroup humanBody = new TransformGroup();
		
		humanBody = createHuman();
		
		int parts = humanBody.numChildren();
		
		for (int i = 0; i < parts - 1; i++) {

			TransformGroup mutantBody = new TransformGroup();
			
			mutantBody = createHuman();
			
			mutantBody.removeChild(i);
			
			// add the mutant plane to the object
			mutantsList.add(createObject(mutantBody));
		}
		
		return mutantsList;
	}
	
}
