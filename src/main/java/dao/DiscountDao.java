package dao;

import model.Discount;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class DiscountDao extends AbstractDaoImpl<Discount> {
    public DiscountDao() {
        super(Discount.class);
    }

    @Override
    public Discount create(Discount modelObject) throws DiscountCodeExistsException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(modelObject);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            throw new DiscountCodeExistsException();
        }

        return modelObject;
    }

    public boolean updateActive(int id, boolean newActive) {
        Discount discount = read(id);

        if (discount == null) {
            return false;
        }

        discount.setActive(newActive);
        update(discount);

        return true;
    }

    public List<Discount> getAll(){
        String sqlQuery = "select * from [pizzeria].[dbo].[discount]";
        Query query = entityManager.createNativeQuery(sqlQuery, Discount.class);
        return query.getResultList();
    }
}
