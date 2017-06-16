package no.hib.models;

public class OtherSubject {
    private String _id;
    private String _rev;
    private String uuid;
    private String name;

    public OtherSubject(String name) {
        this.name = name;
    }

    public OtherSubject(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public OtherSubject(String _id, String _rev, String uuid, String name) {
        this._id = _id;
        this._rev = _rev;
        this.uuid = uuid;
        this.name = name;
    }

    public OtherSubject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OtherSubject that = (OtherSubject) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
