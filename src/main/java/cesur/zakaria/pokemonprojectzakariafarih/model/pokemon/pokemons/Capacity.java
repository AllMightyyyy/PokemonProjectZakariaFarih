package cesur.zakaria.pokemonprojectzakariafarih.model.pokemon.pokemons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class Capacity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static HashMap<String, Capacity> nameMap = new HashMap<String, Capacity>();
	private final String name;
	private final int power;
	private final int accuracy;
	private int powerPoint;
	private final int maxPowerPoint;
	private final CategoryCapacity categoryCapacity;
	private final EnumPokemonType type;
	

	private Capacity(String name, int power,int pp, int accuracy, CategoryCapacity categoryCapacity,EnumPokemonType type) {
		super();
		this.name = Objects.requireNonNull(name);
		if((power<15 || power>300 || power%5!=0) && power!=0 ) {
			throw new IllegalArgumentException("The power need to be between 15 and 300, and he should be a multiple of 5 and is  "+power);
		}
		this.power = power;
		this.powerPoint=pp;
		if(accuracy<0 || accuracy>100) {
			throw new IllegalArgumentException("The precision is a percentage rate, it need to be between 0 and 100");
		}
		this.accuracy = accuracy;
		this.categoryCapacity = Objects.requireNonNull(categoryCapacity);
		this.type=type;
		maxPowerPoint=pp;
		
	}

	public EnumPokemonType getType() {
		return type;
	}

	public int getPower() {
		return power;
	}

	public String getName() {
		return name;
	}
	

	public static Capacity instance(String name, int power,int powerPoint, int accuracy, CategoryCapacity categoryCapacity,EnumPokemonType type) {
		if(nameMap.containsKey(name)) {
			throw new IllegalArgumentException("The capacity name is already taken");
		}
		nameMap.put(name, new Capacity(name, power,powerPoint, accuracy, categoryCapacity,type));
		return new Capacity(name, power,powerPoint, accuracy, categoryCapacity,type);
	}


	@Override
	public String toString() {
		return "Capacity [name=" + name + ", power=" + power + ", accuracy=" + accuracy + ", categoryCapacity="
				+ categoryCapacity + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryCapacity == null) ? 0 : categoryCapacity.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + power;
		result = prime * result + accuracy;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Capacity other = (Capacity) obj;
		if (categoryCapacity != other.categoryCapacity)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (power != other.power)
			return false;
		if (accuracy != other.accuracy)
			return false;
		return true;
	}
	

	public boolean isUsable() {
		if(powerPoint==0) {
			return false;
		}
		return true;
	}

	public int getPowerPoint() {
		return powerPoint;
	}

	public int getMaxPowerPoint() {
		return maxPowerPoint;
	}

	public void substractPP() {
		if(powerPoint>0) {
			powerPoint-=1;
		}
	}
	
	
	
	
	
	
}
