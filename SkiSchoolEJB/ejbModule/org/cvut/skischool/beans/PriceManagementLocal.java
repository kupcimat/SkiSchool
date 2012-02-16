package org.cvut.skischool.beans;

import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Category;
import org.cvut.skischool.model.Price;

/**
 *
 * @author matej
 */
@Local
public interface PriceManagementLocal {

    void createPrice(Price price);

    void updatePrice(Price price);

    void deletePrice(Price price);

    void createCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Category category);

    List<Price> getAllPrices();

    List<Price> getPricesByCategory(String category);

    List<Category> getAllCategories();
}
