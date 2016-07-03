package usp.icmc.labes.control;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Group;
import javax.media.j3d.SceneGraphObject;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

/**
 * This class handles common Java3D operations
 * @author stevao
 *
 */

public class Java3DOperations {
	
	
	//Creates the View side BranchGroup. The ViewPlatform is wired in beneath the TransformGroups.
	public BranchGroup createViewBranchGroup(TransformGroup[] tgArray, ViewPlatform vp) {
		BranchGroup vpBranchGroup = new BranchGroup();

		if (tgArray != null && tgArray.length > 0) {
			Group parentGroup = vpBranchGroup;
			TransformGroup curTg = null;

			for (int n = 0; n < tgArray.length; n++) {
				curTg = tgArray[n];
				parentGroup.addChild(curTg);
				parentGroup = curTg;
			}

			tgArray[tgArray.length - 1].addChild(vp);
		} else
			vpBranchGroup.addChild(vp);

		return vpBranchGroup;
	}
	
	
	
	/**
	 * Create the scene side of the scene graph
	 */
	public BranchGroup createSceneBranchGroup(BranchGroup sceneBranchGroup) {

		// create the root of the scene side scene graph
		BranchGroup objRoot = new BranchGroup();
		objRoot.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		objRoot.setCapability(Group.ALLOW_CHILDREN_READ);
		objRoot.setCapability(Group.ALLOW_CHILDREN_WRITE);
	

		// create a spherical bounding volume
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);


		// create the BranchGroup which contains the objects
		// we add/remove to and from the scene graph
		sceneBranchGroup = new BranchGroup();

		// allow the BranchGroup to have children added at runtime
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_READ);
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);
		sceneBranchGroup.setCapability( BranchGroup.ALLOW_DETACH );

		
		// create the colors for the lights
		Color3f lColor1 = new Color3f(0.7f, 0.7f, 0.7f);
		Vector3f lDir1 = new Vector3f(-1.0f, -1.0f, -1.0f);
		Color3f alColor = new Color3f(0.2f, 0.2f, 0.2f);

		// create the ambient light
		AmbientLight aLgt = new AmbientLight(alColor);
		aLgt.setInfluencingBounds(bounds);

		// create the directional light
		DirectionalLight lgt1 = new DirectionalLight(lColor1, lDir1);
		lgt1.setInfluencingBounds(bounds);

		// add the lights to the scene graph
		objRoot.addChild(aLgt);
		objRoot.addChild(lgt1);

		objRoot.addChild(sceneBranchGroup);
		
		// return the root of the scene side of the scene graph
		return objRoot;
	}
	
	
	
	//Removes a BranchGroup from the scene based on user data
	public BranchGroup removeShape(BranchGroup sceneBranchGroup) {
		try {
			
			//Get a list with all avaliable classes
			List<String> className = new ArrayList<String>();
			String scannedPackage = "usp.icmc.labes.examples";
			List<Class<?>> classes = Util.find(scannedPackage);
			for(Class<?> it : classes){
				className.add(it.toString());
			}
			
			@SuppressWarnings("rawtypes")
			java.util.Enumeration enum2 = sceneBranchGroup.getAllChildren();
			int index = 0;

			while (enum2.hasMoreElements() != false) {
				SceneGraphObject sgObject = (SceneGraphObject) enum2.nextElement();
				System.out.println(sgObject.getClass().toString());
				Object userData = sgObject.getUserData();
				
				for(String name: className){
					
					if (userData instanceof String && ((String) userData).compareTo(name) == 0) {
						System.out.println("Removing: " + sgObject.getUserData());
						sceneBranchGroup.removeChild(index);
					}
				}

				index++;
			}
		} catch (Exception e) {
			// the scenegraph may not have yet been synchronized...
		}
		
		return sceneBranchGroup;
	}
	
	
}
