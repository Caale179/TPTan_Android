package fr.nantes.iut.tptan.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Arret implements Parcelable {

    public static final Parcelable.Creator<Arret> CREATOR = new Parcelable.Creator<Arret>() {
        public Arret createFromParcel(Parcel in) {
            return new Arret(in);
        }

        public Arret[] newArray(int size) {
            return new Arret[size];
        }
    };

	private String codeLieu;
	private String libelle;
	private String distance;

    @SerializedName("ligne")
	private List<NumLigne> lignes;

	private Stop stop;

    public Arret() {
    }

    private Arret(Parcel in) {
        codeLieu = in.readString();
        libelle = in.readString();
        distance = in.readString();
    }

	/**
	 * @return the codeLieu
	 */
	public String getCodeLieu() {
		return codeLieu;
	}

	/**
	 * @param codeLieu the codeLieu to set
	 */
	public void setCodeLieu(String codeLieu) {
		this.codeLieu = codeLieu;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return lignes
	 */
	public List<NumLigne> getLignes() {
		return lignes;
	}

	/**
	 * @param lignes the ligne to set
	 */
	public void setLignes(List<NumLigne> lignes) {
		this.lignes = lignes;
	}


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.codeLieu);
        out.writeString(this.libelle);
        out.writeString(this.distance);
    }

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}
}
