/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hu.agnos.cube.specification.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author parisek
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostCalculationSpecification {
    @JacksonXmlProperty(isAttribute = true)
    private String measureName;

    @JacksonXmlProperty(isAttribute = true)
    private String dimensionName;

     @JacksonXmlProperty(isAttribute = true)
    private String levelName;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

}
