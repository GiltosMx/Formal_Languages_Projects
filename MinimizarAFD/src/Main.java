import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class Main {
	
				//Atributos
	//----------------------------------
	private static boolean found;
	//----------------------------------
	
	
	public static Scanner scanner = new Scanner(System.in);
	
	
	/**
	 * Se encarga de leer los datos de entrada al programa para
	 * tener el automata que se va a minimizar.
	 * @return Es el objeto de tipo AFD que fue creado con los valores
	 * introducidos por el usuario.
	 */
	public static AFD readInput(){
		AFD afd = new AFD();
		
		//Stores the number of states
		System.out.println("Number of states:");
		
		afd.statesCount = scanner.nextInt();
		
		scanner.nextLine();
		
		//Stores the symbols
		System.out.println("Symbols (comma separated):");
		
		afd.symbols = scanner.nextLine().split(",");
				
		//Stores the favorable states
		System.out.println("Favorable states (comma separated):");
		
		boolean[] favStates_boolTmp = new boolean[afd.statesCount + 1];
		
		String[] favStates_stringTmp = scanner.nextLine().split(",");
						
		for (int i = 0; i < favStates_stringTmp.length; i++) {
			favStates_boolTmp[Integer.parseInt(favStates_stringTmp[i])] = true; 
		}
		
		afd.isFavorable = favStates_boolTmp;
		
		
		//Stores start state
		System.out.println("Start state:\n");
		afd.startState = scanner.nextInt();		
		scanner.nextLine();
		
		afd.favorableState = Integer.parseInt(favStates_stringTmp[0]);
		
		
		//Stores the rules for the behavior
		System.out.println("Rules for the automaton (comma separated: st,sym,st. Finish with enter and \";\"):\n");
		
		String nextLine = "";
		
		do {
			try {
				nextLine = scanner.nextLine();
				
				String[] ruleArrayTmp = nextLine.split(",");			
				
				afd.rules.put(new Leftside(Integer.parseInt(ruleArrayTmp[0]), ruleArrayTmp[1]), 
						Integer.parseInt(ruleArrayTmp[2]));	
				
			} catch (Exception e) {
				break;
			}
			
		} while (!nextLine.contains(";"));
		
		return afd;
	}
	
	
	/**
	 * Se encarga de hacer el paso 2 del procedimiento marcar, verificando cuales
	 * pares de estados son distinguibles.
	 * @param afd Objeto AFD con los datos del automata que se va a minimizar.
	 * @return HashMap que ya contiene los pares de estados distinguibles marcados
	 * en true.
	 */
	public static HashMap<Pair, Boolean> setupMarkedPairs(AFD afd){
		
		HashMap<Pair, Boolean> pairs = new HashMap<Pair, Boolean>();
		
		for (int i = 1; i <= afd.statesCount; ++i) {
			
			for (int j = 1; j <= afd.statesCount; ++j) {
				boolean marked = ( (afd.isFavorable[i] == true && afd.isFavorable[j] == false) || 
						(afd.isFavorable[i] == false && afd.isFavorable[j] == true) );
				
				pairs.put(new Pair(i, j), marked);
			}
		}
		
		return pairs;		
	}
	
	
	/**
	 * Se encarga de hacer el paso 3 del procedimiento marcar, verificando la funcion
	 * de transicion extendida de cada uno de los pares de estados que estaban
	 * marcados como indistinguibles.
	 * @param afd Objeto de tipo AFD, que es el automata que se va a minimizar.
	 * @param pairs HashMap obtenido de setupMarkedPairs(AFD afd). Se utiliza para
	 * obtener los pares de estados indistinguibles, y en caso de que se reconozca
	 * un nuevo par distinguible, se marca dentro del mismo.
	 */
	public static void processPairs(AFD afd, HashMap<Pair, Boolean> pairs){
		do {
			
			found = false;
			
			for(Map.Entry<Pair, Boolean> entry : pairs.entrySet()) {
			    Pair k = entry.getKey();
			    Boolean v = entry.getValue();

				if (v == false){
					for (String a : afd.symbols) {
						Integer d1 = afd.rules.get(new Leftside(k.P, a));
						Integer d2 = afd.rules.get(new Leftside(k.Q, a));
						
						if(d1 != null && d2 != null){													
							if(pairs.get(new Pair(d1, d2)) != null && pairs.get(new Pair(d1, d2)) == true){
								pairs.put(new Pair(k.P, k.Q), true);
								found = true;
								break;
							}
						}
					}
					
				}
			}
			
		} while (found);
	}
	
	/**
	 * Se encarga de crear las clases de equivalencia con base en los estados distinguibles
	 * e indistinguibles obtenidos de processPairs(AFD afd, HashMap<Pair, Boolean> pairs).
	 * @param afd Objeto AFD que tiene los datos del automata que se va a minimizar. 
	 * @param pairs HashMap que contiene los pares de estados (Key) y si son distinguibles
	 * o indistinguibles (Value).
	 * @return Arreglo con las clases de equivalencia procesadas, para minimizar el automata.
	 */
	public static int[] createEqClasses(AFD afd, HashMap<Pair, Boolean> pairs){
		
		int[] e_class = new int[afd.statesCount + 1];
		
		for (int i = 1; i <= afd.statesCount; ++i) {			
			e_class[i] = i;
		}
		
		pairs.forEach((k,v) -> {
			
			if(v == false){
				for (int i = 1; i <= afd.statesCount; ++i) {
					
					if(e_class[i] == k.P){
						e_class[i] = k.Q;
					}
				}
			}			
		});
		
		return e_class;
	}
	
	/**
	 * Solo se encarga de imprimir los resultados a consola, indicando al usuario el automata
	 * original que introdujo, y el automata reducido despues de haber pasado por el algoritmo.
	 * @param afd Objeto AFD que tiene los datos del automata que se va a minimizar.
	 * @param pairs HashMap que contiene los pares de estados (Key) y si son distinguibles
	 * o indistinguibles (Value).
	 * @param e_class Arreglo con las clases de equivalencia obtenidas de
	 * createEqClasses(AFD afd, HashMap<Pair, Boolean> pairs)
	 */
	public static void outputResults(AFD afd, HashMap<Pair, Boolean> pairs, int[] e_class){
		
		HashMap<Integer, Boolean> states = new HashMap<Integer, Boolean>();
		
		for (int state = 1; state <= afd.statesCount; ++state) {
			states.put(e_class[state], true);
		}
		
		
		HashMap<String, Boolean> rules = new HashMap<String, Boolean>();
		
		afd.rules.forEach((k,v) -> {
			
			StringBuilder rule_str = new StringBuilder();
			
			rule_str.append("(" + e_class[k.state]);
			rule_str.append(", " + k.symbol);
			rule_str.append(") -> " + e_class[v]);
			
			rules.put(rule_str.toString(), true);			
		});
		
		
		System.out.println("Reduced automaton:\n-------------\n");
		
		System.out.print("States: ");
		
		states.forEach((k,v) -> {
			
			System.out.print(k + " ");
		});
		
		System.out.println();
		
		System.out.println("Inital state: " + e_class[afd.startState] 
				+ "\nFavorable state: " + e_class[afd.favorableState]);
		
		System.out.println("Rules:");
		rules.forEach((k,v) -> {
			
			System.out.print(k + "\n");			
		});
		
		System.out.println("-------------");
	}
	

	public static void main(String[] args) {
		
		AFD afd = readInput();
		
		System.out.println(afd);
		
		HashMap<Pair, Boolean> pairs = setupMarkedPairs(afd);
		
		processPairs(afd, pairs);
		
		int[] e_class = createEqClasses(afd, pairs);
		
		outputResults(afd, pairs, e_class);
	}
	
}
