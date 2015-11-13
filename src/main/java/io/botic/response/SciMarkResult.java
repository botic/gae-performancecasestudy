package io.botic.response;

public class SciMarkResult {

    private double CompositeScore;
    private double FFT; // Fast Fourier Transform
    private double SOR; // Jacobi Successive Over-relaxation
    private double MonteCarlo; // Monte Carlo integration
    private double SparseMultiply; // Sparse matrix multiply
    private double LowerUpperFactorization; // dense LU matrix factorization

    public SciMarkResult() {
    }

    public SciMarkResult(double compositeScore, double FFT, double SOR, double monteCarlo, double sparseMultiply, double lowerUpperFactorization) {
        CompositeScore = compositeScore;
        this.FFT = FFT;
        this.SOR = SOR;
        MonteCarlo = monteCarlo;
        SparseMultiply = sparseMultiply;
        LowerUpperFactorization = lowerUpperFactorization;
    }

    public double getFFT() {
        return FFT;
    }

    public void setFFT(double FFT) {
        this.FFT = FFT;
    }

    public double getSOR() {
        return SOR;
    }

    public void setSOR(double SOR) {
        this.SOR = SOR;
    }

    public double getMonteCarlo() {
        return MonteCarlo;
    }

    public void setMonteCarlo(double monteCarlo) {
        MonteCarlo = monteCarlo;
    }

    public double getSparseMultiply() {
        return SparseMultiply;
    }

    public void setSparseMultiply(double sparseMultiply) {
        SparseMultiply = sparseMultiply;
    }

    public double getLowerUpperFactorization() {
        return LowerUpperFactorization;
    }

    public void setLowerUpperFactorization(double lowerUpperFactorization) {
        LowerUpperFactorization = lowerUpperFactorization;
    }

    public double getCompositeScore() {
        return CompositeScore;
    }

    public void setCompositeScore(double compositeScore) {
        CompositeScore = compositeScore;
    }
}
