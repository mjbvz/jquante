package name.mjw.jquante.math.qm.event;

/**
 * Represents an event in an SCF cycle
 * 
 * @author V.Ganesh
 * @version 2.0 (Part of MeTA v2.0)
 */
public final class SCFEvent extends java.util.EventObject {

	/**
	 * Eclipse generated serialVersionUID
	 */
	private static final long serialVersionUID = 3063823325261803339L;

	/**
	 * Holds value of property currentIteration.
	 */
	private int currentIteration;

	/**
	 * Holds value of property currentEnergy.
	 */
	private double currentEnergy;

	/**
	 * Holds value of property type.
	 */
	private int type;

	/**
	 * Informative message about SCF, like intermediate energy etc.
	 */
	public static final int INFO_EVENT = 1;

	/**
	 * SCF Convergence event
	 */
	public static final int CONVERGED_EVENT = 2;

	/**
	 * Failed SCF convergence event
	 */
	public static final int FAILED_CONVERGENCE_EVENT = 2;

	/**
	 * Creates a new instance of SCFEvent
	 * 
	 * @param source
	 *            The object upon which the Event in question initially occurred
	 *            upon.
	 */
	public SCFEvent(Object source) {
		super(source);

		currentEnergy = 0.0;
		currentIteration = 0;

		type = INFO_EVENT;
	}

	/**
	 * Getter for property currentIteration.
	 * 
	 * @return Value of property currentIteration.
	 */
	public int getCurrentIteration() {
		return this.currentIteration;
	}

	/**
	 * Setter for property currentIteration.
	 * 
	 * @param currentIteration
	 *            New value of property currentIteration.
	 */
	public void setCurrentIteration(int currentIteration) {
		this.currentIteration = currentIteration;
	}

	/**
	 * Getter for property currentEnergy.
	 * 
	 * @return Value of property currentEnergy.
	 */
	public double getCurrentEnergy() {
		return this.currentEnergy;
	}

	/**
	 * Setter for property currentEnergy.
	 * 
	 * @param currentEnergy
	 *            New value of property currentEnergy.
	 */
	public void setCurrentEnergy(double currentEnergy) {
		this.currentEnergy = currentEnergy;
	}

	/**
	 * Getter for property type.
	 * 
	 * @return Value of property type.
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Setter for property type.
	 * 
	 * @param type
	 *            New value of property type.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Simple overridden toString()
	 * 
	 * @return the string representation of this event
	 */
	@Override
	public String toString() {
		return getCurrentIteration() + " " + getCurrentEnergy();
	}
}