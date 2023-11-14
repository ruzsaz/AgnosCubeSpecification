package hu.agnos.cube.specification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author parisek
 */
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Cube")
@ToString
public class CubeSpecification {

    @Getter
    @JacksonXmlProperty(isAttribute = true)
    private String cubeUniqueName;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBDriver")
    private String sourceDBDriver;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBURL")
    private String sourceDBURL;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBUser")
    private String sourceDBUser;

    @Getter
    @JacksonXmlProperty(localName = "SourceDBPassword")
    private String sourceDBPassword;

    @JacksonXmlProperty(isAttribute = true)
    private boolean isValid;

    @Getter
    @JacksonXmlElementWrapper(localName = "Measures")
    @JacksonXmlProperty(localName = "Measure")
    private List<MeasureSpecification> measures;

    @Getter
    @JacksonXmlElementWrapper(localName = "Dimensions")
    @JacksonXmlProperty(localName = "Dimension")
    private final List<DimensionSpecification> dimensions;

    @Getter
    @JacksonXmlElementWrapper(localName = "Aggregations")
    @JacksonXmlProperty(localName = "Aggregation")
    private final List<AggregationSpecification> aggregations;

    @Getter
    @JacksonXmlElementWrapper(localName = "ExtraCalculations")
    @JacksonXmlProperty(localName = "PostCalculation")
    private final List<PostCalculationSpecification> extraCalculations;

    public CubeSpecification() {
        this.measures = new ArrayList<>();
        this.dimensions = new ArrayList<>();
        this.aggregations = new ArrayList<>();
        this.extraCalculations = new ArrayList<>();
    }

    public void addDimension(DimensionSpecification hierarchy) {
        this.dimensions.add(hierarchy);
    }

    public void addMeasure(MeasureSpecification measure) {
        this.measures.add(measure);
    }

    public DimensionSpecification getDimensionByName(String dimensionName) {
        DimensionSpecification result = null;
        for (DimensionSpecification hier : this.dimensions) {
            if (hier.getUniqueName().equals(dimensionName)) {
                result = hier;
                break;
            }
        }
        return result;
    }

    public void addAggregation(AggregationSpecification aggregation) {
        this.aggregations.add(aggregation);
    }

    public AggregationSpecification getAggregationByName(String dimensionName, String measureName) {
        AggregationSpecification result = null;
        for (AggregationSpecification a : this.aggregations) {
            if (a.getDimensionName().equals(dimensionName) && a.getMeasureName().equals(measureName)) {
                result = a;
                break;
            }
        }
        return result;
    }

    public List<AggregationSpecification> getAggregationsByDimensionName(String dimensionName) {
        List<AggregationSpecification> result = new ArrayList<>();
        for (AggregationSpecification a : this.aggregations) {
            if (a.getDimensionName().equals(dimensionName)) {
                result.add(a);
            }
        }
        return result;
    }

    public String getAggregationFunctionByName(String dimensionName, String measureName) {
        AggregationSpecification agg = getAggregationByName(dimensionName, measureName);
        if (agg != null) {
            return agg.getAggregationFunction();
        } else {
            return "sum";
        }
    }

    public void addPostCalculation(PostCalculationSpecification calculation) {
        this.extraCalculations.add(calculation);
    }

    @JsonIgnore
    public List<String> getIsOfflineCalculatedDimensonList() {
        List<String> result = new ArrayList<>();

        for (DimensionSpecification hier : this.dimensions) {
            if (hier.isOfflineCalculated()) {
                if (!result.contains(hier.getUniqueName())) {
                    result.add(hier.getUniqueName());
                }
            }
        }

        for (AggregationSpecification agg : this.aggregations) {
            if (!agg.getAggregationFunction().equalsIgnoreCase("sum")) {
                if (!result.contains(agg.getDimensionName())) {
                    result.add(agg.getDimensionName());
                }
            }
        }
        return result;
    }

    @JsonIgnore
    public List<String> getDistinctDimensionColumnList() {
        List<String> result = new ArrayList<>();
        for (DimensionSpecification dim : this.dimensions) {
            for (LevelSpecification level : dim.getLevels()) {
                if (!result.contains(level.getCodeColumnSourceName())) {
                    result.add(level.getCodeColumnSourceName());
                }
                if (!level.getNameColumnName().equals(level.getCodeColumnSourceName()) && !result.contains(level.getNameColumnName())) {
                    result.add(level.getNameColumnName());
                }
            }
        }
        return result;
    }

    @JsonIgnore
    public List<String> getDistinctMeasureColumnList() {
        List<String> result = new ArrayList<>();
        for (MeasureSpecification measure : getMeasures()) {
            if (!result.contains(measure.getUniqueName()) && !measure.isCalculatedMeasure() && !measure.isVirtual()) {
                result.add(measure.getUniqueName());
            }
        }
        return result;
    }

    public void modifyCalculatedFormula(String uniqueName, String calculatedFormula) {
        for (MeasureSpecification m : this.measures) {
            if (m.getUniqueName().equals(uniqueName)) {
                m.changeCalculatedFormula(calculatedFormula);
            }
        }
    }



}
