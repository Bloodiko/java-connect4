package com.bloodiko;

import java.util.Arrays;
import java.util.Scanner;

public class VierGewinnt {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";

	public static void main(String[] args) {
	
		String[][] dummy = new String[6][7];
		for (String[] row : dummy)
			Arrays.fill(row, " ");
		System.out.println("Lets play Connect Four (For 2 Players): ");
//		dummy[0][6] = "\u001B[34m●\u001B[0m";
		printGame(dummy);// Void
		Scanner input = new Scanner(System.in);

		String p1 = "";
		String p2 = "";

		boolean invalid;
		do {
			System.out.println("Player 1 --  Choose 'Blue' (\u001B[34m●\u001B[0m) or 'Red' (\u001B[31m●\u001B[0m) : ");
			System.out.println("Player 2 will use the other Symbol.");
			String userinput1 = input.nextLine();
			String userinput = userinput1.substring(0, 1).toUpperCase() + userinput1.substring(1);

			// input.close();

			if (userinput.equals("Blue")) {
				System.out.println("Player 1 chose Blue: \u001B[34m●\u001B[0m ");
				System.out.println("Player 2 is Red: \u001B[31m●\u001B[0m ");
				p1 = "\u001B[34m●\u001B[0m";
				p2 = "\u001B[31m●\u001B[0m";
				invalid = false;
			} else if (userinput.equals("Red")) {
				System.out.println("Player 1 chose Red: \u001B[31m●\u001B[0m ");
				System.out.println("Player 2 is Blue: \u001B[34m●\u001B[0m ");
				p2 = "\u001B[34m●\u001B[0m";
				p1 = "\u001B[31m●\u001B[0m";
				invalid = false;
			} else {
				System.out.println("Input not valid - Try again...");
				invalid = true;
			}
		} while (invalid);

		int winner = 0;
		for (int i = 0; i < 42 && winner ==0 ; i++) {

			int player = 0;
			if (i % 2 == 0)
				player = 1;
			else if (i % 2 == 1)
				player = 2;
			System.out.println();
			System.out.println("Player " + player + "'s Turn:");
			System.out.println("Enter Row as number 1 to 7 to insert - Press Enter when done: ");
			int x = 0;
			while (x<1 || x>7) {
			
				System.out.println("Please enter only 1 to 7. Do not enter Red Rows. ");
				System.out.println("You will return here if input is invalid or the chosen row is Full.");
				x = input.nextInt();
				if(x>7 || x<1) x=0;
				else if (dummy[0][x-1].contains("●")) x = 8;
			}
			
			x--;

			WriteInput(dummy, player, x, p1, p2);

			printGame(dummy);

			if (i >= 6) winner = checkWinCondition(dummy, p1, p2);
			System.out.println("Winner Debug: "+ winner);

//			System.out.println(x + "+" + y);

			
		}
		input.close();

	}



	public static int checkWinCondition(String[][] dummy, String p1, String p2) {
		int winnerCheck = 0;

	/*	String line1 = dummy[0][0] + dummy[0][1] + dummy[0][2];
		String line2 = dummy[1][0] + dummy[1][1] + dummy[1][2];
		String line3 = dummy[2][0] + dummy[2][1] + dummy[2][2];
		String row1 = dummy[0][0] + dummy[1][0] + dummy[2][0];
		String row2 = dummy[0][1] + dummy[1][1] + dummy[2][1];
		String row3 = dummy[0][2] + dummy[1][2] + dummy[2][2];
		String diag = dummy[0][0] + dummy[1][1] + dummy[2][2];
		String diag2 = dummy[0][2] + dummy[1][1] + dummy[2][0];

	 */

		String[] win = GetWinConditions(dummy) ;
		String winp1 = p1.repeat(4);
		String winp2 = p2.repeat(4);
		System.out.println(Arrays.toString(win)); //← Debug Win Conditions
		for (String s : win) {
			if (s.contains(winp1)) {
				System.out.println("Player one (" + p1 + ") wins! GG.");
				System.out.println("Thanks for Playing  :) ");
				winnerCheck = 1;
				break;
			} else if (s.contains(winp2)) {
				System.out.println("Player two (" + p2 + ") wins! GG.");
				System.out.println("Thanks for Playing  :) ");
				winnerCheck = 2;
				break;
			}
		}

		System.out.println("winnerCheck Debug: " + winnerCheck);
		return winnerCheck;

	}

