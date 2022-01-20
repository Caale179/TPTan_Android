package fr.nantes.iut.tptan.data.entity;

import java.io.Serializable;

import android.content.Context;

public class NumLigne implements Serializable{

	private String numLigne;

	public String getNumLigne() {
		return numLigne;
	}

	public void setNumLigne(String numLigne) {
		this.numLigne = numLigne;
	}
	
	
	public int getNumLigneImageResourceId(Context mContext){
		return mContext.getResources().getIdentifier("ligne_" + numLigne.toLowerCase(), "drawable",mContext.getPackageName());
	}
}
