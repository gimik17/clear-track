class Linear {
	private double[][] matrix;
	private double[] k;
	private int size;

	public Linear(int paramInt) {
		this.size = paramInt;
		this.matrix = new double[paramInt][paramInt + 1];
		this.k = new double[paramInt];
	}

	public void setCell(int paramInt1, int paramInt2, double paramDouble) {
		this.matrix[paramInt1][paramInt2] = paramDouble;
	}

	public void gauss() {
		int j;
		double d;
		for (int i = 0; i < this.size - 1; i++) {
			j = i;
			while ((this.matrix[j][i] == 0.0D) && (j < this.size - 1))
				j++;

			if (this.matrix[j][i] != 0.0D) {
				int m;
				if (i != j) {
					for (m = i; m < this.size + 1; m++) {
						d = this.matrix[j][m];
						this.matrix[j][m] = this.matrix[i][m];
						this.matrix[i][m] = d;
					}

				}

				for (j = i + 1; j < this.size; j++) {
					if (this.matrix[j][i] != 0.0D) {
						d = -this.matrix[j][i] / this.matrix[i][i];
						this.matrix[j][i] = 0.0D;
						for (m = i + 1; m < this.size + 1; m++) {
							this.matrix[j][m] += this.matrix[i][m] * d;
						}
					}
				}
			}

		}

		for (int i = this.size - 1; i >= 0; i--) {
			d = 0.0D;
			for (j = this.size - 1; j > i; j--)
				d += this.matrix[i][j] * this.k[j];
			this.k[i] = ((this.matrix[i][this.size] - d) / this.matrix[i][i]);
		}
	}

	public static void main(String[] paramArrayOfString) {
		Linear localLinear = new Linear(3);

		localLinear.setCell(0, 0, 1.0D);
		localLinear.setCell(0, 1, 1.0D);
		localLinear.setCell(0, 2, 1.0D);
		localLinear.setCell(0, 3, 2.0D);

		localLinear.setCell(1, 0, 2.0D);
		localLinear.setCell(1, 1, 4.0D);
		localLinear.setCell(1, 2, 3.0D);
		localLinear.setCell(1, 3, -1.0D);

		localLinear.setCell(2, 0, 3.0D);
		localLinear.setCell(2, 1, -1.0D);
		localLinear.setCell(2, 2, 4.0D);
		localLinear.setCell(2, 3, 7.0D);
		localLinear.gauss();
	}

	public double[] getCoefficients() {
		return this.k;
	}
}

/*
 * Location: C:\Users\kurt\Desktop\Cleartrack\ClearTrack.jar Qualified Name:
 * Linear JD-Core Version: 0.6.2
 */