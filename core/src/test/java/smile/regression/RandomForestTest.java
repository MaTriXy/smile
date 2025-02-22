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
package smile.regression;

import java.util.Arrays;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.data.type.StructField;
import smile.datasets.*;
import smile.io.Read;
import smile.io.Write;
import smile.math.MathEx;
import smile.validation.*;
import smile.validation.metric.RMSE;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Haifeng Li
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class RandomForestTest {
    long[] seeds = {
            342317953, 521642753, 72070657, 577451521, 266953217, 179976193,
            374603777, 527788033, 303395329, 185759582, 261518209, 461300737,
            483646580, 532528741, 159827201, 284796929, 655932697, 26390017,
            454330473, 867526205, 824623361, 719082324, 334008833, 699933293,
            823964929, 155216641, 150210071, 249486337, 713508520, 558398977,
            886227770, 74062428, 670528514, 701250241, 363339915, 319216345,
            757017601, 459643789, 170213767, 434634241, 414707201, 153100613,
            753882113, 546490145, 412517763, 888761089, 628632833, 565587585,
            175885057, 594903553, 78450978, 212995578, 710952449, 835852289,
            415422977, 832538705, 624345857, 839826433, 260963602, 386066438,
            530942946, 261866663, 269735895, 798436064, 379576194, 251582977,
            349161809, 179653121, 218870401, 415292417, 86861523, 570214657,
            701581299, 805955890, 358025785, 231452966, 584239408, 297276298,
            371814913, 159451160, 284126095, 896291329, 496278529, 556314113,
            31607297, 726761729, 217004033, 390410146, 70173193, 661580775,
            633589889, 389049037, 112099159, 54041089, 80388281, 492196097,
            912179201, 699398161, 482080769, 363844609, 286008078, 398098433,
            339855361, 189583553, 697670495, 709568513, 98494337, 99107427,
            433350529, 266601473, 888120086, 243906049, 414781441, 154685953,
            601194298, 292273153, 212413697, 568007473, 666386113, 712261633,
            802026964, 783034790, 188095005, 742646355, 550352897, 209421313,
            175672961, 242531185, 157584001, 201363231, 760741889, 852924929,
            60158977, 774572033, 311159809, 407214966, 804474160, 304456514,
            54251009, 504009638, 902115329, 870383757, 487243777, 635554282,
            564918017, 636074753, 870308031, 817515521, 494471884, 562424321,
            81710593, 476321537, 595107841, 418699893, 315560449, 773617153,
            163266399, 274201241, 290857537, 879955457, 801949697, 669025793,
            753107969, 424060977, 661877468, 433391617, 222716929, 334154852,
            878528257, 253742849, 480885528, 99773953, 913761493, 700407809,
            483418083, 487870398, 58433153, 608046337, 475342337, 506376199,
            378726401, 306604033, 724646374, 895195218, 523634541, 766543466,
            190068097, 718704641, 254519245, 393943681, 796689751, 379497473,
            50014340, 489234689, 129556481, 178766593, 142540536, 213594113,
            870440184, 277912577};

    Abalone abalone;
    Bank32nh bank32nh;
    BostonHousing bostonHousing;
    CalHousing calHousing;

    public RandomForestTest() throws Exception {
        abalone = new Abalone();
        bank32nh = new Bank32nh();
        bostonHousing = new BostonHousing();
        calHousing = new CalHousing();
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }
    
    @BeforeEach
    public void setUp() {
        System.setProperty("smile.regression_tree.bins", "100");
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testLongley() throws Exception {
        System.out.println("longley");
        MathEx.setSeed(19650218); // to get repeatable results for cross validation.
        var longley = new Longley();
        var options = new RandomForest.Options(100, 3, 20, 10, 3, 1.0);
        RandomForest model = RandomForest.fit(longley.formula(), longley.data(), options, Arrays.stream(seeds));

        double[] importance = model.importance();
        System.out.println("----- importance -----");
        for (int i = 0; i < importance.length; i++) {
            System.out.format("%-15s %12.4f%n", model.schema().names()[i], importance[i]);
        }

        assertEquals(39293.8193, importance[0], 1E-4);
        assertEquals( 6578.6575, importance[1], 1E-4);
        assertEquals(10222.5344, importance[2], 1E-4);
        assertEquals(36198.4239, importance[3], 1E-4);
        assertEquals(30099.2986, importance[4], 1E-4);
        assertEquals(31644.9317, importance[5], 1E-4);

        System.out.println("----- Progressive RMSE -----");
        double[][] test = model.test(longley.data());
        for (int i = 0; i < test.length; i++) {
            System.out.format("RMSE with %3d trees: %.4f%n", i+1, RMSE.of(longley.y(), test[i]));
        }

        RegressionMetrics metrics = LOOCV.regression(longley.formula(), longley.data(),
                (f, x) -> RandomForest.fit(f, x, options, Arrays.stream(seeds)));

        System.out.println(metrics);
        assertEquals(2.7034, metrics.rmse(), 1E-4);

        java.nio.file.Path temp = Write.object(model);
        Read.object(temp);
    }

    public void test(String name, Formula formula, DataFrame data, double expected) {
        System.out.println(name);

        MathEx.setSeed(19650218); // to get repeatable results for cross validation.
        var options = new RandomForest.Options(100, 3, 20, 100, 5, 1.0);
        RegressionValidations<RandomForest> result = CrossValidation.regression(3, formula, data,
                (f, x) -> RandomForest.fit(f, x, options, Arrays.stream(seeds)));

        System.out.println(result);
        assertEquals(expected, result.avg().rmse(), 1E-4);

        RandomForest model = RandomForest.fit(formula, data);
        double[] importance = model.importance();
        System.out.println("----- importance -----");
        for (int i = 0; i < importance.length; i++) {
            System.out.format("%-15s %12.4f%n", model.schema().names()[i], importance[i]);
        }
    }

    @Test
    public void testCPU() throws Exception {
        var cpu = new CPU();
        test("CPU", cpu.formula(), cpu.data(), 69.0170);
    }

    @Test
    public void test2DPlanes() throws Exception {
        System.setProperty("smile.regression_tree.bins", "1");
        var planes = new Planes2D();
        test("2dplanes - exact", planes.formula(), planes.data(), 1.3581);
    }

    @Test
    public void test2DPlanesHist() throws Exception {
        var planes = new Planes2D();
        test("2dplanes - hist", planes.formula(), planes.data(), 1.2999);
    }

    @Test
    public void testAbalone() {
        test("abalone", abalone.formula(), abalone.train(), 2.1931);
    }

    @Test
    public void testAilerons() throws Exception {
        var ailerons = new Ailerons();
        test("ailerons", ailerons.formula(), ailerons.data(), 0.0002);
    }

    @Test
    public void testBank32nh() {
        System.setProperty("smile.regression_tree.bins", "1");
        test("bank32nh - exact", bank32nh.formula(), bank32nh.data(), 0.0978);
    }

    @Test
    public void testBank32nhHist() {
        test("bank32nh - hist", bank32nh.formula(), bank32nh.data(), 0.0996);
    }

    @Test
    public void testAutoMPG() throws Exception {
        var autoMPG = new AutoMPG();
        test("autoMPG", autoMPG.formula(), autoMPG.data(), 3.5532);
    }

    @Test
    public void testCalHousing() {
        System.setProperty("smile.regression_tree.bins", "300");
        test("cal_housing", calHousing.formula(), calHousing.data(), 59481.6595);
    }

    @Test
    public void testPuma8nh() throws Exception {
        var puma = new Puma8NH();
        test("puma8nh", puma.formula(), puma.data(), 3.3895);
    }

    @Test
    public void testKin8nm() throws Exception {
        var kin8nm = new Kin8nm();
        test("kin8nm", kin8nm.formula(), kin8nm.data(), 0.1774);
    }

    @Test
    public void testTrim() {
        System.out.println("trim");
        var options = new RandomForest.Options(50, 3, 20, 100, 5, 1.0);
        RandomForest model = RandomForest.fit(abalone.formula(), abalone.train(), options, Arrays.stream(seeds));
        System.out.println(model.metrics());
        assertEquals(50, model.size());

        double rmse = RMSE.of(abalone.testy(), model.predict(abalone.test()));
        System.out.format("RMSE = %.4f%n", rmse);
        assertEquals(2.0734, rmse, 1E-4);

        RandomForest trimmed = model.trim(40);
        assertEquals(50, model.size());
        assertEquals(40, trimmed.size());

        double rmse1 = Arrays.stream(model.models())
                .mapToDouble(m -> m.metrics().rmse())
                .max().orElseThrow();
        double rmse2 = Arrays.stream(trimmed.models())
                .mapToDouble(m -> m.metrics().rmse())
                .max().orElseThrow();
        assertTrue(rmse1 > rmse2);

        rmse = RMSE.of(abalone.testy(), trimmed.predict(abalone.test()));
        assertEquals(2.0748, rmse, 1E-4);
    }

    @Test
    public void testMerge() {
        System.out.println("merge");
        var options = new RandomForest.Options(50, 3, 20, 100, 5, 1.0);
        RandomForest forest1 = RandomForest.fit(abalone.formula(), abalone.train(), options, Arrays.stream(seeds));
        RandomForest forest2 = RandomForest.fit(abalone.formula(), abalone.train(), options, Arrays.stream(seeds).skip(50));
        RandomForest forest = forest1.merge(forest2);
        double rmse1 = RMSE.of(abalone.testy(), forest1.predict(abalone.test()));
        double rmse2 = RMSE.of(abalone.testy(), forest2.predict(abalone.test()));
        double rmse  = RMSE.of(abalone.testy(), forest.predict(abalone.test()));
        System.out.format("Forest 1 RMSE = %.4f%n", rmse1);
        System.out.format("Forest 2 RMSE = %.4f%n", rmse2);
        System.out.format("Merged   RMSE = %.4f%n", rmse);
        assertEquals(2.0734, rmse1, 1E-4);
        assertEquals(2.0759, rmse2, 1E-4);
        assertEquals(2.0715, rmse,  1E-4);
    }

    @Test
    public void testShap() {
        MathEx.setSeed(19650218); // to get repeatable results.
        System.setProperty("smile.regression_tree.bins", "1");
        var options = new RandomForest.Options(100, 3, 20, 100, 5, 1.0);
        RandomForest model = RandomForest.fit(bostonHousing.formula(), bostonHousing.data(), options, Arrays.stream(seeds));
        double[] importance = model.importance();
        double[] shap = model.shap(bostonHousing.data());

        System.out.println("----- importance -----");
        String[] fields = model.schema().fields().stream().map(StructField::name).toArray(String[]::new);
        smile.sort.QuickSort.sort(importance, fields);
        for (int i = 0; i < importance.length; i++) {
            System.out.format("%-15s %12.4f%n", fields[i], importance[i]);
        }

        System.out.println("----- SHAP -----");
        fields = model.schema().fields().stream().map(StructField::name).toArray(String[]::new);
        smile.sort.QuickSort.sort(shap, fields);
        for (int i = 0; i < shap.length; i++) {
            System.out.format("%-15s %12.4f%n", fields[i], shap[i]);
        }

        String[] expected = {"CHAS", "RAD", "B", "ZN", "AGE", "DIS", "TAX", "CRIM", "INDUS", "NOX", "PTRATIO", "RM", "LSTAT"};
        assertArrayEquals(expected, fields);
    }
}
