package tztexpress.models;

/**
 * Created by Ewout on 30-5-2017.
 */
public class TraincourierDbModel {
    private long id;
    private boolean vogApproved;
    private String identification;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isVogApproved() {
        return vogApproved;
    }

    public void setVogApproved(boolean vogApproved) {
        this.vogApproved = vogApproved;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TraincourierDbModel that = (TraincourierDbModel) o;

        if (id != that.id) return false;
        if (vogApproved != that.vogApproved) return false;
        if (identification != null ? !identification.equals(that.identification) : that.identification != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (vogApproved ? 1 : 0);
        result = 31 * result + (identification != null ? identification.hashCode() : 0);
        return result;
    }
}
