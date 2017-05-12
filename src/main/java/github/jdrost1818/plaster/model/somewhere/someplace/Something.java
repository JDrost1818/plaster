package github.jdrost1818.plaster.model.somewhere.someplace;

import javax.persistence.*;

import java.util.Date;

@Entity
public class Something {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	private Integer something;

	private Date dob;

	public Integer getSomething() {
		return this.something;
	}

	public Integer setSomething(Integer something) {
		this.something = something;
	}
	public Date getDob() {
		return this.dob;
	}

	public Date setDob(Date dob) {
		this.dob = dob;
	}

}
