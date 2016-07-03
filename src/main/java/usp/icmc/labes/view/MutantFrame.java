package usp.icmc.labes.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import usp.icmc.labes.control.Util;

public class MutantFrame extends JFrame implements ActionListener {

	// Attributes
	private static final long serialVersionUID = 1L;

	static ScenePanel scenePanel = new ScenePanel();
	static MutantPanel mutantPanel = new MutantPanel();
	static SelectPanel selectPanel = new SelectPanel();
	
	String scannedPackage = "usp.icmc.labes.examples";
	List<String> fullName = new ArrayList<String>();

	// Constructor
	public MutantFrame() {
		setResizable(false);
		setTitle("SIN514 - Fundamentos de Processamento Gráfico (Stevão Andrade)");
		getContentPane().setLayout(null);

		fullName = Util.getClassesName(scannedPackage);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	// helps to create menu items
	private JMenuItem createMenuItem(String menuText, String buttonText, ActionListener listener) {
		JMenuItem menuItem = new JMenuItem(buttonText);
		menuItem.addActionListener(listener);
		menuItem.setActionCommand(menuText + "|" + buttonText);
		return menuItem;
	}

	// Create the menu
	public JMenuBar createMenuBar() {

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = null;

		// Create the items off Scenes dynamically
		menu = new JMenu("Scenes");

		for (String name : fullName) {

			// split the full name to get only the class name with the package
			String[] names = name.split(Pattern.quote(" "));

			// packeage+class is always the last element
			String packageName = names[names.length - 1];

			// split the package+class name and get only the class name
			String[] names2 = packageName.split(Pattern.quote("."));

			String className = names2[names2.length - 1];

			menu.add(createMenuItem("Scenes", className, scenePanel));
		}

		menuBar.add(menu);

		return menuBar;
	}

	// Main
	public static void main(String[] args) {

		// create the frame and add the butons
		MutantFrame frame = new MutantFrame();
		frame.setJMenuBar(frame.createMenuBar());

		//frame.getContentPane().setLayout(myLayout);
		frame.setSize(1050, 755);

		//JPanel scenePanel = new ScenePanel();
		scenePanel.setBounds(1, 0, 450, 700);
		scenePanel.setBackground(Color.PINK);
		scenePanel.setBorder(LineBorder.createGrayLineBorder());
		frame.getContentPane().add(scenePanel);

		//JPanel mutantPanel = new MutantPanel();
		mutantPanel.setBounds(461, 0, 450, 700);
		mutantPanel.setBackground(new Color(0, 255, 255));
		mutantPanel.setBorder(LineBorder.createGrayLineBorder());
		frame.getContentPane().add(mutantPanel);

		//JPanel selectPanel = new JPanel();
		selectPanel.setBounds(921, 0, 120, 700);
		selectPanel.setBorder(LineBorder.createGrayLineBorder());
		frame.getContentPane().add(selectPanel);

		
		frame.setVisible(true);
	}
}
