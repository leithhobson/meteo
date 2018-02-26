//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.17 at 03:24:33 PM CET 
//


package no.api.meteo.jaxb.extremeswwc.v1_2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tidalwater complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tidalwater">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="unit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tidal" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="weathercorrection" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tidalwater")
public class Tidalwater {

    @XmlAttribute(name = "unit")
    protected String unit;
    @XmlAttribute(name = "tidal", required = true)
    protected BigInteger tidal;
    @XmlAttribute(name = "weathercorrection")
    protected BigInteger weathercorrection;

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnit(String value) {
        this.unit = value;
    }

    /**
     * Gets the value of the tidal property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTidal() {
        return tidal;
    }

    /**
     * Sets the value of the tidal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTidal(BigInteger value) {
        this.tidal = value;
    }

    /**
     * Gets the value of the weathercorrection property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWeathercorrection() {
        return weathercorrection;
    }

    /**
     * Sets the value of the weathercorrection property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWeathercorrection(BigInteger value) {
        this.weathercorrection = value;
    }

}
