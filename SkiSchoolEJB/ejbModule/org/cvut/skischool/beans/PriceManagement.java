package org.cvut.skischool.beans;

import java.util.List;
import javax.ejb.Stateless;
import org.cvut.skischool.model.Category;
import org.cvut.skischool.model.Price;

/**
 *
 * @author matej
 */
@Stateless
public class PriceManagement implements PriceManagementLocal {

    @Override
    public void createPrice(Price price) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updatePrice(Price price) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deletePrice(Price price) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void createCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteCategory(Category category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Price> getAllPrices() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Price> getPricesByCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Category> getAllCategories() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
