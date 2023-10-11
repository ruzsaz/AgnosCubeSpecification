package hu.agnos.cube.specification.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import hu.agnos.cube.specification.entity.CubeSpecification;
import hu.agnos.cube.specification.entity.MeasureSpecification;
import hu.agnos.cube.specification.exception.InvalidPostfixExpressionException;
import hu.agnos.cube.specification.exception.NameOfHierarchySpecificationNotUniqueException;
import hu.agnos.cube.specification.exception.NameOfMeasureSpecificationNotUniqueException;
import hu.agnos.cube.specification.validator.HierarchyNameValidator;
import hu.agnos.cube.specification.validator.LevelOrderValidator;
import hu.agnos.cube.specification.validator.MeasureNameValidator;
import hu.agnos.cube.specification.validator.OfflineCalculatedFlagValidator;
import hu.agnos.cube.specification.validator.SequenceValidator;
import hu.agnos.cube.specification.util.PostfixToInfixConverter;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.slf4j.Logger;
import org.xml.sax.SAXException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author parisek
 */
public class CubeSpecificationRepo {

    private final Logger logger;

    public CubeSpecificationRepo() {
        super();
        logger = LoggerFactory.getLogger(CubeSpecificationRepo.class);
    }

    public CubeSpecification findCubeSpecificationByPath(String path) throws NameOfHierarchySpecificationNotUniqueException, NameOfMeasureSpecificationNotUniqueException, IOException, InvalidPostfixExpressionException {
        CubeSpecification xmlCube = null;

        if (validateXML(path)) {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

            CubeSpecification tmpCube = xmlMapper.readValue(new File(path), CubeSpecification.class);
            xmlCube = validateCubeSpecification(path, tmpCube);
        }
        return xmlCube;
    }

    public void setCubeSpecification(String path, CubeSpecification cubeSpec) throws IOException, InvalidPostfixExpressionException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

        CubeSpecification clone = deepCopyWithInfixFormula(cubeSpec);
        xmlMapper.writeValue(new File(path), clone);
    }

    private boolean validateXML(String path) {
        try {
            Source xmlFile = new StreamSource(new File(path));
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory
                    .newSchema(getClass().getResource("/cube.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            return true;
        } catch (SAXException | IOException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    private CubeSpecification validateCubeSpecification(String path, CubeSpecification cube) throws NameOfHierarchySpecificationNotUniqueException, NameOfMeasureSpecificationNotUniqueException, IOException, InvalidPostfixExpressionException {
        boolean isChanged = false;
        String validationResult = HierarchyNameValidator.isValid(cube.getHierarchies());
        if (!validationResult.isEmpty()) {
            throw new NameOfHierarchySpecificationNotUniqueException(validationResult);
        }

        validationResult = MeasureNameValidator.isValid(cube.getMeasures());
        if (!validationResult.isEmpty()) {
            throw new NameOfMeasureSpecificationNotUniqueException(validationResult);
        }

        CubeSpecification validatedCube = LevelOrderValidator.validateLevelOrder(cube);
        if (validatedCube != null) {
            isChanged = true;
            cube = validatedCube;
        }

        validatedCube = OfflineCalculatedFlagValidator.validatePartitionedFlag(cube);
        if (validatedCube != null) {
            isChanged = true;
            cube = validatedCube;
        }

        validatedCube = SequenceValidator.validatePartitionedFlag(cube);
        if (validatedCube != null) {
            isChanged = true;
            cube = validatedCube;
        }

        if (isChanged) {
            setCubeSpecification(path, cube);
        }
        return cube;
    }

    private CubeSpecification deepCopyWithInfixFormula(CubeSpecification origin) throws JsonProcessingException, InvalidPostfixExpressionException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

        //deep-copy
        CubeSpecification clone = xmlMapper.readValue(xmlMapper.writeValueAsString(origin), CubeSpecification.class);

        for (MeasureSpecification originMeasure : origin.getMeasures()) {
            String formula = originMeasure.getCalculatedFormula();
            formula = originMeasure.isCalculatedMeasure()
                    ? PostfixToInfixConverter.convert(formula)
                    : formula;
            clone.modifyCalculatedFormula(originMeasure.getUniqueName(), formula);
        }

        return clone;
    }

}
