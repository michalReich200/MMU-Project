package dm;

import java.util.Objects;


public class DataModel<T>  implements java.io.Serializable {
    java.lang.Long id;
    T content;

    /**
     *
     * @param id - the id of a data
     * @param content -some data
     */
    public  DataModel(java.lang.Long id, T content){
        this.id=id;
        this.content=content;

    }

    /**
     * checks if two data modals ar equals
     * @param o the data we want to compare to
     * @return true if equal False if not
     */
    @Override
    public boolean equals(Object o) {

        DataModel<T> object = (DataModel<T>) o;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return  (object.id.equals(this.id) &&
                object.content.equals(this.content));
    }

    /**
     *
     * @return- -a hash code
     */

    @Override
    public int hashCode() {
        return Objects.hash(id, content);
    }


    /**
     *
     * @return -a string format for data modal
     */
    @Override
    public String toString() {
        return "DataModel{" +
                "id=" + id +
                ", content=" + content +
                '}';
    }
    public T getContent() {
        return content;
    }

    public java.lang.Long getDataModelId(){

        return id;
    }

}
