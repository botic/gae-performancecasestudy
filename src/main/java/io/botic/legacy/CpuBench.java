package io.botic.legacy;

import com.google.api.server.spi.config.*;
import io.botic.response.BenchmarkResult;
import io.botic.response.LinpackResult;
import io.botic.response.SciMarkResult;
import jnt.scimark2.Random;
import jnt.scimark2.SciMarkConstants;
import jnt.scimark2.kernel;
import linpack.Linpack;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Benchmark influenced by CloudCmp:
 * https://github.com/angl/cloudcmp
 */
@Api(
    name="gaebench",
    version = "v1",
    description = "GAE Bench API",
    defaultVersion = AnnotationBoolean.TRUE
)
public class CpuBench {
    // logging
    private static final Logger log = Logger.getLogger(CpuBench.class.getName());

    // this is for security
    private static final double MIN_TIME = 1.75;

    @ApiMethod(name="jnt.scimark2", httpMethod = "GET")
    public SciMarkResult jntScimark2(@Named("minTime") double minTimeParam) {
        double minTime = Math.min(MIN_TIME, Math.abs(minTimeParam));

        int FFT_size = SciMarkConstants.LG_FFT_SIZE;
        int SOR_size =  SciMarkConstants.LG_SOR_SIZE;
        int Sparse_size_M = SciMarkConstants.LG_SPARSE_SIZE_M;
        int Sparse_size_nz = SciMarkConstants.LG_SPARSE_SIZE_nz;
        int LU_size = SciMarkConstants.LG_LU_SIZE;

        Random R = new Random(SciMarkConstants.RANDOM_SEED);
        SciMarkResult result = new SciMarkResult();

        result.setFFT(kernel.measureFFT(FFT_size, minTime, R));
        result.setSOR(kernel.measureSOR(SOR_size, minTime, R));
        result.setMonteCarlo(kernel.measureMonteCarlo(minTime, R));
        result.setSparseMultiply(kernel.measureSparseMatmult(Sparse_size_M, Sparse_size_nz, minTime, R));
        result.setLowerUpperFactorization(kernel.measureLU(LU_size, minTime, R));

        result.setCompositeScore((result.getFFT() + result.getSOR() + result.getMonteCarlo() +
            result.getSparseMultiply() + result.getLowerUpperFactorization()) / 5);

        return result;
    }

    @ApiMethod(name="linpack", httpMethod = "GET")
    public LinpackResult linpack() {
        Linpack linpack = new Linpack();
        return linpack.run_benchmark();
    }

    @ApiMethod(name="io.botic.stringbuffer", httpMethod = "GET")
    public BenchmarkResult stringBuffer(@Named("runs") int runsParam) {
        int runs = Math.min(10000000, Math.abs(runsParam));
        BenchmarkResult result = new BenchmarkResult();

        Random random = new Random();
        long startTime = System.nanoTime();
        long dummy = (long) Math.abs(System.nanoTime() / random.nextDouble());

        StringBuffer sb = new StringBuffer(0);
        int loops = 1;
        for (int i = 0; i < runs; i++) {
            dummy |= sb.append("Some String").length();
        }
        log.info("StringBuffer.append() : Strings : " + dummy);

        loops ++;
        sb = new StringBuffer(0);
        sb.append(dummy);
        for (int i = 0; i < runs; i++) {
            dummy |= sb.append(12345).length();
            dummy |= sb.append(12345.345920).length();
            dummy |= sb.append((byte) 120).length();
            dummy |= sb.append(123.123f).length();
            dummy |= sb.append(123123123123l).length();
        }
        log.info("StringBuffer.append() : Numbers : " + dummy);


        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        result.setText("StringBuffer Test with " + nf.format(runs * loops) + " iterations");
        result.setScore((System.nanoTime() - startTime) / 1000000);
        return result;
    }
}
