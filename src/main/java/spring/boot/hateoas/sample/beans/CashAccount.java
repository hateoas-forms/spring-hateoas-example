package spring.boot.hateoas.sample.beans;

public class CashAccount {

	private final String number;

	private final double availableBalance;

	private final String description;

	public CashAccount(final String number, final String description, final double availableBalance) {
		this.number = number;
		this.description = description;
		this.availableBalance = availableBalance;
	}

	public String getNumber() {
		return number;
	}

	public double getAvailableBalance() {
		return availableBalance;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CashAccount other = (CashAccount) obj;
		if (number == null) {
			if (other.number != null) {
				return false;
			}
		}
		else if (!number.equals(other.number)) {
			return false;
		}
		return true;
	}

}
