//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.02.17 at 03:24:40 PM CET 
//


package no.api.meteo.jaxb.sunrise.v1_1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * 
 *         Solar related information, including solar noon.  All
 *         timestamps are given in UTC. Daylenght is given in hours.
 *         The rise and set values are computed when the angle to the Sun
 *         is -0.21 degrees.  Civil twilight is -6 degrees, nautical -12
 *         and astronomical -18.
 *       
 * 
 * <p>Java class for sunType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sunType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="noon" type="{}transitType" minOccurs="0"/>
 *         &lt;element name="twilight" type="{}twilightType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="error" type="{}errorType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="rise" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="never_rise" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="set" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="never_set" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="daylength" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sunType", propOrder = {
    "noon",
    "twilight",
    "error"
})
public class SunType {

    protected TransitType noon;
    protected List<TwilightType> twilight;
    protected ErrorType error;
    @XmlAttribute(name = "rise")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar rise;
    @XmlAttribute(name = "never_rise")
    protected Boolean neverRise;
    @XmlAttribute(name = "set")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar set;
    @XmlAttribute(name = "never_set")
    protected Boolean neverSet;
    @XmlAttribute(name = "daylength")
    protected BigDecimal daylength;

    /**
     * Gets the value of the noon property.
     * 
     * @return
     *     possible object is
     *     {@link TransitType }
     *     
     */
    public TransitType getNoon() {
        return noon;
    }

    /**
     * Sets the value of the noon property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransitType }
     *     
     */
    public void setNoon(TransitType value) {
        this.noon = value;
    }

    /**
     * Gets the value of the twilight property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the twilight property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTwilight().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TwilightType }
     * 
     * 
     */
    public List<TwilightType> getTwilight() {
        if (twilight == null) {
            twilight = new ArrayList<TwilightType>();
        }
        return this.twilight;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorType }
     *     
     */
    public ErrorType getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorType }
     *     
     */
    public void setError(ErrorType value) {
        this.error = value;
    }

    /**
     * Gets the value of the rise property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRise() {
        return rise;
    }

    /**
     * Sets the value of the rise property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRise(XMLGregorianCalendar value) {
        this.rise = value;
    }

    /**
     * Gets the value of the neverRise property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeverRise() {
        return neverRise;
    }

    /**
     * Sets the value of the neverRise property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeverRise(Boolean value) {
        this.neverRise = value;
    }

    /**
     * Gets the value of the set property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSet() {
        return set;
    }

    /**
     * Sets the value of the set property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSet(XMLGregorianCalendar value) {
        this.set = value;
    }

    /**
     * Gets the value of the neverSet property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNeverSet() {
        return neverSet;
    }

    /**
     * Sets the value of the neverSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNeverSet(Boolean value) {
        this.neverSet = value;
    }

    /**
     * Gets the value of the daylength property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDaylength() {
        return daylength;
    }

    /**
     * Sets the value of the daylength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDaylength(BigDecimal value) {
        this.daylength = value;
    }

}