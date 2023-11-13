package hu.agnos.cube.specification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import hu.agnos.cube.specification.util.InfixToPostfixConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author parisek
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeasureSpecification {

    @Setter
    @JacksonXmlProperty(isAttribute = true)
    private String uniqueName;
    
    @JacksonXmlProperty(isAttribute = true)
    private String calculatedFormula;
    
    @JacksonXmlProperty(isAttribute = true)
    private boolean isVirtual;
    
    public void setCalculatedFormula(String calculatedFormula) {
        this.calculatedFormula = InfixToPostfixConverter.convert(calculatedFormula);
    }     
    
     public void changeCalculatedFormula(String calculatedFormula) {
        this.calculatedFormula = calculatedFormula;
    }     
    
    @JsonIgnore    
    public boolean isCalculatedMeasure(){
        return this.calculatedFormula != null;
    }

    @Override
    public String toString() {
        return "MeasureSpecification{" + "uniqueName=" + uniqueName + ", calculatedFormula=" + calculatedFormula + '}';
    }
    
    
   
}
