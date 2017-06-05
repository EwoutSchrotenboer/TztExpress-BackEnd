package tztexpress.models;

import javax.persistence.*;

/**
 * Created by Ewout on 1-6-2017.
 */
@Entity
@Table(name="`externalcourier`")
public class Externalcourier {
    private Long id;
    private String companyname;
    private String type;
    private String email;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="Externalcourier_Id_seq")
    @SequenceGenerator(name="Externalcourier_Id_seq", sequenceName = "Externalcourier_Id_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "companyname", nullable = false, length = 255)
    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 255)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Externalcourier that = (Externalcourier) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (companyname != null ? !companyname.equals(that.companyname) : that.companyname != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyname != null ? companyname.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
