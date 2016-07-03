package usp.icmc.labes.examples;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Primitive;

import usp.icmc.labes.model.ShapeObj;

public class Sphere implements ShapeObj {

	private BranchGroup getSphere(float size, Color3f color, int laps, int speed) {

		BranchGroup bg = new BranchGroup();

		Material greenSphMaterial = new Material();
		greenSphMaterial.setDiffuseColor(color);
		Appearance greenSphAppearance = new Appearance();
		greenSphAppearance.setMaterial(greenSphMaterial);
		com.sun.j3d.utils.geometry.Sphere greenSph = new com.sun.j3d.utils.geometry.Sphere(size,
				Primitive.GENERATE_NORMALS, 50, greenSphAppearance);

		TransformGroup tgRotate = new TransformGroup();
		tgRotate = rotate(greenSph, new Alpha(laps, speed));

		bg.addChild(tgRotate);

		return bg;
	}

	private TransformGroup rotate(Node node, Alpha alpha) {

		TransformGroup xformGroup = new TransformGroup();
		xformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// Create an interpolator for rotating the node.
		RotationInterpolator interpolator = new RotationInterpolator(alpha, xformGroup);

		// Establish the animation region for this
		// interpolator.
		interpolator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0));

		// Populate the xform group.
		xformGroup.addChild(interpolator);
		xformGroup.addChild(node);

		return xformGroup;

	}

	/**
	 * Create a BranchGroup that contains a Sphere. The user data for the
	 * BranchGroup is set so the BranchGroup can be identified.
	 */
	public BranchGroup createObject(BranchGroup branch) {

		// parameters
		float size = 2.0f;
		Color3f color = new Color3f(0.0f, 1.0f, 0.0f);
		int laps = -1;
		int speed = 40000;

		branch = getSphere(size, color, laps, speed);
		branch.setCapability(BranchGroup.ALLOW_DETACH);

		branch.setUserData(getClass().toString());
		return branch;
	}

	public BranchGroup createObject(BranchGroup branch, float size, Color3f color, int laps, int speed) {

		branch = getSphere(size, color, laps, speed);
		branch.setCapability(BranchGroup.ALLOW_DETACH);

		branch.setUserData(getClass().toString());
		return branch;
	}

	public ArrayList<BranchGroup> getSphereMutants() {

		ArrayList<BranchGroup> mutantsList = new ArrayList<BranchGroup>();

		for (int i = 0; i < 10; i++) {

			BranchGroup bgMutant = new BranchGroup();

			Random rand = new Random();

			// parameters
			float size = (float) (rand.nextFloat() * (3.0 - 0.2) + 0.2);
			Color3f color = new Color3f((float) (rand.nextFloat() * (50.0 - 0.0) + 0.0),
					(float) (rand.nextFloat() * (50.0 - 0.0) + 0.0), (float) (rand.nextFloat() * (50.0 - 0.0) + 0.0));
			int laps = (int) (rand.nextInt() * (10 - (-1.0)) + (-1));
			// int speed = (rand.nextInt() * (800000 - (50000)) + (80000));;
			int speed = 50000;

			// avoid stoped mutant
			if (laps == 0)
				laps = -1;

			// add the mutant plane to the object
			mutantsList.add(createObject(bgMutant, size, color, laps, speed));
		}

		return mutantsList;
	}

}
