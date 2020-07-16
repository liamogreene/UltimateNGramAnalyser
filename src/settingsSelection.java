import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

public class settingsSelection {

	public static void renderPreFilesAnalysed() {	

		List<File> trainingFilesList = new ArrayList<File>();

		JFrame frame1 = new JFrame("Ultimate N-Gram Analyser");
		frame1.getContentPane().setBackground(Color.WHITE);

		JButton loadTrainingFilesButton = new JButton("Load Training Files");
		loadTrainingFilesButton.setBounds(60, 400, 300, 100);
		loadTrainingFilesButton.setBackground(Color.green);

		JButton analyseTrainingFilesButton = new JButton("Analyse Training Files");
		analyseTrainingFilesButton.setBounds(880, 400, 300, 100);
		analyseTrainingFilesButton.setBackground(Color.green);

		JSpinner m_numberSpinner;
		SpinnerNumberModel m_numberSpinnerModel;
		Double current = new Double(1.00);
		Double min = new Double(1.00);
		Double max = new Double(1000000.00);
		Double step = new Double(1);
		m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
		m_numberSpinner = new JSpinner(m_numberSpinnerModel);
		m_numberSpinner.setBounds(460, 400, 300, 100);
		Font fontSpinner = new Font("Arial Unicode", Font.BOLD,30);
		m_numberSpinner.setFont(fontSpinner);

		JTextArea spinner = new JTextArea();
		spinner.setBounds(470, 360, 1365, 1200);
		spinner.setText("N-Gram Size");
		spinner.setFont(fontSpinner);

		JTextArea intro = new JTextArea();
		intro.setBounds(60, 10, 1365, 1200);
		intro.setText("Welcome to the Liam Greene N-Gram Machine! Thanks for taking the time to explore this program. \n\n"
				+ "This program is method of evaluating textual uniquenesss. To get started, you should have .TXT files of the texts you wish to examine. \n\n" 
				+ "When you are ready, click the 'Load Training Files' button and navigate to your text files and select them for use. The program will load the files you selected into the training database.\n\n"
				+ "Then select your N-Gram size to utilise in the study (1 for unigrams, 2 for bigrams, 3 for trigrams and so on). When you have your desired settings, click 'Analyse Training Files' and the program will generate N-Gram files of selected size for each training file. \n \n"
				+ "After this, the programme will request your testing sample files for examination. An example use of this program would be for Authorship analysis based on N-Gram tokens: For example, the training files could be {1. Orwell Works, 2. Steinbeck Works, 3. Falkner Works}. Once this files are analysed, one could use testing samples of various texts of each the Authors used in the training example to see if the program correctly matches the test sample to the correct author. Such an test could be further broken down to the works of an author seperately, or even character homogeneity.");
		Font fontText = new Font("Arial Unicode", Font.BOLD,18);
		intro.setFont(fontText);
		intro.setLineWrap(true);
		intro.setEditable(false);
		intro.setBackground(Color.WHITE);

		frame1.add(analyseTrainingFilesButton);
		frame1.add(m_numberSpinner);
		frame1.add(loadTrainingFilesButton);

		frame1.add(spinner);
		frame1.add(intro);


		frame1.pack();
		frame1.setExtendedState(JFrame.MAXIMIZED_BOTH);   
		frame1.setLayout(null);    
		frame1.setVisible(true);    
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		loadTrainingFilesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.showOpenDialog(frame1);
				File[] files = chooser.getSelectedFiles();
				for(int i = 0; i < files.length; i++) {
					trainingFilesList.add(files[i]);
				}
			}
		});

		analyseTrainingFilesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (trainingFilesList.size() < 1) {
					JOptionPane.showMessageDialog(null, "Please select your training file(s) first and try again.");
				}
				else {
					try {
						Double value = (double) ((Double)m_numberSpinner.getValue()).floatValue();
						int nGramSize = (int) Math.round(value);
						analyse.analyseTrainingFiles(trainingFilesList, nGramSize);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});


	}

	public static void renderPostFilesAnalysed(int nGramSize, List<File> trainingFilesList, List<String> nGramFilesList) {	

		// create a frame 
		JFrame frame2 = new JFrame("Run NGram Comparisons"); 
		JButton compareLoadedFilesButton = new JButton("Compare Loaded Training Files Against Each Other");
		compareLoadedFilesButton.setBounds(60, 400, 300, 100);
		compareLoadedFilesButton.setBackground(Color.green);

		JButton compareNewTestFilesButton = new JButton("Compare New Test Files Against Loaded Training Files");
		compareNewTestFilesButton.setBounds(880, 400, 300, 100);
		compareNewTestFilesButton.setBackground(Color.green);


		JTextArea intro = new JTextArea();
		intro.setBounds(60, 10, 1365, 1200);
		intro.setText("We are coming up to the final stages of analysis. You have two choices now. \n\n"
				+ "Click the appropriate button below depending on your aim. \n\n" );
		Font fontText = new Font("Arial Unicode", Font.BOLD,18);
		intro.setFont(fontText);
		intro.setLineWrap(true);
		intro.setEditable(false);
		intro.setBackground(Color.WHITE);

		frame2.add(compareLoadedFilesButton);
		frame2.add(compareNewTestFilesButton);
		frame2.add(intro);


		frame2.pack();
		frame2.setExtendedState(JFrame.MAXIMIZED_BOTH);   
		frame2.setLayout(null);    
		frame2.setVisible(true);    
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		compareLoadedFilesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int fileCompletionCount = 1;

				for (int i = 0; i < trainingFilesList.size(); i++) {
					for (int j = 0; j < trainingFilesList.size(); j++) {
						try {
							analyse.doTheMaths(nGramFilesList.get(i), nGramFilesList.get(j),nGramSize);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, fileCompletionCount + "/" + trainingFilesList.size() * trainingFilesList.size() + " done!");
						JOptionPane.getRootFrame().dispose();   
						fileCompletionCount++;
					}
				}
			}
		});

		compareNewTestFilesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int fileCompletionCount = 1;
				List<File> testingFilesList = new ArrayList<File>();

				JFileChooser chooser = new JFileChooser();
				chooser.setMultiSelectionEnabled(true);
				chooser.showOpenDialog(frame2);
				File[] files = chooser.getSelectedFiles();
				for(int i = 0; i < files.length; i++) {
					testingFilesList.add(files[i]);
				}
				for (int i = 0; i < testingFilesList.size(); i++) {
					for (int j = 0; j < nGramFilesList.size(); j++) {
						try {
							analyse.analyseTestingFiles(testingFilesList,nGramSize);
							analyse.doTheMaths((testingFilesList.get(i).toString().replace("\\", "--") + " Ngrams.txt").replace("C:", ""),nGramFilesList.get(j).toString(),nGramSize);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, fileCompletionCount + "/" + testingFilesList.size() * nGramFilesList.size() + " done!");
						JOptionPane.getRootFrame().dispose();   
						fileCompletionCount++;
					}
				}
			}
		});

	}
}

