package com.example.quadrodehoras;

import android.os.Parcel;
import android.os.Parcelable;

public class Jornada implements Parcelable {

   String idDoc;
    int hrTrabalho;
    int hrFolgas;

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public Jornada(String idDoc, int hrTrabalho, int hrFolgas) {
        this.idDoc = idDoc;
        this.hrTrabalho = hrTrabalho;
        this.hrFolgas = hrFolgas;
    }

    protected Jornada(Parcel in) {
        idDoc = in.readString();
        hrTrabalho = in.readInt();
        hrFolgas = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idDoc);
        dest.writeInt(hrTrabalho);
        dest.writeInt(hrFolgas);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Jornada> CREATOR = new Creator<Jornada>() {
        @Override
        public Jornada createFromParcel(Parcel in) {
            return new Jornada(in);
        }

        @Override
        public Jornada[] newArray(int size) {
            return new Jornada[size];
        }
    };

    @Override
    public String toString() {
        return "Jornadas{" +
                ", hrInicio=" + hrTrabalho +
                ", hrTermino=" + hrFolgas +
                '}';
    }


    public int getHrTrabalho() {
        return hrTrabalho;
    }

    public void setHrTrabalho(int hrTrabalho) {
        this.hrTrabalho = hrTrabalho;
    }

    public int getHrFolgas() {
        return hrFolgas;
    }

    public void setHrFolgas(int hrFolgas) {
        this.hrFolgas = hrFolgas;
    }


}