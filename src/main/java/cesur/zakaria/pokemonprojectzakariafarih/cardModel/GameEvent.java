package cesur.zakaria.pokemonprojectzakariafarih.cardModel;

import java.util.EventObject;

/**
 * Represents an event in the game.
 * Extends EventObject to provide the functionality of a standard event object.
 */
public class GameEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * The enum Target.
     */
// Enumeration representing the target of the event
    public enum Target {
        /**
         * Deck target.
         */
        DECK,
        /**
         * Gwin target.
         */
        GWIN }

    /**
     * The enum Action.
     */
// Enumeration representing the action of the event
    public enum Action {
        /**
         * Invplay action.
         */
        INVPLAY,
        /**
         * Endgame action.
         */
        ENDGAME,
        /**
         * Removesel action.
         */
        REMOVESEL,
        /**
         * Showtable action.
         */
        SHOWTABLE,
        /**
         * Restart action.
         */
        RESTART,
        /**
         * Showmessage action.
         */
        SHOWMESSAGE,
        /**
         * Mustendturn action.
         */
        MUSTENDTURN }

    private Target target; // The target of the event
    private Action action; // The action associated with the event
    private String arg; // Additional argument for the event

    /**
     * Constructs a GameEvent with the specified source, target, action, and argument.
     *
     * @param source   The object on which the Event initially occurred.
     * @param aTarget  The target of the event.
     * @param anAction The action associated with the event.
     * @param anArg    Additional argument for the event.
     */
    public GameEvent(Object source, Target aTarget, Action anAction, String anArg) {
        super(source);
        target = aTarget;
        action = anAction;
        arg = anArg;
    }

    /**
     * Retrieves the target of the event.
     *
     * @return The target of the event.
     */
    public Target getTarget() {
        return target;
    }

    /**
     * Retrieves the action associated with the event.
     *
     * @return The action associated with the event.
     */
    public Action getAction() {
        return action;
    }

    /**
     * Retrieves the additional argument for the event.
     *
     * @return The additional argument for the event.
     */
    public String getArg() {
        return arg;
    }

    /**
     * Returns a string representation of the GameEvent.
     * @return A string representation of the GameEvent.
     */
    public String toString() {
        return target + ":" + action + ":" + arg;
    }
}
