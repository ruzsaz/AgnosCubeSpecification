package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.CubeSpecification;
import hu.agnos.cube.specification.entity.DimensionSpecification;
import hu.agnos.cube.specification.entity.LevelSpecification;

/**
 *
 * @author parisek
 */
public class LevelOrderValidator {

    public static CubeSpecification validateLevelOrder(CubeSpecification cube) {
        boolean isChange = false;
        for (DimensionSpecification h : cube.getDimensions()) {
            int i = 1;
            for (LevelSpecification l : h.getLevels()) {
                if (l.getDepth() != i) {
                    isChange = true;
                    l.setDepth(i);
                }
                i++;
            }
        }
        return isChange ? cube : null;
    }
}
