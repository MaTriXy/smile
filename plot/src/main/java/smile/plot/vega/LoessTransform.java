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
package smile.plot.vega;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The loess transform (for locally-estimated scatterplot smoothing) uses
 * locally-estimated regression to produce a trend line. Loess performs
 * a sequence of local weighted regressions over a sliding window of
 * nearest-neighbor points. For standard parametric regression options,
 * see the regression transform.
 *
 * @author Haifeng Li
 */
public class LoessTransform {
    /** VegaLite's Loess definition object. */
    final ObjectNode spec;

    /**
     * Hides the constructor so that users cannot create the instances directly.
     */
    LoessTransform(ObjectNode spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        return spec.toString();
    }

    /**
     * Returns the specification in pretty print.
     * @return the specification in pretty print.
     */
    public String toPrettyString() {
        return spec.toPrettyString();
    }

    /**
     * Sets a bandwidth parameter in the range [0, 1] that determines
     * the amount of smoothing.
     *
     * @param width the bandwidth parameter in [0, 1].
     * @return this object.
     */
    public LoessTransform bandwidth(double width) {
        spec.put("bandwidth", width);
        return this;
    }

    /**
     * Sets the data fields to group by. If not specified, a single group
     * containing all data objects will be used.
     *
     * @param fields The data fields to group by. If not specified,
     *              a single group containing all data objects will be used.
     * @return this object.
     */
    public LoessTransform groupby(String... fields) {
        ArrayNode node = spec.putArray("groupby");
        for (var field : fields) {
            node.add(field);
        }
        return this;
    }

    /**
     * Sets the output field names for the smoothed points generated
     * by the loess transform.
     *
     * @param fields The output field names for the smoothed points
     *              generated by the loess transform.
     * @return this object.
     */
    public LoessTransform as(String... fields) {
        ArrayNode node = spec.putArray("as");
        for (var field : fields) {
            node.add(field);
        }
        return this;
    }
}
