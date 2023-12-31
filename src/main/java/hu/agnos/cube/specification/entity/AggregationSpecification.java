/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class AggregationSpecification {

    @JacksonXmlProperty(isAttribute = true)
    private String measureName;

    @JacksonXmlProperty(isAttribute = true)
    private String dimensionName;

    @JacksonXmlProperty(isAttribute = true)
    private String aggregationFunction;

}
