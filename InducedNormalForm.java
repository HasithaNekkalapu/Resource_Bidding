import java.util.ArrayList;
import java.util.HashMap;

public class InducedNormalForm {

	static String calculateUtility(int[] strategy_agent1, int purse, int n) {
		int sum = n;
		ArrayList<ArrayList<Integer>> strategies1 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> strategies2 = new ArrayList<>();
		for (int i = 0; i <= sum; i++) {
			for (int j = 0; i + j <= sum; j++) {
				for (int k = 0; i + j + k <= sum; k++) {
					ArrayList<Integer> strat = new ArrayList<>();
					strat.add(i);
					strat.add(j);
					strat.add(k);
					strategies1.add(strat);
					strategies2.add(strat);
				}
			}
		}
		System.out.println(strategies1);
		System.out.println(strategies2);

		ArrayList normalform_matrix[][] = new ArrayList[strategies1.size()][strategies2.size()];
		boolean first_Agent1 = true;
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(1, 0);
		map.put(2, 0);

		for (int i = 0; i < strategies1.size(); i++) {
			for (int j = 0; j < strategies2.size(); j++) {
				int purse_player1 = purse;
				int purse_player2 = purse;
				
				//Uncomment this for question 2 part 1
				/*ArrayList<Integer> strat1 = new ArrayList<>();
				for (int z = 0; z < strategy_agent1.length; z++) {
					strat1.add(strategy_agent1[z]);
				}*/
				// System.out.println(strat1);
				ArrayList<Integer> strat1 = strategies1.get(i);
				ArrayList<Integer> strat2 = strategies2.get(j);
				for (int k = 0; k <= 2; k++) {
					if ((strat1.get(k) > strat2.get(k)) && purse_player1 >= 0 && purse_player2 >= 0) {
						map.put(1, map.get(1) + 1);
						purse_player1 = purse_player1 - strat1.get(k);
						first_Agent1 = !first_Agent1;

					} else if ((strat1.get(k) == strat2.get(k)) && purse_player1 >= 0 && purse_player2 >= 0) {
						if (first_Agent1) {
							map.put(1, map.get(1) + 1);
							purse_player1 = purse_player1 - strat1.get(k);
						} else {
							map.put(2, map.get(2) + 1);
							purse_player2 = purse_player2 - strat2.get(k);
						}
						first_Agent1 = !first_Agent1;
					} else if ((strat1.get(k) < strat2.get(k)) && purse_player1 >= 0 && purse_player2 >= 0) {
						map.put(2, map.get(2) + 1);
						purse_player2 = purse_player2 - strat2.get(k);
						first_Agent1 = !first_Agent1;
					}
				}
				ArrayList<Integer> table = new ArrayList<>();
				table.add(map.get(1) * 3 + purse_player1);
				table.add(map.get(2) * 3 + purse_player2);
				normalform_matrix[i][j] = table;
				map.put(1,0);
				map.put(2,0);
			}
		}
		for (int a = 0; a < 20; a++) {
			for (int b = 0; b < 20; b++) {
				System.out.print(normalform_matrix[a][b]);
			}
			System.out.println("");
		}
		return "";
	}

	public static void main(String args[]) {
		int[] strategy_agent1 = new int[] { 3, 0, 0 };
		int m = 3;
		int n = 3;
		calculateUtility(strategy_agent1, m, n);
	}
}