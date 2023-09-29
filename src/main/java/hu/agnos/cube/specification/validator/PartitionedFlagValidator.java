/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.AggregationSpecification;
import hu.agnos.cube.specification.entity.CubeSpecification;

/**
 *
 * @author parisek
 */
public class PartitionedFlagValidator {

    public static CubeSpecification validatePartitionedFlag(CubeSpecification cube) {
        boolean isChange = false;
        for (AggregationSpecification aggregation : cube.getAggregations()) {
            if (!aggregation.getAggregationFunction().toLowerCase().equals("sum")) {
                String hierarchyName = aggregation.getHierarchyName();
                if (!cube.getHierarchyByName(hierarchyName).isPartitioned()) {
                    isChange = true;
                    cube.getHierarchyByName(hierarchyName).setPartitioned(true);
                }
            }
        }
        return isChange ? cube : null;
    }

}
