import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;
public class Assig4
{	
	private String correctWord = null;
	private StringBuilder wordInProgress = new StringBuilder("");
	private	StringBuilder lettersGuessed = new StringBuilder("");
	private int guesses = 0;
	private boolean tru = false;



	private JFrame theFrame;
	private JPanel playPanel;
	private MyPanel drawPanel;
	private JPanel buttonPanel;
	private JButton startGame;
	private JButton nextPlayer;
	private JButton endGame;
	private JButton newWord;
	private JButton endPlayer1;
	private JButton endPlayer2;
	private JButton newWord2;
	//This block for the second column
	private JLabel yourGuess;
	private JLabel currWord;
	private JLabel gameInfo;
	private JLabel gameStat;
	private JTextField currentGuess;
	
	//This block for the first column of PlayPanel
	private JLabel your;
	private JLabel currW;
	private JLabel gameI;
	private JLabel gameS;
	private JLabel currGuess;
	private HangPlayer hp;
	private ArrayList<HangPlayer> P;
	private int x;
	private	int y;
	
	public Assig4()
	{
		theFrame = new JFrame("Assignment 4");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setLayout(new GridLayout(1,3));
		drawPanel = new MyPanel(80,100);
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,1));
		playPanel = new JPanel();
		playPanel.setLayout(new GridLayout(5,2));
		//Play Panel
		{
		gameS = new JLabel("Game Status: ");
		gameI = new JLabel("Game Info: ");
		currW = new JLabel("Current Word: ");
		your = new JLabel("Your Guesses: ");
		currGuess = new JLabel("Current Guess: ");
		
		gameStat = new JLabel("Game Not Started");
		gameInfo = new JLabel("Welcome to the Hangman Game");
		currWord = new JLabel("");
		yourGuess= new JLabel("");
		currentGuess = new JTextField();
		currentGuess.setEditable(false);
		
		playPanel.add(gameS);
		playPanel.add(gameStat);
		playPanel.add(gameI);
		playPanel.add(gameInfo);
		playPanel.add(currW);
		playPanel.add(currWord);
		playPanel.add(your);
		playPanel.add(yourGuess);
		playPanel.add(currGuess);
		playPanel.add(currentGuess);
		
		}
		
		//Button Panel
		{
		startGame = new JButton("Start Game");
		nextPlayer = new JButton("Next Player");
		endGame = new JButton("End Game");
		newWord = new JButton("New Word");
		newWord2 = new JButton("New Word");
		endPlayer1 = new JButton("End Player");
		endPlayer2 = new JButton("End Player");
		endGame.setEnabled(false);
		newWord.setEnabled(false);
		endPlayer1 = new JButton("End Player");
		
		MyListener ml = new MyListener();

		
		startGame.addActionListener(ml);
		nextPlayer.addActionListener(ml);
		endGame.addActionListener(ml);
		newWord.addActionListener(ml);
		currentGuess.addActionListener(ml);
		endPlayer1.addActionListener(ml);
		endPlayer2.addActionListener(ml);
		newWord2.addActionListener(ml);
		
		buttonPanel.add(startGame);
		buttonPanel.add(endGame);
		buttonPanel.add(newWord);
		
		
		}
		
		theFrame.add(drawPanel);
		theFrame.add(playPanel);
		theFrame.add(buttonPanel);
		
	
		
		theFrame.pack();
		theFrame.setVisible(true);
	}
	
	public static void main(String[]args) throws FileNotFoundException
	{
		new Assig4();
	}

	class MyListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == startGame)
			{
				buttonPanel.remove(startGame);
				buttonPanel.remove(endGame);
				buttonPanel.remove(newWord);
				buttonPanel.add(nextPlayer);
				buttonPanel.add(endGame);
				buttonPanel.add(newWord);
				endGame.setEnabled(true);
				buttonPanel.revalidate();
				buttonPanel.repaint();
				P = loadPlayers();
				JOptionPane.showMessageDialog(null, "Players Loading.... Ok", "Loaded", JOptionPane.INFORMATION_MESSAGE);
			}
			if(e.getSource() == endGame)
			{
				String message = "Are you sure you want to quit?";
				String title = "Confirmation";
				int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) 
				{
					System.exit(0);
					saveData();
				}
				else
				{
				}
			}
			if(e.getSource() == nextPlayer)
			{
				String name = JOptionPane.showInputDialog("Please input your name: ");
				hp = createHangPlayer(name, P);
				nextPlayer.setEnabled(false);
				buttonPanel.remove(endGame);
				buttonPanel.remove(nextPlayer);
				buttonPanel.remove(newWord);
				buttonPanel.add(nextPlayer);
				buttonPanel.add(endPlayer1);
				buttonPanel.add(newWord);
				newWord.setEnabled(true);
				gameInfo.setText("Welcome to Hangman, " + name);
				theFrame.repaint();
				theFrame.revalidate();
				theFrame.pack();
			}
			if(e.getSource() == endPlayer1)
			{
				String message = "Are you sure you want to stop playing?";
				String title = "Confirmation";
				int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) 
				{
					
					buttonPanel.remove(nextPlayer);
					buttonPanel.remove(endPlayer1);
					buttonPanel.remove(newWord);
					buttonPanel.add(nextPlayer);
					nextPlayer.setEnabled(true);
					buttonPanel.add(endGame);
					buttonPanel.add(newWord);
					newWord.setEnabled(false);
					theFrame.revalidate();
					theFrame.repaint();
					saveData();
				}
			}
			if(e.getSource() == endPlayer2)
			{
				String message = "Are you sure you want to give up on this word and stop playing?";
				String title = "Confirmation";
				int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) 
				{
					y++;
					currWord.setText(correctWord);
					hp.setLosses(y);
					buttonPanel.remove(endPlayer2);
					buttonPanel.remove(nextPlayer);
					buttonPanel.remove(newWord2);
					buttonPanel.add(nextPlayer);
					nextPlayer.setEnabled(true);
					buttonPanel.add(endGame);
					buttonPanel.add(newWord);
					newWord.setEnabled(false);
					theFrame.revalidate();
					theFrame.repaint();
					currentGuess.setEditable(false);
					gameInfo.setText("See the word below");
					currWord.setText(correctWord);
					saveData();
				}
				else
				{
				}
				
			}	
			if(e.getSource() == newWord)
			{
				guesses = 0;
				wordInProgress.setLength(0);
				lettersGuessed.setLength(0);
			
				buttonPanel.remove(endPlayer1);
				buttonPanel.remove(nextPlayer);
				buttonPanel.remove(newWord);
				buttonPanel.add(nextPlayer);
				buttonPanel.add(endPlayer2);
				buttonPanel.add(newWord2);
				theFrame.repaint();
				theFrame.revalidate();
				
				currentGuess.setEditable(true);
				WordServer wordServer = createWordServer();
				playHangman(wordServer);
				gameStat.setText("Game in Progress");
			}
			if(e.getSource() == newWord2)
			{
				String message = "Are you sure you want to give up on this word?";
				String title = "Confirmation";
				int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) 
				{
				y++;
				hp.setLosses(y);
				currWord.setText(correctWord);
				guesses = 0;
				wordInProgress.setLength(0);
				lettersGuessed.setLength(0);
			
				buttonPanel.remove(endPlayer1);
				buttonPanel.remove(nextPlayer);
				buttonPanel.remove(newWord2);
				buttonPanel.add(nextPlayer);
				buttonPanel.add(endPlayer2);
				buttonPanel.add(newWord2);
				theFrame.repaint();
				theFrame.revalidate();
				
				currentGuess.setEditable(true);
				WordServer wordServer = createWordServer();
				playHangman(wordServer);
				gameStat.setText("Game in Progress");
				}
				else{
				}
			}
			if(e.getSource() == currentGuess)
			{
			
				x = hp.getWins();
				y = hp.getLosses();
				wordCheck();
			}
			

		}
	}	
	
	private static ArrayList<HangPlayer> loadPlayers()
	{
		String f = "players.txt";
		File file = new File(f);
		Scanner fileScanner = null;
		try
		{
			fileScanner = new Scanner(file);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		ArrayList<HangPlayer> P = new ArrayList<HangPlayer>();
		while(fileScanner.hasNextLine())
		{
			String s = fileScanner.nextLine();
			HangPlayer hangP = new HangPlayer(s);
			int wins = Integer.parseInt(fileScanner.nextLine());
			hangP.setWins(wins);
			int loss = Integer.parseInt(fileScanner.nextLine());
			hangP.setLosses(loss);
			P.add(hangP);
		}
		
		return P;
	}
	
	
	private static HangPlayer createHangPlayer(String name, ArrayList<HangPlayer> P)
	{
		HangPlayer hang = null;
		for(HangPlayer h : P)
		{
			if (h.equals(name) == true)
			{
				hang = h;
				JOptionPane.showMessageDialog(null, "Welcome back " + name + "\n" + h.toString());
			}
			else
			{
				hang = new HangPlayer(name);
				hang.setWins(0);
				hang.setLosses(0);
			}
		}
		P.add(hang);
		return hang;
	}
	
	private static WordServer createWordServer()
	{
		File file = new File("words.txt");
		Scanner fileScanner = null;
		try
		{
			fileScanner = new Scanner(file);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		WordServer ws = new WordServer();
		ws.loadWords(fileScanner);
		return ws;
	}
	private void playHangman(WordServer wordServer)
	{
		correctWord = wordServer.getNextWord();
		for(int i = 0; i < correctWord.length(); i++)
			wordInProgress.append("_");
		currWord.setText(wordInProgress.toString());

	}
	
	private void wordCheck(){
		boolean flag = false;
		boolean ftag = false;
		char guess = currentGuess.getText().charAt(0);
		HangFigure hf = new HangFigure(40,40,40);
		for(int i = 0; i < lettersGuessed.length(); i++)
		{
			if(guess == lettersGuessed.charAt(i))
			{
				gameInfo.setText("You entered that already");
				flag = true;
			}
		}
		if(!flag)
		{
			for(int j = 0; j < correctWord.length(); j++)
			{
				if(guess == correctWord.charAt(j))
				{
					wordInProgress.setCharAt(j, guess);
					currWord.setText(wordInProgress.toString());
					gameInfo.setText(guess + " was in the word");
					yourGuess.setText(lettersGuessed.toString());
					ftag = true;
				}
			}
			lettersGuessed.append(guess);
			if(ftag == false)
			{
				yourGuess.setText(lettersGuessed.toString());
				gameInfo.setText("Sorry " + guess + " wasn't in the word");
				guesses++;
			}
		}
		
		currentGuess.setText("");
		if(wordInProgress.toString().equalsIgnoreCase(correctWord))
		{
			gameInfo.setText("Great job! You got the word below");
			x++;
			hp.setWins(x);
			hp.setLosses(y);
			
			buttonPanel.remove(endPlayer2);
			buttonPanel.remove(nextPlayer);
			buttonPanel.remove(newWord2);
			buttonPanel.add(nextPlayer);
			buttonPanel.add(endPlayer1);
			buttonPanel.add(newWord);
			theFrame.revalidate();
			theFrame.repaint();
			theFrame.pack();
			
			currentGuess.setEditable(false);
		}
		else if(guesses == 7)
		{
			gameInfo.setText("Sorry you were stumped by the word below");
			currWord.setText(correctWord);
			y++;
			hp.setWins(x);
			hp.setLosses(y);
			
			buttonPanel.remove(endPlayer2);
			buttonPanel.remove(nextPlayer);
			buttonPanel.remove(newWord2);
			buttonPanel.add(nextPlayer);
			buttonPanel.add(endPlayer1);
			buttonPanel.add(newWord);
			theFrame.revalidate();
			theFrame.repaint();
			theFrame.pack();
			
			currentGuess.setEditable(false);
		}
		
	}
	
	private void saveData(){
	try{
	
			PrintWriter pw = new PrintWriter("players.txt");
			for(int i = 0; i < P.size(); i++)
			{
				pw.print(P.get(i).toStringAlso());
			}
			pw.close();
		}
		catch(Exception e)
		{
		System.out.println(e);
		}
	}
	class MyPanel extends JPanel
	{
		private int width, height;
		public MyPanel(int w, int h)
		{
			width = w;
			height = h;
		}
		public Dimension getPreferredSize()
		{
			return new Dimension(width, height);
		}
		public void paintComponent (Graphics g) 
		{
			HangFigure f = new HangFigure(width, height, 30);
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			if (f != null)
				f.draw(g2d);
		}
	}
}