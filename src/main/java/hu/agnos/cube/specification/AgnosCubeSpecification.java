/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package hu.agnos.cube.specification;

import hu.agnos.cube.specification.entity.CubeSpecification;
import hu.agnos.cube.specification.exception.InvalidPostfixExpressionException;
import hu.agnos.cube.specification.exception.NameOfHierarchySpecificationNotUniqueException;
import hu.agnos.cube.specification.exception.NameOfMeasureSpecificationNotUniqueException;
import hu.agnos.cube.specification.repo.CubeSpecificationRepo;
import java.io.IOException;

/**
 *
 * @author parisek
 */
public class AgnosCubeSpecification {

    public static void main(String[] args) throws NameOfHierarchySpecificationNotUniqueException, NameOfMeasureSpecificationNotUniqueException, InvalidPostfixExpressionException, IOException {
        CubeSpecificationRepo handler = new CubeSpecificationRepo();
        CubeSpecification cube = handler.findCubeSpecificationByPath("/home/parisek/AGNOS_HOME/AgnosCubeBuilder/Meta/HB_AMI_MSSQL.cube.xml");
        System.out.println(cube.toString());
    }
}
