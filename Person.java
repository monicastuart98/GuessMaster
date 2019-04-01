/**
 * Created by monica on 2018-04-01.
 */

package com.example.monica.guessmaster;
public class Person extends com.example.monica.guessmaster.Entity{
    private String gender;

    public Person(String name, Date birthDate) {
        super(name, birthDate);
    }

    public Person(String name,
                  Date birthDate,
                  String gender,
                  double difficulty) {
        super(name, birthDate, difficulty);
        this.gender = gender;
    }

    public Person(Person person) {
        super(person);
        this.gender = person.gender;
    }

    public String entityType() {
        return "This entity is a person!";
    }

//	public String welcomeMessage() {
//		return "Welcome! Letís start the game! "+entityType();
//	}

    public String toString() {
        return super.toString() + "Gender: " + gender + "\n";
    }

    public Person clone() {
        return new Person(this);
    }
}

