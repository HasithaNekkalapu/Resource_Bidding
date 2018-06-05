import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BackwardInduction {

	static String calculateUtility(int[] strategy_agent1, int purse, int n) {
//Stategies are calculated. Can also be given as input in case of arbitrary purses and rounds
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
		// System.out.println(strategies1);

		ArrayList normalform_matrix[][] = new ArrayList[strategies1.size()][strategies2.size()];
		ArrayList played_by[][] = new ArrayList[strategies1.size()][strategies2.size()];
		boolean first_Agent1 = true;//flag to keep track of the rounds 
		HashMap<Integer, Integer> map = new HashMap<>(); // to keep track of agents and their wins 
		Map<String, Map.Entry<ArrayList<Integer>, ArrayList<Integer>>> chosen_strat = new HashMap<String, Map.Entry<ArrayList<Integer>, ArrayList<Integer>>>();
		map.put(1, 0);
		map.put(2, 0);

		for (int i = 0; i < strategies1.size(); i++) {
			for (int j = 0; j < strategies2.size(); j++) {
				int purse_player1 = purse;
				int purse_player2 = purse;
				ArrayList<Integer> strat1 = strategies1.get(i);
				/*for (int z = 0; z < strategy_agent1.length; z++) {
					strat1.add(strategy_agent1[z]);
				}
				System.out.println(strat1);*/
				ArrayList<Integer> strat2 = strategies2.get(j);
				ArrayList<Integer> strat1_local = new ArrayList<>();
				ArrayList<Integer> strat2_local = new ArrayList<>();
				
				for (int k = 0; k <= 2; k++) {
					if ((strat1.get(k) > strat2.get(k)) && purse_player1 >= 0 && purse_player2 >= 0) {
						map.put(1, map.get(1) + 1);
						purse_player1 = purse_player1 - strat1.get(k);
						first_Agent1 = !first_Agent1;
						strat1_local.add(strat1.get(k));
						strat2_local.add(strat2.get(k));
					} else if ((strat1.get(k) == strat2.get(k)) && purse_player1 >= 0 && purse_player2 >= 0) {
						if (first_Agent1) {
							map.put(1, map.get(1) + 1);
							purse_player1 = purse_player1 - strat1.get(k);
						} else {
							map.put(2, map.get(2) + 1);
							purse_player2 = purse_player2 - strat2.get(k);
						}
						first_Agent1 = !first_Agent1;
						strat1_local.add(strat1.get(k));
						strat2_local.add(strat2.get(k));
					} else if ((strat1.get(k) < strat2.get(k)) && purse_player1 >= 0 && purse_player2 >= 0) {
						map.put(2, map.get(2) + 1);
						purse_player2 = purse_player2 - strat2.get(k);
						first_Agent1 = !first_Agent1;
						strat1_local.add(strat1.get(k));
						strat2_local.add(strat2.get(k));
					}
				}
				ArrayList<Integer> table = new ArrayList<>();
				table.add(map.get(1) * 3 + purse_player1);
				table.add(map.get(2) * 3 + purse_player2);
				normalform_matrix[i][j] = table; //matrix of normal form
				chosen_strat.put("" + i + "-" + j + "",
						new AbstractMap.SimpleEntry<ArrayList<Integer>, ArrayList<Integer>>(strat1_local, strat2_local));
				map.put(1, 0);
				map.put(2, 0);
			}
		}
		for (int a = 0; a < 20; a++) {
			for (int b = 0; b < 20; b++) {
				//System.out.print(a + "-" + b + "" + normalform_matrix[a][b]);
			}
			//System.out.println("");
		}
		// Figure out Perfect Nash Sub problem here
		for (int a = 0; a < 20; a++) {
			int Perfectiv = 0, Perfectjv = 0, Perfecti = 0, Perfectj = 0;
			int PerfectAiv = 0, PerfectAjv = 0, PerfectAi = 0, PerfectAj = 0;
			int PerfectBiv = 0, PerfectBjv = 0, PerfectBi = 0, PerfectBj = 0;
			System.out.print("\n");
			for (int b = 0; b < 20; b++) {
				System.out.print(normalform_matrix[a][b]);
				if (Perfectiv < Integer.parseInt(normalform_matrix[a][b].get(0).toString())
						&& Perfectjv < Integer.parseInt(normalform_matrix[a][b].get(1).toString())) {
					Perfectiv = Integer.parseInt(normalform_matrix[a][b].get(0).toString());
					Perfectjv = Integer.parseInt(normalform_matrix[a][b].get(1).toString());
					Perfecti = a;
					Perfectj = b;
				}
				if (PerfectAiv < Integer.parseInt(normalform_matrix[a][b].get(0).toString())) {
					PerfectAiv = Integer.parseInt(normalform_matrix[a][b].get(0).toString());
					PerfectAjv = Integer.parseInt(normalform_matrix[a][b].get(1).toString());
					PerfectAi = a;
					PerfectAj = b;
				}
				if (PerfectBiv < Integer.parseInt(normalform_matrix[a][b].get(1).toString())) {
					PerfectBiv = Integer.parseInt(normalform_matrix[a][b].get(0).toString());
					PerfectBjv = Integer.parseInt(normalform_matrix[a][b].get(0).toString());
					PerfectBi = a;
					PerfectBi = b;
				}
			}
			System.out.println("");
			System.out.println("\n~~~~~~~Perfect Nash Equilibrium SubTree~~~~~~~~~");
			System.out.println("utilities = " + Perfectiv + "," + Perfectjv);
			printRoot(chosen_strat, Perfecti, Perfectj);
			System.out.print("\n Perfect Strategy Utility for Agent 1"+ " is " + PerfectAiv + "," + PerfectAjv + "\n");
			printRoot(chosen_strat, PerfectAi, PerfectAj);
			System.out.println("\n Perfect Strategy Utility for Agent 2" + " is " + PerfectBiv + "," + PerfectBjv);
			printRoot(chosen_strat, PerfectBi, PerfectBj);
		}

		return "";
	}

	static void printRoot(Map<String, Map.Entry<ArrayList<Integer>, ArrayList<Integer>>> chosen_strat, int Perfecti,
			int Perfectj) {
		Map.Entry<ArrayList<Integer>, ArrayList<Integer>> root = chosen_strat.get("" + Perfecti + "-" + Perfectj);
		ArrayList<Integer> Agent1 = root.getKey();
		ArrayList<Integer> Agent2 = root.getValue();
		int Agent1_p = 0;
		int Agent2_p = 0;
		for (int r = 0; r < 6; r++) {
			if (r % 2 == 0 && Agent1_p < 3) {
				System.out.print("(1 ) " + Agent1.get(Agent1_p) + " -->");
				Agent1_p++;
			} else if (r % 2 != 0 && Agent2_p < 3) {
				System.out.print("(2 ) " + Agent2.get(Agent2_p));
				if (r != 5) {
					System.out.print(" -->");
				}
				Agent2_p++;
			}
		}
	}

	public static void main(String args[]) {
		int[] strategy_agent1 = new int[] { 3, 0, 0 };
		int purse = 3;
		int n = 3;
		calculateUtility(strategy_agent1, purse, n);
	}
}