	public static void WriteInput(String[][] dummy, int player, int x, String p1, String p2)
	{
		int i = 0;
		
		if (player==1)
		{
			
			while(i<6 &&dummy[i][x].contentEquals(" "))
				{i++;}
				i--;
				dummy[i][x]=p1;
		}
		else if (player==2)
		{
			while(i<6 && dummy[i][x].contentEquals(" "))
				{i++;}
				i--;
				dummy[i][x]=p2;
		}
	}
	//String builder not useful in this case
	@SuppressWarnings("StringConcatenationInLoop")
	public static String[] GetWinConditions(String[][] dummy)
	{
		String[] win = new String[25];
		int i = 0;
		String tmp = "";
		//columns:
		for (int i2 = 0; i2<6; i2++)
		{
			for (int i3 = 0; i3<7; i3++)
			{
				tmp += dummy[i2][i3];
			}
			win[i]= tmp;
			tmp="";
			i++;
		}
		//System.out.println("I - after rows, should be 6: "+i);
		//rows:
		for (int i2 = 0; i2<7; i2++)
		{
			for (int i3 = 0; i3<6; i3++)
			{
				tmp += dummy[i3][i2];

			}
			win[i]=tmp;
			tmp="";
			i++;
		}
		//System.out.println("I - after columns, should be 13: "+i);
		//Diagonal left top to right bottom

		int x = 2;
		int y = 0;
		for (int i2 = 0; i2<6 ; i2++  )
		{
			int x2 = x;
			int y2 = y;


			for (int i3 = 0 ; i3< 6 ; i3++)
			{

				tmp += dummy[x2][y2];
				x2 ++;
				y2 ++;
				if (x2 > 5 || y2 > 6) {
					break;
				}
			}


			win[i]=tmp;
			tmp="";
			i++;
			//System.out.println("I - in Diagonal 1 , should be 15,16,17,18,19,20: " + i);

			if (x == 0 )
				y ++;
			else x--;



		}
		//System.out.println("I - after Diagonal 1, should be 19: "+ i);

		//Diagonal top Right , to Bottom Left
		x = 0;
		y = 3;
		for (int i2 = 0; i2<6 ; i2++  )
		{
			int x2 = x;
			int y2 = y;


			for (int i3 = 0 ; i3< 6 ; i3++)
			{

				tmp += dummy[x2][y2];
				x2 ++;
				y2 --;
				if (x2 > 5 || y2 < 0 ) {
					break;
				}
			}


			win[i]=tmp;
			tmp="";
			i++;

			if (y == 6 )
				x ++;
			else y++;



		}

		//System.out.println("I after Diagonal 2 ,  Should be 25: "+i);

		return win;
	}
	public static void printGame(String[][] dummy) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
//		System.out.println("        1     2     3     4     5     6     7   ");
		System.out.print("     ");
		for (int i = 0; i<6;i++) {
			if(dummy[0][i].contentEquals(" "))
				System.out.print("   "+ANSI_GREEN+(i+1)+ANSI_RESET+"  ");
			else if(!dummy[0][i].contentEquals(" "))
				System.out.print("   "+ANSI_RED+(i+1)+ANSI_RESET+"  ");			
		}
		if(dummy[0][6].contentEquals(" "))
			System.out.println(ANSI_GREEN+"   7   "+ANSI_RESET);
		else if(!dummy[0][6].contentEquals(" "))
			System.out.println(ANSI_RED+"   7   "+ANSI_RESET);
		System.out.println("     ___________________________________________");
		System.out.println("     |     |     |     |     |     |     |     |");
		System.out.println("     |  " + dummy[0][0] + "  |  " + dummy[0][1] + "  |  " + dummy[0][2] + "  |  " + dummy[0][3] + "  |  " + dummy[0][4] + "  |  " + dummy[0][5] + "  |  " + dummy[0][6] + "  |");
		System.out.println("     |_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("     |     |     |     |     |     |     |     |");
		System.out.println("     |  " + dummy[1][0] + "  |  " + dummy[1][1] + "  |  " + dummy[1][2] + "  |  " + dummy[1][3] + "  |  " + dummy[1][4] + "  |  " + dummy[1][5] + "  |  " + dummy[1][6] + "  |");		
		System.out.println("     |_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("     |     |     |     |     |     |     |     |");
		System.out.println("     |  " + dummy[2][0] + "  |  " + dummy[2][1] + "  |  " + dummy[2][2] + "  |  " + dummy[2][3] + "  |  " + dummy[2][4] + "  |  " + dummy[2][5] + "  |  " + dummy[2][6] + "  |");		
		System.out.println("     |_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("     |     |     |     |     |     |     |     |");
		System.out.println("     |  " + dummy[3][0] + "  |  " + dummy[3][1] + "  |  " + dummy[3][2] + "  |  " + dummy[3][3] + "  |  " + dummy[3][4] + "  |  " + dummy[3][5] + "  |  " + dummy[3][6] + "  |");		
		System.out.println("     |_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("     |     |     |     |     |     |     |     |");
		System.out.println("     |  " + dummy[4][0] + "  |  " + dummy[4][1] + "  |  " + dummy[4][2] + "  |  " + dummy[4][3] + "  |  " + dummy[4][4] + "  |  " + dummy[4][5] + "  |  " + dummy[4][6] + "  |");		
		System.out.println("     |_____|_____|_____|_____|_____|_____|_____|");
		System.out.println("     |     |     |     |     |     |     |     |");
		System.out.println("     |  " + dummy[5][0] + "  |  " + dummy[5][1] + "  |  " + dummy[5][2] + "  |  " + dummy[5][3] + "  |  " + dummy[5][4] + "  |  " + dummy[5][5] + "  |  " + dummy[5][6] + "  |");		
		System.out.println("     |_____|_____|_____|_____|_____|_____|_____|");
		System.out.println();
		System.out.println();
	}
}
