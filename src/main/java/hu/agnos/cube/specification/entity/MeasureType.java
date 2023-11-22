/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package hu.agnos.cube.specification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author parisek
 */
@Getter
@AllArgsConstructor
public enum MeasureType {
    CLASSICAL("Classical"),
    CALCULATED("Calculated") ,
    COUNT_DISTINCT("CountDistinct");
    
    private String type;
    
}
