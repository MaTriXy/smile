/*
 * Copyright (c) 2010-2025 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile.  If not, see <https://www.gnu.org/licenses/>.
 */
package smile.datasets;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import org.apache.commons.csv.CSVFormat;
import smile.data.CategoricalEncoder;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.io.Read;
import smile.util.Paths;

/**
 * Breast cancer dataset. Features are computed from a digitized image of
 * a fine needle aspirate (FNA) of a breast mass.  They describe
 * characteristics of the cell nuclei present in the image. A few of the
 * images can be found at <a href="http://www.cs.wisc.edu/~street/images/">
 * http://www.cs.wisc.edu/~street/images/</a>.
 *
 * @param data data frame.
 * @param formula modeling formula.
 * @author Haifeng Li
 */
public record BreastCancer(DataFrame data, Formula formula) {
    /**
     * Constructor.
     * @throws IOException when fails to read the file.
     * @throws ParseException when fails to parse the file.
     */
    public BreastCancer() throws IOException, ParseException {
        this(load(Paths.getTestData("classification/breastcancer.csv")), Formula.lhs("diagnosis"));
    }

    /**
     * Constructor.
     * @param first the path string or initial part of the path string.
     * @param more additional strings to be joined to form the path string.
     * @throws IOException when fails to read the file.
     * @throws ParseException when fails to parse the file.
     */
    public BreastCancer(String first, String... more) throws IOException, ParseException {
        this(load(first, more), Formula.lhs("diagnosis"));
    }

    private static DataFrame load(String first, String... more) throws IOException, ParseException {
        return load(Path.of(first, more));
    }

    private static DataFrame load(Path path) throws IOException, ParseException {
        CSVFormat format = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build();
        var data = Read.csv(path, format);
        data = data.drop("id").factorize("diagnosis");
        return data;
    }

    /**
     * Returns the sample features.
     * @return the sample features.
     */
    public double[][] x() {
        return formula.x(data).toArray(false, CategoricalEncoder.DUMMY);
    }

    /**
     * Returns the class labels.
     * @return the class labels.
     */
    public int[] y() {
        return formula.y(data).toIntArray();
    }
}
