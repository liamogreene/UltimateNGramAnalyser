import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math; 
import java.util.*; 
import java.util.stream.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTextArea;


public class analyse {
	public static List<String> nGramFilesList = new ArrayList<String>();
	

	public static void analyseTrainingFiles(List<File> trainingFilesList, int nGramSize) throws IOException {
		int fileCompletionCount = 1;

		for (int i = 0; i < trainingFilesList.size(); i++) {
			generateOutputNgramFiles(getFileTextFromFileLocation(trainingFilesList.get(i).toString()),trainingFilesList.get(i).toString(), nGramSize, trainingFilesList);
		}

		settingsSelection.renderPostFilesAnalysed(nGramSize, trainingFilesList, nGramFilesList);
	}
	
	public static void analyseTestingFiles(List<File> trainingFilesList, int nGramSize) throws IOException {
		int fileCompletionCount = 1;

		for (int i = 0; i < trainingFilesList.size(); i++) {
			generateOutputNgramFilesForTestFiles(getFileTextFromFileLocation(trainingFilesList.get(i).toString()),trainingFilesList.get(i).toString(), nGramSize, trainingFilesList);
		}
	}

	public static String getFileTextFromFileLocation(String fileLocation) throws IOException {
		Scanner scanner = new Scanner( new File(fileLocation));
		String text = scanner.useDelimiter("\\A").next();
		scanner.close();
		return text;

	}

