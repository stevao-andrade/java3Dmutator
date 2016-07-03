package usp.icmc.labes.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.Group;
import javax.media.j3d.Locale;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import usp.icmc.labes.control.Java3DOperations;
import usp.icmc.labes.model.ShapeObj;

public class MutantPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Object object;
	private BranchGroup sceneBranchGroup = null;
	Java3DOperations j3dO = new Java3DOperations();

	public BranchGroup getSceneBranchGroup() {
		return sceneBranchGroup;
	}

	public void setSceneBranchGroup(BranchGroup sceneBranchGroup) {
		this.sceneBranchGroup = sceneBranchGroup;
	}

	public Java3DOperations getJ3dO() {
		return j3dO;
	}

	public void setJ3dO(Java3DOperations j3dO) {
		this.j3dO = j3dO;
	}

	// constructor
	public MutantPanel() {

		setLayout(new BorderLayout());
		init();
	}

	// initialization
	public void init() {

		VirtualUniverse virtualUniverse = new VirtualUniverse();

		Locale locale = new Locale(virtualUniverse);

		sceneBranchGroup = j3dO.createSceneBranchGroup(sceneBranchGroup);

		// allow the BranchGroup to have children added at runtime
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_READ);
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);

		Background background = createBackground();

		if (background != null)
			sceneBranchGroup.addChild(background);

		ViewPlatform vp = createViewPlatform();
		BranchGroup viewBranchGroup = j3dO.createViewBranchGroup(getViewTransformGroupArray(), vp);

		locale.addBranchGraph(sceneBranchGroup);
		locale.addBranchGraph(viewBranchGroup); // Adds the View side of the
												// scene graph to the Locale

		createView(vp);

	}

	// Callback to allow the Canvas3D to be added to a Panel.
	public void addCanvas3D(Canvas3D c3d) {
		add(c3d, BorderLayout.CENTER);
	}

	// Create a Java 3D View and attach it to a ViewPlatform
	public View createView(ViewPlatform vp) {
		View view = new View();

		PhysicalBody pb = new PhysicalBody();
		PhysicalEnvironment pe = new PhysicalEnvironment();

		view.setPhysicalEnvironment(pe);
		view.setPhysicalBody(pb);

		if (vp != null)
			view.attachViewPlatform(vp);

		view.setBackClipDistance(100.0);
		view.setFrontClipDistance(1.0);

		// create the visible canvas
		Canvas3D c3d = createCanvas3D();
		view.addCanvas3D(c3d);

		// add the visible canvas to a component
		addCanvas3D(c3d);

		return view;
	}

	// Create a Background for the Canvas3D.
	public Background createBackground() {
		Background background = new Background(new Color3f(0.9f, 1.9f, 1.9f));
		background.setApplicationBounds(createApplicationBounds());
		return background;
	}

	// Create a Bounds object for the scene.
	public Bounds createApplicationBounds() {
		return new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
	}

	// Create a Canvas3D.
	public Canvas3D createCanvas3D() {
		GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
		gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);
		GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

		Canvas3D c3d = new Canvas3D(gd[0].getBestConfiguration(gc3D));
		c3d.setSize(500, 500);

		return c3d;
	}

	// Get the TransformGroup for the View side of the scenegraph
	public TransformGroup[] getViewTransformGroupArray() {
		TransformGroup[] tgArray = new TransformGroup[1];
		tgArray[0] = new TransformGroup();

		// move the camera BACK a little...
		// note that we have to invert the matrix as
		// we are moving the viewer
		Transform3D t3d = new Transform3D();
		t3d.setScale(3);
		t3d.setTranslation(new Vector3d(0.0, 0.0, -20.0));
		t3d.invert();
		tgArray[0].setTransform(t3d);

		return tgArray;
	}

	// Creates the View Platform for the View
	public ViewPlatform createViewPlatform() {
		ViewPlatform vp = new ViewPlatform();
		vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
		vp.setActivationRadius(100);

		return vp;
	}
	
	
	public void clearPanel(){
		sceneBranchGroup = j3dO.removeShape(sceneBranchGroup);			
		this.updateUI();
	}
	
	public void updatePanel(BranchGroup branch){
		
		//add a banchgroup to the mutant scene
		sceneBranchGroup = j3dO.removeShape(sceneBranchGroup);
		sceneBranchGroup.addChild(branch);
		
		this.updateUI();
	
	}
	
	public void actionPerformed(ActionEvent e) {


	}

}
