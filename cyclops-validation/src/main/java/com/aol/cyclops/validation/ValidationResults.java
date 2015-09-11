package com.aol.cyclops.validation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationResults {
	List<ValidationResult> results;
	
	public String toString(){
		return results.toString();
	}
}