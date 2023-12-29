package lv.nixx.oop.domain;

public class Person {
	
	int id;
	String name;
	String surname;
	String iid;

	public Person(int id, String name, String surname, String iid) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.iid = iid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		Person other = (Person) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			return other.surname == null;
		} else return surname.equals(other.surname);
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", iid=" + iid + "]";
	}

}