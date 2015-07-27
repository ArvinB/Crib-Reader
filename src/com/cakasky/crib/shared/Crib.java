package com.cakasky.crib.shared;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;


/*
 *  Purpose:
 *  
 *  Works with the crib
 *  notes and has a job
 *  to show them to the
 *  user
 * 
 *  Last modified by:
 *  Cameron Bhatnagar - 7.7.15
 *  
 */

@Entity
public class Crib implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Index @Id 
	public Long id;

	@Unindex private String cribName;
	@Unindex private String cribContext;

	// Default Constructor
	public Crib() {
	}
	
	public String getCribName() {
		return cribName;
	}

	public void setCribName(String cribName) {
		this.cribName = cribName;
	}

	public String getCribContext() {
		return cribContext;
	}

	public void setCribContext(String cribContext) {
		this.cribContext = cribContext;
	}
}
