package usp.icmc.labes.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import usp.icmc.labes.control.Util;

/**
 * Simple command extension to make calls to mujava tool
 * 
 * @author stevao
 *
 */
@SuppressWarnings("unused")
public class Mujava {

	private String session;

	/**
	 * Generic command execution
	 * 
	 * @param command
	 *            mujava script command
	 */
	private void executeCommand(String command) {

		// Get runtime
		java.lang.Runtime rt = java.lang.Runtime.getRuntime();

		// Start a new process
		try {
			java.lang.Process p = rt.exec(command);

			// Show exit code of process
			System.out.println("Process executed: " + command);

			// show the output
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

			// read the output from the command
			System.out.println("Standard output:\n");
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			System.out.println("Standard error:\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create a test session
	 * 
	 * @param className
	 *            name of the target class that will be muted
	 * @param sessionName
	 *            name of the session of test
	 */
	private void createTestSession(String className, String sessionName) {

		// Define the command
		String command = String.format("java mujava.cli.testnew %s %s", sessionName, className);

		// execute
		executeCommand(command);

	}

	/**
	 * Generate the mutants for a target session
	 * 
	 * @param operators
	 *            list of operators that will be used to generate the mutants
	 * @param sessionName
	 *            name of the session of test
	 */
	private void generateMutants(String operators, String sessionName) {

		// define the command
		String command = String.format("java mujava.cli.genmutes %s %s", operators, sessionName);

		// execute
		executeCommand(command);
	}

	/**
	 * Do all the operation related to mujava
	 * 
	 * @param source
	 *            package that contain the class that wants to mutate
	 * @param destination
	 *            mujava src dir to put the class that will be mutated
	 * @param classPath
	 *            path to .class compiled files
	 * 
	 * @param sessionName
	 *            name of the testsession that will be created
	 * @param className
	 *            name of the class that will be muted
	 * @param operators
	 *            operators used to generate the mutants
	 * @return a int value to identify if the operations runned without any
	 *         errors
	 */
	public int doMutation(String source, String destination, String classPath, String sessionName, String className,
			String operators) {

		try {

			// get the source path
			File sourcePath = new File(source);
			File destPath = new File(destination);

			// create a path with the name of the session
			File sessionPath = new File("C:\\mujava\\" + sessionName);

			// if the directory of the session already exists, delete it
			if (sessionPath.isDirectory())
				while (destPath.delete())

					// create the directory inside muJava src dir
					Util.copyDirectory(sourcePath, destPath);

			// create a test session
			createTestSession(className, sessionName);

			// copy the compiled files to class directory
			File byteCodePath = new File(classPath);
			File destByteCodePath = new File(sessionPath + "\\classes");
			Util.copyDirectory(byteCodePath, destByteCodePath);

			// generate the mutants
			generateMutants(operators, sessionName);

			return 0;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
