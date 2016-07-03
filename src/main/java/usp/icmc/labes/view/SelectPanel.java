package usp.icmc.labes.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.media.j3d.BranchGroup;
import javax.swing.JPanel;
import javax.swing.JRootPane;


import usp.icmc.labes.examples.Cube;
import usp.icmc.labes.examples.Human;
import usp.icmc.labes.examples.Plane;
import usp.icmc.labes.examples.Sphere;

public class SelectPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent e) {

		JRootPane rootPane = this.getRootPane();
		ScenePanel scenePanel;
		scenePanel = (ScenePanel) rootPane.getContentPane().getComponent(0);

		MutantPanel mutantPanel;
		mutantPanel = (MutantPanel) rootPane.getContentPane().getComponent(1);

		// get the BG from Scene
		BranchGroup sceneBG = new BranchGroup();
		sceneBG = scenePanel.getSceneBranchGroup();

		if (scenePanel.getShape() instanceof Plane) {

			// get the mutants
			ArrayList<BranchGroup> mutants = new ArrayList<BranchGroup>();
			mutants = ((Plane) scenePanel.getShape()).getPlaneMutants();

			// sub -1 because it's an array.. go from 0 to N
			int id = Integer.parseInt(e.getActionCommand().split(Pattern.quote(" "))[1]) - 1;

			BranchGroup branchMutant = new BranchGroup();
			branchMutant = mutants.get(id);

			mutantPanel.updatePanel(branchMutant);

		}

		if (scenePanel.getShape() instanceof Sphere) {

			// get the mutants
			ArrayList<BranchGroup> mutants = new ArrayList<BranchGroup>();
			mutants = ((Sphere) scenePanel.getShape()).getSphereMutants();

			// sub -1 because it's an array.. go from 0 to N
			int id = Integer.parseInt(e.getActionCommand().split(Pattern.quote(" "))[1]) - 1;

			BranchGroup branchMutant = new BranchGroup();
			branchMutant = mutants.get(id);
			mutantPanel.updatePanel(branchMutant);
		}

		if (scenePanel.getShape() instanceof Cube){
			
			// get the mutants
			ArrayList<BranchGroup> mutants = new ArrayList<BranchGroup>();
			mutants = ((Cube) scenePanel.getShape()).getCubeMutants();

			// sub -1 because it's an array.. go from 0 to N
			int id = Integer.parseInt(e.getActionCommand().split(Pattern.quote(" "))[1]) - 1;

			BranchGroup branchMutant = new BranchGroup();
			branchMutant = mutants.get(id);
			mutantPanel.updatePanel(branchMutant);

			
			
		}

		if (scenePanel.getShape() instanceof Human) {

			// get the mutants
			ArrayList<BranchGroup> mutants = new ArrayList<BranchGroup>();
			mutants = ((Human) scenePanel.getShape()).getHumanMutants();

			// sub -1 because it's an array.. go from 0 to N
			int id = Integer.parseInt(e.getActionCommand().split(Pattern.quote(" "))[1]) - 1;

			BranchGroup branchMutant = new BranchGroup();
			branchMutant = mutants.get(id);
			mutantPanel.updatePanel(branchMutant);

		}

	}

}
