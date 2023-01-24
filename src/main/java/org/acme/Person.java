package org.acme;

import java.util.Objects;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.api.annotations.indexing.Keyword;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@Indexed
public class Person {

	private String id;
	private String name;
	private String surname;
	private Integer birthYear;

	@ProtoFactory
	public Person(String id, String name, String surname, Integer birthYear) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthYear = birthYear;
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

	@ProtoField(number = 4)
	@Keyword(projectable = true, sortable = true)
	public Integer getBirthYear() {
		return birthYear;
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

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", birthYear=" + birthYear + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthYear, id, name, surname);
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
		return Objects.equals(birthYear, other.birthYear) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}	
}
