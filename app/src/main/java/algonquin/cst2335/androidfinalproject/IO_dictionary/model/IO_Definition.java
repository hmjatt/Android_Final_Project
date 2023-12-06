package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

public class IO_Definition implements Parcelable {

    private String definition;

    public IO_Definition(String definition) {
        this.definition = definition;
    }

    protected IO_Definition(Parcel in) {
        definition = in.readString();
    }

    public static final Creator<IO_Definition> CREATOR = new Creator<IO_Definition>() {
        @Override
        public IO_Definition createFromParcel(Parcel in) {
            return new IO_Definition(in);
        }

        @Override
        public IO_Definition[] newArray(int size) {
            return new IO_Definition[size];
        }
    };

    public String getDefinition() {
        return definition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(definition);
    }
}
