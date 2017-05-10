package github.jdrost1818.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class PathUtilTest {

    @Test
    public void normalize_path_with_file() throws Exception {
        assertThat(PathUtil.normalize("/src/main/something.java", "."), equalTo("src.main.something.java"));
    }

    @Test
    public void normalize_path_without_file() throws Exception {
        assertThat(PathUtil.normalize("/src/main/", "/"), equalTo("src/main"));
    }

}