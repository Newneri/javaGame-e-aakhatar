package generation;
import java.util.Random;
import java.util.Scanner;
import java.nio.file.*;
import java.util.List;
import java.io.IOException;


public class Level {
	private int rows;
	private int cols;
	private char[][] map;
	private Player player;
	private int[] initialPosition = new int[2];
	private Random rand = new Random();
	private static int numberOfLevels = 0;
	private boolean gameOn = true;
	private int numberOfCoins;
	
	private enum Movement{
		UP(-1, 0), DOWN(1, 0), RIGHT(0,1), LEFT(0, -1);
		private int y, x;
		private Movement(int y, int x) {
			this.y = y;
			this.x = x;
		}
		
		public int[] getMovement() { return new int[]{this.y, this.x}; }
		
	}	
	
	private Movement up = Movement.UP;
	private Movement down = Movement.DOWN;
	private Movement right = Movement.RIGHT;
	private Movement left = Movement.LEFT;
	
	
	public Level(String filename, String name) {
		
		numberOfLevels++;
		String[] stringMap = readLevel(filename);
		
		this.rows = stringMap.length;
		this.cols = stringMap[0].length();
		this.setMap(stringMap);
		int x;
		int y;
		boolean verif;
		do {
			y = rand.nextInt(this.rows);
			x = rand.nextInt(this.cols);
			verif = this.map[y][x] != ' ';
		} while(verif);
		
		this.initialPosition[0] = y;
		this.initialPosition[1] = x;
		this.player = new Player(this.initialPosition.clone(), name);
		
		this.map[y][x] = this.player.getName().charAt(0);
		
		this.numberOfCoins = this.countCoins();
		
	}
	
	public int countCoins() {
		int coins = 0;
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				if(this.getMap()[y][x] == '.') {
					coins++;
				}
			}
		}
		return coins;
	}	
	

	public String[] readLevel(String filename) {
        try {
            Path path = Paths.get(filename);
            
            List<String> lines = Files.readAllLines(path);
            
            return lines.toArray(new String[0]);
            
        } catch (IOException e) {
            System.err.println("Fatal error : Impossible to read file " + filename);
            e.printStackTrace();
            
            throw new RuntimeException("Program Stopped : File not found.");
        }
    }
	
	public int getNumberOfCoins() { return this.numberOfCoins; }
	
	public void setNumberOfCoins(int coins) {
		if(coins >= 0) {
			this.numberOfCoins = coins;
		} else {
			this.numberOfCoins = 0;
		}
	}

	public boolean getGameOn() { return this.gameOn; }

	public void setGameOn(boolean state) {
		this.gameOn = state;
	}
	
	public static int getNumberOfLevels() { return numberOfLevels; }
	
	public int getRows() { return this.rows; }
	
	public int getCols() { return this.cols; }
	
	public char[][] getMap() { return this.map; }
	
	public void setMap(String[] stringMap) {
		this.map = new char[this.getRows()][this.getCols()];		
		for (int i = 0; i < this.rows; i++) {
	        this.map[i] = stringMap[i].toCharArray();
	    }
		
	}

	public Player getPlayer() { return this.player; }
	
	public int[] getInitialPosition() { return this.initialPosition.clone(); }

	@Override
	public String toString() {
		return "Level" + (getNumberOfLevels() + " Coins: " + this.getNumberOfCoins());
	}

	public void show() {
		System.out.println(this.getPlayer() + " --- " + this);
		for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
                System.out.print(this.getMap()[i][j]);
            }
            System.out.println(); 
        }
	}
	
	public boolean isWall(int y, int x) {
		return (this.getMap()[y][x] == '#');
	}

	public boolean checkMoveValidity(int y, int x) {
		return (!this.isWall(this.getPlayer().getPosition()[0] + y, this.getPlayer().getPosition()[1] + x) && 
        		( (this.getPlayer().getPosition()[1] + x ) >= 0 && (this.getPlayer().getPosition()[1] + x < this.getCols()) ) && 
        		( (this.getPlayer().getPosition()[0] + y ) >= 0 && (this.getPlayer().getPosition()[0] + y < this.getRows()) )); 
	}

	public void update() {
		int move[] = this.getInputKey();
		int y = move[0];
		int x = move[1];
		if(this.checkMoveValidity(y,x)) {
			this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = ' ';
			this.getPlayer().move(y, x);
			if(this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] == '.') {
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = this.player.getName().charAt(0);
				this.getPlayer().setScore(this.getPlayer().getScore() + 10);
				this.setNumberOfCoins(this.getNumberOfCoins() - 1);
			} else if(this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] == '*') {
				this.getPlayer().setLives(this.getPlayer().getLives() - 2);
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = ' ';
				this.getPlayer().setPosition(this.getInitialPosition());
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = this.player.getName().charAt(0);
			} else {
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = this.player.getName().charAt(0);

			}			
		}
		this.show();
	}

	public int[] getInputKey() {
		Scanner myScanner = new Scanner(System.in);
		int[] move = {0, 0};
		char key = String.valueOf(myScanner.nextLine()).charAt(0);
		switch (key){
			case 'q':
				move = this.left.getMovement();
				break;
			case 'z':
				move = this.up.getMovement();
				break;
			case 'd':
				move = this.right.getMovement();
				break;
			case 's':
				move = this.down.getMovement();
				break;
			default:
				break;
		}
		myScanner = null;
		return move;
	}

	public static void main(String[] args) {

		
	}
	/*
	 * if(this.getMap()[this.getPlayer().getPosition()[0] + y][this.getPlayer().getPosition()[1] + x] == '.') {
				this.getPlayer().setScore(this.getPlayer().getScore() + 10);
				this.setNumberOfCoins(this.getNumberOfCoins() - 1);
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = ' ';
				this.getMap()[this.getPlayer().getPosition()[0] + y][this.getPlayer().getPosition()[1] + x] = String.valueOf(Player.getNumberOfPlayers()).charAt(0);
				this.getPlayer().move(y, x);
			} else if(this.getMap()[this.getPlayer().getPosition()[0] + y][this.getPlayer().getPosition()[1] + x] == '*') {
				this.getPlayer().setLives(this.getPlayer().getLives() - 2);
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = ' ';
				this.getMap()[this.getPlayer().getPosition()[0]+y][this.getPlayer().getPosition()[1]+x] = ' ';
				this.getPlayer().setPosition(this.getInitialPosition());
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = String.valueOf(Player.getNumberOfPlayers()).charAt(0);
				
			} else {
				this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]] = ' ';
				this.getMap()[this.getPlayer().getPosition()[0] + y][this.getPlayer().getPosition()[1] + x] = String.valueOf(Player.getNumberOfPlayers()).charAt(0);
				this.getPlayer().move(y, x);
			}
	 * */

}
