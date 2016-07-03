package usp.icmc.labes.examples;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Loader;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

import usp.icmc.labes.control.LoaderFactory;
import usp.icmc.labes.model.ShapeObj;

public class Plane implements ShapeObj {

	private Scene scene;
	
	String obj_name = "plane.obj";
	//String obj_name = "man.obj";
	
	public Scene getScene() {
		return scene;
	}

	public Plane() throws FileNotFoundException, IncorrectFormatException, ParsingErrorException {
		
		
		
		Loader loader = LoaderFactory.getWavefrontLoader();
		this.scene = loader.load(obj_name);

	}

	private TransformGroup buildPlane(Map<String, Shape3D> partsMap) {

		/*
		 * Obtain the scene BranchGroup, from which the components are removed
		 */
		BranchGroup root = this.scene.getSceneGroup();
		root.removeAllChildren();

		TransformGroup fullPlane = new TransformGroup();

		for (Shape3D part : partsMap.values()) {
			
			Shape3D newPart = new Shape3D();
			newPart = (Shape3D) part.cloneNode(true);
			
			TransformGroup tgPart = new TransformGroup();
			tgPart.addChild(newPart);

			fullPlane.addChild(tgPart);
		}
		return fullPlane;
	}

	private TransformGroup createTGplane() {
		// add the object to the transformgroup object
		TransformGroup fullPlane = new TransformGroup();
		Map<String, Shape3D> partsMap = scene.getNamedObjects();
		fullPlane = buildPlane(partsMap);
		return fullPlane;
	}
	
	private BranchGroup getPlaneBG(TransformGroup tgPlane) {
		
		// create a new instance to remove the informations of the cone
		TransformGroup tgObj = new TransformGroup();

		// define a transformation to scale the object
		Transform3D t3d = new Transform3D();
		t3d.setScale(2.5);
		t3d.setTranslation(new Vector3f(0.5f, 0.3f, 0.0f));
		tgObj.setTransform(t3d);
		
		
		//add the plane to the obj
		tgObj.addChild(tgPlane);

		BranchGroup branchgroupObj = new BranchGroup();
		branchgroupObj.addChild(tgObj);
		return branchgroupObj;

	}


	public BranchGroup createObject(BranchGroup branch) {
		TransformGroup tgPlane = new TransformGroup();
		tgPlane = createTGplane();
		
		branch = getPlaneBG(tgPlane);

		branch.setCapability(BranchGroup.ALLOW_DETACH);
		branch.setUserData(getClass().toString());

		return branch;
	}
	
	public BranchGroup createObject(BranchGroup branch, boolean mutant) {

		branch.setCapability(BranchGroup.ALLOW_DETACH);
		branch.setUserData(getClass().toString());

		return branch;
	}

	public ArrayList<BranchGroup> getPlaneMutants() {
		
		Map<String, Shape3D> partsMap = scene.getNamedObjects();
		ArrayList<BranchGroup> mutantsList = new ArrayList<BranchGroup>();
		
		TransformGroup fullPlane = createTGplane();  
		int size = fullPlane.numChildren();
		
		for (int i = 0; i < size; i++) {
			TransformGroup tgPlaneCopy = createTGplane();
			tgPlaneCopy.removeChild(i);
			
			System.out.println(tgPlaneCopy.numChildren());
			
			BranchGroup bgMutant = new BranchGroup();
			bgMutant = getPlaneBG(tgPlaneCopy);
			
			//add the mutant plane to the object
			mutantsList.add(createObject(bgMutant,true));
		}
		
		return mutantsList;
	}

}
