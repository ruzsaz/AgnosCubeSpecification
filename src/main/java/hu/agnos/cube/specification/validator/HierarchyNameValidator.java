package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.DimensionSpecification;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author parisek
 */
public class HierarchyNameValidator {

    public static String isValid(List<DimensionSpecification> hiererchies) {
        String result = "";
        Set<String> tmp = new HashSet<>();
        for (DimensionSpecification h : hiererchies) {
            String hierarchyName = h.getUniqueName();
            if (tmp.contains(hierarchyName)) {
                result = hierarchyName;
                break;
            }
            tmp.add(hierarchyName);
        }
        return result;
    }
}
