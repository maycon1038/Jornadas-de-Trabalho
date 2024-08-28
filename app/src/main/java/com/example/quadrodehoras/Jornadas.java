package com.example.quadrodehoras;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Jornadas implements Parcelable {

    private String idDoc;

    private String name;

   ArrayList<Jornada> listJornadas = new ArrayList<>();

    protected Jornadas(Parcel in) {
        idDoc = in.readString();
        name = in.readString();
        listJornadas = in.createTypedArrayList(Jornada.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idDoc);
        dest.writeString(name);
        dest.writeTypedList(listJornadas);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Jornadas> CREATOR = new Creator<Jornadas>() {
        @Override
        public Jornadas createFromParcel(Parcel in) {
            return new Jornadas(in);
        }

        @Override
        public Jornadas[] newArray(int size) {
            return new Jornadas[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public Jornadas(String name,   ArrayList<Jornada> listJornadas) {
        this.idDoc =  GeradorIdDoc.gerarIdDocUnico();
        this.name = name;
        this.listJornadas = listJornadas;
    }
}