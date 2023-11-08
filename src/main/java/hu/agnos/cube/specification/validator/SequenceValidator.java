package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.CubeSpecification;
import hu.agnos.cube.specification.entity.DimensionSpecification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author parisek
 */
public class SequenceValidator {

    public static CubeSpecification validatePartitionedFlag(CubeSpecification cube) {
        boolean isChange = false;
        List<DimensionSpecification> tmpHierarchies = new ArrayList<>();
        List<DimensionSpecification> sortedPartitionedHierarchyList = getSortedPartitionedHierarchyList(cube);
        List<DimensionSpecification> sortedNotPartitionedHierarchyList = getSortedNotPartitionedHierarchyList(cube);
        
        int i = 0;
        
        for (DimensionSpecification hier : sortedPartitionedHierarchyList) {
            hier.setOrder(i);
            tmpHierarchies.add(hier);
            i++;
        }
        
        for (DimensionSpecification hier : sortedNotPartitionedHierarchyList) {
            hier.setOrder(i);
            tmpHierarchies.add(hier);
            i++;
        }
        
        if (!isSorted(cube, tmpHierarchies)) {
            isChange = true;
            cube.getDimensions().clear();
            for (DimensionSpecification hier : tmpHierarchies) {
                cube.getDimensions().add(hier);
            }
        }
        
        return isChange ? cube : null;
    }

    private static boolean isSorted(CubeSpecification cube, List<DimensionSpecification> actualHierarchies) {
        List<DimensionSpecification> cubeDimensions = cube.getDimensions();
        if (cubeDimensions.size() != actualHierarchies.size()) {
            return false;
        }

        for (int i = 0; i < cubeDimensions.size(); i++) {
            if (!cubeDimensions.get(i).getUniqueName().equals(actualHierarchies.get(i).getUniqueName())) {
                return false;
            }
            if (cubeDimensions.get(i).getOrder() != actualHierarchies.get(i).getOrder()) {
                return false;
            }
        }
        return true;
    }

    private static List<DimensionSpecification> getSortedPartitionedHierarchyList(CubeSpecification cube) {
        List<DimensionSpecification> result = new ArrayList<>();
        for (String hierName : cube.getIsOfflineCalculatedDimensonList()) {
            result.add(cube.getDimensionByName(hierName));
        }
        result.sort((h1, h2) -> h2.getOrder() - h1.getOrder());
        return result;
    }

    private static List<DimensionSpecification> getSortedNotPartitionedHierarchyList(CubeSpecification cube) {
        List<DimensionSpecification> result = new ArrayList<>();
        List<String> partitionedHierarchyNameList = cube.getIsOfflineCalculatedDimensonList();

        for (DimensionSpecification hier : cube.getDimensions()) {
            if (!partitionedHierarchyNameList.contains(hier.getUniqueName())) {
                if (!result.contains(hier)) {
                    result.add(hier);
                }
            }
        }
        result.sort((h1, h2) -> h2.getOrder() - h1.getOrder());
        return result;
    }

}
