package tabusearch;

/**
 * This class represent a tabu move.
 * @author bmael
 *
 */
public class Move {
	private int variable;
	private int value;


	/**
	* Constructor
	* @param variable The variable of the Move
	* @param value The value of the Move
	*/
	public Move(int variable, int value){
		this.variable = variable;
		this.value = value;
	}

	/**
	* Variable Getter
	* @return the variable
	*/
	public int getVariable() {
		return variable;
	}
	
	/**
	* Value Getter
	* @return the value
	*/
	public int getValue() {
		return value;
	}
	
	/**
	* Variable Setter
	* @param variable the variable to set
	*/
	public void setVariable(int variable) {
		this.variable = variable;
	}
	
	/**
	* Value Setter
	* @param value the value to set
	*/
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object object) {
		if(this.getClass() == object.getClass()){
			Move tmp = (Move) object;
			return(this.variable == tmp.getVariable() && this.value == tmp.getValue());
		}
	
		return false;
	}

}
