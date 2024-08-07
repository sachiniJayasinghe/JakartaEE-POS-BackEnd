package lk.ijse.gdse68.javaeeposbackend.dao.custom;

import lk.ijse.gdse68.javaeeposbackend.dao.CrudDAO;
import lk.ijse.gdse68.javaeeposbackend.entity.Items;

public interface ItemsDAO extends CrudDAO<Items> {
    public Boolean updateQty(String code,int qty) throws Exception;

}
