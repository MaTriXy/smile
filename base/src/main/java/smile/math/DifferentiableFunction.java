/*
 * Copyright (c) 2010-2025 Haifeng Li. All rights reserved.
 *
 * Smile is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Smile is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Smile. If not, see <https://www.gnu.org/licenses/>.
 */
package smile.math;

/**
 * A differentiable function is a function whose derivative exists at each point
 * in its domain.
 *
 * @author Haifeng Li
 */
public interface DifferentiableFunction extends Function {
    /**
     * Computes the gradient/derivative at x.
     *
     * @param x a real number.
     * @return the derivative.
     */
    double g(double x);

    /**
     * Compute the second-order derivative at x.
     *
     * @param x a real number.
     * @return the second-order derivative.
     */
    default double g2(double x) {
        throw new UnsupportedOperationException();
    }
}
