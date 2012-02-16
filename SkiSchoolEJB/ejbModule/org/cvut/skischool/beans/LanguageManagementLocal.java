package org.cvut.skischool.beans;

import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Lang;

/**
 *
 * @author matej
 */
@Local
public interface LanguageManagementLocal {

    void createLanguage(Lang language);

    void updateLanguage(Lang language);

    void deleteLanguage(Lang language);

    List<Lang> getAllLanguages();
}
