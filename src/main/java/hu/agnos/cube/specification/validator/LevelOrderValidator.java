/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.agnos.cube.specification.validator;

import hu.agnos.cube.specification.entity.CubeSpecification;
import hu.agnos.cube.specification.entity.HierarchySpecification;
import hu.agnos.cube.specification.entity.LevelSpecification;

/**
 *
 * @author parisek
 */
public class LevelOrderValidator {

    public static CubeSpecification validateLevelOrder(CubeSpecification cube) {
        boolean isChange = false;
        for (HierarchySpecification h : cube.getHierarchies()) {
            int i = 1;
            for (LevelSpecification l : h.getLevels()) {
                int dept = l.getDepth();
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
