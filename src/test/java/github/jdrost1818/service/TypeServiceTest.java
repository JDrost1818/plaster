package github.jdrost1818.service;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TypeServiceTest {

    TypeService classUnderTest = new TypeService();

    @Test
    public void validateType_one_class() throws Exception {
        assertTrue(this.classUnderTest.validateType("Map"));
    }

    @Test
    public void validateType_single_type_arg() throws Exception {
        assertTrue(this.classUnderTest.validateType("Map<String>"));
    }

    @Test
    public void validateType_multiple_type_args() throws Exception {
        assertTrue(this.classUnderTest.validateType("Map<String, Integer>"));
    }

    @Test
    public void validateType_too_many_less_thans() {
        assertFalse(this.classUnderTest.validateType("Map<<String, Integer>"));
    }

    @Test
    public void validateType_missing_less_than() {
        assertFalse(this.classUnderTest.validateType("MapString, Integer>"));
    }

    @Test
    public void validateType_too_many_greater_thans() {
        assertFalse(this.classUnderTest.validateType("Map<String, Integer>>"));
    }

    @Test
    public void validateType_missing_greater_than() {
        assertFalse(this.classUnderTest.validateType("Map<String, Integer"));
    }

    @Test
    public void validateType_wrongly_bracketed() {
        assertFalse(this.classUnderTest.validateType("Map<<String, Integer>>"));
    }

    @Test
    public void validateType_primitive_in_type() {
        assertFalse(this.classUnderTest.validateType("List<boolean>"));
    }


}