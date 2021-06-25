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

package smile.projection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import smile.data.USArrests;
import smile.math.MathEx;

/**
 *
 * @author Haifeng Li
 */
public class PCATest {

    public PCATest() {
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

    @Test
    public void testPCA() {
        System.out.println("learn");
        double[] prop = {0.9655342, 0.02781734, 0.005799535, 0.0008489079};
        double[] cumprop = {0.9655342, 0.99335156, 0.999151092, 1.0000000000};
        double[][] loadings = {
            {-0.0417043206282872, -0.0448216562696701, -0.0798906594208108, -0.994921731246978},
            {-0.995221281426497, -0.058760027857223, 0.0675697350838043, 0.0389382976351601},
            {-0.0463357461197108, 0.97685747990989, 0.200546287353866, -0.0581691430589319},
            {-0.075155500585547, 0.200718066450337, -0.974080592182491, 0.0723250196376097}
        };
        double[][] points = {
            {-64.8021636817436, -11.4480073977837, 2.49493284038366, -2.40790093375486},
            {-92.8274501566946, -17.9829427006718, -20.1265748735977, 4.09404703053042},
            {-124.068216289648, 8.83040304270359, 1.68744835668708, 4.35368520359609},
            {-18.3400354054072, -16.7039113810096, -0.210189364140469, 0.520993603601999},
            {-107.422953125475, 22.5200697704229, -6.74587299235952, 2.81182590291373},
            {-34.9759859594071, 13.7195840329554, -12.2793628034841, 1.72146370000854},
            {60.8872819318546, 12.9325301564357, 8.42065718885695, 0.699902287100493},
            {-66.7310254465241, 1.35379779717711, 11.2809573450628, 3.72798119075026},
            {-165.244370319248, 6.27469006959416, 2.99793314838690, -1.24768071379144},
            {-40.5351765876529, -7.29023959219438, -3.60952945561059, -7.34367284165603},
            {123.536105769847, 24.291207910916, -3.72444284008562, -3.47284940369505},
            {51.7970022509676, -9.46919099467921, 1.52006355638492, 3.347828326557},
            {-78.9920972971611, 12.8970604614357, 5.88326476858706, -0.36764073849423},
            {57.5509607347644, 2.84626471235194, -3.73816048848585, -1.64943016073772},
            {115.586789703854, -3.34213050228539, 0.654029354180591, 0.869495984279686},
            {55.7896941123023, 3.15723392471998, -0.384364163111931, -0.65279168994279},
            {62.383180611208, -10.6732714680385, -2.23708902971565, -3.87621641192646},
            {-78.2776313152135, -4.29491749799139, 3.82786965239577, -4.48355899807498},
            {89.2610442661687, -11.4878272008390, 4.69240561590091, 2.11619948317566},
            {-129.330135502788, -5.00703147597233, 2.34717281642702, 1.92832424634248},
            {21.2662826315275, 19.4501790329282, 7.5071483512444, 1.03481894814417},
            {-85.4515266722104, 5.90455669704365, -6.46434210133595, -0.499047890295277},
            {98.9548155235302, 5.20960058227443, -0.00657375986111987, 0.731895663983765},
            {-86.8563576862984, -27.4284196328667, 5.00343623998489, -3.87975768645157},
            {-7.98628866990333, 5.27564139833892, -5.50057972194064, -0.67940550460075},
            {62.4836353013543, -9.51050205328579, -1.83835536172303, -0.245942647407812},
            {69.096544351272, -0.211195916792749, -0.468020859327737, 0.656566426699305},
            {-83.6135784392142, 15.1021838986663, -15.8886948158415, -0.334196243688033},
            {114.777354501664, -4.73455836403614, 2.28238693378110, 0.935910562750778},
            {10.815725119435, 23.1373388713124, 6.31015739277925, -1.61242729238686},
            {-114.86816260433, -0.336453098274837, -2.26126996009524, 1.38124776395531},
            {-84.2942305183222, 15.9239655420362, 4.72125960088988, -0.89201935012913},
            {-164.325514489152, -31.0966152578345, 11.6961635002625, 2.11119273326001},
            {127.495596550323, -16.1350393817083, 1.31182982457759, 2.30096391525437},
            {50.0868216712847, 12.2793243774034, -1.65733077217564, -2.02911567315058},
            {19.6937228951609, 3.3701310208186, 0.453143294595288, 0.180345739332141},
            {11.1502395794911, 3.8660681516975, -8.12998049836994, 2.91401088922199},
            {64.6891419240492, 8.91154655201737, 3.20646858319651, -1.87493530726351},
            {-3.06397257106163, 18.3739704209844, 17.4700196999280, 2.30825967705141},
            {-107.281068657401, -23.5361158958813, 2.03279501430637, -1.25174625682114},
            {86.1067200506117, -16.5978586472767, -1.31437998489870, 1.25228738950292},
            {-17.5062642727291, -6.50657560196015, -6.10012753172529, -3.9228557513671},
            {-31.2911217386536, 12.984956649108, 0.393409217561249, -4.24200404821675},
            {49.9133974043838, 17.6484576673340, -1.78816852005472, 1.86770523912379},
            {124.714469158317, -27.3135590838199, -4.80277765111824, 2.00498567770794},
            {14.8174481590233, -1.7526150184141, -1.04538813418230, -1.17384083282033},
            {25.0758389831245, 9.96796690580462, -4.78112763617555, 2.69108186122184},
            {91.5446469773181, -22.9528777669385, 0.401983435298255, -0.736878098420183},
            {118.176327855097, 5.50757920474225, 2.71132076743689, -0.204972398473778},
            {10.4345393883043, -5.92445292066816, 3.79444682032120, 0.517867427500318}
        };
        PCA pca = PCA.fit(USArrests.x);
        pca.setProjection(4);
        assertTrue(MathEx.equals(prop, pca.varianceProportion(), 1E-7));
        assertTrue(MathEx.equals(cumprop, pca.cumulativeVarianceProportion(), 1E-7));

        for (int i = 0; i < loadings.length; i++) {
            for (int j = 0; j < loadings[i].length; j++) {
                assertEquals(Math.abs(loadings[i][j]), Math.abs(pca.loadings().get(i, j)), 1E-5);
            }
        }

        double[][] p = pca.project(USArrests.x);
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                assertEquals(Math.abs(points[i][j]), Math.abs(p[i][j]), 1E-5);
            }
        }
    }

    @Test
    public void testPCACor() {
        System.out.println("learn");
        double[] prop = {0.6200604, 0.2474413, 0.0891408, 0.04335752};
        double[] cumprop = {0.6200604, 0.8675017, 0.9566425, 1.00000000};
        double[][] loadings = {
            {0.124288601688222, -0.0969866877028367, 0.0791404742697482, -0.150572299008293},
            {0.00706888610512014, -0.00227861130898090, 0.00325028101296307, 0.00901099154845273},
            {0.0194141494466002, 0.060910660326921, 0.0263806464184195, -0.0093429458365566},
            {0.0586084532558777, 0.0180450999787168, -0.0881962972508558, -0.0096011588898465}
        };
        double[][] points = {
            {0.985565884503144, -1.13339237770997, 0.444268787550732, -0.156267144919713},
            {1.95013775033502, -1.07321325616848, -2.04000333289159, 0.438583439947191},
            {1.76316353972298, 0.745956780637292, -0.0547808243262835, 0.834652924308098},
            {-0.141420289868355, -1.11979678418355, -0.114573691853362, 0.182810896293142},
            {2.52398012651925, 1.54293398865821, -0.598556798722446, 0.341996477783837},
            {1.51456286110159, 0.987555085168417, -1.09500699201534, -0.0014648870296938},
            {-1.35864745985436, 1.08892789190617, 0.643257568830491, 0.118469414050217},
            {0.0477093090600237, 0.3253589245946, 0.71863294241999, 0.88197763723111},
            {3.01304227028722, -0.0392285132807885, 0.576829491848594, 0.0962847520332607},
            {1.63928304283591, -1.27894240278142, 0.3424600796425, -1.07679681203227},
            {-0.912657145897512, 1.57046001204312, -0.0507818939319191, -0.902806863984286},
            {-1.63979985216349, -0.210972917376817, -0.259801342130002, 0.499104100522827},
            {1.37891072442415, 0.681841188954653, 0.677495640922048, 0.122021291627908},
            {-0.505461361080942, 0.151562541804405, -0.228054838283107, -0.424665700407568},
            {-2.25364606966872, 0.104054072521579, -0.164564315384798, -0.0175559157039462},
            {-0.796881121217601, 0.270164704820617, -0.025553307209892, -0.206496427667729},
            {-0.750859074389294, -0.958440286366705, 0.02836941697898, -0.670556671464597},
            {1.56481797641247, -0.871054655078877, 0.783480358209198, -0.454728038190371},
            {-2.39682948807447, -0.376391575937656, 0.0656823864057083, 0.330459817138909},
            {1.76336938853140, -0.427655192047538, 0.157250127177974, 0.559069521095022},
            {-0.486166287394260, 1.47449649688749, 0.60949747567568, 0.179598962611096},
            {2.10844115004569, 0.155396818827412, -0.384868583991876, -0.102372018543760},
            {-1.69268181440212, 0.632261250592135, -0.153070434379820, -0.0673168849653057},
            {0.99649445921525, -2.39379598664439, 0.740808395101789, -0.215508013269186},
            {0.696787329465988, 0.263354790336906, -0.377443826869025, -0.225824461162746},
            {-1.18545190586353, -0.536874371541416, -0.246889321125754, -0.123742226859455},
            {-1.26563654112436, 0.193953729656433, -0.175573906434378, -0.0158928878025202},
            {2.87439453851442, 0.775600196040531, -1.16338049470918, -0.314515475845171},
            {-2.3839154090396, 0.0180822896942706, -0.0368553931656889, 0.0331373375836163},
            {0.181566109647703, 1.44950571194364, 0.764453551161094, -0.243382699500097},
            {1.9800237544635, -0.142848780293863, -0.183692179512918, 0.339533597231392},
            {1.68257737630865, 0.82318414195383, 0.643075093004148, 0.0134843688860191},
            {1.12337860637185, -2.2280033765179, 0.863571787545964, 0.954381667489125},
            {-2.99222561501786, -0.599118824460499, -0.301277284941070, 0.253987327309305},
            {-0.225965422407925, 0.742238237131951, 0.0311391215184618, -0.473915911390733},
            {-0.311782855015387, 0.287854205686879, 0.0153097921537548, -0.0103323207812122},
            {0.0591220767886587, 0.541411453784999, -0.939832976852491, 0.237780688081491},
            {-0.888415824070835, 0.571100352318098, 0.400628705942535, -0.359061123767106},
            {-0.8637720636083, 1.49197842268991, 1.36994569757369, 0.613569429776366},
            {1.32072380381582, -1.93340466253643, 0.300537789621535, 0.131466684662569},
            {-1.98777483728934, -0.823343241027363, -0.389293329061627, 0.109571764040277},
            {0.99974168446248, -0.86025130467328, -0.188082948982953, -0.652864290754125},
            {1.35513820680531, 0.412480819056524, 0.492068857988700, -0.643195491241997},
            {-0.55056526221282, 1.47150460831626, -0.293728036771692, 0.0823140470257147},
            {-2.80141174000027, -1.40228805517747, -0.841263094223904, 0.144889913711331},
            {-0.0963349112360505, -0.199735289142919, -0.0117125418366046, -0.211370813194815},
            {-0.216903378616043, 0.970124183070788, -0.624870937888577, 0.220847793049801},
            {-2.10858540768249, -1.42484670300488, -0.104774671146442, -0.131908830683941},
            {-2.07971416891733, 0.611268624320317, 0.138864997675415, -0.184103743036707},
            {-0.629426663525205, -0.321012967465219, 0.240659233693745, 0.166651800709434}
        };

        PCA pca = PCA.cor(USArrests.x);
        pca.setProjection(4);
        System.out.println(java.util.Arrays.toString(pca.varianceProportion()));
        assertTrue(MathEx.equals(prop, pca.varianceProportion(), 1E-7));
        assertTrue(MathEx.equals(cumprop, pca.cumulativeVarianceProportion(), 1E-7));

        for (int i = 0; i < loadings.length; i++) {
            for (int j = 0; j < loadings[i].length; j++) {
                assertEquals(Math.abs(loadings[i][j]), Math.abs(pca.loadings().get(i, j)), 1E-5);
            }
        }

        double[][] p = pca.project(USArrests.x);
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points[i].length; j++) {
                assertEquals(Math.abs(points[i][j]), Math.abs(p[i][j]), 1E-5);
            }
        }
    }
}