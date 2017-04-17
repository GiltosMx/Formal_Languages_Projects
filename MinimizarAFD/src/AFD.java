import java.util.HashMap;

public class AFD {
	
					//Atributos
	//----------------------------------------------
	public int statesCount;
	public String[] symbols;
	public boolean[] isFavorable;
	public int favorableState;
	public int startState;
	public HashMap<Leftside, Integer> rules;
	//----------------------------------------------
	
	public AFD(){
		statesCount = 0;
		favorableState = 0;
		startState = 0;
		rules = new HashMap<Leftside, Integer>();
	}
	
	public String toString(){
		
		StringBuilder builder = new StringBuilder();
		
		
		builder.append("Automaton to be reduced:\n");
		builder.append("States count: " + statesCount);
		builder.append("\n-------------\nSymbols:\n");
		
		for (String symbol : symbols) {
			
			builder.append(symbol + ", ");			
		}
		
		builder.append("\n-------------\n");
		
		
		builder.append("-------------\nFavorable states:\n");
		
		for (int i = 0; i < isFavorable.length; i++) {
			
			if(isFavorable[i] == true)
				builder.append(i + " : " + isFavorable[i] + ",");
		}
		
		builder.append("\n-------------\n");
		
		builder.append("\nFavorable state: " + favorableState);
		
		builder.append("\nStart state: " + startState);
		
		builder.append("\n-------------\nRules:\n");
		
		this.rules.forEach((k,v) -> builder.append(k.state + "," + k.symbol + "--> " + v + "\n"));
		
		builder.append("-------------\n");
		
		return builder.toString();

	}

}
