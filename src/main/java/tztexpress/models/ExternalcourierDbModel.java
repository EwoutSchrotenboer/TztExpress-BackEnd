package tztexpress.models;

/**
 * Created by Ewout on 30-5-2017.
 */
public class ExternalcourierDbModel {
    private long id;
    private String companyname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalcourierDbModel that = (ExternalcourierDbModel) o;

        if (id != that.id) return false;
        if (companyname != null ? !companyname.equals(that.companyname) : that.companyname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (companyname != null ? companyname.hashCode() : 0);
        return result;
    }
}
