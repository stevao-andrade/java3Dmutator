package usp.icmc.labes.transformations;

import javax.media.j3d.BoundingSphere;
import javax.vecmath.Point3d;
import javax.media.j3d.Alpha;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

public class RotationTrans {

	/**
	*	a rotation interpolator that automatically
	*	rotates the cube and sphere
	*/

	private RotationInterpolator 	rotator = null;
	
	
	
	public BranchGroup rotate(BranchGroup objRoot){
		
		BranchGroup sceneBranchGroup = new BranchGroup();
		
		// create a spherical bounding volume
		BoundingSphere bounds =	new BoundingSphere( new Point3d( 0.0,0.0,0.0 ), 100.0 );		

		// create a TransformGroup to rotate the objects in the scene
		// set the capability bits on the TransformGroup so that it
		// can be modified at runtime
		TransformGroup objTrans = new TransformGroup( );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE );
		objTrans.setCapability( TransformGroup.ALLOW_TRANSFORM_READ );
		
		
		// create a 4x4 transformation matrix
		Transform3D yAxis = new Transform3D( );
		
		// create an Alpha interpolator to automatically generate
		// modifications to the rotation component of the transformation matrix
		Alpha rotationAlpha = new Alpha( -1, Alpha.INCREASING_ENABLE, 0, 0, 4000, 0, 0, 0, 0, 0 );
		
		
		// create a RotationInterpolator behavior to effect the TransformGroup
		rotator = new RotationInterpolator( rotationAlpha, objTrans, yAxis, 0.0f, (float) Math.PI*2.0f );

		// set the scheduling bounds on the behavior
		rotator.setSchedulingBounds( bounds );

		// add the behavior to the scenegraph
		objTrans.addChild( rotator );
		
		// wire the scenegraph together
		objTrans.addChild( sceneBranchGroup );
		objRoot.addChild( objTrans );
		
		return objRoot;
		
	}
	
	


}
