import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Esta clase define el "lado izquierdo" de la funcion de transicion
 * extendida para el automata, es decir, el estado actual y el simbolo
 * del alfabeto recibido.
 * @author adrian
 *
 */
public class Leftside {
	
	
							//Atributos
	//-----------------------------------------------------------
	public int state;
	public String symbol;
	//-----------------------------------------------------------
	
	public Leftside(int st, String sym){
		state = st;
		symbol = sym;		
	}
	
	@Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
            append(state).
            append(symbol).
            toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Leftside))
            return false;
        if (obj == this)
            return true;

        Leftside rhs = (Leftside) obj;
        return new EqualsBuilder().
            append(state, rhs.state).
            append(symbol, rhs.symbol).
            isEquals();
    }
}
