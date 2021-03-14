public class Spline {
	private int n;
	private double[] k;

	public Spline(double[] paramArrayOfDouble) {
		this.n = paramArrayOfDouble.length;

		int i = (this.n - 1) * 4;
		Linear localLinear = new Linear(i);

		int j = 0;

		for (int m = 0; m < this.n - 1; m++) {
			localLinear.setCell(j, 4 * m + 3, 1.0D);
			localLinear.setCell(j++, i, paramArrayOfDouble[m]);

			localLinear.setCell(j, 4 * m, 1.0D);
			localLinear.setCell(j, 4 * m + 1, 1.0D);
			localLinear.setCell(j, 4 * m + 2, 1.0D);
			localLinear.setCell(j, 4 * m + 3, 1.0D);
			localLinear.setCell(j++, i, paramArrayOfDouble[(m + 1)]);
		}

		for (int i1 = 0; i1 < this.n - 2; i1++) {
			localLinear.setCell(j, 4 * i1, 3.0D);
			localLinear.setCell(j, 4 * i1 + 1, 2.0D);
			localLinear.setCell(j, 4 * i1 + 2, 1.0D);
			localLinear.setCell(j++, 4 * i1 + 6, -1.0D);

			localLinear.setCell(j, 4 * i1, 6.0D);
			localLinear.setCell(j, 4 * i1 + 1, 2.0D);
			localLinear.setCell(j++, 4 * i1 + 5, -2.0D);
		}

		localLinear.setCell(j, 2, 1.0D);
		localLinear.setCell(j++, i, paramArrayOfDouble[1]
				- paramArrayOfDouble[0]);

		localLinear.setCell(j, i - 4, 3.0D);
		localLinear.setCell(j, i - 3, 2.0D);
		localLinear.setCell(j, i - 2, 1.0D);
		localLinear.setCell(j, i, paramArrayOfDouble[(this.n - 1)]
				- paramArrayOfDouble[(this.n - 2)]);
		localLinear.gauss();
		this.k = localLinear.getCoefficients();
	}

	public double fn(int paramInt, double paramDouble) {
		return ((this.k[(4 * paramInt)] * paramDouble + this.k[(4 * paramInt + 1)])
				* paramDouble + this.k[(4 * paramInt + 2)])
				* paramDouble + this.k[(4 * paramInt + 3)];
	}

}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Spline JD-Core Version: 0.6.2
 */