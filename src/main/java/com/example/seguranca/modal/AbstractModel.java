package com.example.seguranca.modal;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

@MappedSuperclass
public abstract class AbstractModel<E> implements Serializable {

    public abstract E getId();

    public abstract void setId(E id);

    public abstract List<String> getProperties();

    @Transient
    protected List<String> sort;

    @Transient
    protected Boolean ascendingSort;

    @Transient
    protected List<String> propertiesReturn;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public void setAscendingSort(Boolean ascendingSort) {
        this.ascendingSort = ascendingSort;
    }

    public Boolean getAscendingSort() {
        return ascendingSort;
    }

    public List<String> getPropertiesReturn() {
        return propertiesReturn;
    }

    public void setPropertiesReturn(List<String> propertiesReturn) {
        this.propertiesReturn = propertiesReturn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!this.getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        if (getId() != null && obj instanceof AbstractModel) {
            return getId().equals(((AbstractModel) obj).getId());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}