	public static int generateOutputNgramFiles(String content, String fileLocation, int nGramSize, List<File> trainingFilesList) throws IOException {

		content = content.replace("\n"," ");
		content = content.replace("\r"," ");
		String cs2 = content.replaceAll("           ", " ");
		String cs3 = cs2.replaceAll("          ", " ");
		String cs4 = cs3.replaceAll("         ", " ");
		String cs5 = cs4.replaceAll("        ", " ");
		String cs6 = cs5.replaceAll("       ", " ");
		String cs7 = cs6.replaceAll("      ", " ");
		String cs8 = cs7.replaceAll("     ", " ");
		String cs9 = cs8.replaceAll("    ", " ");
		String cs10 = cs9.replaceAll("1", "one");
		String cs11 = cs10.replaceAll("2", "one");
		String cs12 = cs11.replaceAll("3", "one");
		String cs13 = cs12.replaceAll("4", "one");
		String cs14 = cs13.replaceAll("5", "one");
		String cs15 = cs14.replaceAll("6", "one");
		String cs16 = cs15.replaceAll("7", "one");
		String cs17 = cs16.replaceAll("8", "one");
		String cs18 = cs17.replaceAll("9", "one");
		String cs19 = cs18.replaceAll("0", "zero");
		String cs20 = cs19.replaceAll("&", "and");
		
		
		content = cs19.replaceAll("  ", " ");

		JOptionPane.showMessageDialog(null, fileLocation);
		String[] bg = new String[content.length()];
		int[] bc = new int[content.length()-nGramSize+1];



		for (int i = 0; i < content.length()-nGramSize+1; i++){
			bc[i] = 1;
		}

		for (int i = 0; i<content.length()-nGramSize+1; i++) {
			if(i < content.length()-nGramSize+1){
				bg[i] = content.substring(i,i+nGramSize);
			}
		}

		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			for (int j = i+1; j<content.length(); j++) {
				if (bg[i].equals(bg[j])) {
					bc[i] = bc[i]+1;
				}
			}
		}

		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			for (int j = i+1; j<content.length(); j++) {
				if (bg[i].equals(bg[j])) {
					bc[j] = bc[i];
				}
			}
		}

		String[][] bf = new String[content.length()-nGramSize+1][nGramSize];

		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			bf[i][0] = bg[i];
			bf[i][1] = Integer.toString(bc[i]);
		}


		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			for (int j = i+1; j<content.length()-nGramSize+1; j++) {
				if (bf[i][0].equals(bf[j][0]) && bf[i][1].equals(bf[j][1])) {

					bf[j][0]="null";
					bf[j][1]="null";

				}
			}
		}



		int nullCount = 0;
		int total = 0;


		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			if (bf[i][0].equals("null")){
				nullCount++;
			}
			total++;
		}

		String[][] fin = new String[total-nullCount][nGramSize];
		int add = 0;
		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			if (!bf[i][0].equals("null")){
				fin[add][0] = bf[i][0];
				fin[add][1] = bf[i][1];
				add++;
			}

		}

		PrintWriter outputBigram = new PrintWriter (new FileWriter(fileLocation.replace("\\", "--") + " Ngrams.txt"));
		nGramFilesList.add(fileLocation.replace("\\", "--") + " Ngrams.txt");
		for(int i = 0; i<add; i++) {
			outputBigram.println(fin[i][0] + "&&" + fin[i][1]);
		}

		int totalCount = 0;
		for(int i = 0; i<add; i++) {
			int currentIterations = Integer.parseInt(fin[i][1]);
			totalCount = totalCount + currentIterations;
		}

		//System.out.println(fin.length);
		//System.out.println(totalCount);

		//outputBigram.println("TOTAL NUMBER OF BIGRAMS = " + totalCount);
		//outputBigram.println("TOTAL NUMBER OF UNIQUE BIGRAMS = " + fin.length);


		outputBigram.close();
		return totalCount; 
	}
	
	public static int generateOutputNgramFilesForTestFiles(String content, String fileLocation, int nGramSize, List<File> trainingFilesList) throws IOException {

		content = content.replace("\n"," ");
		content = content.replace("\r"," ");
		String cs2 = content.replaceAll("           ", " ");
		String cs3 = cs2.replaceAll("          ", " ");
		String cs4 = cs3.replaceAll("         ", " ");
		String cs5 = cs4.replaceAll("        ", " ");
		String cs6 = cs5.replaceAll("       ", " ");
		String cs7 = cs6.replaceAll("      ", " ");
		String cs8 = cs7.replaceAll("     ", " ");
		String cs9 = cs8.replaceAll("    ", " ");
		content = cs9.replaceAll("  ", " ");

		JOptionPane.showMessageDialog(null, fileLocation);
		String[] bg = new String[content.length()];
		int[] bc = new int[content.length()-nGramSize+1];



		for (int i = 0; i < content.length()-nGramSize+1; i++){
			bc[i] = 1;
		}

		for (int i = 0; i<content.length()-nGramSize+1; i++) {
			if(i < content.length()-nGramSize+1){
				bg[i] = content.substring(i,i+nGramSize);
			}
		}

		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			for (int j = i+1; j<content.length(); j++) {
				if (bg[i].equals(bg[j])) {
					bc[i] = bc[i]+1;
				}
			}
		}

		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			for (int j = i+1; j<content.length(); j++) {
				if (bg[i].equals(bg[j])) {
					bc[j] = bc[i];
				}
			}
		}

		String[][] bf = new String[content.length()-nGramSize+1][nGramSize];

		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			bf[i][0] = bg[i];
			bf[i][1] = Integer.toString(bc[i]);
		}


		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			for (int j = i+1; j<content.length()-nGramSize+1; j++) {
				if (bf[i][0].equals(bf[j][0]) && bf[i][1].equals(bf[j][1])) {

					bf[j][0]="null";
					bf[j][1]="null";

				}
			}
		}



		int nullCount = 0;
		int total = 0;


		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			if (bf[i][0].equals("null")){
				nullCount++;
			}
			total++;
		}

		String[][] fin = new String[total-nullCount][nGramSize];
		int add = 0;
		for (int i=0; i<content.length()-nGramSize+1; i++)
		{
			if (!bf[i][0].equals("null")){
				fin[add][0] = bf[i][0];
				fin[add][1] = bf[i][1];
				add++;
			}

		}

		PrintWriter outputBigram = new PrintWriter (new FileWriter(fileLocation.replace("\\", "--") + " Ngrams.txt"));
		//nGramFilesList.add(fileLocation.replace("\\", "--") + " Ngrams.txt");
		for(int i = 0; i<add; i++) {
			outputBigram.println(fin[i][0] + "&&" + fin[i][1]);
		}

		int totalCount = 0;
		for(int i = 0; i<add; i++) {
			int currentIterations = Integer.parseInt(fin[i][1]);
			totalCount = totalCount + currentIterations;
		}

		//System.out.println(fin.length);
		//System.out.println(totalCount);

		//outputBigram.println("TOTAL NUMBER OF BIGRAMS = " + totalCount);
		//outputBigram.println("TOTAL NUMBER OF UNIQUE BIGRAMS = " + fin.length);


		outputBigram.close();
		return totalCount; 
	}

	public static String doTheMaths (String filename1, String filename2, int nGramSize) throws IOException {

		Scanner scanner1 = new Scanner( new File(filename1) );
		String bigram1 = scanner1.useDelimiter("\\A").next();
		scanner1.close();

		// Get every other file and compare

		Scanner scanner2 = new Scanner( new File(filename2) );
		String bigram2 = scanner2.useDelimiter("\\A").next();
		scanner2.close();

		String[]t1 = bigram1.split("&&|\r");
		String[]t2 = bigram2.split("&&|\r");
		String[] newFileName1Split = filename1.split("--"); //spliting to just get .txt file name, not the whole file path
		String[] newFileName2Split = filename2.split("--"); //spliting to just get .txt file name, not the whole file path
		//System.out.println(filename1);
		//System.out.println(filename2);
		String newFileName1 = newFileName1Split[newFileName1Split.length-1];
		String newFileName2 = newFileName2Split[newFileName2Split.length-1];


		PrintWriter output = new PrintWriter (new FileWriter(newFileName1 + " VS " + newFileName2 + " .txt"));

		double s1 = 0;
		double s2 = 0;
		double s3 = 0;
		double s4 = 0;

		for(int i = 1; i < t1.length; i=i+2) {

			s1 = s1 + Double.parseDouble(t1[i]);
		}

		// counting total tokens as opposed to total unique tokens 
		for(int i = 1; i < t2.length; i=i+2) {
			s2 = s2 + Double.parseDouble(t2[i]);
		} 
		// counting total unique tokens as opposed to total tokens 
		for(int i = 1; i < t1.length; i=i+2) {
			s3++;
		}

		for(int i = 1; i < t2.length; i=i+2) {
			s4++;
		}

		//System.out.println(s1);
		//System.out.println(s2);

		double totaltokens = s1 + s2; //all token total
		double ttt = s3 + s4; //unique token total
		double answerTotal = 0; //final answer

		//get first token from file one
		for(int i = 0; i < t1.length-1; i=i+2) {
			//loop through each token in file two to find exact match if any exist
			for (int j = 0; j < t2.length-1; j=j+2) {
				//if token i1 matches token j2
				if (t1[i].equals(t2[j]))
				{
					double occurances1 = Integer.parseInt(t1[i+1]); //occurrences of token i in file one
					double occurances2 = Integer.parseInt(t2[j+1]); //occurrences of token i in file two
					double occurancesCombined = occurances1 + occurances2; // combined occurrences of token i in both files
					double eval1 = occurancesCombined * (s1/totaltokens); // estimated value of i in file one
					double eval2 = occurancesCombined * (s2/totaltokens); // estimated value of i in file two
					double occurrences1MinusEval1ThenSquared = (occurances1 - eval1) * (occurances1 - eval1); //see name
					double occurrences2MinusEval2ThenSquared = (occurances2 - eval2) * (occurances2 - eval2); //see name
					double final1 = occurrences1MinusEval1ThenSquared / eval1; //part one of TTT sum equation
					double final2 = occurrences2MinusEval2ThenSquared / eval2; //part two of TTT sum equation
					double final3 = final1 + final2; // add part1 and part2
					answerTotal = answerTotal + final3; // sum for each k
					/*if(final3 > 100) {
						System.out.print(filename1 + " " + filename2 + " = " + t1[i] + " - ");
						System.out.println(final3);

					} */
					output.println(t1[i] + " = " + final3); 
				}
			}
		} 
		answerTotal = answerTotal/(ttt-1); //sum divided by degrees of freedom
		output.println("THE ANSWER: " + answerTotal);
		//System.out.println(filename1 + " & " + filename2 +"," +answerTotal);
		String answer = filename1 + " & " + filename2 +"," + answerTotal;
		output.close();
		return answer;

	}
}