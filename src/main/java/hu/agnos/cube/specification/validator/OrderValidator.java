package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.CubeSpecification;
import hu.agnos.cube.specification.entity.DimensionSpecification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author parisek
 */
public class OrderValidator {

    public static CubeSpecification validateOrder(CubeSpecification cube) {
        boolean isChange = false;
        List<DimensionSpecification> tmpDimensions = new ArrayList<>();
        List<DimensionSpecification> sortedOfflineCalculatedDimensionList = getSortedOfflineCalculatedDimensionList(cube);
        List<DimensionSpecification> sortedNotOfflineCalculatedDimensionHierarchyList = getSortedNotOfflineCalculatedDimensionList(cube);

        int i = 0;

        for (DimensionSpecification dim : sortedOfflineCalculatedDimensionList) {
            dim.setOrder(i);
            tmpDimensions.add(dim);
            i++;
        }

        for (DimensionSpecification dim : sortedNotOfflineCalculatedDimensionHierarchyList) {
            dim.setOrder(i);
            tmpDimensions.add(dim);
            i++;
        }

        if (!isSorted(cube, tmpDimensions)) {
            isChange = true;
            cube.getDimensions().clear();
            for (DimensionSpecification hier : tmpDimensions) {
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

    private static List<DimensionSpecification> getSortedOfflineCalculatedDimensionList(CubeSpecification cube) {
        List<DimensionSpecification> result = new ArrayList<>();
        for (String hierName : cube.getOfflineCalculatedDimensonNameList()) {
            result.add(cube.getDimensionByName(hierName));
        }
        result.sort((h1, h2) -> h1.getOrder() - h2.getOrder());
        return result;
    }

    private static List<DimensionSpecification> getSortedNotOfflineCalculatedDimensionList(CubeSpecification cube) {
        List<DimensionSpecification> result = new ArrayList<>();
        List<String> offlineCalculatedDimensionNameList = cube.getOfflineCalculatedDimensonNameList();

        for (DimensionSpecification dim : cube.getDimensions()) {
            if (!offlineCalculatedDimensionNameList.contains(dim.getUniqueName())) {
                if (!result.contains(dim)) {
                    result.add(dim);
                }
            }
        }
        result.sort((h1, h2) -> h1.getOrder() - h2.getOrder());
        return result;
    }

}
