package usp.icmc.labes.examples;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;

import com.sun.j3d.utils.geometry.ColorCube;

import usp.icmc.labes.model.ShapeObj;


public class Cube implements ShapeObj {

	private BranchGroup getCube(InterpolatorData interpolator) {

		BranchGroup rootBranchGroup = new BranchGroup();
				
		createCubeMoving(rootBranchGroup, interpolator);

		return rootBranchGroup;

	}

	private static class InterpolatorData {
		private final List<Point3f> positions = new ArrayList<Point3f>();
		private final List<Quat4f> orientations = new ArrayList<Quat4f>();

		void add(Point3f p, float angleDeg) {
			positions.add(p);

			AxisAngle4f a = new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(angleDeg));
			Quat4f q = new Quat4f();
			q.set(a);
			orientations.add(q);
		}

		Point3f[] getPositions() {
			return positions.toArray(new Point3f[0]);
		}

		Quat4f[] getOrientations() {
			return orientations.toArray(new Quat4f[0]);
		}

		float[] getAlphas() {
			float alphas[] = new float[positions.size()];
			float delta = 1.0f / (alphas.length - 1);
			for (int i = 0; i < alphas.length; i++) {
				alphas[i] = i * delta;
			}
			return alphas;
		}
	}

	private static void createCubeMoving(BranchGroup rootBranchGroup, InterpolatorData i) {
		Alpha alpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 6000, 0, 0, 0, 0, 0);
		TransformGroup target = new TransformGroup();
		target.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Transform3D axisOfTransform = new Transform3D();

		RotPosPathInterpolator interpolator = new RotPosPathInterpolator(alpha, target, axisOfTransform, i.getAlphas(),
				i.getOrientations(), i.getPositions());
		interpolator.setSchedulingBounds(new BoundingSphere(new Point3d(), 100.0));

		rootBranchGroup.addChild(target);
		target.addChild(interpolator);
		target.addChild(new ColorCube(0.4));
	}

	public BranchGroup createObject(BranchGroup branch) {

		//movement
		InterpolatorData interpolator = new InterpolatorData();
		
		interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 0.0f);
		interpolator.add(new Point3f(-2.0f, 0.0f, -2.0f), 0.0f);
		interpolator.add(new Point3f(-2.0f, 0.0f, -2.0f), 90.0f);
		interpolator.add(new Point3f(2.0f, 0.0f, -2.0f), 90.0f);
		interpolator.add(new Point3f(2.0f, 0.0f, -2.0f), 180.0f);
		interpolator.add(new Point3f(2.0f, 0.0f, 2.0f), 180.0f);
		interpolator.add(new Point3f(2.0f, 0.0f, 2.0f), 270.0f);
		interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 270.0f);
		interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 0.0f);
		
		branch = getCube(interpolator);

		branch.setCapability(BranchGroup.ALLOW_DETACH);
		// Have Java 3D perform optimizations on this scene graph.
		// objRoot.compile();

		branch.setUserData(getClass().toString());
		return branch;
	}
	
	
	public BranchGroup createObject(BranchGroup branch, InterpolatorData interpolator) {

		branch = getCube(interpolator);

		branch.setCapability(BranchGroup.ALLOW_DETACH);
		// Have Java 3D perform optimizations on this scene graph.
		// objRoot.compile();

		branch.setUserData(getClass().toString());
		return branch;
	}
	
	private float getRandon(float min, float max){
		Random rand = new Random();

		// parameters
		float r = (float) (rand.nextFloat() * (max - min) + min);
		
		return r;
	}
	
	public ArrayList<BranchGroup> getCubeMutants() {

		ArrayList<BranchGroup> mutantsList = new ArrayList<BranchGroup>();

		
		
		for (int i = 0; i < 9; i++) {

			BranchGroup bgMutant = new BranchGroup();
			
			//movement
			InterpolatorData interpolator = new InterpolatorData();
			
			if(i == 0){
				interpolator.add(new Point3f(getRandon(-4, 0), 0.0f, 2.0f), 0.0f); //mutant
			}else{
				interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 0.0f);
			}
			
			if(i == 1){
				interpolator.add(new Point3f(2.0f, 0.0f, getRandon(-4, 0)), 0.0f); //mutant
			}else{
				interpolator.add(new Point3f(-2.0f, 0.0f, -2.0f), 0.0f);
			}
			
			if(i == 2){
				interpolator.add(new Point3f(2.0f, 0.0f, 2.0f), 0.0f); //mutant
			}else{
				interpolator.add(new Point3f(-2.0f, 0.0f, -2.0f), 90.0f);
			}
			
			if(i == 3){
				interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 180.0f); //mutant
			}else{
				interpolator.add(new Point3f(2.0f, 0.0f, -2.0f), 90.0f);
			}
			
			if(i == 4){
				interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 270.0f); //mutant
			}else{
				interpolator.add(new Point3f(2.0f, 0.0f, -2.0f), 180.0f);
			}
			
			if(i == 5){
				interpolator.add(new Point3f(-2.0f, 0.0f, -2.0f), 270.0f); //mutant
			}else{
				interpolator.add(new Point3f(2.0f, 0.0f, 2.0f), 180.0f);
			}
			
			if(i == 6){
				interpolator.add(new Point3f(-2, 0.0f, -2.0f), 270.0f); //mutant
			}else{
				interpolator.add(new Point3f(2.0f, 0.0f, 2.0f), 270.0f);
			}
			
			if(i == 7){
				interpolator.add(new Point3f(2.0f, 0.0f, -2.0f), 270.0f); //mutant
			}else{
				interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 270.0f);
			}
			
			if(i == 8){
				interpolator.add(new Point3f(2.0f, 0.0f, -2.0f), 180.0f); //mutant
			}else{
				interpolator.add(new Point3f(-2.0f, 0.0f, 2.0f), 0.0f);
			}
			
			
			// add the mutant plane to the object
			mutantsList.add(createObject(bgMutant, interpolator));
		
		}

		return mutantsList;
	}

}
