package cesur.zakaria.pokemonprojectzakariafarih.model.fight;

import java.util.Objects;

/**
 * The Fight class represents a battle between two trainers.
 * It keeps track of the trainers involved in the fight and manages the turn-based flow of the battle.
 */
public class Fight {

	private final Trainer trainer1;
	private final Trainer trainer2;

	private Trainer currentTrainer;
	private int fightPlan;

    /**
     * Constructs a new Fight object with the given trainers.
     *
     * @param trainer1 The first trainer participating in the fight.
     * @param trainer2 The second trainer participating in the fight.
     * @throws NullPointerException if either trainer1 or trainer2 is null.
     */
    public Fight(Trainer trainer1, Trainer trainer2) {
		this.trainer1 = Objects.requireNonNull(trainer1);
		this.trainer2 = Objects.requireNonNull(trainer2);
		currentTrainer = trainer1;
		fightPlan = 1;
	}

    /**
     * Gets the first trainer participating in the fight.
     *
     * @return The first trainer.
     */
    public Trainer getTrainer1() {
		return trainer1;
	}

    /**
     * Gets the second trainer participating in the fight.
     *
     * @return The second trainer.
     */
    public Trainer getTrainer2() {
		return trainer2;
	}

    /**
     * Gets the current active trainer whose turn it is in the fight.
     *
     * @return The current active trainer.
     */
    public Trainer getCurrentTrainer() {
		return currentTrainer;
	}

    /**
     * Gets the non-active trainer in the fight.
     *
     * @return The non-active trainer.
     */
    public Trainer getNonCurrentTrainer() {
		if (currentTrainer.equals(trainer1)) {
			return trainer2;
		}
		return trainer1;
	}

    /**
     * Advances the turn to the next trainer's turn in the fight.
     */
    public void nextTurn() {
		if (currentTrainer.equals(trainer1)) {
			currentTrainer = trainer2;
		} else {
			currentTrainer = trainer1;
		}
		switchPlan();
	}

    /**
     * Gets the current fight plan.
     *
     * @return The current fight plan.
     */
    public int getFightPlan() {
		return fightPlan;
	}

    /**
     * Switches the fight plan.
     */
    public void switchPlan() {
		if (fightPlan == 0) {
			fightPlan = 1;
			return;
		}
		fightPlan = 0;
	}
}
