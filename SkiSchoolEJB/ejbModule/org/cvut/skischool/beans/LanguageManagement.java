package org.cvut.skischool.beans;

import java.util.List;
import javax.ejb.Stateless;
import org.cvut.skischool.model.Lang;

/**
 *
 * @author matej
 */
@Stateless
public class LanguageManagement implements LanguageManagementLocal {

    @Override
    public void createLanguage(Lang language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateLanguage(Lang language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteLanguage(Lang language) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Lang> getAllLanguages() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
