/*
 * Copyright (c) 2010-2021 Haifeng Li. All rights reserved.
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

package smile.math.distance;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Haifeng Li
 */
public class MinkowskiDistanceTest {

    public MinkowskiDistanceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of distance method, of class MinkowskiDistance.
     */
    @Test
    public void testDistance() {
        System.out.println("distance");
        double[] x = {-2.1968219, -0.9559913, -0.0431738,  1.0567679,  0.3853515};
        double[] y = {-1.7781325, -0.6659839,  0.9526148, -0.9460919, -0.3925300};
        MinkowskiDistance m3 = new MinkowskiDistance(3);
        MinkowskiDistance m4 = new MinkowskiDistance(4);
        assertEquals(2.124599, m3.d(x, y), 1E-6);
        assertEquals(2.044696, m4.d(x, y), 1E-6);
    }
}