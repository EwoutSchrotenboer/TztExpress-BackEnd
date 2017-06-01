package tztexpress.models;

import javax.persistence.*;

/**
 * Created by Ewout on 1-6-2017.
 */
@Entity
public class Traincourier {
    private Long id;
    private Boolean vogApproved;
    private String identification;
    private Long userId;

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator="Traincourier_Id_seq")
    @SequenceGenerator(name="Traincourier_Id_seq", sequenceName = "Traincourier_Id_seq")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Vogapproved", nullable = false)
    public Boolean getVogApproved() {
        return vogApproved;
    }

    public void setVogApproved(Boolean vogApproved) {
        this.vogApproved = vogApproved;
    }

    @Basic
    @Column(name = "Identification", nullable = false, length = 255)
    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    @Basic
    @Column(name = "Userid", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Traincourier that = (Traincourier) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (vogApproved != null ? !vogApproved.equals(that.vogApproved) : that.vogApproved != null) return false;
        if (identification != null ? !identification.equals(that.identification) : that.identification != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (vogApproved != null ? vogApproved.hashCode() : 0);
        result = 31 * result + (identification != null ? identification.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
