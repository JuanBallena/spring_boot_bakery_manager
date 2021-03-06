package com.pablo.bakeryManager.dominio.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PUBLIC)
@ToString(callSuper=true, includeFieldNames=true)
@Table(name="units")
@Entity
public class Unit {
	
	public final static String NAME    = "name";
	public final static String VISIBLE = "visible";
	
	public static final List<String> allowedSorts =  new ArrayList<String>(Arrays.asList(NAME, VISIBLE));
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Unit_Id")
	private Long idUnit;

	@Column(name = "Unit_Name")
	private String name;
	
	@Column(name = "Unit_Visible", insertable = false)
	private Boolean visible;
	
}
