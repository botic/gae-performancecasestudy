package io.botic.response;

public class LinpackResult {
    private double mflops;
    private double time;
    private double precision;
    private double normres;

    public LinpackResult() {
    }

    public LinpackResult(double mflops, double time, double precision, double normres) {
        this.mflops = mflops;
        this.time = time;
        this.precision = precision;
        this.normres = normres;
    }

    public double getMflops() {
        return mflops;
    }

    public void setMflops(double mflops) {
        this.mflops = mflops;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getNormres() {
        return normres;
    }

    public void setNormres(double normres) {
        this.normres = normres;
    }
}
