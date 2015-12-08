package ru.samgtu.collections;

/**
 * Created by Nikolay on 04.11.2015.
 */
public final class ClassForTest implements Cloneable {

    String valueString;
    int valueInt;

    public ClassForTest(String valueString, int valueInt) {
        this.valueString = valueString;
        this.valueInt = valueInt;
    }

    @Override
    public Object clone() {
        ClassForTest clone = null;
        try {
            clone = (ClassForTest) super.clone();
        } catch (CloneNotSupportedException ex) {
            System.out.println("Exception: " + ex.toString());
        }
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassForTest that = (ClassForTest) o;

        if (valueInt != that.valueInt) return false;
        if (valueString != null ? !valueString.equals(that.valueString) : that.valueString != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = valueString != null ? valueString.hashCode() : 0;
        result = 31 * result + valueInt;
        return result;
    }

    @Override
    public String toString() {
        return "ClassForTest{" +
                "valueString='" + valueString + '\'' +
                ", valueInt=" + valueInt +
                '}';
    }
}
