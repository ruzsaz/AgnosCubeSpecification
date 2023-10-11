package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.CubeSpecification;
import hu.agnos.cube.specification.entity.HierarchySpecification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author parisek
 */
public class SequenceValidator {

    public static CubeSpecification validatePartitionedFlag(CubeSpecification cube) {
        boolean isChange = false;
        List<HierarchySpecification> tmpHierarchies = new ArrayList<>();
        List<HierarchySpecification> sortedPartitionedHierarchyList = getSortedPartitionedHierarchyList(cube);
        List<HierarchySpecification> sortedNotPartitionedHierarchyList = getSortedNotPartitionedHierarchyList(cube);
        
        int i = 0;
        
        for (HierarchySpecification hier : sortedPartitionedHierarchyList) {
            hier.setOrder(i);
            tmpHierarchies.add(hier);
            i++;
        }
        
        for (HierarchySpecification hier : sortedNotPartitionedHierarchyList) {
            hier.setOrder(i);
            tmpHierarchies.add(hier);
            i++;
        }
        
        if (!isSorted(cube, tmpHierarchies)) {
            isChange = true;
            cube.getHierarchies().clear();
            for (HierarchySpecification hier : tmpHierarchies) {
                cube.getHierarchies().add(hier);
            }
        }
        
        return isChange ? cube : null;
    }

    private static boolean isSorted(CubeSpecification cube, List<HierarchySpecification> actualHierarchies) {
        List<HierarchySpecification> cubeHierarchies = cube.getHierarchies();
        if (cubeHierarchies.size() != actualHierarchies.size()) {
            return false;
        }

        for (int i = 0; i < cubeHierarchies.size(); i++) {
            if (!cubeHierarchies.get(i).getUniqueName().equals(actualHierarchies.get(i).getUniqueName())) {
                return false;
            }
            if (cubeHierarchies.get(i).getOrder() != actualHierarchies.get(i).getOrder()) {
                return false;
            }
        }
        return true;
    }

    private static List<HierarchySpecification> getSortedPartitionedHierarchyList(CubeSpecification cube) {
        List<HierarchySpecification> result = new ArrayList<>();
        for (String hierName : cube.getIsOfflineCalculatedHierarchyList()) {
            result.add(cube.getHierarchyByName(hierName));
        }
        result.sort((h1, h2) -> h2.getOrder() - h1.getOrder());
        return result;
    }

    private static List<HierarchySpecification> getSortedNotPartitionedHierarchyList(CubeSpecification cube) {
        List<HierarchySpecification> result = new ArrayList<>();
        List<String> partitionedHierarchyNameList = cube.getIsOfflineCalculatedHierarchyList();

        for (HierarchySpecification hier : cube.getHierarchies()) {
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
