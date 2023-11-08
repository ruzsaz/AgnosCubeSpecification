package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.AggregationSpecification;
import hu.agnos.cube.specification.entity.CubeSpecification;

/**
 *
 * @author parisek
 */
public class OfflineCalculatedFlagValidator {

    public static CubeSpecification validatePartitionedFlag(CubeSpecification cube) {
        boolean isChange = false;
        for (AggregationSpecification aggregation : cube.getAggregations()) {
            if (!aggregation.getAggregationFunction().equalsIgnoreCase("sum")) {
                String hierarchyName = aggregation.getDimensionName();
                if (!cube.getDimensionByName(hierarchyName).isOfflineCalculated()) {
                    isChange = true;
                    cube.getDimensionByName(hierarchyName).setOfflineCalculated(true);
                }
            }
        }
        return isChange ? cube : null;
    }

}
