package org.acme;

import java.util.Objects;

import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

public class Person {

	private String id;
	private String name;
	private String surname;

	@ProtoFactory
	public Person(String id, String name, String surname) {
		this.id = id;
		this.name = name;
		this.surname = surname;
	}

	@ProtoField(number = 1)
	public String getId() {
		return id;
	}

	@ProtoField(number = 2)
	public String getName() {
		return name;
	}

	@ProtoField(number = 3)
	public String getSurname() {
		return surname;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, surname);
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
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}

}
